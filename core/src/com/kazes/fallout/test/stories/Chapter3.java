package com.kazes.fallout.test.stories;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.kazes.fallout.test.*;
import com.kazes.fallout.test.actions.*;
import com.kazes.fallout.test.items.ItemActor;
import com.kazes.fallout.test.items.Items;
import com.kazes.fallout.test.items.Sneakers;
import com.kazes.fallout.test.items.Weapons;
import com.kazes.fallout.test.screens.GameScreen;
import com.kazes.fallout.test.screens.Screens;

public class Chapter3 extends Story{
    Mission niar;
    Mission niar2;
    Mission merchant;
    public Chapter3() {
        super(3);
    }

    @Override
    public void setup() {
        dialogueFile = Assets.Dialogues.CHAPTER3;
        parts = new boolean [4];

        niar = new Mission(Objective.GoTo, "Go to Niar");
        niar.addRequirment("Niar");

        niar2 = new Mission(Objective.GoTo, "Return to Niar");
        niar2.addRequirment("Niar");

        merchant = new Mission(Objective.RetrieveItem, "Get an Axe from the merchant");
        merchant.addRequirment(Items.Sneakers.getItem());

    }

    @Override
    public boolean addPartToStory(int part) {
        if(super.addPartToStory(part)) {
            switch (part) {
                case 1: part1();break;
                case 2: part2();break;
                case 3: part3();break;
                case 4:part4();break;
            }
        }
        return false;
    }

    @Override
    public void update() {
        super.update();

        if(Screens.getCurrent().getName() == "Barikad") {
            addPartToStory(1);
        }
        if(Screens.getCurrent().getName() == "Niar") {
            if(checkPart(3) && GameScreen.getFastInventoryActor().getInventory().checkInventory(Items.Sneakers.getItem()) != 0)
                addPartToStory(4);
            else addPartToStory(2);
        }
        if(Screens.getCurrent().getName() == "Kerod" && !checkPart(3)) {
            if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                for(Actor decor : gameScreen.getDecor().getChildren()) {
                    if(decor instanceof Merchant) {
                        if(GameScreen.player.getRectangle().overlaps(((ImageEx)decor).getRectangle())) {
                            addPartToStory(3);
                        }
                    }
                }
            }
        }

    }

    private void part1() {
        NPC keevan = new NPC(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), "keevan", 10, 3.9f, Weapons.NULL);
        keevan.setXflip(true);
        addNPC(keevan);
        cutscene.add(GameScreen.player, Actions.parallel(new ChangeInputPrivilege(gameScreen, false), new ChangeWeaponPrivilege(gameScreen, false)));
        cutscene.add(GameScreen.player, Actions.sequence(new ChangeAnimation(Assets.Animations.HERO + "_walking"), Actions.moveTo(keevan.getX() - 1.5f, keevan.getY(), 5f, Interpolation.sine), new ChangeAnimation(Assets.Animations.HERO + "_idle")));
        cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "third_chapter_start"));
        cutscene.add(GameScreen.player, Actions.sequence(new CheckDialogAction(gameScreen.getDialogueManager()), new AddMission(niar, "Niar"), Actions.parallel(new ChangeInputPrivilege(gameScreen, true), new ChangeWeaponPrivilege(gameScreen, true))));
        cutscene.add(GameScreen.player, new CompletePart(this, 1));

    }

    private void part2() {
        NPC mayor = new NPC(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), "mayor", 15, 6f, Weapons.NULL);
        mayor.setXflip(true);
        addNPC(mayor);
        //cutscene.add(GameScreen.player, new CheckMission(niar));
        cutscene.add(GameScreen.player, Actions.sequence( Actions.parallel(new ChangeInputPrivilege(gameScreen, false), new ChangeWeaponPrivilege(gameScreen, false))));
        cutscene.add(GameScreen.player, Actions.sequence(new ChangeAnimation(Assets.Animations.HERO + "_walking"), Actions.moveTo(mayor.getX() - 1.5f, mayor.getY(), 7.5f, Interpolation.sine), new ChangeAnimation(Assets.Animations.HERO + "_idle")));
        cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "arbitrage"));
        cutscene.add(GameScreen.player, Actions.sequence(new CheckDialogAction(gameScreen.getDialogueManager()), new AddMission(merchant, Items.Sneakers.getItem()), Actions.parallel(new ChangeInputPrivilege(gameScreen, true), new ChangeWeaponPrivilege(gameScreen, true))));
        cutscene.add(GameScreen.player, new CompletePart(this, 2));
    }

    private void part3() {
        cutscene.add(GameScreen.player, Actions.parallel(new ChangeInputPrivilege(gameScreen, false), new ChangeWeaponPrivilege(gameScreen, false)));
        cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "axe_em"));
        cutscene.add(GameScreen.player, Actions.sequence(new CheckDialogAction(gameScreen.getDialogueManager()), new PickItem(GameScreen.getFastInventoryActor(), new ItemActor(Items.Sneakers.getItem(), -1, -1), 1), new ShowDialogue(gameScreen.getDialogueManager(), "axe_em2")));
        cutscene.add(GameScreen.player, Actions.sequence(new CheckDialogAction(gameScreen.getDialogueManager()), new AddMission(niar2, "Niar"), Actions.parallel(new ChangeInputPrivilege(gameScreen, true), new ChangeWeaponPrivilege(gameScreen, true))));
        cutscene.add(GameScreen.player, new CompletePart(this, 3));
    }

    private void part4() {
        NPC mayor = new NPC(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), "mayor", 15, 6f, Weapons.NULL);
        mayor.setXflip(true);
        addNPC(mayor);

        cutscene.add(GameScreen.player, Actions.parallel(new ChangeInputPrivilege(gameScreen, false), new ChangeWeaponPrivilege(gameScreen, false)));
        cutscene.add(GameScreen.player, Actions.sequence(new ChangeAnimation(Assets.Animations.HERO + "_walking"), Actions.moveTo(mayor.getX() - 1.6f, mayor.getY() - .3f, 7.5f, Interpolation.sine), new ChangeAnimation(Assets.Animations.HERO + "_idle")));
        cutscene.add(GameScreen.player, new ShowDialogue(gameScreen.getDialogueManager(), "package_delivered"));
        cutscene.add(GameScreen.player, Actions.sequence(new CheckDialogAction(gameScreen.getDialogueManager()), Actions.parallel(new ChangeInputPrivilege(gameScreen, true), new ChangeWeaponPrivilege(gameScreen, true))));
        cutscene.add(GameScreen.player, new TakeItem(GameScreen.getFastInventoryActor(), Items.Sneakers.getItem(), 1));
        cutscene.add(GameScreen.player, new CompletePart(this, 4));
        cutscene.add(GameScreen.player, new CompleteStory(3));
    }


}
