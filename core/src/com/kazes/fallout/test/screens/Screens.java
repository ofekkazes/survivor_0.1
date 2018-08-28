package com.kazes.fallout.test.screens;

import com.kazes.fallout.test.Survivor;
import com.kazes.fallout.test.screens.*;

public enum Screens {
    Loading {
        public AbstractScreen getScreen(Survivor game) {
            return new LoadingScreen(game);
        }
    },
    SideScroll {
        public AbstractScreen getScreen(Survivor game) {
            return new SideScroll(game);
        }
    },
    Prologue {
        public AbstractScreen getScreen(Survivor game) {
            return new Prologue(game);
        }
    },
    Tribe {
        public AbstractScreen getScreen(Survivor game) {
            return new Tribe(game, 0);
        }
    },
    SplashScreen {
        public AbstractScreen getScreen(Survivor game) {
            return new SplashScreen(game);
        }
    };

    public abstract AbstractScreen getScreen(Survivor game);

}
