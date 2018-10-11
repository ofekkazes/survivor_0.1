package com.kazes.fallout.test.items;

import com.kazes.fallout.test.inventory.Item;

public class Cap extends Item {
    public Cap() {
        super("cap", "Does absolutely nothing");
    }

    @Override
    public <T> boolean useItem(T usedOn) {
        return false;
    }
}
