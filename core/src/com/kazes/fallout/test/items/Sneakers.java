package com.kazes.fallout.test.items;

import com.kazes.fallout.test.inventory.Item;

public class Sneakers extends Item {
    public Sneakers(String textureRegion, String description) {
        super("sneakers", "Run 20% faster");
    }

    @Override
    public <T> boolean useItem(T usedOn) {
        return false;
    }
}
