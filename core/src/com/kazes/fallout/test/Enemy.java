package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.physics.CollisionCategory;

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
    Vector2 prevPos;

    int frameCount;

    public Enemy(Texture img, float xPos, float yPos, World world) {
        super(img, xPos, yPos, world, BodyDef.BodyType.DynamicBody, CollisionCategory.ENEMY, CollisionCategory.ENEMY_COLLIDER);
        body.setFixedRotation(true);
        interactingObjects = new Array<Actor>();
        prevPos = new Vector2();
        frameCount = 0;
        init();
    }

    void init() {
        this.health = 100;
        this.wander = true;
    }

    public void addInteractingObject(Actor actor) {
        interactingObjects.add(actor);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(body.getPosition().x == prevPos.x && body.getPosition().y == prevPos.y) {
            frameCount++;
            if(frameCount > 100) {
                clearActions();
                this.wander = true;
                this.getBody().setLinearVelocity(MathUtils.random(1) , MathUtils.random(1));
                this.getBody().setLinearDamping(1f);
            }
        }
        else
            frameCount = 0;
        prevPos.set(body.getPosition().x, body.getPosition().y);
    }

    public float getHealth(){ return this.health; }

    public void subHealth(float points) {
        this.health -= points;

        if(this.health <= 0)
            this.setRemove();
    }

    public Array<Actor> getInteractingObjects() {
        return this.interactingObjects;
    }
}
