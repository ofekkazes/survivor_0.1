package com.kazes.fallout.test.stories;

import com.badlogic.gdx.utils.Array;

public class Stories {
    private static boolean[] storyGuide = new boolean[] {false, true, false, false, false, false};

    public static boolean isFinished(int chapter) {
        return storyGuide[chapter];
    }

    public static void setFinished(int chapter) {
        storyGuide[chapter] = true;
    }
}
