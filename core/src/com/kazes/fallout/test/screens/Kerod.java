package com.kazes.fallout.test.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.*;
import com.kazes.fallout.test.dialogues.Var;
import com.kazes.fallout.test.items.*;
import com.kyper.yarn.Library;
import com.kyper.yarn.Value;

/**
 * Base town settings and the main town
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class Kerod extends GameScreen {

    Mercenary mercenary;
    Group randomNPCs;
    Merchant jerry;
    public Kerod(Survivor game, float startingPosX) {
        super(game, "Kerod", startingPosX);
        weaponsAllowed = true;
        lastScreen = Screens.Melin;
        nextScreen = Screens.Basmati;

        dialogueManager.dialogue.getLibrary().registerFunction("sendToMission", 0, new Library.Function() {
            @Override
            public void invoke(Value... params) {
                mercenary.startTimer();
            }
        });
        dialogueManager.dialogue.loadFile(Assets.Dialogues.MERCENARIES, false, false, null);
        dialogueManager.addVar(new Var("%risk", 0));
        dialogueManager.addVar(new Var("%time", 0));
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(player.getRectangle().overlaps(mercenary.getRectangle())) {
            if(Gdx.input.isKeyJustPressed(Input.Keys.ALT_LEFT)) {
                dialogueManager.start("Start");
                dialogueManager.updateVar("%risk", mercenary.getRisk());
                dialogueManager.updateVar("%time", mercenary.getTime());
            }
        }
        if(mercenary.isDone()) {
            GameScreen.notifications.add(new Notification(mercenary.getNumOfMercenaries() + " mercenaries returned" + mercenary.getNumberOfKills() + " enemies killed"));
            mercenary.init();
        }
        for(Actor npc : randomNPCs.getChildren()) {
            if(!npc.hasActions()) {
                npc.addAction(Actions.sequence(Actions.moveBy(MathUtils.random(Survivor.getInMeters(-500), Survivor.getInMeters(500)), MathUtils.random(Survivor.getInMeters(-200), Survivor.getInMeters(200)), MathUtils.random(1, 4.5f)),
                        Actions.fadeOut(1f), Actions.moveTo(MathUtils.random(0, MathUtils.random(map.getWidth())), MathUtils.random(0, 5f)), Actions.fadeIn(1f)));
            }
            if(npc.getY() > map.getHeight() - 1) {
                npc.clearActions();
                npc.addAction(Actions.moveBy(0, -1f, 2f));
            }
            if(npc.getY() < 0) {
                npc.clearActions();
                npc.addAction(Actions.moveBy(0, 1f, 2f));
            }
            if(npc.getX() > map.getWidth()) {
                npc.clearActions();
                npc.addAction(Actions.moveBy(-1f, 0, 2f));
            }
            if(npc.getX() < 0) {
                npc.clearActions();
                npc.addAction(Actions.moveBy(1f, 0, 2f));
            }
        }
    }

    @Override
    public void processInput() {
        super.processInput();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if(jerry.getInventory().isVisible())
                jerry.getInventory().setVisible(false);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void setMap() {
        Array<Texture> parallaxTextures = new Array<Texture>();
        for(int i = 0; i < 6;i++){
            parallaxTextures.add(Assets.getAsset(Assets._Parallax1[i], Texture.class));
            parallaxTextures.get(i).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        parallaxBackground = new ParallaxBackground(parallaxTextures, gameStage.getCamera());
        parallaxBackground.setSize(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2);


        map = new ImageEx(game.assetManager.get(Assets.Images.MAP, Texture.class), 0, 0);
    }

    @Override
    public void setDecor() {
        decor.addActor(new ImageEx(game.assetManager.get(Assets.Images.HOUSE1, Texture.class), Survivor.getInMeters(800), Survivor.getInMeters(300)));
        decor.addActor(new ImageEx(game.assetManager.get(Assets.Images.HOUSE2, Texture.class), Survivor.getInMeters(1000), Survivor.getInMeters(300)));
        decor.addActor(new ImageEx(game.assetManager.get(Assets.Images.HOUSE2, Texture.class), Survivor.getInMeters(1200), Survivor.getInMeters(300)));
        decor.addActor(new WatchTower(Survivor.getInMeters(500), Survivor.getInMeters(250), "Watcher", world));
        decor.addActor(new WatchTower(Survivor.getInMeters(500), Survivor.getInMeters(0), "Watcher", world));
        decor.addActor(new WatchTower(Survivor.getInMeters(1500), Survivor.getInMeters(250), "Watcher", world));
        decor.addActor(new WatchTower(Survivor.getInMeters(1500), Survivor.getInMeters(100), "Watcher", world));
        bonfires.addActor(new Bonfire(Survivor.getInMeters(900), 0, rayHandler));
    }

    @Override
    public void setNPCS() {
        followers.addActor(new Watcher(world, Survivor.getInMeters(700), 0, Survivor.getInMeters(700), Survivor.getInMeters(250), 5f));
        followers.addActor(new Watcher(world, Survivor.getInMeters(600), Survivor.getInMeters(250), Survivor.getInMeters(1400), Survivor.getInMeters(250), 10f));
        followers.addActor(new Watcher(world, (Watcher)followers.getChildren().items[followers.getChildren().size - 1]));
        followers.addActor(new Watcher(world, (Watcher)followers.getChildren().items[followers.getChildren().size - 1]));
        followers.addActor(new Watcher(world, Survivor.getInMeters(1400), Survivor.getInMeters(250), Survivor.getInMeters(1400), 0, 5f));

        mercenary = new Mercenary(Survivor.getInMeters(600), Survivor.getInMeters(200), 5, 30, 30);
        npcs.addActor(mercenary);

        randomNPCs = new Group();
        npcs.addActor(randomNPCs);
        for (int i = 0; i < ClanProperties.getNPCCount(); i++) {
            if(i % 5 == 0) {
                randomNPCs.addActor(new ImageEx(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), MathUtils.random(Survivor.getInMeters(700), Survivor.getInMeters(1400)), MathUtils.random(1, Survivor.getInMeters(280))));
            }
        }

        jerry = new Merchant(5, 6, screenStage, fastInventoryActor.getInventory(), inventoryActor.getInventory());
        decor.addActor(jerry);

    }

    @Override
    public void setEnemies() {

    }

    @Override
    public void setItems() {
        items.addActor(new ItemActor(new SmallMedkit(), 5, 5));
        items.addActor(new ItemActor(new WaterBottle(), 7, 5));
    }

}
