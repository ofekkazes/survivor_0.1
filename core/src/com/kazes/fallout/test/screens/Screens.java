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
            return new SideScroll(game, startingPosX);
        }
    },
    Prologue {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            return new Prologue(game);
        }
    },
    Tribe {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            return new Tribe(game, startingPosX);
        }
    },
    Battlegrounds {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            return new Battlegrounds(game, startingPosX);
        }
    },
    Chapter1 {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            return new Chapter1(game, startingPosX);
        }
    },
    SplashScreen {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            return new SplashScreen(game);
        }
    };

    public abstract AbstractScreen getScreen(Survivor game, float startingPosX);

}
