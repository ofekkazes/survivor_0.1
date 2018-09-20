package com.kazes.fallout.test.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.*;
import com.kazes.fallout.test.actions.ChangeAnimation;
import com.kazes.fallout.test.actions.CheckDialogAction;
import com.kazes.fallout.test.actions.ShowDialogue;
import com.kazes.fallout.test.actions.XFlipAction;

public class Chapter1 extends GameScreen {
    Actor camFollow;
    CutsceneManager cutscene;

    Chapter1(Survivor game, float startingPosX) {
        super(game, "Chapter1", startingPosX);
        allowInput = false;
        nextScreen = Screens.Battlegrounds;
        dialogueManager.dialogue.loadFile(Assets.Dialogues.CHAPTER1, false, false, null);

        camFollow = new Actor();
        camFollow.setX(0);
        camFollow.setY(player.getY());
        gameStage.addActor(camFollow);
        cutscene = new CutsceneManager();
        cutscene.add(camFollow, Actions.moveTo(npcs.getChildren().items[0].getX(), 3.5f, 5f, Interpolation.sine));
        cutscene.add(camFollow, Actions.sequence(Actions.delay(4f), new ShowDialogue(dialogueManager, "first_chapter_start")));
        cutscene.add(player, Actions.sequence(Actions.parallel(Actions.moveTo(npcs.getChildren().items[0].getX() - player.getWidth(), 4, 5f), new ChangeAnimation(player, Assets.Animations.HERO + "_walking")), new ChangeAnimation(player, Assets.Animations.HERO + "_idle")));
        cutscene.add(player, new ShowDialogue(dialogueManager, "friend"));
        cutscene.add(npcs.getChildren().get(0), Actions.sequence(new CheckDialogAction(dialogueManager), new XFlipAction((ImageEx)npcs.getChildren().get(0))));
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if(!cutscene.isEmpty() &&
                !cutscene.peekFirst().assignedActor.hasActions() &&
                !cutscene.getLastUsedActor().hasActions()) {
            ActorAction action = cutscene.take();
            action.assignedActor.addAction(action.action);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ((SideScrollingCamera)gameStage.getCamera()).followPos(new Vector2(camFollow.getX(), camFollow.getY()));
    }

    @Override
    public void setMap() {
        Array<Texture> parallaxTextures = new Array<Texture>();
        for(int i = 0; i < 6;i++){
            parallaxTextures.add(Assets.getAsset(Assets._Parallax1[i], Texture.class));
            parallaxTextures.get(i).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);

        }
        parallaxBackground = new ParallaxBackground(parallaxTextures, gameStage.getCamera().viewportWidth, gameStage.getCamera().viewportHeight);
        //parallaxBackground.setSize(Survivor.getInMeters(Gdx.graphics.getWidth() / 2),Survivor.getInMeters(Gdx.graphics.getHeight() / 2));

        map = new ImageEx(game.assetManager.get(Assets.Images.MAP, Texture.class), 0, 0);
    }

    @Override
    public void setDecor() {

    }

    @Override
    public void setNPCS() {
        npcs.addActor(new NPC(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), "Essi", 50, 3, Weapons.NULL));
    }

    @Override
    public void setEnemies() {

    }

    @Override
    public void setItems() {

    }
}
