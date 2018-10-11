package com.kazes.fallout.test.items;

import com.kazes.fallout.test.inventory.Item;

public class RunningShoes extends Item {
    public RunningShoes() {
        super("running_shoes", "Run 40% faster");
    }

    @Override
    public <T> boolean useItem(T usedOn) {
        return false;
    }
}
