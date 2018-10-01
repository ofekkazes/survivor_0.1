package com.kazes.fallout.test;


import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

/**
 * Bonfire with light
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class Bonfire extends ImageEx {
    float timeout;
    public Bonfire(float xPos, float yPos, RayHandler rayHandler) {
        super(Assets.getAsset(Assets.Images.BONFIRE, Texture.class), xPos, yPos);
        timeout = 60 * 5;
        new PointLight(rayHandler, 200, Color.FIREBRICK, 5, getX() + getWidth() / 2, yPos + getHeight() / 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        //Destroy after 5 minutes
        timeout -= delta;
    }

    public float getTimeout() {
        return timeout;
    }
}
