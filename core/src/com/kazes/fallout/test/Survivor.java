package com.kazes.fallout.test;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

public class Survivor extends Game {
    public AssetManager assetManager;

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
}
