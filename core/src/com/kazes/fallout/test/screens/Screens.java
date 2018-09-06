package com.kazes.fallout.test.screens;

import com.kazes.fallout.test.Survivor;
import com.kazes.fallout.test.screens.*;

public enum Screens {
    Loading {
        public AbstractScreen getScreen(Survivor game) {
            return new LoadingScreen(game);
        }
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            return new LoadingScreen(game);
        }
    },
    SideScroll {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            return new SideScroll(game, startingPosX);
        }
        public AbstractScreen getScreen(Survivor game) {
            return new SideScroll(game, 0);
        }
    },
    Prologue {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            return new Prologue(game);
        }
        public AbstractScreen getScreen(Survivor game) {
            return new Prologue(game);
        }
    },
    Tribe {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            return new Tribe(game, startingPosX);
        }
        public AbstractScreen getScreen(Survivor game) {
            return new Tribe(game, 0);
        }
    },
    Battlegrounds {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            return new Battlegrounds(game, startingPosX);
        }
        public AbstractScreen getScreen(Survivor game) {
            return new Battlegrounds(game, 0);
        }
    },
    SplashScreen {
        public AbstractScreen getScreen(Survivor game, float startingPosX) {
            return new SplashScreen(game);
        }
        public AbstractScreen getScreen(Survivor game) {
            return new SplashScreen(game);
        }
    };

    public abstract AbstractScreen getScreen(Survivor game, float startingPosX);
    public abstract AbstractScreen getScreen(Survivor game);

}
