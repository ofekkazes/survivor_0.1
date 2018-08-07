package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Medicine extends ImageEx implements Carryable {
    private String desc;
    public Medicine(Texture texture, float x, float y) {
        super(texture);
        this.setPosition(x, y);
        this.desc = "Used to heal 30 points";
    }

    @Override
    public String getDescription() {
        return this.desc;
    }

    @Override
    public boolean useItem(Player usedOn) {
        if(usedOn.getHealth() == 100)
            return false;
        usedOn.addHealth(20);
        return true;
    }
}
