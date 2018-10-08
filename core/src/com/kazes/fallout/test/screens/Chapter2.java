package com.kazes.fallout.test.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.*;
import com.kazes.fallout.test.actions.*;
import com.kazes.fallout.test.enemies.Enemy;
import com.kazes.fallout.test.enemies.Zombie;
import com.kazes.fallout.test.items.AmmoCrate;
import com.kazes.fallout.test.items.ItemActor;
import com.kazes.fallout.test.items.Weapons;

public class Chapter2 extends GameScreen {
    Actor camFollow;
    CutsceneManager cutscene;
    CamFollowActor currentFollow;

    boolean batchTwo = false;
    boolean batchThree = false;
    boolean batchFour = false;
    float timeout;
    Label timeLabel;

    Chapter2(Survivor game, float startingPosX) {
        super(game, "Chapter2", startingPosX);
        allowInput = false;
        this.completed = false;
        nextScreen = Screens.Tribe;
        dialogueManager.dialogue.loadFile(Assets.Dialogues.CHAPTER2, false, false, null);
        player.setX(0);
        camFollow = new Actor();
        camFollow.setX(0);
        camFollow.setY(player.getY());
        gameStage.addActor(camFollow);
        currentFollow = new CamFollowActor(camFollow);
        gameStage.addActor(currentFollow);
        cutscene = new CutsceneManager();

        cutscene.add(camFollow, Actions.moveTo(npcs.getChildren().get(0).getX(), currentFollow.getY(), 4f, Interpolation.sine));
        cutscene.add(camFollow, Actions.parallel(Actions.delay(0.5f), new ShowDialogue(dialogueManager, "second_chapter_start")));
        cutscene.add(camFollow, Actions.sequence(new CheckDialogAction(dialogueManager), Actions.delay(0.2f), Actions.moveTo(player.getX(), player.getY(), 4f, Interpolation.sine)));
        cutscene.add(currentFollow, Actions.parallel(new FollowActorAction(player), new ChangeInputPrivilege(this, true), new ChangeWeaponPrivilege(this, true)));
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (!cutscene.isEmpty() &&
                !cutscene.peekFirst().assignedActor.hasActions() &&
                !cutscene.getLastUsedActor().hasActions()) {
            ActorAction action = cutscene.take();
            action.assignedActor.addAction(action.action);
        }

        if(player.getX() > 47)
            batch_two();
        if(batchTwo) {
            if(enemies.getChildren().size == 0) {
                batch_three();
            }
        }
        if(batchThree && !batchFour) {
            timeLabel.setText("Time left: " + MathUtils.floor(timeout-- / Gdx.graphics.getFramesPerSecond()));
            if(enemies.getChildren().size < 25) {
                enemies.addActor(new Zombie(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), MathUtils.random(map.getWidth() - 2, map.getWidth()-0.0001f), MathUtils.random(0, 8), world));
                ((Zombie)enemies.getChildren().get(enemies.getChildren().size - 1)).setHealth(99);
                ((Zombie)enemies.getChildren().get(enemies.getChildren().size - 1)).addInteractingObject(player);
            }
            if(timeout < 0) {
                screenStage.getActors().removeValue(timeLabel, false);
                batch_four();
            }
        }
        if(batchFour) {

        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ((SideScrollingCamera) gameStage.getCamera()).followPos(new Vector2(currentFollow.getActor().getX(), currentFollow.getActor().getY()));
    }

    public void batch_two() {
        if(!batchTwo) {
            batchTwo = true;
            cutscene.add(player, Actions.parallel(new ChangeWeaponPrivilege(this, false), new ChangeInputPrivilege(this, false)));
            cutscene.add(player, Actions.sequence(new ChangeAnimation(Assets.Animations.HERO + "_idle"), Actions.delay(0.5f), new ChangeAnimation(Assets.Animations.HERO + "_walking"),  Actions.moveTo(npcs.getChildren().get(0).getX() - 3, npcs.getChildren().get(0).getY(), 1.5f, Interpolation.pow2), new ChangeAnimation(Assets.Animations.HERO + "_idle")));
            cutscene.add(player, new ShowDialogue(dialogueManager, "help_on_the_way"));
            cutscene.add(player, new CheckDialogAction(dialogueManager));
            cutscene.add(camFollow, Actions.sequence(Actions.moveTo(player.getX(), player.getY()), Actions.delay(0.2f)));
            cutscene.add(currentFollow, new FollowActorAction(camFollow));
            cutscene.add(camFollow, Actions.moveTo(80, camFollow.getY(), 4f, Interpolation.sine));
            cutscene.add(camFollow, Actions.sequence(Actions.delay(1.5f)));
            cutscene.add(currentFollow, Actions.sequence(new FollowActorAction(player), new ChangeInputPrivilege(this, true), new ChangeWeaponPrivilege(this, true)));
        }
    }

