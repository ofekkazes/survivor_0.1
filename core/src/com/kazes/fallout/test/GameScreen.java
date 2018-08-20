package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.color;

public abstract class GameScreen extends AbstractScreen implements GameScreenInterface {

    Stage gameStage; //Game container
    Stage screenStage; //Screen container
    float stateTime; //How much time passed since the screen was created

    ImageEx map; //Level map
    ParallaxBackground parallaxBackground; //Level background
    Group decor; //Decoration textures

    Player player; //Game player
    Group bullets; //All the bullets used
    Group enemies; //Enemy actors
    Group npcs; //Friendly actors
    Group items; //Scattered supplies
    Group traps; //Traps set by the player for the enemies
    Group followers; //Npc's the player persuades to join him
    Group bonfires; //Fires the player sets

    boolean completed;

    GameScreen(Survivor game, String name) {
        super(game);
        stateTime = 0;

        //Changing the camera used by the game stage
        SideScrollingCamera camera = new SideScrollingCamera();
        camera.setToOrtho (false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        FitViewport viewp = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        gameStage = new Stage(viewp);
        gameStage.getBatch().enableBlending();
        screenStage = new Stage(new ScreenViewport());

        //Limit the input implementation to the game and screen stages
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(screenStage);
        multiplexer.addProcessor(gameStage);
        Gdx.input.setInputProcessor(multiplexer);

        //Objects initialization
        enemies = new Group();
        npcs = new Group();
        traps = new Group();
        followers = new Group();
        bonfires = new Group();
        bullets = new Group();
        items = new Group();
        decor = new Group();

        //Stage object loading
        setMap();
        setDecor();
        setItems();
        setPlayer();
        setNPCS();
        setEnemies();

        //adding all the objects to the main stage
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
    }//IDEAs

    //Update the logic every frame
    @Override
    public void update(float delta) {
        super.update(delta);
        this.proccessInput();
        this.playerZombieInteraction();

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

    //Player, Enemy and Bullets collision check, plus the most sophisticated AI the world has ever seen for a zombie
    private void playerZombieInteraction() {
        for (int j = 0; j < enemies.getChildren().size; j++) {
            Zombie currentEnemy = (Zombie)enemies.getChildren().items[j];
            if(!currentEnemy.hasActions()) {
                if(currentEnemy.wander && currentEnemy.getHealth() == 100)// OR there is not clean line of sight (obstacles/hiding spots)
                    currentEnemy.addAction(sequence(delay(1.5f), moveBy(MathUtils.random(-30, 30), MathUtils.random(-15, 15f), MathUtils.random(1.5f, 3.0f)), new WanderAction()));
                else {
                    float yTranslate = player.getY() - currentEnemy.getY();
                    float xTranslate = currentEnemy.getX() - player.getX();
                    if((xTranslate > -400 && xTranslate < 400) || currentEnemy.getHealth() != 100) { //OR THERE IS A CLEAN LINE OF SIGHT
                        if((xTranslate > -150 && xTranslate < 150))
                            yTranslate = (yTranslate > 0 ) ? .4f : -.4f;
                        else yTranslate = 0;
                        xTranslate = (xTranslate > 0) ? -.5f : .5f;
                        currentEnemy.clearActions();
                        currentEnemy.translate(xTranslate, yTranslate);
                        currentEnemy.wander = false;
                    }
                    else currentEnemy.wander = true;
                }
            }
            //enemies.get(j).addAction(moveTo(player.getX(), player.getY(), (forcePositive(enemies.get(j).getX() - player.getX())) / 50, Interpolation.pow2In)); //add y to time calculation

            if(player.getRectangle().overlaps(currentEnemy.getRectangle())) {
                player.addAction(getHitAction(currentEnemy.getX(), currentEnemy.getY(), player.getX(), player.getY()));
                player.subHealth(30);
            }
            for (int i = 0; i < this.bullets.getChildren().size; i++) {
                if (((Bullet)this.bullets.getChildren().items[i]).getRectangle().overlaps(currentEnemy.getRectangle())) {

                    currentEnemy.subHealth(20);
                    currentEnemy.clearActions();
                    currentEnemy.addAction(getHitAction(player.getX(), player.getY(), currentEnemy.getX(), currentEnemy.getY()));
                    this.bullets.removeActor(this.bullets.getChildren().items[i]);
                    if (currentEnemy.getHealth() == 0) {
                        this.enemies.removeActor(currentEnemy);
                    }
                    break;
                }
            }

            for(int i = 0; i < traps.getChildren().size; i++) {
                if(((ImageEx)traps.getChildren().items[i]).getRectangle().overlaps(currentEnemy.getRectangle())) {
                    currentEnemy.subHealth(90);
                    currentEnemy.clearActions();
                    currentEnemy.addAction(getHitAction(traps.getChildren().items[i].getX(), traps.getChildren().items[i].getY(), currentEnemy.getX(), currentEnemy.getY()));
                    traps.removeActor(traps.getChildren().items[i]);
                }
            }

            for(ImageEx bonfire : (ImageEx[])bonfires.getChildren().toArray(ImageEx.class)) {
                Vector2 range = getRange(bonfire.getX(), bonfire.getY(), currentEnemy.getX(), currentEnemy.getY());
                if((range.x < 200 && range.x > -200) && (range.y < 100 && range.y > -100))
                    currentEnemy.addAction(getHitAction(bonfire.getX(), bonfire.getY(), currentEnemy.getX(), currentEnemy.getY()));
            }

            currentEnemy.setX(MathUtils.clamp(currentEnemy.getX(), 0, game.assetManager.get(Assets.Images.MAP, Texture.class).getWidth()));
            currentEnemy.setY(MathUtils.clamp(currentEnemy.getY(), 0, game.assetManager.get(Assets.Images.MAP, Texture.class).getHeight() - 200));

        }
    }

    public static float forcePositive(float number) {
        return (number < 0) ? number * -1 : number;
    }

    static ImageEx closestTo(ImageEx[] vectorsArray, ImageEx checkVector) {
        float shortestDist = 0;
        ImageEx closestVector = null;
        for(ImageEx point : vectorsArray){
            float dst2 = checkVector.getOrigin().dst2(point.getOrigin());
            if(closestVector == null || dst2 < shortestDist){
                shortestDist = dst2;
                closestVector = point;
            }
        }
        return closestVector;
    }

    private static Vector2 getRange(float xOrigin, float yOrigin, float xTarget, float yTarget) {
        return new Vector2(xOrigin - xTarget, yOrigin - yTarget);
    }

    //Create an hit action using the coordinates given
    private static ParallelAction getHitAction(float fromPosX, float fromPosY, float toPosX, float toPosY) {
        return parallel(moveBy(((toPosX - fromPosX > 0)? 50 : -50) * 2, (toPosY - fromPosY), .3f),
                sequence(color(Color.RED), color(Color.WHITE, 1f)));
    }

    private void checkCompleteLevel() {
        if(enemies.getChildren().size == 0)
            completed = true;
    }
}
