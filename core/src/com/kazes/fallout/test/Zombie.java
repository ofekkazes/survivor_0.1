package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;


public class Zombie extends ImageEx {

    float health;
    public boolean wander;
    private Array<Actor> interactingObjects;

    public Zombie(Texture img, float xPos, float yPos, World world) {
        super(img, xPos, yPos, world, BodyDef.BodyType.DynamicBody);
        body.setFixedRotation(true);
        interactingObjects = new Array<Actor>();
        init();
    }

    public void init() {
        this.health = 100;
        this.wander = true;
    }

    public void addInteractingObject(Actor actor) {
        interactingObjects.add(actor);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(!this.hasActions()) {
            if(this.wander && this.getHealth() == 100)// OR there is not clean line of sight (obstacles/hiding spots)
                this.addAction(sequence(delay(1.5f), Actions.moveBy(MathUtils.random(Survivor.getInMeters(-30), Survivor.getInMeters(30)), MathUtils.random(Survivor.getInMeters(-15), Survivor.getInMeters(15)), MathUtils.random(1.5f, 3.0f)), new WanderAction()));
            else {
                for(Actor actor : interactingObjects) {
                    float yTranslate = actor.getY() - this.getY();
                    float xTranslate = this.getX() - actor.getX();
                    if ((xTranslate > Survivor.getInMeters(-400) && xTranslate < Survivor.getInMeters(400)) || this.getHealth() != 100) { //OR THERE IS A CLEAN LINE OF SIGHT
                        if ((xTranslate > Survivor.getInMeters(-150) && xTranslate < Survivor.getInMeters(150)))
                            yTranslate = (yTranslate > 0) ? Survivor.getInMeters(.4f) : Survivor.getInMeters(-.4f);
                        else yTranslate = 0;
                        xTranslate = (xTranslate > 0) ? Survivor.getInMeters(-.5f) : Survivor.getInMeters(.5f);
                        this.clearActions();
                        this.getBody().setTransform(xTranslate + this.getX(), yTranslate + this.getY(), 0);
                        this.wander = false;
                    } else this.wander = true;
                }
            }
        }
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
