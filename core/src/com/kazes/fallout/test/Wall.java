package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;

public class Wall extends ImageEx {

    public Wall(float posX, float posY, boolean vFlip) {
        super(Assets.getAsset(Assets.Images.WALL, Texture.class), posX, posY);
    }
}
