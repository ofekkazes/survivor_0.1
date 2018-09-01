package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class ParallaxBackground extends Actor {

    private float scroll;
    private Array<Texture> layers;
    private final int LAYER_SPEED_DIFFERENCE = 2;

    float x,y,width,height,scaleX,scaleY;
    boolean flipX,flipY;
    float srcX;

    private float speed;

    public ParallaxBackground(Array<Texture> textures, float width, float height){
        layers = textures;
        for(int i = 0; i <textures.size;i++){
            layers.get(i).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        scroll = 0;
        speed = 0;

        //x = y = originX = originY = rotation = srcY = 0;
        this.width =  width;
        this.height = height / 2;
        y = height / 2;
        scaleX = scaleY = 1;
        flipX = flipY = false;
        setVisible(true);
    }

    public void setSpeed(float newSpeed){
        this.speed = newSpeed;
    }

    public void setXPos(float x) {
        this.x = x;
        setX(x);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);

        scroll+=speed;
        for(int i = 0;i<layers.size;i++) {
            srcX = scroll + i*this.LAYER_SPEED_DIFFERENCE *scroll;
            batch.draw(layers.get(i), x, y, 0, 0, width, height,scaleX,scaleY,0,(int)srcX,0,layers.get(i).getWidth(),layers.get(i).getHeight(),false,false);
        }
    }

    public void dispose() {
        for(int i = 0; i < layers.size; i++)
            layers.get(i).dispose();
    }
}
