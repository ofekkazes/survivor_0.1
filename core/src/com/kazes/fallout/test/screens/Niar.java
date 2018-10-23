package com.kazes.fallout.test.screens;

import com.kazes.fallout.test.SideScrollingCamera;
import com.kazes.fallout.test.Survivor;
import com.kazes.fallout.test.stories.Chapter3;
import com.kazes.fallout.test.stories.Stories;

public class Niar extends GameScreen {
    Chapter3 story;
    Niar(Survivor game, float startingPosX) {
        super(game, "Niar", startingPosX);
        lastScreen = Screens.Melin;
        nextScreen = Screens.Battlegrounds;
        story = (Chapter3)Stories.getStory(3, this);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(story != null) {
                story.update();
                story.render();
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
