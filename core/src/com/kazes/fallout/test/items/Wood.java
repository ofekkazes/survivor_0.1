package com.kazes.fallout.test.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.Assets;
import com.kazes.fallout.test.ImageEx;
import com.kazes.fallout.test.items.Carryable;
@Deprecated
public class Wood extends ImageEx implements Carryable {
    private String desc;

    public Wood(float xPos, float yPos) {
        super(Assets.getAsset(Assets.Images.TREELOG, Texture.class), xPos, yPos);

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
