package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;

public class Tuna extends ImageEx implements Carryable {
    private String desc;

    public Tuna(float xPos, float yPos) {
        super(Assets.getAsset(Assets.Images.TUNA, Texture.class), xPos, yPos);
        desc = "Used to cure hunger";
    }

    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public boolean useItem(Player usedOn) {
        usedOn.eat(10);
        return true;
    }
}
