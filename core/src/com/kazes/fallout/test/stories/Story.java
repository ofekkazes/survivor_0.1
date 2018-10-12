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

public abstract class Story implements Disposable {
    int chapter;
    boolean disposed;
    CutsceneManager cutscene;
    GameScreen gameScreen;
    Actor camFollow;
    CamFollowActor currentFollow;

    Array<NPC> storyNpcs;
    Array<Enemy> storyEnemies;
    Array<ImageEx> storyDecoration;
    Array<ItemActor> storyItems;

    public Story(GameScreen gameScreen, int chapterNum) {
        if(Stories.isFinished(chapterNum)) {
            disposed = true;
            return;
        }
        this.gameScreen = gameScreen;
        this.chapter = chapterNum;
        Gdx.app.log("chapter",this.chapter + "");
        Gdx.app.log("Complete?", Stories.isFinished(chapter) + "");

        currentFollow = new CamFollowActor(GameScreen.player);
        camFollow = new Actor();

        storyNpcs = new Array<NPC>();
        storyEnemies = new Array<Enemy>();
        storyDecoration = new Array<ImageEx>();
        storyItems = new Array<ItemActor>();
    }

    public void update() {
        if(!this.disposed) {
            if (!Stories.isFinished(chapter)) {
                if (!cutscene.isEmpty() &&
                        !cutscene.peekFirst().assignedActor.hasActions() &&
                        !cutscene.getLastUsedActor().hasActions()) {
                    ActorAction action = cutscene.take();
                    action.assignedActor.addAction(action.action);
                }
            } else
                this.dispose();
        }
    }

    public void render() {
        if(!disposed)
            ((SideScrollingCamera)gameScreen.getGameStage().getCamera()).followPos(new Vector2(currentFollow.getActor().getX(), currentFollow.getActor().getY()));

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

        disposed = true;
    }


    public int getChapter() {
        return chapter;
    }

    public boolean isDisposed() {
        return disposed;
    }
}
