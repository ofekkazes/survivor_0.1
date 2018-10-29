package com.kazes.fallout.test.stories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.screens.GameScreen;
import com.kazes.fallout.test.screens.Screens;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

/**
 *Stories static class manages story chapters
 * @author Ofek Kazes
 */
public class Stories {

    public static HashMap<Integer, Story> stories; //All story chapters are saved in the hash map

    /**
     * Chapters are added at initialization
     */
    public static void init() {
        stories = new HashMap<Integer, Story>();
        stories.put(1, new Chapter1());
        stories.put(2, new Chapter2());
        stories.put(3, new Chapter3());
        stories.put(4, new Chapter4());
        setFinished(1);
        setFinished(2);
        setFinished(3);
        setFinished(4);
        setFinished(5);
    }

    /**
     * Take a story from the list
     * @param chapter the requested chapter number
     * @param gameScreen the current using game screen
     * @return The story requested
     */
    public static Story getStory(int chapter, GameScreen gameScreen) {
        if(isFinished(chapter))
            return null;
        if(stories.get(chapter).gameScreen == null) {
            stories.get(chapter).updateScreen(gameScreen);
            stories.get(chapter).setup();
        }
        else stories.get(chapter).updateScreen(gameScreen);
        stories.get(chapter).addFunctions();
        stories.get(chapter).gameScreen.getDialogueManager().dialogue.loadFile(stories.get(chapter).dialogueFile, false, false, null);
        return stories.get(chapter);
    }

    /**
     * Is the chapter given was processed already
     * @param chapter the chapter number to check
     * @return true if the chapter was done, false otherwise
     */
    public static boolean isFinished(int chapter) {
        if(stories.containsKey(chapter))
            return stories.get(chapter).isFinished();
        return true;
    }

    /**
     * Set a chapter as finished
     * @param chapter the chapter number to set.
     */
    public static void setFinished(int chapter) {
        stories.get(chapter).finished = true;
        if(chapter == 4) {
            if(Chapter4.MAYORSIDE()) {
                stories.put(5, new Chapter5B());
            }
            else  {
                stories.put(5, new Chapter5A());
                //stories.put(6, new Chapter6A());
            }
        }
        stories.get(chapter).dispose();
    }

    /**
     *
     * @return Checks for which chapter to process.
     */
    public static int getCurrentChapter() {
        for (int i = 1; i <= stories.size(); i++) {
            if(!isFinished(i))
                return i;
        }

        return -1;
    }

}
