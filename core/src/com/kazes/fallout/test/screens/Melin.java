package com.kazes.fallout.test.screens;

import com.kazes.fallout.test.SideScrollingCamera;
import com.kazes.fallout.test.Survivor;
import com.kazes.fallout.test.stories.Chapter2;
import com.kazes.fallout.test.stories.Stories;

public class Melin extends GameScreen {
    Chapter2 chapter2;
    Melin(Survivor game, float startingPosX) {
        super(game, "Melin", startingPosX);
        lastScreen = Screens.Eryon;
        nextScreen = Screens.Kerod;
        chapter2 = (Chapter2)Stories.getStory(2, this);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        if(!chapter2.isFinished()) {
            chapter2.updateAndRender();
        }
        else ((SideScrollingCamera)gameStage.getCamera()).followPos(player.getOrigin());
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
