package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.inventory.FastInventoryActor;
import com.kazes.fallout.test.inventory.Item;
import com.kazes.fallout.test.inventory.Slot;

/**
 * Action to remove an item from the inventory
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-10-28
 */
public class TakeItem extends Action {
    FastInventoryActor inventoryActor;
    Item item;
    int amount;

    public TakeItem(FastInventoryActor inventoryActor, Item item, int amount) {
        this.inventoryActor = inventoryActor;
        this.item = item;
        this.amount = amount;
    }
    @Override
    public boolean act(float delta) {
        for(Slot slot : inventoryActor.getInventory().getSlots()) {
            if(slot.getItem() != null && slot.getItem().getClass().equals(item.getClass())) {
                slot.take(amount);
                return true;
            }
        }
        return false;
    }
}
