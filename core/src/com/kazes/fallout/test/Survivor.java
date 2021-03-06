package com.kazes.fallout.test;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.kazes.fallout.test.screens.LoadingScreen;

/**
 * The game
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class Survivor extends Game {
    public AssetManager assetManager;
    public static final float PPM = 64 / 1.8f; ////Pixels Per Meter

    public Survivor() {
        super();
        assetManager = new AssetManager();
    }

    @Override
    public void create() {

        Assets.loadFonts();

        this.setScreen(new LoadingScreen(this));
    }

    @Override
    public void pause() {
        super.pause();


    }

    @Override
    public void dispose() {
        super.dispose();

        assetManager.dispose();
        Assets.dispose();

    }

    @Override
    public void render() {
        super.render();

    }

    public static float getInMeters(float point) {
        return point / PPM;
    }
}
