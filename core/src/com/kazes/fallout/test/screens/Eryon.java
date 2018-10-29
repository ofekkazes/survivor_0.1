package com.kazes.fallout.test.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.Assets;
import com.kazes.fallout.test.ParallaxBackground;
import com.kazes.fallout.test.SideScrollingCamera;
import com.kazes.fallout.test.Survivor;
import com.kazes.fallout.test.stories.Chapter1;
import com.kazes.fallout.test.stories.Chapter4;
import com.kazes.fallout.test.stories.Stories;

public class Eryon extends GameScreen {
    Eryon(Survivor game, float startingPosX) {
        super(game, "Eryon", startingPosX);
        lastScreen = Screens.Barikad;
        nextScreen = Screens.Melin;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void setMap() {
        Array<Texture> parallax = new Array<Texture>();
        parallax.add(Assets.getAsset(Assets.Images.PARALLAX_11, Texture.class), Assets.getAsset(Assets.Images.PARALLAX_12, Texture.class), Assets.getAsset(Assets.Images.PARALLAX_13, Texture.class));
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
