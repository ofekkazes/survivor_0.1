package com.kazes.fallout.test.items;

import com.kazes.fallout.test.inventory.Item;

public class Perfume extends Item {

    public Perfume() {
        super("perfume", "Zombies hate that smell and will get away");
    }

    @Override
    public <T> boolean useItem(T usedOn) {
        return false;
    }
}
