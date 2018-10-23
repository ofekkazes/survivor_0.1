package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.stories.Story;

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
