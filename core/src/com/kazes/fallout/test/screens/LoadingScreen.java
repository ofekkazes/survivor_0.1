package com.kazes.fallout.test.screens;

import com.badlogic.gdx.graphics.Color;
import com.kazes.fallout.test.Assets;
import com.kazes.fallout.test.Survivor;
import com.kazes.fallout.test.stories.Stories;

/**
 * Basic loading screen, using to load all assets
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class LoadingScreen extends AbstractScreen {
    private static final float LOAD_DELAY = 0.5f;

    private boolean loaded = false;
    private float loadTime = 0f;

    public LoadingScreen(Survivor game) {
        super(game);
        this.clear = Color.LIGHT_GRAY;


        Assets.loadAll(game.assetManager);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        boolean switchScreen = false;

        if (loaded) {
            loadTime += delta;
            if (loadTime >= LOAD_DELAY) switchScreen = true;

        } else if (game.assetManager.update()) {
            loaded = true;
            loadTime = 0;
        }
        if (switchScreen) {
            Assets.finishLoading();
            Stories.init();
            game.setScreen(Screens.Meviah.getScreen(game, 0));
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        
    }
}
