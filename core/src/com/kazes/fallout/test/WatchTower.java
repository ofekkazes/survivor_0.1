package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;

public class WatchTower extends Group {

    public WatchTower(float xPos, float yPos, String name, World world) {
        ImageEx tower = new ImageEx(Assets.getAsset(Assets.Images.HOUSE1, Texture.class), xPos, yPos, world, BodyDef.BodyType.StaticBody);
        ImageEx watcher = new ImageEx(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), xPos, yPos);
        watcher.translate(watcher.getWidth() / 2, tower.getHeight() / 2);
        this.setName(name);

        this.addActor(tower);
        this.addActor(watcher);
    }
}
