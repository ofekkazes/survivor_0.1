package com.kazes.fallout.test.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.Assets;
import com.kazes.fallout.test.ImageEx;
import com.kazes.fallout.test.MagicAttack;
import com.kazes.fallout.test.Survivor;
import com.kazes.fallout.test.physics.CollisionCategory;
import com.kazes.fallout.test.screens.GameScreen;

/**
 * The father class for all enemies
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public abstract class Enemy extends ImageEx {
    float health;
    public boolean wander;
    Array<Actor> interactingObjects;
    Actor closestInteractingObject;
    Vector2 prevPos;

    int frameCount;
    //MagicAttack hurt;

    public Enemy(Texture img, float xPos, float yPos, World world) {
        super(img, xPos, yPos, world, BodyDef.BodyType.DynamicBody, CollisionCategory.ENEMY, CollisionCategory.ENEMY_COLLIDER);
        body.setFixedRotation(true);
        body.setUserData(this);
        interactingObjects = new Array<Actor>();
        prevPos = new Vector2();
        frameCount = 0;
        init();
        this.setLateUpdateTicker(Gdx.graphics.getFramesPerSecond());
        //hurt = new MagicAttack(Assets.getAsset(Assets.ParticleEffects.blood, ParticleEffect.class), xPos, yPos);
    }

    void init() {
        this.health = 100;
        this.wander = true;
    }

    public void addInteractingObject(Actor actor) {
        interactingObjects.add(actor);
        calcClosest();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        //hurt.act(delta);
        //hurt.setPos(getX() + getWidth() / 2, getY() + getHeight() / 2);
        if(body.getPosition().x == prevPos.x && body.getPosition().y == prevPos.y) {
            frameCount++;
            if(frameCount > MathUtils.random(10, 25)) {
                //clearActions();
                this.wander = false;
                this.body.setLinearVelocity(MathUtils.random(-0.84f, 0.84f), MathUtils.random(-0.84f, 0.84f));
                this.body.setLinearDamping(1f);
            }
        }
        else
            frameCount = 0;
        prevPos.set(body.getPosition().x, body.getPosition().y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        //hurt.draw(batch, parentAlpha);
    }

    public float getHealth(){ return this.health; }

    public void subHealth(float points) {
        this.health -= points;
        //this.hurt.start();

        if(this.health <= 0)
            this.setRemove();
    }

    public Array<Actor> getInteractingObjects() {
        return this.interactingObjects;
    }

    @Override
    protected void doLateUpdateTicker() {
        calcClosest();
    }

    public void calcClosest() {
        closestInteractingObject = GameScreen.closestTo(interactingObjects, this);
    }
}
