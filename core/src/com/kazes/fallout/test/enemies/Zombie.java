package com.kazes.fallout.test.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.Survivor;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * First class enemy, slow and weak
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class Zombie extends Enemy {


    public Zombie(Texture img, float xPos, float yPos, World world) {
        super(img, xPos, yPos, world);
        body.setFixedRotation(true);
        interactingObjects = new Array<Actor>();
        prevPos = new Vector2();
        frameCount = 0;
        init();
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        if(!this.hasActions()) {
            if(this.wander && this.getHealth() == 100)// OR there is not clean line of sight (obstacles/hiding spots)
                this.addAction(Actions.sequence(delay(1.5f), Actions.moveBy(MathUtils.random(Survivor.getInMeters(-30), Survivor.getInMeters(30)), MathUtils.random(Survivor.getInMeters(-15), Survivor.getInMeters(15)), MathUtils.random(1.5f, 3.0f)), new WanderAction()));
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

}
