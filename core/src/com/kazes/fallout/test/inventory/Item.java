package com.kazes.fallout.test.inventory;

import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.items.Carryable;

public enum Item implements Carryable {
    BEAR_TRAP("bear_trap"),
    CRATE("crate"),
    MEDKIT("medkit"),
    TUNA("tuna"),
    WATER("water"),
    WOOD("wood");

    private String textureRegion;

    Item(String textureRegion) {
        this.textureRegion = textureRegion;
    }

    public String getTextureRegion() {
        return textureRegion;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public <T> boolean useItem(T usedOn, Array<Float> objects) {
        return false;
    }
}