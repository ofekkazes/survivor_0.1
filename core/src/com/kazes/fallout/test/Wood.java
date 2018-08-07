package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;

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
    public boolean useItem(Player usedOn) {
        return true;
    }

}
