package com.kazes.fallout.test.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.Assets;
import com.kazes.fallout.test.ParallaxBackground;
import com.kazes.fallout.test.SideScrollingCamera;
import com.kazes.fallout.test.Survivor;
import com.kazes.fallout.test.stories.Chapter2;
import com.kazes.fallout.test.stories.Stories;

public class Melin extends GameScreen {
    Melin(Survivor game, float startingPosX) {
        super(game, "Melin", startingPosX);
        lastScreen = Screens.Eryon;
        nextScreen = Screens.Kerod;
    }


    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void setMap() {
        Array<Texture> parallax = new Array<Texture>();
        parallax.add(Assets.getAsset(Assets.Images.PARALLAX_21, Texture.class), Assets.getAsset(Assets.Images.PARALLAX_22, Texture.class),
                Assets.getAsset(Assets.Images.PARALLAX_23, Texture.class));
        parallax.add(Assets.getAsset(Assets.Images.PARALLAX_24, Texture.class),
                Assets.getAsset(Assets.Images.PARALLAX_25, Texture.class));
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
