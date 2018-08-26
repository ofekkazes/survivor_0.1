package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Medicine extends ImageEx implements Carryable {
    private String desc;
    public Medicine(float x, float y) {
        super(Assets.getAsset(Assets.Images.MEDKIT, Texture.class), x, y);
        this.desc = "Used to heal 30 points";
    }

    @Override
    public String getDescription() {
        return this.desc;
    }

    @Override
    public <T> boolean useItem(T usedOn, Array<Float> objects) {
        if(((Player)usedOn).getHealth() == 100)
            return false;
        ((Player)usedOn).addHealth(20);
        return true;
    }
}
