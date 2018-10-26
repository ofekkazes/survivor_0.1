package com.kazes.fallout.test.screens;

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
