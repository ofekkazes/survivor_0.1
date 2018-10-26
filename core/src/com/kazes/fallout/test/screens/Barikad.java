package com.kazes.fallout.test.screens;

import com.badlogic.gdx.graphics.Texture;
import com.kazes.fallout.test.Assets;
import com.kazes.fallout.test.ImageEx;
import com.kazes.fallout.test.SideScrollingCamera;
import com.kazes.fallout.test.Survivor;

public class Barikad extends GameScreen {
    Barikad(Survivor game, float startingPosX) {
        super(game, "Barikad", startingPosX);
        lastScreen = Screens.Meviah;
        nextScreen = Screens.Eryon;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void setMap() {
        map = new ImageEx(Assets.getAsset(Assets.Images.MAP_GREEN, Texture.class), 0, 0);
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
