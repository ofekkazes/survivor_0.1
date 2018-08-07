package com.kazes.fallout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Hospital extends Building{


    public Hospital() {
        super("Hospital", new Vector2(350, 80),"medieval-tavern_10000.png", true);
    }

    public Hospital(Vector2 pos, boolean isPlaced) {
        super("Hospital", pos, "medieval-tavern_10000.png", isPlaced);
    }
}
