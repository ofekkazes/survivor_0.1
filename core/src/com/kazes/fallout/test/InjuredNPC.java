package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.kazes.fallout.test.dialogues.Dialog;
import com.kazes.fallout.test.items.Weapons;
import com.kazes.fallout.test.physics.B2DBodyBuilder;
import com.kazes.fallout.test.physics.CollisionCategory;
import com.kazes.fallout.test.screens.GameScreen;

/**
 * NPC the player should save
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class InjuredNPC extends ImageEx {
    private Dialog dialog; //each will have some things to say
    private float health;
    private Weapons weapon; //some will have weapon, others null
    private Jobs desire;
    //private Work type; //what his desires are (warrior, scientist, doctor)

    public InjuredNPC(World world, float xPos, float yPos, Weapons weapon) {
        super(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), xPos, yPos);
        this.world = world;
        this.body = B2DBodyBuilder.createBody(world, getX(), getY(), getWidth(), getHeight(), BodyDef.BodyType.StaticBody, CollisionCategory.FRIENDLY, CollisionCategory.FRIENDLY_COLLIDER);
        this.health = 50;
        this.weapon = weapon;
        desire = Jobs.setValue(MathUtils.random(0,4));
        Gdx.app.log("NPC", desire.toString());
        this.body.setTransform(body.getPosition(), 90 * MathUtils.degreesToRadians);
    }

    public float decHealth(float amount) {
        this.health -=amount;
        health = (health < 0) ? health = 0 : health;
        return health;
    }

    public void save() {
        switch (desire) {
            case Warrior: ClanProperties.Warriors++; break;
            case Doctor: ClanProperties.Doctors++; break;
            case Scientist: ClanProperties.Scientists++; break;
            case Merchant: ClanProperties.Merchant = true; break;
            case PayedMercenary: ClanProperties.PayedMercenaries++; break;
        }

        GameScreen.notifications.add("Clan updated: There are now " + ClanProperties.getValue(desire) + " "+ desire.toString() + "s");
        setRemove();
    }

}//in a future update
