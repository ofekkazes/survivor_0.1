package com.kazes.fallout.test.screens;

import com.kazes.fallout.test.SideScrollingCamera;
import com.kazes.fallout.test.Survivor;

public class Basmati extends GameScreen {
    Basmati(Survivor game, float startingPosX) {
        super(game, "Basmati", startingPosX);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ((SideScrollingCamera)gameStage.getCamera()).followPos(player.getOrigin());
    }

    @Override
    public void setMap() {

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
