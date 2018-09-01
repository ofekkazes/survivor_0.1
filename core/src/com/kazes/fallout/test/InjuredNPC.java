package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.kazes.fallout.test.physics.B2DBodyBuilder;
import com.kazes.fallout.test.physics.CollisionCategory;

public class InjuredNPC extends ImageEx {
    private Dialog dialog; //each will have some things to say
    private float health;
    private Weapons weapon; //some will have weapon, others null
    //private Work type; //what his desires are (warrior, scientist, doctor)

    public InjuredNPC(World world, float xPos, float yPos, Weapons weapon) {
        super(Assets.getAsset(Assets.Images.NPC_TEMP_2, Texture.class), xPos, yPos);
        this.world = world;
        this.body = B2DBodyBuilder.createBody(world, getX(), getY(), getWidth(), getHeight(), BodyDef.BodyType.StaticBody, CollisionCategory.FRIENDLY, CollisionCategory.FRIENDLY_COLLIDER);
        this.health = 50;
        this.weapon = weapon;
    }

    public float decHealth(float amount) {
        this.health -=amount;
        health = (health < 0) ? health = 0 : health;
        return health;
    }

}//in a future update
