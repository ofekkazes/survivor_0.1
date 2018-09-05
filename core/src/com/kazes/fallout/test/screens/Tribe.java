package com.kazes.fallout.test.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.*;
import com.kazes.fallout.test.dialogues.Var;
import com.kyper.yarn.Library;
import com.kyper.yarn.Value;

public class Tribe extends GameScreen {

    Mercenary mercenary;

    public Tribe(Survivor game, float startingPosX) {
        super(game, "Tribe", startingPosX);
        weaponsAllowed = false;
        lastScreen = Screens.SideScroll;
        screenStage.addActor(dialogueManager.getWindow());

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
        if(mercenary.isDone())
            Gdx.app.log("Mercenary returned", mercenary.getNumberOfKills() + " enemies killed\n" + mercenary.getNumOfMercenaries() + " mercenaries returned");
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ((SideScrollingCamera)gameStage.getCamera()).followPos(player.getOrigin());
    }

    @Override
    public void setMap() {
        Array<Texture> parallaxTextures = new Array<Texture>();
        for(int i = 0; i < 6;i++){
            parallaxTextures.add(Assets.getAsset(Assets._Parallax1[i], Texture.class));
            parallaxTextures.get(i).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        parallaxBackground = new ParallaxBackground(parallaxTextures, gameStage.getCamera().viewportWidth, gameStage.getCamera().viewportHeight);
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
        bonfires.addActor(new Bonfire(Survivor.getInMeters(900), 0));
    }

    @Override
    public void setPlayer(float startingPointX) {
        player.setX(startingPointX);
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
    }

    @Override
    public void setEnemies() {

    }

    @Override
    public void setItems() {

    }

}
