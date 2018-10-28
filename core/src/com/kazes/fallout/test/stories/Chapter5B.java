package com.kazes.fallout.test.stories;

import com.kazes.fallout.test.Assets;

public class Chapter5B extends Story {
    public Chapter5B() {
        super(5);
    }

    @Override
    public void setup() {
        dialogueFile = Assets.Dialogues.CHAPTER5B;
    }
}
