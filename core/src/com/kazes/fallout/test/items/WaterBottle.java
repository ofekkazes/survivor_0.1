package com.kazes.fallout.test.items;

import com.kazes.fallout.test.Player;
import com.kazes.fallout.test.inventory.Item;

public class WaterBottle extends Item {
    public WaterBottle() {
        super("tuna_cans", "Used to cure thirst");
    }

    @Override
    public <T> boolean useItem(T usedOn) {
        ((Player)usedOn).drink(.1f);
        return true;
    }
}