    public void batch_three() {
        if(!batchThree) {
            batchThree = true;
            cutscene.add(player, Actions.parallel(new ChangeWeaponPrivilege(this, false), new ChangeInputPrivilege(this, false)));
            cutscene.add(player, new ChangeAnimation(Assets.Animations.HERO + "_idle"));
            cutscene.add(player, new ShowDialogue(dialogueManager, "we_are_coming"));
            cutscene.add(player, Actions.parallel(new ChangeWeaponPrivilege(this, true), new ChangeInputPrivilege(this, true)));
            timeout = Gdx.graphics.getFramesPerSecond()  * 60;
            timeLabel = new Label("Time left: " + (timeout / Gdx.graphics.getFramesPerSecond()), Assets.getAsset(Assets.UI_SKIN, Skin.class));
            timeLabel.setPosition(Gdx.graphics.getWidth() / 2 - timeLabel.getWidth() / 2, Gdx.graphics.getHeight() -  timeLabel.getHeight() / 2);
            cutscene.add(screenStage.getRoot(), new AddObject(timeLabel));
        }
    }

    public void batch_four() {
        if(!batchFour) {
            batchFour = true;
            camFollow.setPosition(player.getX(), player.getY());
            cutscene.add(player, Actions.parallel(new ChangeWeaponPrivilege(this, false), new ChangeInputPrivilege(this, false)));
            cutscene.add(currentFollow, new FollowActorAction(camFollow));
            cutscene.add(player, new ShowDialogue(dialogueManager, "help_arrived"));
            cutscene.add(camFollow, Actions.sequence(new CheckDialogAction(dialogueManager), Actions.moveTo(0, camFollow.getY(), 4, Interpolation.sine)));
            cutscene.add(player, Actions.parallel(new ChangeWeaponPrivilege(this, true), new ChangeInputPrivilege(this, true)));
            cutscene.add(npcs, new AddObject(new NPC(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), "General", 5, 5, Weapons.Pistol)));
            cutscene.add(npcs, new AddObject(new NPC(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), "Soldier 1", 4, 3, Weapons.Pistol)));
            cutscene.add(npcs, new AddObject(new NPC(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), "Soldier 2", 4.5f, 6, Weapons.Pistol)));

        }
    }

    @Override
    public void setMap() {
        Array<Texture> parallaxTextures = new Array<Texture>();
        for (int i = 0; i < 6; i++) {
            parallaxTextures.add(Assets.getAsset(Assets._Parallax1[i], Texture.class));
            parallaxTextures.get(i).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);

        }
        parallaxBackground = new ParallaxBackground(parallaxTextures, gameStage.getCamera());
        //parallaxBackground.setSize(Survivor.getInMeters(Gdx.graphics.getWidth() / 2),Survivor.getInMeters(Gdx.graphics.getHeight() / 2));

        map = new ImageEx(game.assetManager.get(Assets.Images.MAP, Texture.class), 0, 0);
    }

    @Override
    public void setDecor() {

    }

    @Override
    public void setNPCS() {
        npcs.addActor(new InjuredNPC(world, 57, 3, Weapons.NULL));
        npcs.addActor(new InjuredNPC(world, 58, 3, Weapons.NULL));
        ((ImageEx)npcs.getChildren().get(1)).setXflip(true);
    }

    @Override
    public void setEnemies() {
        for (int i = 0; i < 10; i++) {
            enemies.addActor(new Zombie(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), MathUtils.random(75, 90), MathUtils.random(5f), world));
            ((Enemy)enemies.getChildren().get(i)).addInteractingObject(player);
            ((Enemy)enemies.getChildren().get(i)).addInteractingObject(npcs.getChildren().get(0));
            ((Enemy)enemies.getChildren().get(i)).addInteractingObject(npcs.getChildren().get(1));
        }
    }

    @Override
    public void setItems() {
        items.addActor(new ItemActor(new AmmoCrate(), 60, 6));
    }
}


