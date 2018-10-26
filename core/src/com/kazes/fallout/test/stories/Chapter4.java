package com.kazes.fallout.test.stories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.kazes.fallout.test.Assets;
import com.kazes.fallout.test.Mission;
import com.kazes.fallout.test.NPC;
import com.kazes.fallout.test.Objective;
import com.kazes.fallout.test.actions.*;
import com.kazes.fallout.test.items.Items;
import com.kazes.fallout.test.items.Weapons;
import com.kazes.fallout.test.screens.GameScreen;
import com.kazes.fallout.test.screens.Screens;
import com.kyper.yarn.Library;
import com.kyper.yarn.Value;

public class Chapter4 extends Story {
    private static boolean MAYORSIDE;

    Mission killMission;
    Mission gotoMayor;
    public Chapter4() {
        super(4);
    }

    @Override
    public void setup() {
        dialogueFile = Assets.Dialogues.CHAPTER4;
        parts = new boolean[] {true, true, false};

        killMission = new Mission(Objective.KillCount, gameScreen, "Kill 50 undead");
        killMission.addRequirment(10);

        gotoMayor = new Mission(Objective.GoTo, gameScreen, "Go to see Mayor");
        gotoMayor.addRequirment("Niar");
    }

    @Override
    public void addFunctions() {
        gameScreen.getDialogueManager().dialogue.getLibrary().registerFunction("setAlly", 1, new Library.Function() {
            @Override
            public void invoke(Value... values) {
                Value boolValue = values[0];
                MAYORSIDE = boolValue.getBoolValue();
            }
        });
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

        if(Screens.getCurrent().getName() == "Barikad") {
            if(checkPart(1) && killMission.isCompleted())
                addPartToStory(2);
            else addPartToStory(1);
        }
        if(Screens.getCurrent().getName() == "Niar" && checkPart(2))
            addPartToStory(3);

    }

    private void part_one() {
        NPC keevan = new NPC(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), "keevan", 11, 4.6f, Weapons.NULL);
        keevan.setXflip(true);
        addNPC(keevan);
        cutscene.add(GameScreen.player, Actions.parallel(new ChangeInputPrivilege(gameScreen, false), new ChangeWeaponPrivilege(gameScreen, false)));
        cutscene.add(GameScreen.player, Actions.sequence(new ChangeAnimation(Assets.Animations.HERO + "_walking"), Actions.moveTo(keevan.getX() - 1.5f, keevan.getY(), 5.5f, Interpolation.sine), new ChangeAnimation(Assets.Animations.HERO + "_idle")));
        cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "fourth_chapter_start"));
        cutscene.add(GameScreen.player, Actions.sequence(new CheckDialogAction(gameScreen.getDialogueManager()), new AddMission(killMission, 10), Actions.parallel(new ChangeInputPrivilege(gameScreen, true), new ChangeWeaponPrivilege(gameScreen, true))));
        cutscene.add(GameScreen.player, Actions.parallel(new ChangeInputPrivilege(gameScreen, true), new ChangeWeaponPrivilege(gameScreen, true)));
        cutscene.add(GameScreen.player, new CompletePart(this, 1));
    }

    private void part_two() {
        NPC keevan = new NPC(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), "keevan", 11, 4.6f, Weapons.NULL);
        keevan.setXflip(true);
        addNPC(keevan);
        cutscene.add(GameScreen.player, Actions.parallel(new ChangeInputPrivilege(gameScreen, false), new ChangeWeaponPrivilege(gameScreen, false)));
        cutscene.add(GameScreen.player, Actions.sequence(new ChangeAnimation(Assets.Animations.HERO + "_walking"), Actions.moveTo(keevan.getX() - 1.5f, keevan.getY(), 5.5f, Interpolation.sine), new ChangeAnimation(Assets.Animations.HERO + "_idle")));
        cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "maga"));
        cutscene.add(GameScreen.player, Actions.sequence(new CheckDialogAction(gameScreen.getDialogueManager()), new AddMission(gotoMayor, "Niar"), Actions.parallel(new ChangeInputPrivilege(gameScreen, true), new ChangeWeaponPrivilege(gameScreen, true))));
        cutscene.add(GameScreen.player, new CompletePart(this, 2));
    }

    private void part_three() {
        NPC mayor = new NPC(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), "mayor", 14, 6f, Weapons.NULL);
        mayor.setXflip(true);
        addNPC(mayor);
        //cutscene.add(GameScreen.player, new CheckMission(niar));
        cutscene.add(GameScreen.player, Actions.sequence( Actions.parallel(new ChangeInputPrivilege(gameScreen, false), new ChangeWeaponPrivilege(gameScreen, false))));
        cutscene.add(GameScreen.player, Actions.sequence(new ChangeAnimation(Assets.Animations.HERO + "_walking"), Actions.moveTo(mayor.getX() - 1.5f, mayor.getY(), 7f, Interpolation.sine), new ChangeAnimation(Assets.Animations.HERO + "_idle")));
        cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "not_happening"));
        cutscene.add(GameScreen.player, Actions.sequence(new CheckDialogAction(gameScreen.getDialogueManager()), Actions.parallel(new ChangeInputPrivilege(gameScreen, true), new ChangeWeaponPrivilege(gameScreen, true))));
        cutscene.add(GameScreen.player, new CompletePart(this, 3));
    }

    public static boolean MAYORSIDE() {
        return MAYORSIDE;
    }
}
