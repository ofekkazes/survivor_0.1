package com.kazes.fallout.test.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.AnimationEx;
import com.kazes.fallout.test.ImageEx;
import com.kazes.fallout.test.items.AmmoCrate;
import com.kazes.fallout.test.items.ItemActor;
import com.kazes.fallout.test.items.Items;
import com.kazes.fallout.test.physics.CollisionCategory;
import com.kazes.fallout.test.screens.GameScreen;
import com.kazes.fallout.test.screens.Screens;

/**
 * The father class for all enemies
 * @author Ofek Kazes
 * @version 1.05
 * @since 2018-10-28
 */
public abstract class Enemy extends AnimationEx {
    float health;
    public boolean wander;
    Array<ImageEx> interactingObjects;
    ImageEx closestInteractingObject;
    Vector2 prevPos;
    boolean die;

    int frameCount;
    //MagicAttack hurt;

    public Enemy(TextureAtlas img, float xPos, float yPos, World world) {
        super(img, xPos, yPos);
        initPhysics(world, CollisionCategory.ENEMY, CollisionCategory.ENEMY_COLLIDER);
        body.setUserData(this);
        interactingObjects = new Array<ImageEx>();
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

    public void addInteractingObject(ImageEx actor) {
        interactingObjects.add(actor);
        calcClosest();
    }
    public void addInteractingObject(Actor actor) {
        interactingObjects.add((ImageEx)actor);
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
                changeAnimation("walk");
                //clearActions();
                this.wander = false;
                this.body.setLinearVelocity(MathUtils.random(-0.84f, 0.84f), MathUtils.random(-0.84f, 0.84f));
                this.body.setLinearDamping(1f);
            }
        }
        else {
            frameCount = 0;
        }
        prevPos.set(body.getPosition().x, body.getPosition().y);
        if(health == 0) {
            changeAnimation("die");
            if(isAnimationFinished())
                die = true;
        } else {
            if(!getCurrentKey().contains("attack")) {
                if (body.getLinearVelocity().isZero() || body.getLinearVelocity().x < 0.1 && body.getLinearVelocity().x > -0.1f) {
                    changeAnimation("crouch");
                    changeSpeed(1 / 6f);
                } else if (body.getLinearVelocity().x > 0.5 && body.getLinearVelocity().x < -0.5f) {
                    changeAnimation("run");
                    changeSpeed(1 / 14f);
                } else {
                    changeAnimation("walk");
                    changeSpeed(1 / 12f);
                }
            }
            if(body.getLinearVelocity().x > 0)
                flipAnimation(false);
            else flipAnimation(true);
        }
        if(die) {
            Screens.getCurrent().getItems().addActor(new ItemActor(Items.getRandom(), getX(), getY()));
            setRemove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

    }

    public float getHealth(){ return this.health; }

    public void subHealth(float points) {
        this.health -= points;
        //this.hurt.start();

        if(this.health <= 0) {

            this.health = 0;
        }
    }

    public void setHealth(float points) {
        this.health = points;

    }

    public Array<ImageEx> getInteractingObjects() {
        return this.interactingObjects;
    }

    @Override
    protected void doLateUpdateTicker() {
        calcClosest();
    }

    public void calcClosest() {
        closestInteractingObject = GameScreen.closestTo(interactingObjects, this);
    }

    public boolean isDead() {
        return die;
    }
}
