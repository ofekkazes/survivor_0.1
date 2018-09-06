package com.kazes.fallout.test.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.Assets;
import com.kazes.fallout.test.ImageEx;
import com.kazes.fallout.test.Player;

public class Ammo extends ImageEx implements Carryable {
    public Ammo(float xPos, float yPos) {
        super(Assets.getAsset(Assets.Images.CRATE, Texture.class), xPos, yPos);
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public <T> boolean useItem(T usedOn, Array<Float> objects) {
        ((Player)usedOn).fillAmmo(30);
        setRemove();
        return true;
    }
}
