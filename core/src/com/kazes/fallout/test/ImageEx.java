package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ImageEx extends Image {

    Texture texture;
    boolean xFlipped;
    boolean yFlipped;

    private ImageEx flipped;
    private ImageEx original;

    private Rectangle rectangle;

    public ImageEx(Texture texture) {
        super(texture);
        this.texture = texture;
        xFlipped = false;
        yFlipped = false;

        original = new ImageEx(new TextureRegion(texture,0,0, texture.getWidth(), texture.getHeight()));
        flipped = new ImageEx(ImageEx.flipTexture(texture, true, false));

        this.rectangle = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
    public ImageEx(Texture texture, float xPos, float yPos) {
        super(texture);
        this.texture = texture;
        xFlipped = false;
        yFlipped = false;

        original = new ImageEx(new TextureRegion(texture,0,0, texture.getWidth(), texture.getHeight()));
        flipped = new ImageEx(ImageEx.flipTexture(texture, true, false));

        this.setPosition(xPos, yPos);

        this.rectangle = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public ImageEx(TextureRegion texture) { this(texture, 0, 0); }

    public ImageEx(TextureRegion texture, float xPos, float yPos) {
        super(texture);
        this.setPosition(xPos, yPos);
        this.rectangle = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public ImageEx(Texture texture, boolean xFlip, boolean yFlip) {
        super(ImageEx.flipTexture(texture, xFlip, yFlip));
        this.rectangle = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.rectangle.setX(this.getX());
        this.rectangle.setY(this.getY());

    }

    public void translate(float x, float y) {
        this.setPosition(this.getX() + x, this.getY() + y);
    }

    public void translateX(float x) {
        translate(x, 0);
    }

    public void translateY(float y) {
        translate(0, y);
    }

    public void flip(boolean xFlip, boolean yFlip) {
        if(!xFlipped)
            this.setDrawable(flipped.getDrawable());
        else
            this.setDrawable(original.getDrawable());
        xFlipped = !xFlipped;
    }

    public Rectangle getRectangle() {
        return this.rectangle;
    }

    public static TextureRegion flipTexture(Texture texture, boolean xFlip, boolean yFlip) {
        TextureRegion tmp = new TextureRegion(texture,0,0, texture.getWidth(), texture.getHeight());
        tmp.flip(xFlip, yFlip);
        return tmp;
    }

    public Vector2 getOrigin() {
        return new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }

}
