package com.kazes.fallout.test.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.Assets;
import com.kazes.fallout.test.ParallaxBackground;
import com.kazes.fallout.test.SideScrollingCamera;
import com.kazes.fallout.test.Survivor;

public class Meviah extends GameScreen {
    Meviah(Survivor game, float startingPosX) {
        super(game, "Meviah", startingPosX);
        nextScreen = Screens.Barikad;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void setMap() {
        Array<Texture> parallax = new Array<Texture>();
        parallax.add(Assets.getAsset(Assets.Images.PARALLAX_31, Texture.class), Assets.getAsset(Assets.Images.PARALLAX_32, Texture.class),
                Assets.getAsset(Assets.Images.PARALLAX_33, Texture.class));
        parallax.add(Assets.getAsset(Assets.Images.PARALLAX_34, Texture.class));
        parallaxBackground = new ParallaxBackground(parallax, gameStage.getCamera());
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
