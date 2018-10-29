package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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

    private float x,y,width,height,scaleX,scaleY;

    public ParallaxBackground(Array<Texture> textures, Camera cam){
        if(textures.get(0).getWidth() < 1920) {
            Array<Texture> modified = new Array<Texture>();
            for(Texture texture : textures) {
                if(textures.get(0).getWidth() < 600)
                    modified.add(extendTexture(texture, 4));
                else modified.add(extendTexture(texture, 3));
            }

            layers = modified;
        }
        else layers = textures;
        for(int i = 0; i <textures.size;i++){
            layers.get(i).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        camera = (SideScrollingCamera)cam;
        this.width =  camera.viewportWidth;
        this.height = camera.viewportHeight / 2;
        y = camera.viewportHeight / 2;
        scaleX = scaleY = 1;
        setVisible(true);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor().r * parentAlpha, getColor().g * parentAlpha, getColor().b * parentAlpha, getColor().a * parentAlpha);
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

    /**
     * Multiply a texture horizontally
     * @param texture the texture you want to extend
     * @param multiplier How many times to multiply the texture
     * @return an extended texture
     */
    private static Texture extendTexture(Texture texture, int multiplier) {
        Pixmap pixmap = new Pixmap(texture.getWidth() * multiplier, texture.getHeight(), Pixmap.Format.RGBA8888);
        for(int i = 0; i < multiplier; i++) {
            texture.getTextureData().prepare();
            pixmap.drawPixmap(texture.getTextureData().consumePixmap(), i * texture.getWidth(), 0);
        }
        return new Texture(pixmap);
    }
}
