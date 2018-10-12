package com.kazes.fallout.test.stories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.kazes.fallout.test.Assets;
import com.kazes.fallout.test.CamFollowActor;
import com.kazes.fallout.test.InjuredNPC;
import com.kazes.fallout.test.actions.*;
import com.kazes.fallout.test.enemies.FastZombie;
import com.kazes.fallout.test.enemies.Zombie;
import com.kazes.fallout.test.items.AmmoCrate;
import com.kazes.fallout.test.items.ItemActor;
import com.kazes.fallout.test.items.Weapons;
import com.kazes.fallout.test.screens.GameScreen;

public class Chapter1 extends Story {

    FastZombie zombie;
    boolean batchTwo = false;
    boolean batchThree = false;
    boolean batchFour = false;
    boolean batchFive = false;

    public Chapter1(GameScreen gameScreen) {
        super(gameScreen, 1);
        gameScreen.getDialogueManager().dialogue.loadFile(Assets.Dialogues.CHAPTER1, false, false, null);

        if(disposed)
            return;
        addItem(new ItemActor(new AmmoCrate(), 60, 6));
        addNPC(new InjuredNPC(gameScreen.getPhysicsWorld() , 50, 3, Weapons.NULL));
        for(int i = 0; i < 5; i++)
            addEnemy(new Zombie(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), MathUtils.random(100, 115), MathUtils.random(5f), gameScreen.getPhysicsWorld()));

