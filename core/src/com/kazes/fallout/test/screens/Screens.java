package com.kazes.fallout.test.screens;

import com.kazes.fallout.test.Survivor;

/**
 * All game screens
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-10-28
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
    Kerod {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            Kerod screen = new Kerod(game, startingPosX);
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
    Singleton {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            SingletonRoad screen = new SingletonRoad(game, startingPosX);
            current = screen;
            return screen;
        }
    },
    Meviah {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            Meviah screen = new Meviah(game, startingPosX);
            current = screen;
            return screen;
        }
    },
    Eryon {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            Eryon screen = new Eryon(game, startingPosX);
            current = screen;
            return screen;
        }
    },
    Basmati {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            Basmati screen = new Basmati(game, startingPosX);
            current = screen;
            return screen;
        }
    },
    Melin {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            Melin screen = new Melin(game, startingPosX);
            current = screen;
            return screen;
        }
    },
    Barikad {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            Barikad screen = new Barikad(game, startingPosX);
            current = screen;
            return screen;
        }
    },
    Niar {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            Niar screen = new Niar(game, startingPosX);
            current = screen;
            return screen;
        }
    },
    SplashScreen {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            return new SplashScreen(game);
        }
    };

    static AbstractScreen current; //current screen

    /**
     * Get a screen from the enum
     * @param game the base game
     * @param startingPosX Where the player would start
     * @return A screen at load time
     */
    public abstract AbstractScreen getScreen(Survivor game, float startingPosX);

    public static GameScreen getCurrent() {
        return (GameScreen)current;
    }
}
