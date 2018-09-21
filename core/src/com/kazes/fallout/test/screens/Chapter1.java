package com.kazes.fallout.test.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.*;
import com.kazes.fallout.test.actions.*;

public class Chapter1 extends GameScreen {
    Actor camFollow;
    CutsceneManager cutscene;
    CamFollowActor currentFollow;
    boolean batchTwo = false;

    Chapter1(Survivor game, float startingPosX) {
        super(game, "Chapter1", startingPosX);
        allowInput = false;
        nextScreen = Screens.Battlegrounds;
        dialogueManager.dialogue.loadFile(Assets.Dialogues.CHAPTER1, false, false, null);



        camFollow = new Actor();
        camFollow.setX(0);
        camFollow.setY(player.getY());
        gameStage.addActor(camFollow);
        currentFollow = new CamFollowActor(camFollow);
        gameStage.addActor(currentFollow);
        cutscene = new CutsceneManager();
        cutscene.add(camFollow, Actions.moveTo(npcs.getChildren().items[0].getX(), 3.5f, 5f, Interpolation.sine));
        cutscene.add(npcs.getChildren().get(0), Actions.repeat(4, Actions.moveBy(-1f, 0, 1.5f, Interpolation.sine)));
        cutscene.add(camFollow, Actions.sequence(Actions.delay(4f), new ShowDialogue(dialogueManager, "first_chapter_start")));
        cutscene.add(player, Actions.sequence(Actions.parallel(Actions.moveTo(npcs.getChildren().items[0].getX() - 4 - player.getWidth(), 4, 5f), new ChangeAnimation(Assets.Animations.HERO + "_walking")), new ChangeAnimation(Assets.Animations.HERO + "_idle")));
        cutscene.add(player, new ShowDialogue(dialogueManager, "friend"));
        cutscene.add(npcs.getChildren().get(0), Actions.sequence(new CheckDialogAction(dialogueManager), new XFlipAction()));
        cutscene.add(npcs.getChildren().get(0), Actions.sequence(new ShowDialogue(dialogueManager, "help"), new CheckDialogAction(dialogueManager), Actions.delay(1f), new SaveAction()));
        cutscene.add(player, Actions.sequence(new ShowDialogue(dialogueManager, "new_beginnings"), new CheckDialogAction(dialogueManager), Actions.delay(1f)));
        cutscene.add(currentFollow, Actions.sequence(new FollowActorAction(player), new ChangeInputPrivilege(this, true)));

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
        if(player.getX() > 60)
            batch_two();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ((SideScrollingCamera)gameStage.getCamera()).followPos(new Vector2(currentFollow.getActor().getX(), currentFollow.getActor().getY()));
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
        npcs.addActor(new InjuredNPC(world , 50, 3, Weapons.NULL));
    }

    @Override
    public void setEnemies() {

    }

    @Override
    public void setItems() {

    }

    private void batch_two() {
        if(!batchTwo) {
            batchTwo = true;
            cutscene.add(player, new ChangeInputPrivilege(this, false));
            cutscene.add(camFollow, Actions.moveTo(player.getX(), player.getY()));
            cutscene.add(currentFollow, new FollowActorAction(camFollow));
            cutscene.add(camFollow, Actions.moveBy(17, 0, 2f, Interpolation.sine));
            cutscene.add(player, Actions.sequence(new ChangeAnimation(Assets.Animations.HERO + "_idle"), new ShowDialogue(dialogueManager, "problem")));
            cutscene.add(player, Actions.sequence(new CheckDialogAction(dialogueManager)));
            cutscene.add(currentFollow, Actions.sequence(new FollowActorAction(player), new ChangeInputPrivilege(this, true)));
        }
    }
}
