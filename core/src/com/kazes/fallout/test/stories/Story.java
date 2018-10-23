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

public abstract class Story implements Disposable, StoryProperties {
    int chapter;
    boolean finished;
    CutsceneManager cutscene;
    GameScreen gameScreen;
    Actor camFollow;
    CamFollowActor currentFollow;

    Array<NPC> storyNpcs;
    Array<Enemy> storyEnemies;
    Array<ImageEx> storyDecoration;
    Array<ItemActor> storyItems;

    boolean[] parts;
    private boolean[] isPartAdded;
    String dialogueFile;

    public Story(int chapterNum) {

        this.chapter = chapterNum;
        Gdx.app.log("chapter",this.chapter + "");

        cutscene = new CutsceneManager();

        currentFollow = new CamFollowActor(GameScreen.player);
        camFollow = new Actor();

        storyNpcs = new Array<NPC>();
        storyEnemies = new Array<Enemy>();
        storyDecoration = new Array<ImageEx>();
        storyItems = new Array<ItemActor>();
    }

    public void updateScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void update() {
                if (!cutscene.isEmpty() &&
                        !cutscene.peekFirst().assignedActor.hasActions() &&
                        !cutscene.getLastUsedActor().hasActions()) {
                    ActorAction action = cutscene.take();
                    action.assignedActor.addAction(action.action);

        }
    }

    public void render() {
        if(currentFollow.getActor() != null)
            ((SideScrollingCamera)gameScreen.getGameStage().getCamera()).followPos(new Vector2(currentFollow.getActor().getX(), currentFollow.getActor().getY()));
    }

    public void updateAndRender() {
        update();
        render();
    }

    public void updatePart(int part) {
        if(parts != null && parts.length > part - 1)
            parts[part - 1] = true;
    }

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
