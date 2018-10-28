package com.kazes.fallout.test.inventory;

/**
 * Simple class for item image, description, and price
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-10-28
 */
public abstract class Item {
    public String region;
    public String description;
    public float price;

    public Item(String textureRegion, String description) {
        this.region = textureRegion;
        this.description = description;
        this.price = 0.05f;
    }

    public String getTextureRegion() {
        return this.region;
    }
    public abstract <T> boolean useItem(T usedOn);
}
