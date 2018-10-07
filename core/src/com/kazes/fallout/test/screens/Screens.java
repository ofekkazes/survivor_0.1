package com.kazes.fallout.test.screens;

import com.kazes.fallout.test.Survivor;

/**
 * All game screens
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public enum Screens {

    Loading {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            return new LoadingScreen(game);
        }
    },
    SideScroll {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            SideScroll screen = new SideScroll(game, startingPosX);
            current = screen;
            return screen;
        }
    },
    Prologue {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            Prologue screen = new Prologue(game);
            current = screen;
            return screen;
        }
    },
    Tribe {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            Tribe screen = new Tribe(game, startingPosX);
            current = screen;
            return screen;
        }
    },
    Battlegrounds {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            Battlegrounds screen = new Battlegrounds(game, startingPosX);
            current = screen;
            return screen;
        }
    },
    Chapter1 {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            Chapter1 screen = new Chapter1(game, startingPosX);
            current = screen;
            return screen;
        }
    },
    Chapter2 {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            Chapter2 screen = new Chapter2(game, startingPosX);
            current = screen;
            return screen;
        }
    },
    SplashScreen {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            return new SplashScreen(game);
        }
    };

    static AbstractScreen current;

    public abstract AbstractScreen getScreen(Survivor game, float startingPosX);

    public static GameScreen getCurrent() {
        return (GameScreen)current;
    }
}
