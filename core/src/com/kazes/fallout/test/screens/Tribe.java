package com.kazes.fallout.test.screens;

import com.badlogic.gdx.Gdx;
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
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ((SideScrollingCamera)gameStage.getCamera()).followPos(player.getOrigin());
        parallaxBackground.setXPos(gameStage.getCamera().position.x - gameStage.getCamera().viewportWidth / 2);

    }

    @Override
    public void setMap() {
        Array<Texture> parallaxTextures = new Array<Texture>();
        for(int i = 0; i < 6;i++){
            parallaxTextures.add(Assets.getAsset(Assets._Parallax1[i], Texture.class));
            parallaxTextures.get(i).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        parallaxBackground = new ParallaxBackground(parallaxTextures);
        parallaxBackground.setSize(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2);

        map = new ImageEx(game.assetManager.get(Assets.Images.MAP, Texture.class));
    }

    @Override
    public void setDecor() {
        decor.addActor(new ImageEx(game.assetManager.get(Assets.Images.HOUSE1, Texture.class), 800, 300));
        decor.addActor(new ImageEx(game.assetManager.get(Assets.Images.HOUSE2, Texture.class), 1000, 300));
        decor.addActor(new ImageEx(game.assetManager.get(Assets.Images.HOUSE2, Texture.class), 1200, 300));
        decor.addActor(new WatchTower(500, 250, "Watcher"));
        decor.addActor(new ImageEx(Assets.getAsset(Assets.Images.WALL, Texture.class), 500, 200));
        decor.addActor(new ImageEx(Assets.getAsset(Assets.Images.WALL, Texture.class), 500, 0));
        decor.addActor(new ImageEx(Assets.getAsset(Assets.Images.WALL, Texture.class), 500, 64));
        decor.addActor(new WatchTower(500, 100, "Watcher"));
        decor.addActor(new WatchTower(1500, 250, "Watcher"));
        decor.addActor(new WatchTower(1500, 100, "Watcher"));
        bonfires.addActor(new Bonfire(900, 0));
    }

    @Override
    public void setPlayer(float startingPointX) {
        this.player.setX(startingPointX);
    }

    @Override
    public void setNPCS() {
        followers.addActor(new Watcher(700, 0, 700, 250, 5f));
        followers.addActor(new Watcher(600, 250, 1400, 250, 10f));
        followers.addActor(new Watcher((Watcher)followers.getChildren().items[followers.getChildren().size - 1]));
        followers.addActor(new Watcher((Watcher)followers.getChildren().items[followers.getChildren().size - 1]));
        followers.addActor(new Watcher(1400, 250, 1400, 0, 5f));
    }

    @Override
    public void setEnemies() {

    }

    @Override
    public void setItems() {

    }
}
