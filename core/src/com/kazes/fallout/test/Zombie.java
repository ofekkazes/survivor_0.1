package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Zombie extends ImageEx {

    float health;
    public boolean wander;

    public Zombie(Texture img, Vector2 position, boolean verticalFlip) {
        super(img, verticalFlip, false);
        this.setPosition(position.x, position.y);

        init();
    }
    public Zombie(Texture img, int xPos, int yPos, boolean verticalFlip) {
        super(img, verticalFlip, false);
        this.setPosition(xPos, yPos);

        init();
    }

    public void init() {
        this.health = 100;
        this.wander = true;
    }

    public float getHealth(){ return this.health; }

    public void subHealth(float points) {
        this.health -= points;

        if(this.health < -1)
            this.health = 0;
    }
}
