package com.kazes.fallout.test;

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
    SplashScreen {
        public AbstractScreen getScreen(Survivor game) {
            return new SplashScreen(game);
        }
    };

    public abstract AbstractScreen getScreen(Survivor game);

}
