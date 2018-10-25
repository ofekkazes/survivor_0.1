package com.kazes.fallout.test.items;

import com.kazes.fallout.test.inventory.Item;

public class Axe_Story extends Item {
    public Axe_Story() {
        super("axe", "Story item");
    }

    @Override
    public <T> boolean useItem(T usedOn) {
        return false;
    }
}
