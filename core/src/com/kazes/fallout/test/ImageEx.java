package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ImageEx extends Image {
    private boolean remove;
    private Rectangle rectangle;
    World world;
    Body body;


    public ImageEx(Texture texture, float xPos, float yPos) {
        super(texture);
        this.setPosition(xPos, yPos);
        this.rectangle = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public ImageEx(TextureRegion texture, float xPos, float yPos) {
        super(texture);
        this.setPosition(xPos, yPos);
        this.rectangle = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }



    @Override
    public void act(float delta) {
        super.act(delta);
        this.rectangle.setX(this.getX());
        this.rectangle.setY(this.getY());
        if(body != null) {
            if(body.isAwake()) {
                setX(body.getPosition().x);
                setY(body.getPosition().y);
            }
        }

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
