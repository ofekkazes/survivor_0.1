package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class GameScreen extends AbstractScreen {

    protected Stage gameStage; //game container
    protected Stage screenStage; // screen container
    protected float stateTime;


    public GameScreen(Survivor game, String name) {
        super(game);
        stateTime = 0;

        SideScrollingCamera camera = new SideScrollingCamera();
        camera.setToOrtho (false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        FitViewport viewp = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        gameStage = new Stage(viewp);
        gameStage.getBatch().enableBlending();
        screenStage = new Stage(new ScreenViewport());

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(screenStage);
        multiplexer.addProcessor(gameStage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    //Update the logic every frame
    @Override
    public void update(float delta) {
        super.update(delta);
        stateTime += delta;
        if(stateTime % 1 > 0.9f)
            stateTime += 1-(stateTime % 1);
    }

    //Draws the current frame
    @Override
    public void render(float delta) {
        super.render(delta);
        gameStage.act(Gdx.graphics.getDeltaTime());
        gameStage.draw();

        screenStage.act(Gdx.graphics.getDeltaTime());
        screenStage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        gameStage.dispose();
        screenStage.dispose();
    }
}
