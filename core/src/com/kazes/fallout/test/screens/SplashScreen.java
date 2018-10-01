package com.kazes.fallout.test.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.Assets;
import com.kazes.fallout.test.ImageEx;
import com.kazes.fallout.test.ParallaxBackground;
import com.kazes.fallout.test.Survivor;
/**
 * A screen showing the game's title
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class SplashScreen extends GameScreen {
    float timeout;

    public SplashScreen(Survivor game) {
        super(game, "Splash", 0);
        Label label = new Label("Fallout Survivor", Assets.getAsset(Assets.UI_SKIN, Skin.class));
        screenStage.addActor(label);
        nextScreen = Screens.Tribe;

        timeout = 15 * Gdx.graphics.getFramesPerSecond();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        /*timeout--;
        if(timeout == 0) {
            completed = true;
            player.setX(map.getWidth());
        }*/

    }

    @Override
    public void setMap() {
        map = new ImageEx(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), 0, 0);
        map.setVisible(false);
        Array<Texture> parallaxTextures = new Array<Texture>();
        for(int i = 0; i < 6;i++){
            parallaxTextures.add(Assets.getAsset(Assets._Parallax1[i], Texture.class));
            parallaxTextures.get(i).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);

        }
        parallaxBackground = new ParallaxBackground(parallaxTextures, gameStage.getCamera());
        parallaxBackground.setVisible(false);
    }

    @Override
    public void setDecor() {

    }

    @Override
    public void setNPCS() {

    }

    @Override
    public void setEnemies() {

    }

    @Override
    public void setItems() {

    }

}
