package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ObjectMap;
import com.kazes.fallout.test.physics.B2DBodyBuilder;
import com.kazes.fallout.test.physics.CollisionCategory;


//rabbit valve gauge
//find way project
//assist truth blur
//void pulp shadow

public class Player extends AnimationActor {

    public Weapons weapon;
    public int cooldown;
    public Bag bag;
    public Progress hunger;
    public Progress thirst;
    public Progress health;
    public float time;
    public Vector2 playerTranslation;
    public int ammo;

    public Player(ObjectMap<String, Animation<TextureRegion>> t, Vector2 position) {
        super(t, "Player", position.x, position.y);
        weapon = Weapons.Pistol;
        bag = new Bag("Bag", Assets.getAsset(Assets.UI_SKIN, Skin.class));
        bag.setVisible(false);
        this.health = new Progress(0, 1, 0.001f, false);
        this.health.setValue(1);
        this.hunger = new Progress(0, 1, 0.01f, false);
        this.hunger.setValue(1);
        this.thirst = new Progress(0, 1, 0.01f, false);
        this.thirst.setValue(1);
        this.time = 0;
        this.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
        this.cooldown = 0;
        this.playerTranslation = new Vector2();
        this.ammo = 50;
    }

    public void initPhysics(World world) {
        this.world = world;
        body = B2DBodyBuilder.createBody(world, getX(), getY(), getWidth(), getHeight(), BodyDef.BodyType.DynamicBody, CollisionCategory.FRIENDLY, CollisionCategory.FRIENDLY_COLLIDER);
        body.setFixedRotation(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);


        if(Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            bag.setVisible(!bag.isVisible());
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if(weapon == Weapons.Pistol)
                weapon = Weapons.SMG;
            else if(weapon == Weapons.SMG)
                weapon = Weapons.Pistol;
            Gdx.app.log("Weapon", "changed to " + weapon);
        }

        time += delta;

        if(time % 1 > 0.9f)
            time += 1-(time % 1);

        if((time % 60) == 0) {
            hunger.reduceValue(0.01f);
            Gdx.app.log("Progress", hunger + "");
        }
        if((time % 30) == 0) {
            thirst.reduceValue(0.01f);
            Gdx.app.log("Thirst", thirst + "");
        }
        if(cooldown <= 20)
            cooldown++;

        body.setLinearVelocity(playerTranslation.x, playerTranslation.y);
        //this.translate(playerTranslation.x, playerTranslation.y);
    }

    public void addHealth(float amount) {
        health.addValue(amount);
    }

    public float subHealth(float amount) {
        return health.reduceValue(amount);
    }

    public float getHealth() { return this.health.getValue(); }

    public Weapons getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapons weapon) {
        this.weapon = weapon;
    }

    public void eat(float amount) {
        hunger.addValue(amount);
    }

    public void drink(float amount) {
        thirst.addValue(amount);
    }


}
