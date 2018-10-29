package com.kazes.fallout.test.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.*;
import com.kazes.fallout.test.actions.*;
import com.kazes.fallout.test.enemies.FastZombie;
import com.kazes.fallout.test.enemies.Zombie;
import com.kazes.fallout.test.items.AmmoCrate;
import com.kazes.fallout.test.items.ItemActor;
import com.kazes.fallout.test.items.Weapons;
import com.kazes.fallout.test.stories.ActorAction;
import com.kazes.fallout.test.stories.CutsceneManager;
import com.kazes.fallout.test.stories.Stories;
@Deprecated
public class Chapter1 extends GameScreen {
    Actor camFollow;
    CutsceneManager cutscene;
    CamFollowActor currentFollow;
    FastZombie zombie;
    boolean batchTwo = false;
    boolean batchThree = false;
    boolean batchFour = false;
    boolean batchFive = false;

    Chapter1(Survivor game, float startingPosX) {
        super(game, "Chapter1", startingPosX);
        allowInput = false;
        this.completed = false;
        nextScreen = Screens.Chapter2;
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
        cutscene.add(camFollow, Actions.sequence(Actions.delay(3f), new ShowDialogue(dialogueManager, "first_chapter_start"), new CheckDialogueAction(dialogueManager)));
        cutscene.add(player, Actions.sequence(Actions.parallel(Actions.moveTo(npcs.getChildren().items[0].getX() - 4 - player.getWidth(), 4, 5f), new ChangeAnimation(Assets.Animations.HERO + "_walking")), new ChangeAnimation(Assets.Animations.HERO + "_idle")));
        cutscene.add(player, new ShowDialogue(dialogueManager, "friend"));
        cutscene.add(npcs.getChildren().get(0), Actions.sequence(new CheckDialogueAction(dialogueManager), new XFlipAction()));
        cutscene.add(npcs.getChildren().get(0), Actions.sequence(new ShowDialogue(dialogueManager, "help"), new CheckDialogueAction(dialogueManager), Actions.delay(1f), new SaveAction()));
        cutscene.add(player, Actions.sequence(new ShowDialogue(dialogueManager, "new_beginnings"), new CheckDialogueAction(dialogueManager), Actions.delay(1f)));
        cutscene.add(currentFollow, Actions.sequence(new FollowActorAction(player), new ChangeInputPrivilege(this, true)));

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(!Stories.isFinished(1)) {
            if (!cutscene.isEmpty() &&
                    !cutscene.peekFirst().assignedActor.hasActions() &&
                    !cutscene.getLastUsedActor().hasActions()) {
                ActorAction action = cutscene.take();
                action.assignedActor.addAction(action.action);
            }
            if (player.getX() > 55)
                batch_two();
            if (player.getX() > 80)
                batch_three();

            if (this.enemies.getChildren().size == 0)
                batch_four();

            if (batchFour) {
                if (zombie.getHealth() < 1)
                    batch_five();
            }
        }
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
        parallaxBackground = new ParallaxBackground(parallaxTextures, gameStage.getCamera());
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
        for(int i = 0; i < 5; i++)
            enemies.addActor(new Zombie(MathUtils.random(100, 115), MathUtils.random(5f), world));
    }

    @Override
    public void setItems() {
        items.addActor(new ItemActor(new AmmoCrate(), 60, 6));
    }

    private void batch_two() {
        if(!batchTwo) {
            batchTwo = true;
            cutscene.add(player, new ChangeInputPrivilege(this, false));
            cutscene.add(player, Actions.sequence(new ChangeAnimation(Assets.Animations.HERO + "_idle"), Actions.delay(1.5f), Actions.parallel(new ChangeAnimation(Assets.Animations.HERO + "_walking"), Actions.moveTo(items.getChildren().get(0).getX() - player.getWidth() / 2, items.getChildren().get(0).getY() - player.getHeight() / 2, 1f, Interpolation.sine)), new ChangeAnimation(Assets.Animations.HERO + "_idle")));
            cutscene.add(player, new ShowDialogue(dialogueManager, "ammo"));
            cutscene.add(player, Actions.sequence(new CheckDialogueAction(dialogueManager)));
            cutscene.add(player, Actions.sequence(new PickItem(fastInventoryActor, (ItemActor)items.getChildren().get(0), 2), new AddNotification("Ammo bag picked up")));
            cutscene.add(player, new ChangeInputPrivilege(this, true));
        }
    }

    private void batch_three() {
        if(!batchThree) {
            batchThree = true;
            cutscene.add(player, new ChangeInputPrivilege(this, false));
            cutscene.add(player, new ChangeAnimation(Assets.Animations.HERO + "_idle"));
            cutscene.add(camFollow, Actions.moveTo(player.getX(), player.getY()));
            cutscene.add(currentFollow, new FollowActorAction(camFollow));
            cutscene.add(camFollow, Actions.moveBy(17, 0, 2f, Interpolation.sine));
            cutscene.add(player, Actions.sequence(new ShowDialogue(dialogueManager, "problem")));
            cutscene.add(player, Actions.sequence(new CheckDialogueAction(dialogueManager)));
            cutscene.add(currentFollow, Actions.sequence(new FollowActorAction(player), new ChangeInputPrivilege(this, true)));
            cutscene.add(player, new ChangeWeaponPrivilege(this, true));
        }
    }

    private void batch_four() {
        if(!batchFour) {
            batchFour = true;
            cutscene.add(player, Actions.parallel(new ChangeInputPrivilege(this, false), new ChangeWeaponPrivilege(this, false)));
            cutscene.add(player, new ShowDialogue(dialogueManager, "gone"));
            cutscene.add(player, new CheckDialogueAction(dialogueManager));
            zombie = new FastZombie(79, 0, world);
            zombie.addInteractingObject(player);
            zombie.setHealth(250f);
            cutscene.add(enemies, new AddObject(zombie));
            cutscene.add(zombie, Actions.sequence(Actions.moveBy(3f, 1f, 2f, Interpolation.sine), Actions.delay(.5f),
                    Actions.moveBy(2f, -0.5f, 2f, Interpolation.sine), Actions.delay(0.5f),
                    Actions.moveBy(5f, 1f, 4f, Interpolation.sine), Actions.delay(0.5f),
                    Actions.moveBy(2.5f, 2f, 4f, Interpolation.sine), Actions.delay(0.5f),
                    Actions.moveBy(1f, 0, 1.4f, Interpolation.sine)));
            cutscene.add(player, new ShowDialogue(dialogueManager, "from_the_frying_pan"));
            cutscene.add(player, Actions.parallel(new ChangeInputPrivilege(this, true), new ChangeWeaponPrivilege(this, true)));
        }
    }

    private void batch_five() {
        if(!batchFive) {
            batchFive = true;
            completed = true;
            cutscene.add(player, Actions.parallel(new ChangeInputPrivilege(this, false), new ChangeWeaponPrivilege(this, false)));
            cutscene.add(player, new ShowDialogue(dialogueManager, "the_white_rider"));
            cutscene.add(player, Actions.sequence(new CheckDialogueAction(dialogueManager), Actions.delay(1f), new ChangeAnimation(Assets.Animations.HERO + "_walking"),  Actions.moveTo(map.getWidth(), player.getY(), 4f, Interpolation.pow2)));
            cutscene.add(player, Actions.parallel(new ChangeInputPrivilege(this, true), new ChangeWeaponPrivilege(this, true)));
            cutscene.add(player, new CompleteStory(1));
        }
    }
}
