package com.kazes.fallout.test.screens;

public class Stories {
    private static boolean[] storyGuide;

    public static boolean isFinished(int chapter) {
        return storyGuide[chapter + 1];
    }
}
