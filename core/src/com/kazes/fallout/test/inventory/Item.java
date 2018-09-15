package com.kazes.fallout.test.inventory;


public abstract class Item {
    public String region;
    public String description;

    public Item(String textureRegion, String description) {
        this.region = textureRegion;
        this.description = description;
    }

    public String getTextureRegion() {
        return this.region;
    }
    public abstract <T> boolean useItem(T usedOn);
}
