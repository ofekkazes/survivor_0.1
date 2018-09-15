package com.kazes.fallout.test.items;

import com.kazes.fallout.test.Player;
import com.kazes.fallout.test.inventory.Item;

public class AmmoCrate extends Item {

    public AmmoCrate() {
        super("crate", "Gives you 30 bullets");
    }

    @Override
    public <T> boolean useItem(T usedOn) {
        ((Player)usedOn).fillAmmo(30);
        return false;
    }
}
