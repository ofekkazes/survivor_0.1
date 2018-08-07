package com.kazes.fallout.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.kazes.fallout.SuperObject;

import java.awt.*;

public class Button {
    private String text;
    private Rectangle rectangle;
    private float radius;
    private Vector3 location;
    private Vector3 initLoc;
    private boolean isSelected;
    private Color color;
    private Color selectedColor;
    private int counter;

    public Button(String text, Rectangle rectangle) {
        this(text, rectangle, 5f);
    }

    public Button(String text, Rectangle rectangle, float radius) {
        this.text = text;
        this.rectangle = rectangle;
        this.location = new Vector3(rectangle.x, rectangle.y, 0);
        this.initLoc = new Vector3(rectangle.x, rectangle.y, 0);
        this.radius = radius;

        this.isSelected = false;
        this.counter = 0;

        this.color = new Color(0.122f, 0.275f, 0.333f, 1f);
        this.selectedColor = new Color(0.078f, 0.192f, 0.235f,1f);
    }

    public void update() {
        this.isSelected = false;
        this.rectangle = new Rectangle((int)this.location.x, (int)this.location.y, this.rectangle.width, this.rectangle.height);
        if(rectangle.contains(SuperObject.mouseInWorld2D.x, SuperObject.mouseInWorld2D.y)) {
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.justTouched() && Gdx.input.isTouched()){
                this.isSelected = true;
            }
            if(!Gdx.input.isTouched())
               this.isSelected = false;
        }
    }

    public void render(OrthographicCamera camera) {
        if(this.isSelected) this.counter = Gdx.graphics.getFramesPerSecond() / 5;

        if(counter > 0) {
            SuperObject.shapeRenderer.setColor(this.selectedColor);
            counter--;
        } else SuperObject.shapeRenderer.setColor(this.color);

        this.location = new Vector3(this.initLoc.x, this.initLoc.y, 0);
        camera.unproject(this.location);

        SuperObject.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        SuperObject.roundedRect(location.x, location.y, rectangle.width, rectangle.height, this.radius, SuperObject.shapeRenderer);
        SuperObject.shapeRenderer.end();

        SuperObject.windowBatch.begin();
        SuperObject.windowFont.draw(SuperObject.windowBatch, this.text, this.initLoc.x, Gdx.graphics.getHeight() - this.initLoc.y - SuperObject.windowFont.getLineHeight() / 2);
        SuperObject.windowBatch.end();

        SuperObject.shapeRenderer.setColor(this.color);
    }

    public boolean isSelected() { return this.isSelected; }
}
