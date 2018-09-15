package com.kazes.fallout.test.items;

import com.kazes.fallout.test.Player;
import com.kazes.fallout.test.inventory.Item;

public class SmallMedkit extends Item {
    public SmallMedkit() {
        super("medkit", "Heals 20 points");
    }

    @Override
    public <T> boolean useItem(T usedOn) {
        if(((Player)usedOn).getHealth() != 1) {
            ((Player) usedOn).addHealth(.2f);
            return true;
        }
        else return false;
    }
}
