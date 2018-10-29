package com.kazes.fallout.test.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.Assets;
import com.kazes.fallout.test.ParallaxBackground;
import com.kazes.fallout.test.SideScrollingCamera;
import com.kazes.fallout.test.Survivor;
import com.kazes.fallout.test.stories.Chapter3;
import com.kazes.fallout.test.stories.Stories;

public class Niar extends GameScreen {
    Niar(Survivor game, float startingPosX) {
        super(game, "Niar", startingPosX);
        lastScreen = Screens.Singleton;
        nextScreen = Screens.Battlegrounds;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void setMap() {
        Array<Texture> parallax = new Array<Texture>();
        parallax.add(Assets.getAsset(Assets.Images.PARALLAX_41, Texture.class), Assets.getAsset(Assets.Images.PARALLAX_44, Texture.class),
                Assets.getAsset(Assets.Images.PARALLAX_42, Texture.class));
        parallax.add(Assets.getAsset(Assets.Images.PARALLAX_43, Texture.class));
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
