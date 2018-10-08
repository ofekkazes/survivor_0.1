package com.kazes.fallout.test.screens;

import com.kazes.fallout.test.SideScrollingCamera;
import com.kazes.fallout.test.Survivor;

public class SingletonRoad extends GameScreen {
    SingletonRoad(Survivor game, float startingPosX) {
        super(game, "Singleton Road", startingPosX);
        lastScreen = Screens.Chapter2;
        nextScreen = Screens.Meviah;
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
