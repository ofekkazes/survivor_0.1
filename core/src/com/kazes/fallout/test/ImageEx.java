package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.kazes.fallout.test.screens.GameScreen;

public class ImageEx extends Image {
    private boolean remove;
    private Rectangle rectangle;
    World world;
    Body body;


    public ImageEx(Texture texture, float xPos, float yPos) {
        super(texture);
        this.setPosition(xPos, yPos);
        this.setSize(Survivor.getInMeters(getWidth()), Survivor.getInMeters(getHeight()));
        this.rectangle = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
    public ImageEx(Texture texture, float xPos, float yPos, World world, BodyDef.BodyType type) {
        super(texture);
        this.setPosition(xPos, yPos);
        this.setSize(Survivor.getInMeters(getWidth()), Survivor.getInMeters(getHeight()));
        this.rectangle = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        this.world = world;
        this.body = B2DBodyBuilder.createBody(world, xPos, yPos, getWidth(), getHeight(), type);
    }

    public ImageEx(TextureRegion texture, float xPos, float yPos) {
        super(texture);
        this.setPosition(xPos, yPos);
        this.setSize(Survivor.getInMeters(getWidth()), Survivor.getInMeters(getHeight()));
        this.rectangle = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }




    @Override
    public void act(float delta) {
        super.act(delta);

        if(body != null) {
            if(!hasActions()) {
                if (body.isAwake()) {
                    setX(body.getPosition().x);
                    setY(body.getPosition().y);
                    setRotation(MathUtils.radiansToDegrees * body.getAngle());
                }
            }
            else {
                body.setTransform(getX(), getY(), 0);
            }
        }
        this.rectangle.setX(this.getX());
        this.rectangle.setY(this.getY());
        if(remove) {
            if(body != null)
                world.destroyBody(body);
            this.remove();
        }
    }


    public void translate(float x, float y) {
        this.setPosition(this.getX() + x, this.getY() + y);
    }

    public Rectangle getRectangle() {
        return this.rectangle;
    }

    public Body getBody() { return this.body; }

    public Vector2 getOrigin() {
        return new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }

    public void setRemove() { this.remove = true; }

}
