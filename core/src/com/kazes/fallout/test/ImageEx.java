package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.kazes.fallout.test.physics.B2DBodyBuilder;
import com.kazes.fallout.test.physics.CollisionCategory;

/**
 * Image extending for the game usage
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class ImageEx extends Image {
    protected boolean remove;
    private Rectangle rectangle;
    World world;
    public Body body;
    float xOffset;
    float yOffset;
    private boolean xFlip = false;

    public int lateUpdateTicker = 0;
    private int currentTick = 0;

    public ImageEx(Texture texture, float xPos, float yPos) {
        super(texture);
        this.setPosition(xPos, yPos);
        this.setSize(Survivor.getInMeters(getWidth()), Survivor.getInMeters(getHeight()));
        this.rectangle = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
    public ImageEx(Texture texture, float xPos, float yPos, World world, BodyDef.BodyType type, CollisionCategory categoryBits, CollisionCategory maskBits) {
        super(texture);
        this.setPosition(xPos, yPos);
        this.setSize(Survivor.getInMeters(getWidth()), Survivor.getInMeters(getHeight()));
        this.rectangle = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        this.world = world;
        this.body = B2DBodyBuilder.createBody(world, xPos, yPos, getWidth(), getHeight(), type, categoryBits, maskBits);
    }

    public ImageEx(TextureRegion texture, float xPos, float yPos) {
        super(texture);
        this.setPosition(xPos, yPos);
        this.setSize(Survivor.getInMeters(getWidth()), Survivor.getInMeters(getHeight()));
        this.rectangle = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public void setLateUpdateTicker(int value) {
        this.lateUpdateTicker = value;
    }


    @Override
    public void act(float delta) {
        super.act(delta);

        if(body != null) {
            if(!hasActions()) {
                if (body.isAwake()) {
                    setX(body.getPosition().x - xOffset);
                    setY(body.getPosition().y - yOffset);
                    setRotation(MathUtils.radiansToDegrees * body.getAngle());
                }
            }
            else {
                body.setTransform(getX() + xOffset, getY() + yOffset, getRotation() * MathUtils.degreesToRadians);

            }
        }
        this.rectangle.setX(this.getX());
        this.rectangle.setY(this.getY());

        if(lateUpdateTicker != 0) {
            if (++currentTick == lateUpdateTicker) {
                currentTick = 0;
                doLateUpdateTicker();
            }
        }


        if(remove) {
            if(body != null)
                world.destroyBody(body);
            this.remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        getDrawable().draw(batch, xFlip ? getX() + getWidth() : getX(), getY(), xFlip ? -getWidth() : getWidth(), getHeight());
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

    public void setBody(World world, Body body) { this.world = world; this.body = body; }

    public void setOffset(float x, float y) {
        this.xOffset = x;
        this.yOffset = y;
        this.body.setTransform(getX() + xOffset, getY() + yOffset, 0f);
    }

    public void setXflip(boolean flip) {
        this.xFlip = flip;
    }

    public boolean getXflip() {
        return xFlip;
    }

    public void setRemove() { this.remove = true; }

    @Override
    protected void positionChanged() {

    }

    protected void doLateUpdateTicker() {

    }
}
