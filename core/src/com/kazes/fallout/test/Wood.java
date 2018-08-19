package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Wood extends ImageEx implements Carryable{
    private String desc;

    public Wood(Texture texture, float xPos, float yPos) {
        super(texture, xPos, yPos);

        this.desc = "Used to make fire";
    }

    @Override
    public String getDescription() {
        return this.desc;
    }

    @Override
    public <T> boolean useItem(T usedOn, Array<Float> objects) {
        return true;
    }

}
