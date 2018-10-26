package com.kazes.fallout.test.stories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.Assets;
import com.kazes.fallout.test.CamFollowActor;
import com.kazes.fallout.test.InjuredNPC;
import com.kazes.fallout.test.NPC;
import com.kazes.fallout.test.actions.*;
import com.kazes.fallout.test.enemies.FastZombie;
import com.kazes.fallout.test.enemies.Zombie;
import com.kazes.fallout.test.items.AmmoCrate;
import com.kazes.fallout.test.items.ItemActor;
import com.kazes.fallout.test.items.Weapons;
import com.kazes.fallout.test.screens.GameScreen;

public class Chapter2 extends Story {

    Array<Actor> soldiers;
    NPC general;
    NPC soldier1;
    NPC soldier2;
    NPC soldier3;
    float timeout;
    Label timeLabel;

    public Chapter2() {
        super(2);
    }

    @Override
    public void setup() {
        dialogueFile = Assets.Dialogues.CHAPTER2;

        addNPC(new InjuredNPC(gameScreen.getPhysicsWorld(), 57, 3, Weapons.NULL));
        addNPC(new InjuredNPC(gameScreen.getPhysicsWorld(), 58, 3, Weapons.NULL));
        storyNpcs.get(1).setXflip(true);
        storyNpcs.get(1).setRotation(90);

        for (int i = 0; i < 10; i++) {
            addEnemy(new Zombie(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), MathUtils.random(75, 90), MathUtils.random(5f), gameScreen.getPhysicsWorld()));
            storyEnemies.get(i).addInteractingObject(GameScreen.player);
            storyEnemies.get(i).addInteractingObject(storyNpcs.get(0));
            storyEnemies.get(i).addInteractingObject(storyNpcs.get(1));
        }
        addItem(new ItemActor(new AmmoCrate(), 60, 6));

