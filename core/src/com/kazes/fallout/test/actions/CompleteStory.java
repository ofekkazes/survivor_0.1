package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.screens.Stories;

public class CompleteStory extends Action {
    private int chapter;

    public CompleteStory(int chapter) {
        this.chapter = chapter;
    }

    @Override
    public boolean act(float delta) {
        Stories.setFinished(chapter);
        return true;
    }
}
