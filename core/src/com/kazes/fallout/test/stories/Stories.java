package com.kazes.fallout.test.stories;

import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.screens.GameScreen;
import com.kazes.fallout.test.screens.Screens;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

public class Stories {

    public static HashMap<Integer, Story> stories;
    public static void init() {
        stories = new HashMap<Integer, Story>();
        stories.put(1, new Chapter1());
        stories.put(2, new Chapter2());
        stories.put(3, new Chapter3());
        //stories.put(4, new Chapter4());
    }

    public static Story getStory(int chapter, GameScreen gameScreen) {
        if(isFinished(chapter))
            return null;
        if(stories.get(chapter).gameScreen == null) {
            stories.get(chapter).updateScreen(gameScreen);
            stories.get(chapter).setup();
        }
        else stories.get(chapter).updateScreen(gameScreen);
        stories.get(chapter).gameScreen.getDialogueManager().dialogue.loadFile(stories.get(chapter).dialogueFile, false, false, null);
        return stories.get(chapter);
    }

    public static boolean isFinished(int chapter) {
        return stories.get(chapter).isFinished();
    }

    public static void setFinished(int chapter) {
        stories.get(chapter).finished = true;
        stories.get(chapter).dispose();
    }

}
