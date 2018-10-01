package com.kazes.fallout.test.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.kazes.fallout.test.Survivor;

/**
 * First class enemy, slow and weak
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class Zombie extends Enemy {
    private float xChange;
    private float yChange;
    private float time;

    public Zombie(Texture img, float xPos, float yPos, World world) {
        super(img, xPos, yPos, world);
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        /*if (!this.hasActions()) {
            if (this.wander && this.getHealth() == 100) {// OR there is not clean line of sight (obstacles/hiding spots)
                wander = false;//this.addAction(Actions.sequence(delay(1.5f), Actions.moveBy(MathUtils.random(Survivor.getInMeters(-30), Survivor.getInMeters(30)), MathUtils.random(Survivor.getInMeters(-15), Survivor.getInMeters(15)), MathUtils.random(1.5f, 3.0f)), new WanderAction()));
            } else {
                //for (Actor actor : interactingObjects) {
                    float yTranslate = closestInteractingObject.getY() - this.getY();
                    float xTranslate = this.getX() - closestInteractingObject.getX();
                    if ((xTranslate > Survivor.getInMeters(-400) && xTranslate < Survivor.getInMeters(400)) || this.getHealth() != 100) { //OR THERE IS A CLEAN LINE OF SIGHT
                        if ((xTranslate > Survivor.getInMeters(-150) && xTranslate < Survivor.getInMeters(150)))
                            yTranslate = (yTranslate > 0) ? Survivor.getInMeters(12f) : Survivor.getInMeters(-12f);
                        else yTranslate = 0;
                        xTranslate = (xTranslate > 0) ? Survivor.getInMeters(-15f) : Survivor.getInMeters(15f);
                        //this.clearActions();
                        //this.getBody().setTransform(xTranslate + this.getX(), yTranslate + this.getY(), 0);
                        this.body.setLinearVelocity(xTranslate, yTranslate);
                        this.wander = false;
                    } else this.wander = true;
                //}
            }
        }*/
        if(!wander && closestInteractingObject != null) {
            float yTranslate = closestInteractingObject.getY() + closestInteractingObject.getHeight() / 2 - this.getY();
            float xTranslate = this.getX() - closestInteractingObject.getX() + closestInteractingObject.getWidth() / 2;
            if ((xTranslate > Survivor.getInMeters(-400) && xTranslate < Survivor.getInMeters(400)) || this.getHealth() != 100) { //OR THERE IS A CLEAN LINE OF SIGHT

                if ((xTranslate > Survivor.getInMeters(-150) && xTranslate < Survivor.getInMeters(150)))
                    yTranslate = (yTranslate > 0) ? Survivor.getInMeters(12f) : Survivor.getInMeters(-12f);
                else yTranslate = (yTranslate > 0) ? Survivor.getInMeters(3f) : Survivor.getInMeters(-3f);
                xTranslate = (xTranslate > 0) ? Survivor.getInMeters(-15f) : Survivor.getInMeters(15f);

                //this.body.setLinearVelocity(xTranslate, yTranslate);
                this.body.setLinearVelocity(xTranslate, yTranslate);
            }
            else this.wander = true;
        }
    }
}