        soldiers = new Array<Actor>();
        general = new NPC(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), "General", 5, 5, Weapons.SMG);
        soldier1 = new NPC(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), "Soldier1", 6, 4, Weapons.Pistol);
        soldier2 = new NPC(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), "Soldier2", 4, 3, Weapons.Pistol);
        soldier3 = new NPC(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), "Soldier3", 5, 1.2f, Weapons.Pistol);

        soldiers.add(general);
        soldiers.add(soldier1);
        soldiers.add(soldier2);
        soldiers.add(soldier3);

        addNPC(general);
        addNPC(soldier1);
        addNPC(soldier2);
        addNPC(soldier3);

        general.setVisible(false);
        soldier1.setVisible(false);
        soldier2.setVisible(false);
        soldier3.setVisible(false);

        parts = new boolean[5];
        addPartToStory(1);
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
        for(int i = 0; i < storyEnemies.size; i++) {
            if(storyEnemies.get(i).getHealth() < 1) {
                storyEnemies.removeIndex(i);
                break;
            }
        }

        if(GameScreen.player.getX() > 47)
            addPartToStory(2);
        Gdx.app.log("asd", checkPart(4) + "");
        if(cutscene.actions.size != 0)
            Gdx.app.log("asd0", this.cutscene.actions.first().action.toString());
            if(storyEnemies.size == 0) {
                addPartToStory(3);
            }

        if(checkPart(3) && !checkPart(4)) {
            timeLabel.setText("Time left: " + MathUtils.floor(timeout-- / Gdx.graphics.getFramesPerSecond()));
            if(storyEnemies.size < 25) {
                addEnemy(new Zombie(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), MathUtils.random(gameScreen.getMap().getWidth() - 2, gameScreen.getMap().getWidth()-0.0001f), MathUtils.random(0, 8), gameScreen.getPhysicsWorld()));
                storyEnemies.get(storyEnemies.size - 1).setHealth(99);
                storyEnemies.get(storyEnemies.size - 1).addInteractingObject(GameScreen.player);
            }
            if(timeout < 0) {
                GameScreen.getScreenStage().getActors().removeValue(timeLabel, false);
                addPartToStory(4);
            }
        }
        if(checkPart(4) && !checkPart(5)) {
            if(storyEnemies.size == 0) {
                addPartToStory(5);
            }
        }
    }

    @Override
    public void render() {
        super.render();

    }

    private void part_one() {
        camFollow.setX(0);
        camFollow.setY(GameScreen.player.getY());
        gameScreen.getGameStage().addActor(camFollow);
        currentFollow = new CamFollowActor(camFollow);
        gameScreen.getGameStage().addActor(currentFollow);
        cutscene.add(camFollow, Actions.moveTo(storyNpcs.get(0).getX(), currentFollow.getY(), 4f, Interpolation.sine));
        cutscene.add(camFollow, Actions.parallel(Actions.delay(0.5f), new ShowDialogue(gameScreen.getDialogueManager(), "second_chapter_start")));
        cutscene.add(GameScreen.player, Actions.moveTo(3, GameScreen.player.getY()));
        cutscene.add(camFollow, Actions.sequence(new CheckDialogAction(gameScreen.getDialogueManager()), Actions.delay(0.2f), Actions.moveTo(GameScreen.player.getX() - 3f, GameScreen.player.getY(), 4f, Interpolation.sine)));
        cutscene.add(currentFollow, Actions.parallel(new FollowActorAction(GameScreen.player), new ChangeInputPrivilege(gameScreen, true), new ChangeWeaponPrivilege(gameScreen, true)));
        cutscene.add(GameScreen.player, new CompletePart(this, 1));
    }

    private void part_two() {
        cutscene.add(GameScreen.player, Actions.parallel(new ChangeWeaponPrivilege(gameScreen, false), new ChangeInputPrivilege(gameScreen, false)));
        cutscene.add(GameScreen.player, Actions.sequence(new ChangeAnimation(Assets.Animations.HERO + "_idle"), Actions.delay(0.5f), new ChangeAnimation(Assets.Animations.HERO + "_walking"),  Actions.moveTo(storyNpcs.get(0).getX() - 3, storyNpcs.get(0).getY(), 1.5f, Interpolation.pow2), new ChangeAnimation(Assets.Animations.HERO + "_idle")));
        cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "help_on_the_way"));
        cutscene.add(GameScreen.player, new CheckDialogAction(gameScreen.getDialogueManager()));
        cutscene.add(camFollow, Actions.sequence(Actions.moveTo(GameScreen.player.getX(), GameScreen.player.getY()), Actions.delay(0.2f)));
        cutscene.add(currentFollow, new FollowActorAction(camFollow));
        cutscene.add(camFollow, Actions.moveTo(80, camFollow.getY(), 4f, Interpolation.sine));
        cutscene.add(camFollow, Actions.sequence(Actions.delay(1.5f)));
        cutscene.add(currentFollow, Actions.sequence(new FollowActorAction(GameScreen.player), new ChangeInputPrivilege(gameScreen, true), new ChangeWeaponPrivilege(gameScreen, true)));
        cutscene.add(GameScreen.player, new CompletePart(this, 2));
    }

    private void part_three() {
        cutscene.add(GameScreen.player, Actions.parallel(new ChangeWeaponPrivilege(gameScreen, false), new ChangeInputPrivilege(gameScreen, false)));
        cutscene.add(GameScreen.player, new ChangeAnimation(Assets.Animations.HERO + "_idle"));
        cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "we_are_coming"));
        cutscene.add(GameScreen.player, Actions.parallel(new ChangeWeaponPrivilege(gameScreen, true), new ChangeInputPrivilege(gameScreen, true)));
        timeout = Gdx.graphics.getFramesPerSecond()  * 5;
        timeLabel = new Label("Time left: " + (timeout / Gdx.graphics.getFramesPerSecond()), Assets.getAsset(Assets.UI_SKIN, Skin.class));
        timeLabel.setPosition(Gdx.graphics.getWidth() / 2 - timeLabel.getWidth() / 2, Gdx.graphics.getHeight() -  timeLabel.getHeight() / 2);
        cutscene.add(GameScreen.getScreenStage().getRoot(), new AddObject(timeLabel));
        cutscene.add(GameScreen.player, new CompletePart(this, 3));

    }

    private void part_four() {
        general.setVisible(true);
        soldier1.setVisible(true);
        soldier2.setVisible(true);
        soldier3.setVisible(true);
        camFollow.setPosition(GameScreen.player.getX(), GameScreen.player.getY());
        cutscene.add(GameScreen.player, Actions.parallel(new ChangeWeaponPrivilege(gameScreen, false), new ChangeInputPrivilege(gameScreen, false)));
        cutscene.add(currentFollow, new FollowActorAction(camFollow));
        cutscene.add(gameScreen.getFollowers(), new AddObject(general));
        cutscene.add(gameScreen.getFollowers(), new AddObject(soldier1));
        cutscene.add(gameScreen.getFollowers(), new AddObject(soldier2));
        cutscene.add(gameScreen.getFollowers(), new AddObject(soldier3));
        cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "help_arrived"));
        cutscene.add(GameScreen.player, Actions.parallel(new ChangeWeaponPrivilege(gameScreen, true), new ChangeInputPrivilege(gameScreen, true)));
        cutscene.add(gameScreen.getFollowers(), Actions.delay(1f));
        cutscene.add(general, Actions.sequence(new CheckDialogAction(gameScreen.getDialogueManager()), new CoordinatedAction(soldiers, Actions.moveBy(GameScreen.player.getX() - general.getX(), GameScreen.player.getY() - general.getY(), 5f, Interpolation.sine))));
        cutscene.add(currentFollow, new FollowActorAction(GameScreen.player));
        cutscene.add(GameScreen.player, new CompletePart(this, 4));

    }

    private void part_five() {
        cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "who_are_you"));
        cutscene.add(GameScreen.player, Actions.parallel(new CheckDialogAction(gameScreen.getDialogueManager())));
        cutscene.add(general, Actions.visible(false));
        cutscene.add(soldier1, Actions.visible(false));
        cutscene.add(soldier2, Actions.visible(false));
        cutscene.add(soldier3, Actions.visible(false));
        cutscene.add(GameScreen.player, Actions.parallel(new ChangeInputPrivilege(gameScreen, true), new ChangeWeaponPrivilege(gameScreen, true)));

        cutscene.add(GameScreen.player, new CompletePart(this, 5));
    }
}
