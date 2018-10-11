package com.kazes.fallout.test.screens;

import com.badlogic.gdx.utils.Array;

public class Stories {
    private static boolean[] storyGuide = new boolean[15];

    public static boolean isFinished(int chapter) {
        if(storyGuide.length > chapter + 1) {
            return  storyGuide[chapter + 1];
        }
        return false;
    }

    public static void setFinished(int chapter) {
        storyGuide[chapter + 1] = true;
    }
}
