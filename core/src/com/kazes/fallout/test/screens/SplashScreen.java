package com.kazes.fallout.test.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.kazes.fallout.test.Assets;
import com.kazes.fallout.test.Survivor;
import com.kazes.fallout.test.screens.GameScreen;
/**
 * A screen showing the game's title
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class SplashScreen extends GameScreen {
    public SplashScreen(Survivor game) {
        super(game, "Splash", 0);
        Label label = new Label("Fallout Survivor", Assets.getAsset(Assets.UI_SKIN, Skin.class));
        screenStage.addActor(label);
    }

    @Override
    public void setMap() {

    }

    @Override
    public void setDecor() {

    }

    @Override
    public void setNPCS() {

    }

    @Override
    public void setEnemies() {

    }

    @Override
    public void setItems() {

    }

}
