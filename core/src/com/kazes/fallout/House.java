package com.kazes.fallout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class House extends Building{


    public House() {
        super("House", new Vector2(750, 80), "images/maps/medieval-tavern_10000.png", true);
    }
    public House(Vector2 pos, boolean isPlaced) {
        super("House", pos, "images/maps/medieval-tavern_10000.png", true);
    }
}
