package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;

public class Bullet extends ImageEx {
    private Vector2 direction;
    private float speed;
    private float timeToLive;

    public Bullet(float xPos, float yPos, Vector2 direction) {
        super(createTexture(xPos, yPos), xPos, yPos);
        speed = 2;
        timeToLive = 3;
        this.direction = direction;
        Vector2 velocity = direction.scl(speed * 40);
        this.addAction(forever(Actions.moveBy(velocity.x, velocity.y, 0.001f)));
    }
    @Override
    public void act(float delta) {
        super.act(delta);

        //Increase the direction by x speed, this is actually called velocity so let's name it correctly
        Vector2 velocity = direction.scl(speed);

        //Calculate angle
        float angle = velocity.angle();

        //translate(velocity.x, velocity.y);
        timeToLive -= delta;
    }

    public float getTimeToLive() { return timeToLive; }

    private static Texture createTexture(float xPos, float yPos) {
        Pixmap pixmap = new Pixmap( 64, 64, Pixmap.Format.RGBA8888 );
        pixmap.setColor( 0, 0, 0, 1 );
        pixmap.fillCircle( 32, 32, 4 );
        Texture pixmaptex = new Texture( pixmap );
        pixmap.dispose();
        return  pixmaptex;
    }
}
