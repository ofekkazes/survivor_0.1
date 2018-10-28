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
import com.kazes.fallout.test.screens.Screens;

public class Chapter1 extends Story {

    FastZombie zombie;

    public Chapter1() {
        super(1);
    }

    @Override
    public void setup() {
        dialogueFile = Assets.Dialogues.CHAPTER1;

        parts = new boolean[5];


    }

    @Override
    public boolean addPartToStory(int part) {
        if(super.addPartToStory(part)) {
            switch (part) {
                case 1: part_one();break;
                case 2: part_two();break;
                case 3: part_three();break;
                case 4: part_four();break;
                case 5: part_five();break;
            }
        }
        return false;
    }

    @Override
    public void update() {
        super.update();
        if(Screens.getCurrent().getName() == "Eryon") {
            addPartToStory(1);
            if (GameScreen.player.getX() > 55)
                this.addPartToStory(2);
            if (GameScreen.player.getX() > 80)
                this.addPartToStory(3);

            if (this.gameScreen.getEnemies().getChildren().size == 0)
                this.addPartToStory(4);

            if (checkPart(4)) {
                if (zombie.getHealth() < 1)
                    this.addPartToStory(5);
            }
        }
    }

    @Override
    public void render() {
        super.render();

    }

    private void part_one() {
        addItem(new ItemActor(new AmmoCrate(), 60, 6));
        addNPC(new InjuredNPC(gameScreen.getPhysicsWorld() , 50, 3, Weapons.NULL));
        for(int i = 0; i < 5; i++)
            addEnemy(new Zombie(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), MathUtils.random(100, 115), MathUtils.random(5f), gameScreen.getPhysicsWorld()));

        camFollow.setX(0);
        camFollow.setY(GameScreen.player.getY());
        gameScreen.getGameStage().addActor(camFollow);
        currentFollow = new CamFollowActor(camFollow);
        gameScreen.getGameStage().addActor(currentFollow);

        cutscene.add(camFollow, Actions.moveTo(this.storyNpcs.get(0).getX(), 3.5f, 5f, Interpolation.sine));
        cutscene.add(this.storyNpcs.get(0), Actions.repeat(4, Actions.moveBy(-1f, 0, 1.5f, Interpolation.sine)));
        cutscene.add(camFollow, Actions.sequence(Actions.delay(3f), new ShowDialogue(gameScreen.getDialogueManager(), "first_chapter_start"), new CheckDialogueAction(gameScreen.getDialogueManager())));
        cutscene.add(GameScreen.player, Actions.sequence(Actions.parallel(Actions.moveTo(this.storyNpcs.get(0).getX() - 4 - GameScreen.player.getWidth(), 4, 5f), new ChangeAnimation(Assets.Animations.HERO + "_walking")), new ChangeAnimation(Assets.Animations.HERO + "_idle")));
        cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "friend"));
        cutscene.add(this.storyNpcs.get(0), Actions.sequence(new CheckDialogueAction(gameScreen.getDialogueManager()), new XFlipAction()));
        cutscene.add(this.storyNpcs.get(0), Actions.sequence(new ShowDialogue(gameScreen.getDialogueManager(), "help"), new CheckDialogueAction(gameScreen.getDialogueManager()), Actions.delay(1f), new SaveAction()));
        cutscene.add(GameScreen.player, Actions.sequence(new ShowDialogue(gameScreen.getDialogueManager(), "new_beginnings"), new CheckDialogueAction(gameScreen.getDialogueManager()), Actions.delay(1f)));
        cutscene.add(currentFollow, Actions.sequence(new FollowActorAction(GameScreen.player), new ChangeInputPrivilege(gameScreen, true)));
        cutscene.add(GameScreen.player, new CompletePart(this, 1));
    }

    private void part_two() {
            cutscene.add(GameScreen.player, new ChangeInputPrivilege(gameScreen, false));
            cutscene.add(GameScreen.player, Actions.sequence(new ChangeAnimation(Assets.Animations.HERO + "_idle"), Actions.delay(1.5f), Actions.parallel(new ChangeAnimation(Assets.Animations.HERO + "_walking"), Actions.moveTo(storyItems.get(0).getX() - GameScreen.player.getWidth() / 2, storyItems.get(0).getY() - GameScreen.player.getHeight() / 2, 1f, Interpolation.sine)), new ChangeAnimation(Assets.Animations.HERO + "_idle")));
            cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "ammo"));
            cutscene.add(GameScreen.player, Actions.sequence(new CheckDialogueAction(gameScreen.getDialogueManager())));
            cutscene.add(GameScreen.player, Actions.sequence(new PickItem(GameScreen.getFastInventoryActor(), storyItems.get(0), 2), new AddNotification("Ammo bag picked up")));
            cutscene.add(GameScreen.player, new ChangeInputPrivilege(gameScreen, true));
            cutscene.add(GameScreen.player, new CompletePart(this, 2));
    }

    private void part_three() {
            cutscene.add(GameScreen.player, new ChangeInputPrivilege(gameScreen, false));
            cutscene.add(GameScreen.player, new ChangeAnimation(Assets.Animations.HERO + "_idle"));
            cutscene.add(camFollow, Actions.moveTo(GameScreen.player.getX(), GameScreen.player.getY()));
            cutscene.add(currentFollow, new FollowActorAction(camFollow));
            cutscene.add(camFollow, Actions.moveBy(17, 0, 2f, Interpolation.sine));
            cutscene.add(GameScreen.player, Actions.sequence(new ShowDialogue(gameScreen.getDialogueManager(), "problem")));
            cutscene.add(GameScreen.player, Actions.sequence(new CheckDialogueAction(gameScreen.getDialogueManager())));
            cutscene.add(currentFollow, Actions.sequence(new FollowActorAction(GameScreen.player), new ChangeInputPrivilege(gameScreen, true)));
            cutscene.add(GameScreen.player, new ChangeWeaponPrivilege(gameScreen, true));
            cutscene.add(GameScreen.player, new CompletePart(this, 3));

    }

    private void part_four() {
            cutscene.add(GameScreen.player, Actions.parallel(new ChangeInputPrivilege(gameScreen, false), new ChangeWeaponPrivilege(gameScreen, false)));
            cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "gone"));
            cutscene.add(GameScreen.player, new CheckDialogueAction(gameScreen.getDialogueManager()));
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
            cutscene.add(GameScreen.player, new CompletePart(this, 4));

    }

    private void part_five() {
            cutscene.add(GameScreen.player, Actions.parallel(new ChangeInputPrivilege(gameScreen, false), new ChangeWeaponPrivilege(gameScreen, false)));
            cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "the_white_rider"));
            cutscene.add(GameScreen.player, Actions.sequence(new CheckDialogueAction(gameScreen.getDialogueManager()), Actions.delay(1f), new ChangeAnimation(Assets.Animations.HERO + "_walking"),  Actions.parallel(Actions.moveTo(gameScreen.getMap().getWidth(), GameScreen.player.getY(), 4f, Interpolation.pow2), new CompleteStory(1))));
            cutscene.add(GameScreen.player, Actions.parallel(new ChangeInputPrivilege(gameScreen, true), new ChangeWeaponPrivilege(gameScreen, true)));
            cutscene.add(GameScreen.player, new CompleteStory(1));
            cutscene.add(GameScreen.player, new CompletePart(this, 5));
    }
}
