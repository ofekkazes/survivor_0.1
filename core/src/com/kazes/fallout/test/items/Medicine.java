package com.kazes.fallout.test.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.Assets;
import com.kazes.fallout.test.ImageEx;
import com.kazes.fallout.test.Player;
import com.kazes.fallout.test.items.Carryable;

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
        ((Player)usedOn).addHealth(.20f);
        return true;
    }
}
