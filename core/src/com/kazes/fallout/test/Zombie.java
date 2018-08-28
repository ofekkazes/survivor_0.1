package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Zombie extends ImageEx {

    float health;
    public boolean wander;

    public Zombie(Texture img, float xPos, float yPos) {
        super(img, xPos, yPos);

        init();
    }

    public void init() {
        this.health = 100;
        this.wander = true;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public float getHealth(){ return this.health; }

    public void subHealth(float points) {
        this.health -= points;

        if(this.health <= 0)
            this.setRemove();
    }
}
