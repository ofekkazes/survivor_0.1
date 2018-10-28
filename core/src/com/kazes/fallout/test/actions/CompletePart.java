package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.stories.Story;

/**
 * Action to finish a part of the story
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-10-28
 */
public class CompletePart extends Action {
    Story story;
    int part;

    public CompletePart(Story story, int part) {
        this.story = story;
        this.part = part;
    }
    @Override
    public boolean act(float delta) {
        story.updatePart(part);
        return true;
    }
}
