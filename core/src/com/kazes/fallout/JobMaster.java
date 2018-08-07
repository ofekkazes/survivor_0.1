package com.kazes.fallout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import java.awt.*;

public class JobMaster {

    private boolean focus;
    private boolean show;
    private Vector3 vec;

    public JobMaster() {
        this.focus = false;
        this.show = false;
        this.vec = new Vector3(50, 50, 0);
    }

    public void show() {
        this.show = true;
    }

    public void update() {
        if(Gdx.input.getY() > 50  && 80 > Gdx.input.getY()&& Gdx.input.isTouched())
            this.show = false;

    }

    public void render(OrthographicCamera camera) {
        if(this.show) {
            vec = new Vector3(50, 50, 0);
            camera.unproject(vec);

            Color temp = SuperObject.shapeRenderer.getColor();
            SuperObject.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            {
                SuperObject.shapeRenderer.setColor(0.078f, 0.192f, 0.235f, 1f);
                SuperObject.roundedRect(vec.x, vec.y,200, 300, 5, SuperObject.shapeRenderer);


                for (int i = 0; i< Colony.survivors.size(); i++) {
                    SuperObject.shapeRenderer.setColor(0,0,0, 1f);
                    SuperObject.roundedRect(vec.x + 150, vec.y + 40 * i, 40, 25, 5, SuperObject.shapeRenderer);
                }SuperObject.shapeRenderer.setColor(temp);
            }
            SuperObject.shapeRenderer.end();
            SuperObject.gameBatch.begin();
            {
                int i = 0;
                int x = 0;
                int y = 0;
                for (Survivor s: Colony.survivors  ) {
                    if(s instanceof Person) {
                        SuperObject.worldFont.draw(SuperObject.gameBatch, "Person " + ++x, vec.x + 20, vec.y + 20 + 30 * i - SuperObject.worldFont.getLineHeight() / 2);
                    }
                    if(s instanceof Soldier) {
                        SuperObject.worldFont.draw(SuperObject.gameBatch, "Soldier" + ++y, vec.x + 20, vec.y + 20 + 30 * i - SuperObject.worldFont.getLineHeight() / 2);
                    }
                    SuperObject.worldFont.draw(SuperObject.gameBatch, "Change", vec.x + 150, vec.y + 20 + 30 * i - SuperObject.worldFont.getLineHeight() / 2);
                    i++;
                }
            }
            SuperObject.gameBatch.end();
        }
    }
}
