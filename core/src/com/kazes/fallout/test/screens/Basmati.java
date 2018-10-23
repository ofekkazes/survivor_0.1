package com.kazes.fallout.test.screens;

import com.kazes.fallout.test.SideScrollingCamera;
import com.kazes.fallout.test.Survivor;
import com.kazes.fallout.test.stories.Chapter3;
import com.kazes.fallout.test.stories.Stories;

public class Basmati extends GameScreen {
    Chapter3 story;
    Basmati(Survivor game, float startingPosX) {
        super(game, "Basmati", startingPosX);
        lastScreen = Screens.Kerod;
        nextScreen = Screens.Singleton;

        story = (Chapter3)Stories.getStory(3, this);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(!story.isFinished()) {
                story.updateAndRender();
        }

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
