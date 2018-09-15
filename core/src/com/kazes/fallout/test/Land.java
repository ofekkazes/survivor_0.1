package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Legacy code
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class Land extends Actor {
    private Texture texture;
    private Vector2[] avalibleLocations;

    public Land(String filename) {
        this.texture = new Texture(filename);

        this.setBounds(getX(), getY(), this.texture.getWidth(), this.texture.getWidth());
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,this.getX(),getY(),this.getOriginX(),this.getOriginY(),this.getWidth(),
                this.getHeight(),this.getScaleX(), this.getScaleY(),this.getRotation(),0,0,
               texture.getWidth(),texture.getHeight(),false,false);
    }
}
