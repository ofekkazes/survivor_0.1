package com.kazes.fallout.test.items;

import com.kazes.fallout.test.inventory.Item;

public class Boots extends Item {
    public Boots() {
        super("hiking_boots", "Run 30% faster");
    }

    @Override
    public <T> boolean useItem(T usedOn) {
        return false;
    }
}
