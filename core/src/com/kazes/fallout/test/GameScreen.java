package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class GameScreen extends AbstractScreen implements GameScreenInterface {

    Stage gameStage; //game container
    Stage screenStage; // screen container
    float stateTime;

    ImageEx map;
    ParallaxBackground parallaxBackground;
    Group decor;

    Player player;
    Group bullets;
    Group enemies; //enemy actors
    Group npcs; //Friendly foes
    Group items; //Scattered supplies
    Group traps;
    Group followers;
    Group bonfires;

    boolean completed;

    GameScreen(Survivor game, String name) {
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

        enemies = new Group();
        npcs = new Group();
        traps = new Group();
        followers = new Group();
        bonfires = new Group();
        bullets = new Group();
        items = new Group();
        decor = new Group();

        setMap();
        setDecor();
        setItems();
        setPlayer();
        setNPCS();
        setEnemies();

        gameStage.addActor(parallaxBackground);
        gameStage.addActor(map);
        gameStage.addActor(decor);

        gameStage.addActor(enemies);
        gameStage.addActor(npcs);
        gameStage.addActor(traps);
        gameStage.addActor(followers);
        gameStage.addActor(bonfires);
        gameStage.addActor(bullets);
        gameStage.addActor(items);


        if(player != null)
            gameStage.addActor(player);

        completed = false;
    }

    public GameScreen(Survivor game, String name, Player player) {
        this(game, name);
        this.player = player;
        this.player.setX(0);
        gameStage.addActor(player);
    }

    //Update the logic every frame
    @Override
    public void update(float delta) {
        super.update(delta);
        this.proccessInput();

        stateTime += delta;
        if(stateTime % 1 > 0.9f)
            stateTime += 1-(stateTime % 1);

        checkCompleteLevel();
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

    @Override
    public void proccessInput() {
        parallaxBackground.setSpeed(0);
        player.changeAnimation(Assets.Animations.HERO + "_idle");

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.translateX(-3);
            if(((SideScrollingCamera)gameStage.getCamera()).getUpdateCamera())
                parallaxBackground.setSpeed(-0.2f);
            if(!player.xFlipped)
                player.flip(true, false);
            player.changeAnimation(Assets.Animations.HERO + "_walking");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.translateX(3);
            if(((SideScrollingCamera)gameStage.getCamera()).getUpdateCamera())
                parallaxBackground.setSpeed(0.2f);
            if(player.xFlipped)
                player.flip(true, false);
            player.changeAnimation(Assets.Animations.HERO + "_walking");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W))
            player.translateY(3);
        if(Gdx.input.isKeyPressed(Input.Keys.S))
            player.translateY(-3);
    }

    private void checkCompleteLevel() {
        if(enemies.getChildren().size == 0)
            completed = true;
    }
}
