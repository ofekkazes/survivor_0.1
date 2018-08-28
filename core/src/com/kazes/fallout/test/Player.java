package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.kazes.fallout.test.screens.GameScreen;
import org.omg.CORBA.ValueBaseHolder;


//rabbit valve gauge
//find way project
//assist truth blur
//void pulp shadow

public class Player extends AnimationActor {

    public Weapons weapon;
    public int cooldown;
    public float health;
    public Bag bag;
    public float hunger;
    public float time;
    public float thirst;
    public Vector2 playerTranslation;

    public Player(ObjectMap<String, Animation<TextureRegion>> t, Vector2 position) {
        super(t, "Player", position.x, position.y);
        weapon = Weapons.Pistol;
        bag = new Bag("Bag", Assets.getAsset(Assets.UI_SKIN, Skin.class));
        bag.setVisible(false);
        this.health = 10000;
        this.hunger = 100;
        this.thirst = 100;
        this.time = 0;
        this.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
        this.cooldown = 0;
        this.playerTranslation = new Vector2();
    }

    public void initPhysics(World world) {
        this.world = world;
        body = B2DBodyBuilder.createBody(world, getX(), getY(), getWidth(), getHeight(), BodyDef.BodyType.DynamicBody);
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
            hunger--;
            Gdx.app.log("Hunger", hunger + "");
        }
        if((time % 30) == 0) {
            thirst--;
            Gdx.app.log("Thirst", thirst + "");
        }
        if(cooldown <= 20)
            cooldown++;

        body.setLinearVelocity(playerTranslation.x, playerTranslation.y);
        //this.translate(playerTranslation.x, playerTranslation.y);
    }

    void addHealth(float amount) {
        this.health = (amount + this.health > 100) ? 100 : amount + this.health;
    }

    public boolean subHealth(float amount) {
        this.health = (0 > this.health - amount) ? 0 : this.health - amount;
        return this.health == 0;
    }

    public float getHealth() { return this.health; }

    public Weapons getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapons weapon) {
        this.weapon = weapon;
    }

    public void eat(float amount) {
        hunger += amount;
    }

    public void drink(float amount) {
        thirst += amount;
    }


}
