package com.kazes.fallout.test.items;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.Assets;
import com.kazes.fallout.test.ImageEx;
import com.kazes.fallout.test.Player;
import com.kazes.fallout.test.items.Carryable;
@Deprecated
public class Water extends ImageEx implements Carryable {
    private String desc;

    public Water(float xPos, float yPos) {
        super(Assets.getAsset(Assets.Images.WATER, Texture.class), xPos, yPos);
        desc = "Used to cure thirst";
    }
    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public <T> boolean useItem(T usedOn, Array<Float> objects) {
        ((Player)usedOn).drink(.1f);

        return true;
    }
}
