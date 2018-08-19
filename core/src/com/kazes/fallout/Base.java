package com.kazes.fallout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Base extends Building{


    public Base() {
        super("Base", new Vector2(750, 820), "images/maps/medieval-tavern_10000.png", true);
    }

    public Base(Vector2 pos, boolean isPlaced) {
        super("Base", pos, "images/maps/medieval-tavern_10000.png", isPlaced);
    }


}
