package com.kazes.fallout.test.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kazes.fallout.test.WindowEx;
import com.kazes.fallout.test.items.AmmoCrate;
import com.kazes.fallout.test.items.SmallMedkit;
import com.kazes.fallout.test.screens.GameScreen;

/**
 * Merchant based on the inventory system
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-10-28
 */
public class MerchantInventoryActor extends WindowEx {

    Inventory inventory;
    Label moneyLabel;

    Inventory fastInventory;
    Inventory bagInventory;

    public MerchantInventoryActor(Inventory inventory, Skin skin, Stage parent, final Inventory fastInventory, Inventory bag) {
        super(skin);
        this.setMovable(false);
        defaults().space(8);
        row().fill().expandX();

        this.fastInventory = fastInventory;
        this.bagInventory = bag;

        int i = 0;
        for (final Slot slot : inventory.getSlots()) {
            SlotActor slotActor = new SlotActor(skin, slot, parent, false);
            add(slotActor);
            slotActor.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(slot.getItem() != null) {
                        if(GameScreen.bitcoin >= slot.getItem().price) {
                            GameScreen.bitcoin -= slot.getItem().price;
                            moneyLabel.setText(GameScreen.bitcoin + "B");
                            if (!fastInventory.store(slot.getItem(), 1))
                                bagInventory.store(slot.getItem(), 1);

                        }
                    }
                }
            });

            i++;
            if (i % 5 == 0) {
                row();
            }
        }
        row();
        this.add();
        this.add();
        this.add();
        this.add();
        this.moneyLabel = new Label(GameScreen.bitcoin + "B", skin);
        this.add(moneyLabel);

        pack();



        // it is hidden by default
        setVisible(false);

        this.inventory = inventory;

        getInventory().store(new SmallMedkit(), 1);
        getInventory().store(new AmmoCrate(), 1);

        //setScale(0.8f, 0.8f);
        //setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10);
        setPosition(Gdx.graphics.getWidth() / 2 - this.getWidth() / 2, Gdx.graphics.getHeight() / 2 - this.getHeight() / 2);

    }

    public Inventory getInventory() {
        return inventory;
    }

    public void show(String action) {
        if(action == "buy") {

        }
        if(action == "sell") {

        }
        setVisible(true);
        this.moneyLabel.setText(GameScreen.bitcoin + "B");
    }


}
