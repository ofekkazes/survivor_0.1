package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class NPC extends ImageEx {
    private boolean moveable; //Can the actor move
    //DIALOGS

    public NPC(Texture texture, String name, float xPos, float yPos, boolean vFlip) {
        super(texture, vFlip, false);
        this.setName(name);
        this.setPosition(xPos, yPos);
    }

    public void update() {

    }
}
