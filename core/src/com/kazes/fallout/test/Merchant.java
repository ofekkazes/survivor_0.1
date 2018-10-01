package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.kazes.fallout.test.inventory.Inventory;
import com.kazes.fallout.test.inventory.MerchantInventoryActor;

public class Merchant extends ImageEx {
    final MerchantInventoryActor inventory;

    public Merchant(float xPos, float yPos, Stage parent) {
        super(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), xPos, yPos);
        this.inventory = new MerchantInventoryActor(new Inventory(15), InputHelper.dragAndDrop, Assets.getAsset(Assets.UI_SKIN, Skin.class), parent);
        parent.addActor(this.inventory);
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                inventory.show("buy");
            }
        });
    }
}
