package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.kazes.fallout.test.physics.CollisionCategory;

/**
 * Physics wall
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class Wall extends ImageEx {

    public Wall(float posX, float posY, World world) {
        super(Assets.getAsset(Assets.Images.WALL, Texture.class), posX, posY, world, BodyDef.BodyType.StaticBody, CollisionCategory.DECORATION, CollisionCategory.DECORATION_COLLIDER);
    }
}
