package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

/**
 * NPC a player will be interacting
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class NPC extends ImageEx {
    private Weapons weapon;
    private Array<Bullet> bullets;
    private int cooldown;
    private boolean moveable; //Can the actor move
    //DIALOGS

    public NPC(Texture texture, String name, float xPos, float yPos, Weapons weapon) {
        super(texture, xPos, yPos);
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
