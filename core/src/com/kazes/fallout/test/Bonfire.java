package com.kazes.fallout.test;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;

public class Bonfire extends ImageEx {
    float timeout;
    public Bonfire(float xPos, float yPos) {
        super(Assets.getAsset(Assets.Images.BONFIRE, Texture.class), xPos, yPos);
        timeout = 60 * 5;
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
