package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * Parallax used in the game
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 * @see com.kazes.fallout.test.screens.GameScreen
 */
public class ParallaxBackground extends Actor {
    SideScrollingCamera camera;
    private Array<Texture> layers;

    float x,y,width,height,scaleX,scaleY;
    boolean flipX,flipY;

    public ParallaxBackground(Array<Texture> textures, Camera cam){
        layers = textures;
        for(int i = 0; i <textures.size;i++){
            layers.get(i).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        camera = (SideScrollingCamera)cam;
        this.width =  camera.viewportWidth;
        this.height = camera.viewportHeight / 2;
        y = camera.viewportHeight / 2;
        scaleX = scaleY = 1;
        flipX = flipY = false;
        setVisible(true);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);

        if(camera != null) {
            for ( int i = 0; i < layers.size; i++) {
                batch.draw(layers.get(i), camera.position.x - camera.viewportWidth / 2, y, 0, 0, width, height,scaleX,scaleY,0,(int)(i * (camera.viewportWidth / layers.size + camera.position.x)),0,layers.get(i).getWidth(),layers.get(i).getHeight(),false,false);
            }
        }
    }

    public void dispose() {
        for(int i = 0; i < layers.size; i++)
            layers.get(i).dispose();
    }
}
