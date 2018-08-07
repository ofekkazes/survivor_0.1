package com.kazes.fallout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class DropdownMenu
{
    private Rectangle rectangle;
    private Rectangle selected;
    private boolean appear;
    private ShapeRenderer shapeRenderer;

    private String[] objects;
    private int selectedObejctIndex;


    public DropdownMenu(String[] items) {
        this.rectangle = new Rectangle();
        this.selected = new Rectangle();
        this.appear = false;
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setColor(0.8f, 0.1f, 0.4f, 1);
        this.objects = items;
        this.selectedObejctIndex = -1;
    }

    public void update(Vector2 mousePos, boolean control) {
        if (appear) {
            if(this.rectangle.getWidth() == 0) {
                this.rectangle.x = mousePos.x;
                this.rectangle.y = mousePos.y;
                this.rectangle.width = 120;
                this.rectangle.height = (20 * this.objects.length);
            }
            if(!this.rectangle.contains(mousePos)) {
                this.selected.set(0,0,0,0);
                this.selectedObejctIndex = -1;
                if(Gdx.input.justTouched())
                    this.appear = false;
            }
            else {
                for(int i = 0; i < this.objects.length; i++) {
                    if(mousePos.x >= this.rectangle.x && mousePos.x <= this.rectangle.x + this.rectangle.width &&
                            mousePos.y >= this.rectangle.y + (20 * i) && mousePos.y <= this.rectangle.y + (20 * i) + 20) {
                        this.selected.set(this.rectangle.x, this.rectangle.y + (20 * i), this.rectangle.width, 20);

                        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.justTouched()) {
                            this.selectedObejctIndex = i;

                            this.appear = false;
                            this.rectangle.set(0,0,0,0);
                            this.selected.set(0,0,0,0);
                        }
                    }
                }
            }
        }
        else {
            if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && Gdx.input.justTouched() && control)  {
                this.rectangle.x = mousePos.x;
                this.rectangle.y = mousePos.y;
                this.rectangle.width = 120;
                this.rectangle.height = (20 * this.objects.length);
                //this.acceleration = 2f;
                this.appear = true;
            }
        }
        //System.out.println(this.selectedObejctIndex);
    }

    public void render(BitmapFont font, SpriteBatch batch, OrthographicCamera camera) {
        this.shapeRenderer.setProjectionMatrix(camera.combined);
        if(this.appear) {
            this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            {
                this.shapeRenderer.setColor(0.122f, 0.275f, 0.333f, 1);
                SuperObject.roundedRect(this.rectangle.x, this.rectangle.y, this.rectangle.width, this.rectangle.height, 5, this.shapeRenderer);
                this.shapeRenderer.setColor(0.078f, 0.192f, 0.235f, 1);
                SuperObject.roundedRect(this.selected.x, this.selected.y, this.selected.width, this.selected.height, 5, this.shapeRenderer);
            }
            this.shapeRenderer.end();
            batch.begin();
            for(int i = 0; i < this.objects.length; i++) {
                font.draw(batch, this.objects[i], this.rectangle.x + 5, this.rectangle.y + (20 * i) + 5);
            }
            batch.end();
        }

    }

    public int isSelected() {
        return this.selectedObejctIndex;
    }

    public void cancelSelection() {
        this.selectedObejctIndex = -1;
    }

    public void show() {this.appear = true;}


}
