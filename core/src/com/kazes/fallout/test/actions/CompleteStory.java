package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.stories.Stories;

/**
 * Action to finish a chapter in the story
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-10-28
 */
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
