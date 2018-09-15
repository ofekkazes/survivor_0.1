package com.kazes.fallout.test.items;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.kazes.fallout.test.Assets;
import com.kazes.fallout.test.ImageEx;
import com.kazes.fallout.test.inventory.Item;

public class ItemActor extends ImageEx {
    private static TextureAtlas icons = Assets.getAsset(Assets.Atlases.items, TextureAtlas.class);

    private Item pointer;

    public ItemActor(Item pointer, String texture, float xPos, float yPos) {
        super(icons.findRegion(texture).getTexture(), xPos, yPos);
        this.pointer = pointer;
    }
    public ItemActor(Item pointer, float xPos, float yPos) {
        super(icons.findRegion(pointer.region), xPos, yPos);
        this.pointer = pointer;
    }


    public Item getItem() {
        setRemove();
        return pointer;
    }
}
