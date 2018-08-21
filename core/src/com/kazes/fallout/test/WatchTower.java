package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;

public class WatchTower extends Group {
    private ImageEx watcher;
    private ImageEx tower;

    public WatchTower(float xPos, float yPos, String name) {
        tower = new ImageEx(Assets.getAsset(Assets.Images.HOUSE1, Texture.class), xPos, yPos);
        watcher = new ImageEx(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), xPos, yPos);
        watcher.translate(watcher.getWidth() / 2, tower.getHeight() / 2);
        this.setName(name);

        this.addActor(tower);
        this.addActor(watcher);
    }
}
