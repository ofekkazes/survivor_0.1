package com.kazes.fallout.test.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.kazes.fallout.test.WindowEx;
import com.kazes.fallout.test.screens.GameScreen;
/**
 * Top inventory, always on screen
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class FastInventoryActor extends WindowEx {

    Inventory inventory;

    public FastInventoryActor(Inventory inventory, DragAndDrop dragAndDrop, Skin skin, Stage parent) {
        super(skin);
        this.setMovable(false);
        defaults().space(8);
        row().fill().expandX();

        int i = 0;
        for (final Slot slot : inventory.getSlots()) {
            SlotActor slotActor = new SlotActor(skin, slot, parent, false);
            dragAndDrop.addSource(new SlotSource(slotActor));
            dragAndDrop.addTarget(new SlotTarget(slotActor));
            add(slotActor);
            slotActor.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(slot.getItem() != null) {
                        slot.getItem().useItem(GameScreen.player);
                        slot.take(1);
                    }
                }
            });

            i++;
            if (i % 5 == 0) {
                row();
            }
        }

        pack();

        // it is hidden by default
        setVisible(true);

        this.inventory = inventory;

        setScale(0.8f, 0.8f);
        //setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10);
        setPosition(Gdx.graphics.getWidth() / 2 - this.getWidth() / 2, Gdx.graphics.getHeight() - this.getHeight());

    }

    public Inventory getInventory() {
        return inventory;
    }
}
