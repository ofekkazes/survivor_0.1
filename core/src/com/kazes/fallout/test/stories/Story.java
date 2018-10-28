package com.kazes.fallout.test.stories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.kazes.fallout.test.*;
import com.kazes.fallout.test.enemies.Enemy;
import com.kazes.fallout.test.items.ItemActor;
import com.kazes.fallout.test.screens.GameScreen;

interface StoryProperties {
    void setup();
    void update();
    void render();
}

/**
 * Story is a collection of parts, each manages a storytelling variables
 * @author Ofek Kazes
 */
public abstract class Story implements Disposable, StoryProperties {
    int chapter; //The chapter number of the story
    boolean finished; //Is the story done?
    CutsceneManager cutscene; //Manager of storytelling variables
    GameScreen gameScreen; //the screen to show the story on
    Actor camFollow; //A base actor used to move the camera around
    CamFollowActor currentFollow; //The actor the camera follows

    Array<NPC> storyNpcs; //npcs used in the story
    Array<Enemy> storyEnemies; //enemies used in the story
    Array<ImageEx> storyDecoration; //decoration used in the story
    Array<ItemActor> storyItems; //items used in the story

    boolean[] parts; //part length of a story
    private boolean[] isPartAdded; //manages added parts
    String dialogueFile; //Dialogue file to load

    public Story(int chapterNum) {

        this.chapter = chapterNum;

        cutscene = new CutsceneManager();

        currentFollow = new CamFollowActor(GameScreen.player);
        camFollow = new Actor();

        storyNpcs = new Array<NPC>();
        storyEnemies = new Array<Enemy>();
        storyDecoration = new Array<ImageEx>();
        storyItems = new Array<ItemActor>();
    }

    /**
     * Add dialogue functions
     */
    public void addFunctions() {
        
    }

    /**
     *
     * @param gameScreen the screen to update the story on
     */
    public void updateScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    /**
     * Update the cutscene manager
     */
    public void update() {
                if (!cutscene.isEmpty() &&
                        !cutscene.peekFirst().assignedActor.hasActions() &&
                        !cutscene.getLastUsedActor().hasActions()) {
                    ActorAction action = cutscene.take();
                    action.assignedActor.addAction(action.action);

        }
    }

    /**
     * Draws the frame
     */
    public void render() {
        if(currentFollow.getActor() != null)
            ((SideScrollingCamera)gameScreen.getGameStage().getCamera()).followPos(new Vector2(currentFollow.getActor().getX(), currentFollow.getActor().getY()));
        else ((SideScrollingCamera)gameScreen.getGameStage().getCamera()).followPos(GameScreen.player.getOrigin());
    }

    /**
     * Update, then draws the frame
     */
    public void updateAndRender() {
        update();
        render();
    }

    /**
     * Ends parts
     * @param part the part to end
     */
    public void updatePart(int part) {
        if(parts != null && parts.length > part - 1)
            parts[part - 1] = true;
    }

    /**
     * Add the part to the story
     * @param part which part to add
     * @return whether a part can be added by the super class
     */
    public boolean addPartToStory(int part) {
        if(parts != null && parts.length > part - 1 && !parts[part - 1]) {
            if(part != 1 && !parts[part - 2])
                return false;
            if (isPartAdded == null)
                isPartAdded = new boolean[parts.length];
            if (!isPartAdded[part - 1]) {
                isPartAdded[part - 1] = true;
                return true;
            } else return false;
        }
        return false;
    }

    /**
     * Check if a part has ended
     * @param part part number in the story
     * @return true if a part has ended, false otherwise
     */
    public boolean checkPart(int part) {
        return (parts != null && parts.length > part - 1 && parts[part - 1]);
    }

    public void addNPC(NPC npc) {
        this.storyNpcs.add(npc);
        gameScreen.getNpcs().addActor(npc);
    }

    public void addEnemy(Enemy enemy) {
        this.storyEnemies.add(enemy);
        gameScreen.getEnemies().addActor(enemy);
    }

    public void addDecoration(ImageEx image) {
        this.storyDecoration.add(image);
        gameScreen.getDecor().addActor(image);
    }

    public void addItem(ItemActor item) {
        this.storyItems.add(item);
        gameScreen.getItems().addActor(item);
    }

    @Override
    public void dispose() {
        for(NPC npc : storyNpcs) {
            gameScreen.getNpcs().removeActor(npc);
            storyNpcs.removeValue(npc, false);
        }
        for(Enemy enemy : storyEnemies) {
            gameScreen.getEnemies().removeActor(enemy);
            storyEnemies.removeValue(enemy, false);
        }
        for(ImageEx decoration : storyItems) {
            gameScreen.getDecor().removeActor(decoration);
            storyDecoration.removeValue(decoration, false);
        }
        for(ItemActor item : storyItems) {
            gameScreen.getItems().removeActor(item);
            storyItems.removeValue(item, false);
        }
    }


    public int getChapter() {
        return chapter;
    }

    public boolean isFinished() {
        return finished;
    }
}
