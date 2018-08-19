package com.kazes.fallout.test;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SplashScreen extends GameScreen {
    public SplashScreen(Survivor game) {
        super(game, "Splash");
        Label label = new Label("Fallout Survivor", Assets.getAsset(Assets.UI_SKIN, Skin.class));
        screenStage.addActor(label);
    }
}
