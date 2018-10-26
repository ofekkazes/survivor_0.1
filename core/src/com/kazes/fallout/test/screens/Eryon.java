package com.kazes.fallout.test.screens;

import com.badlogic.gdx.math.Vector2;
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
