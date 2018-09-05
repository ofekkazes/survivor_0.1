package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.items.Carryable;

public class BearTrap extends ImageEx implements Trap, Carryable {
    String desc;
    public BearTrap(float xPos, float yPos) {
        super(Assets.getAsset(Assets.Images.BEARTRAP, Texture.class), xPos, yPos);
        desc = "Use this trap to slay one zombie";
    }

    @Override
    public <T> boolean useItem(T usedOn, Array<Float> objects) {
        this.setPosition(objects.get(0), objects.get(1));
        ((Group)usedOn).addActor(this);
        return true;
    }

    @Override
    public String getDescription() {
        return this.desc;
    }
}
