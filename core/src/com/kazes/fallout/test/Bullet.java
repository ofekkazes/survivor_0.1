package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.kazes.fallout.test.physics.B2DBodyBuilder;
import com.kazes.fallout.test.physics.CollisionCategory;

/**
 * Physics bullet
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class Bullet extends ImageEx {
    private static ShaderProgram shader = new ShaderProgram(Gdx.files.internal("shaders/shakyCam.vs"), Gdx.files.internal("shaders/shakyCam.fs"));
    private float speed;
    private float timeToLive;

    public Bullet(World world, float xPos, float yPos, Vector2 direction    ) {
        super(createTexture(xPos, yPos), xPos, yPos);
        speed = 120;
        timeToLive = 3;

        CircleShape circle = new CircleShape();
        //circle.setAsBox(getWidth() / 2, getHeight() / 2, new Vector2(getWidth() / 2, getHeight() / 2), MathUtils.degreesToRadians * direction.angle());
        circle.setRadius(this.getWidth()/2);
        circle.setPosition(new Vector2(getWidth() / 2, getHeight() / 2));
        this.world = world;
        body = B2DBodyBuilder.createBody(world, circle, xPos, yPos, BodyDef.BodyType.DynamicBody, CollisionCategory.BULLET, CollisionCategory.BULLET_COLLIDER);
        body.setUserData(this);
        body.setTransform(body.getWorldCenter(), MathUtils.degreesToRadians * direction.angle());
        setOrigin(0, 0);
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        body.setBullet(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        float velX = MathUtils.cos(body.getAngle()) * speed; // X-component.
        float velY = MathUtils.sin(body.getAngle()) * speed; // Y-component.

        body.setLinearVelocity(velX, velY);


        timeToLive -= delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(timeToLive > 2.94) {
            shader.begin();
            shader.setUniformf("rand", new Vector3(Survivor.getInMeters(MathUtils.random(1, 4)), Survivor.getInMeters(MathUtils.random(1, 4)), 0));
            shader.end();
            batch.setShader(shader);
        }
        else batch.setShader(null);
    }

    public float getTimeToLive() { return timeToLive; }

    private static Texture createTexture(float xPos, float yPos) {
        Pixmap pixmap = new Pixmap( 16, 16, Pixmap.Format.RGBA8888 );

        pixmap.setColor( 0, 0, 0, 1 );
        pixmap.fillCircle( 8, 8, 8 );
        Texture pixmaptex = new Texture( pixmap );
        pixmap.dispose();
        return  pixmaptex;
    }
}