        camFollow.setX(0);
        camFollow.setY(GameScreen.player.getY());
        gameScreen.getGameStage().addActor(camFollow);
        currentFollow = new CamFollowActor(camFollow);
        gameScreen.getGameStage().addActor(currentFollow);
        cutscene = new CutsceneManager();
        cutscene.add(camFollow, Actions.moveTo(this.storyNpcs.get(0).getX(), 3.5f, 5f, Interpolation.sine));
        cutscene.add(this.storyNpcs.get(0), Actions.repeat(4, Actions.moveBy(-1f, 0, 1.5f, Interpolation.sine)));
        cutscene.add(camFollow, Actions.sequence(Actions.delay(3f), new ShowDialogue(gameScreen.getDialogueManager(), "first_chapter_start"), new CheckDialogAction(gameScreen.getDialogueManager())));
        cutscene.add(GameScreen.player, Actions.sequence(Actions.parallel(Actions.moveTo(this.storyNpcs.get(0).getX() - 4 - GameScreen.player.getWidth(), 4, 5f), new ChangeAnimation(Assets.Animations.HERO + "_walking")), new ChangeAnimation(Assets.Animations.HERO + "_idle")));
        cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "friend"));
        cutscene.add(this.storyNpcs.get(0), Actions.sequence(new CheckDialogAction(gameScreen.getDialogueManager()), new XFlipAction()));
        cutscene.add(this.storyNpcs.get(0), Actions.sequence(new ShowDialogue(gameScreen.getDialogueManager(), "help"), new CheckDialogAction(gameScreen.getDialogueManager()), Actions.delay(1f), new SaveAction()));
        cutscene.add(GameScreen.player, Actions.sequence(new ShowDialogue(gameScreen.getDialogueManager(), "new_beginnings"), new CheckDialogAction(gameScreen.getDialogueManager()), Actions.delay(1f)));
        cutscene.add(currentFollow, Actions.sequence(new FollowActorAction(GameScreen.player), new ChangeInputPrivilege(gameScreen, true)));

    }

    @Override
    public void update() {
        super.update();

        if (GameScreen.player.getX() > 55)
            batch_two();
        if (GameScreen.player.getX() > 80)
            batch_three();

        if (this.gameScreen.getEnemies().getChildren().size == 0)
            batch_four();

        if (batchFour) {
            if (zombie.getHealth() < 1)
                batch_five();
        }
    }

    @Override
    public void render() {
        super.render();

    }

    private void batch_two() {
        if(!batchTwo) {
            batchTwo = true;
            cutscene.add(GameScreen.player, new ChangeInputPrivilege(gameScreen, false));
            cutscene.add(GameScreen.player, Actions.sequence(new ChangeAnimation(Assets.Animations.HERO + "_idle"), Actions.delay(1.5f), Actions.parallel(new ChangeAnimation(Assets.Animations.HERO + "_walking"), Actions.moveTo(storyItems.get(0).getX() - GameScreen.player.getWidth() / 2, storyItems.get(0).getY() - GameScreen.player.getHeight() / 2, 1f, Interpolation.sine)), new ChangeAnimation(Assets.Animations.HERO + "_idle")));
            cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "ammo"));
            cutscene.add(GameScreen.player, Actions.sequence(new CheckDialogAction(gameScreen.getDialogueManager())));
            cutscene.add(GameScreen.player, Actions.sequence(new PickItem(GameScreen.getFastInventoryActor(), storyItems.get(0), 2), new AddNotification("Ammo bag picked up")));
            cutscene.add(GameScreen.player, new ChangeInputPrivilege(gameScreen, true));
        }
    }

    private void batch_three() {
        if(!batchThree) {
            batchThree = true;
            cutscene.add(GameScreen.player, new ChangeInputPrivilege(gameScreen, false));
            cutscene.add(GameScreen.player, new ChangeAnimation(Assets.Animations.HERO + "_idle"));
            cutscene.add(camFollow, Actions.moveTo(GameScreen.player.getX(), GameScreen.player.getY()));
            cutscene.add(currentFollow, new FollowActorAction(camFollow));
            cutscene.add(camFollow, Actions.moveBy(17, 0, 2f, Interpolation.sine));
            cutscene.add(GameScreen.player, Actions.sequence(new ShowDialogue(gameScreen.getDialogueManager(), "problem")));
            cutscene.add(GameScreen.player, Actions.sequence(new CheckDialogAction(gameScreen.getDialogueManager())));
            cutscene.add(currentFollow, Actions.sequence(new FollowActorAction(GameScreen.player), new ChangeInputPrivilege(gameScreen, true)));
            cutscene.add(GameScreen.player, new ChangeWeaponPrivilege(gameScreen, true));
        }
    }

    private void batch_four() {
        if(!batchFour) {
            batchFour = true;
            cutscene.add(GameScreen.player, Actions.parallel(new ChangeInputPrivilege(gameScreen, false), new ChangeWeaponPrivilege(gameScreen, false)));
            cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "gone"));
            cutscene.add(GameScreen.player, new CheckDialogAction(gameScreen.getDialogueManager()));
            zombie = new FastZombie(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), 79, 0, gameScreen.getPhysicsWorld());
            zombie.addInteractingObject(GameScreen.player);
            zombie.setHealth(250f);
            addEnemy(zombie);
            cutscene.add(zombie, Actions.sequence(Actions.moveBy(3f, 1f, 2f, Interpolation.sine), Actions.delay(.5f),
                    Actions.moveBy(2f, -0.5f, 2f, Interpolation.sine), Actions.delay(0.5f),
                    Actions.moveBy(5f, 1f, 4f, Interpolation.sine), Actions.delay(0.5f),
                    Actions.moveBy(2.5f, 2f, 4f, Interpolation.sine), Actions.delay(0.5f),
                    Actions.moveBy(1f, 0, 1.4f, Interpolation.sine)));
            cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "from_the_frying_pan"));
            cutscene.add(GameScreen.player, Actions.parallel(new ChangeInputPrivilege(gameScreen, true), new ChangeWeaponPrivilege(gameScreen, true)));
        }
    }

    private void batch_five() {
        if(!batchFive) {
            batchFive = true;
            cutscene.add(GameScreen.player, Actions.parallel(new ChangeInputPrivilege(gameScreen, false), new ChangeWeaponPrivilege(gameScreen, false)));
            cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "the_white_rider"));
            cutscene.add(GameScreen.player, Actions.sequence(new CheckDialogAction(gameScreen.getDialogueManager()), Actions.delay(1f), new ChangeAnimation(Assets.Animations.HERO + "_walking"),  Actions.parallel(Actions.moveTo(gameScreen.getMap().getWidth(), GameScreen.player.getY(), 4f, Interpolation.pow2), new CompleteStory(1))));
            cutscene.add(GameScreen.player, Actions.parallel(new ChangeInputPrivilege(gameScreen, true), new ChangeWeaponPrivilege(gameScreen, true)));
            cutscene.add(GameScreen.player, new CompleteStory(1));
        }
    }
}
