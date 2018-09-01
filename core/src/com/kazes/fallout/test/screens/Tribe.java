package com.kazes.fallout.test.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.kazes.fallout.test.*;
import com.kazes.fallout.test.screens.GameScreen;

public class Tribe extends GameScreen {

    public Tribe(Survivor game, float startingPosX) {
        super(game, "Tribe", startingPosX);
        weaponsAllowed = false;
        lastScreen = Screens.SideScroll;
    }

    @Override
    public void update(float delta) {
        super.update(delta);


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
        decor.addActor(new Wall(Survivor.getInMeters(500), Survivor.getInMeters(200), world));
        decor.addActor(new Wall(Survivor.getInMeters(500), 0, world));
        decor.addActor(new Wall(Survivor.getInMeters(500), Survivor.getInMeters(64), world));
        decor.addActor(new WatchTower(Survivor.getInMeters(500), Survivor.getInMeters(100), "Watcher", world));
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


    }

    @Override
    public void setEnemies() {

    }

    @Override
    public void setItems() {

    }
}
