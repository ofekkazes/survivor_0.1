package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.inventory.FastInventoryActor;
import com.kazes.fallout.test.items.ItemActor;

/**
 * Action to add an item to the inventory
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-10-28
 */
public class PickItem extends Action {
    FastInventoryActor inventoryActor;
    ItemActor itemActor;
    int amount;

    public PickItem(FastInventoryActor inventoryActor, ItemActor itemActor, int amount) {
        this.inventoryActor = inventoryActor;
        this.itemActor = itemActor;
        this.amount = amount;
    }

    @Override
    public boolean act(float delta) {
        inventoryActor.getInventory().store(itemActor.getItem(), amount);
        return true;
    }
}
