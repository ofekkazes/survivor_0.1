package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.kazes.fallout.test.physics.B2DBodyBuilder;
import com.kazes.fallout.test.physics.CollisionCategory;

/**
 * Watch towers to see if enemies arrive from a distance
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class WatchTower extends Group {

    public WatchTower(float xPos, float yPos, String name, World world) {
        ImageEx tower = new ImageEx(Assets.getAsset(Assets.Images.HOUSE1, Texture.class), xPos, yPos);
        tower.setBody(world, B2DBodyBuilder.createBody(world, tower.getX(), tower.getY(), tower.getWidth(), tower.getHeight() / 2, BodyDef.BodyType.StaticBody, CollisionCategory.DECORATION, CollisionCategory.DECORATION_COLLIDER));
        tower.setOffset(0, tower.getHeight() / 2);
        ImageEx watcher = new ImageEx(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), xPos, yPos);
        watcher.translate(watcher.getWidth() / 2, tower.getHeight() / 2);
        this.setName(name);

        this.addActor(tower);
        this.addActor(watcher);
    }
}
