package com.kazes.fallout.test.screens;

import com.badlogic.gdx.math.Vector2;
import com.kazes.fallout.test.SideScrollingCamera;
import com.kazes.fallout.test.Survivor;
import com.kazes.fallout.test.stories.Chapter1;
import com.kazes.fallout.test.stories.Stories;

public class Eryon extends GameScreen {
    Chapter1 story;
    Eryon(Survivor game, float startingPosX) {
        super(game, "Eryon", startingPosX);
        lastScreen = Screens.Tribe;
        nextScreen = Screens.Basmati;

        story = new Chapter1(this);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if(story != null) {
            if(story.isDisposed())
                story = null;
            else story.update();

        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(story != null)
            story.render();
        else ((SideScrollingCamera)gameStage.getCamera()).followPos(new Vector2(player.getX(), player.getY()));
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
