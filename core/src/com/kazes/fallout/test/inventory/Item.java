package com.kazes.fallout.test.inventory;

public enum Item {
    MEDICINE("medicine"),
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
}