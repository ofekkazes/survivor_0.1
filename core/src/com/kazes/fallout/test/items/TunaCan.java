package com.kazes.fallout.test.items;

import com.kazes.fallout.test.Player;
import com.kazes.fallout.test.inventory.Item;

public class TunaCan extends Item {
    public TunaCan() {
        super("tuna_cans", "Used to cure hunger");
    }

    @Override
    public <T> boolean useItem(T usedOn) {
        ((Player)usedOn).eat(.1f);
        return true;
    }
}
