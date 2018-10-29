package com.kazes.fallout.test.stories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.kazes.fallout.test.Assets;
import com.kazes.fallout.test.Mission;
import com.kazes.fallout.test.NPC;
import com.kazes.fallout.test.Objective;
import com.kazes.fallout.test.actions.*;
import com.kazes.fallout.test.enemies.Boss;
import com.kazes.fallout.test.items.Weapons;
import com.kazes.fallout.test.screens.GameScreen;
import com.kazes.fallout.test.screens.Screens;

public class Chapter5A extends Story {
    Mission bossKiller;
    public Chapter5A() {
        super(5);
    }

    @Override
    public void setup() {
        parts = new boolean[3];
        dialogueFile = Assets.Dialogues.CHAPTER5A;
        bossKiller = new Mission(Objective.KillBoss, "Take care of Meviah's happenings");
        bossKiller.addRequirment("Giorgio"); //Terrifying boss name
    }

    @Override
    public boolean addPartToStory(int part) {
        if(super.addPartToStory(part)) {
            switch (part) {
                case 1: part_one();break;
                case 2: part_two();break;
                case 3: part_three();break;
            }
        }
        return false;
    }

    @Override
    public void update() {
        super.update();
        if(Screens.getCurrent().getName() == "Barikad")
            addPartToStory(1);
        if(checkPart(1) && Screens.getCurrent().getName() == "Meviah")
            addPartToStory(2);
        if(checkPart(2) && bossKiller.isCompleted() && Screens.getCurrent().getName() == "Barikad")
            addPartToStory(3);
    }

    private void part_one() {
        NPC keevan = new NPC(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), "keevan", 20, 4.6f, Weapons.NULL);
        //keevan.setXflip(true);
        addNPC(keevan);
        cutscene.add(GameScreen.player, Actions.parallel(new ChangeInputPrivilege(gameScreen, false), new ChangeWeaponPrivilege(gameScreen, false)));
        cutscene.add(GameScreen.player, Actions.sequence(new ChangeAnimation(Assets.Animations.HERO + "_walking"), Actions.moveTo(keevan.getX() + 1.5f, keevan.getY(), 3.5f, Interpolation.sine), new ChangeAnimation(Assets.Animations.HERO + "_idle")));
        cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "fifth_chapter_start"));
        cutscene.add(GameScreen.player, Actions.sequence(new CheckDialogueAction(gameScreen.getDialogueManager()), new AddMission(bossKiller, "Giorgio"), Actions.parallel(new ChangeInputPrivilege(gameScreen, true), new ChangeWeaponPrivilege(gameScreen, true))));
        cutscene.add(GameScreen.player, Actions.parallel(new ChangeInputPrivilege(gameScreen, true), new ChangeWeaponPrivilege(gameScreen, true)));
        cutscene.add(GameScreen.player, new CompletePart(this, 1));
    }

    private void part_two() {
        Boss giorgio = new Boss(30, 4, gameScreen.getPhysicsWorld());
        addEnemy(giorgio);
        cutscene.add(GameScreen.player, Actions.parallel(new ChangeInputPrivilege(gameScreen, false), new ChangeWeaponPrivilege(gameScreen, false)));
        cutscene.add(GameScreen.player, Actions.sequence(new ChangeAnimation(Assets.Animations.HERO + "_walking"), Actions.moveTo(50, 5f, 10f, Interpolation.sine), new ChangeAnimation(Assets.Animations.HERO + "_idle")));
        cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "noisy_environment"));
        cutscene.add(GameScreen.player, Actions.sequence(new CheckDialogueAction(gameScreen.getDialogueManager()), new ChangeAnimation(Assets.Animations.HERO + "_walking"), Actions.moveTo(giorgio.getX() + 15f, 5f, 5f, Interpolation.sine), new ChangeAnimation(Assets.Animations.HERO + "_idle")));
        cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "big_bang"));
        cutscene.add(GameScreen.player, Actions.sequence(new CheckDialogueAction(gameScreen.getDialogueManager()), Actions.parallel(new ChangeInputPrivilege(gameScreen, true), new ChangeWeaponPrivilege(gameScreen, true))));
        cutscene.add(GameScreen.player, new CompletePart(this, 2));
    }

    private void part_three() {
        NPC keevan = new NPC(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), "keevan", 11, 4.6f, Weapons.NULL);
        keevan.setXflip(true);
        addNPC(keevan);
        cutscene.add(GameScreen.player, Actions.parallel(new ChangeInputPrivilege(gameScreen, false), new ChangeWeaponPrivilege(gameScreen, false)));
        cutscene.add(GameScreen.player, Actions.sequence(new ChangeAnimation(Assets.Animations.HERO + "_walking"), Actions.moveTo(keevan.getX() - 1.5f, keevan.getY(), 5.5f, Interpolation.sine), new ChangeAnimation(Assets.Animations.HERO + "_idle")));
        cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "boss_killed"));
        cutscene.add(GameScreen.player, Actions.sequence(new CheckDialogueAction(gameScreen.getDialogueManager()), new AddMission(bossKiller, "Giorgio"), Actions.parallel(new ChangeInputPrivilege(gameScreen, true), new ChangeWeaponPrivilege(gameScreen, true))));
        cutscene.add(GameScreen.player, Actions.parallel(new ChangeInputPrivilege(gameScreen, true), new ChangeWeaponPrivilege(gameScreen, true)));
        cutscene.add(GameScreen.player, Actions.parallel(new CompletePart(this, 3), new CompleteStory(5)));
    }
}
