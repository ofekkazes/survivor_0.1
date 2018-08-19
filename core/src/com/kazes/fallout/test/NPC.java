package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class NPC extends ImageEx {
    private Weapons weapon;
    private Array<Bullet> bullets;
    private int cooldown;
    private boolean moveable; //Can the actor move
    //DIALOGS

    public NPC(Texture texture, String name, float xPos, float yPos, boolean vFlip, Weapons weapon) {
        super(texture, vFlip, false);
        this.setName(name);
        this.setPosition(xPos, yPos);
        this.weapon = weapon;
        this.cooldown = 0;

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        cooldown++;
    }

    public Weapons getWeapon() {
        return weapon;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void resetCooldown() {
        this.cooldown = 0;
    }
}
