package com.kazes.fallout.test.items;

import com.kazes.fallout.test.Player;
import com.kazes.fallout.test.inventory.Item;

public class LargeMedkit extends Item {
    public LargeMedkit() {
        super("medkit", "Heals 50 points");
    }

    @Override
    public <T> boolean useItem(T usedOn) {
        if(((Player)usedOn).getHealth() != 1) {
            ((Player) usedOn).addHealth(.5f);
            return true;
        }
        else return false;
    }
}