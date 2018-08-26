package com.kazes.fallout.test.screens;

import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kazes.fallout.test.*;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.color;

public abstract class GameScreen extends AbstractScreen implements GameScreenInterface {
    public static final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    public static final int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    public static final int PPM = 32; ////Pixels Per Meter
    static Player player; //Game player

    Stage gameStage; //Game container
    Stage screenStage; //Screen container
    float stateTime; //How much time passed since the screen was created

    ShaderProgram shader;

    ImageEx map; //Level map
    ParallaxBackground parallaxBackground; //Level background
    Group decor; //Decoration textures


    Group bullets; //All the bullets used
    Group enemies; //Enemy actors
    Group npcs; //Friendly actors
    Group items; //Scattered supplies
    Group traps; //Traps set by the player for the enemies
    Group followers; //Npc's the player persuades to join him
    Group bonfires; //Fires the player sets

    boolean completed;

    World world;
    RayHandler rayHandler;
    Box2DDebugRenderer renderer;

    GameScreen(Survivor game, String name, float startingPosX) {
        super(game);
        stateTime = 0;

        //Changing the camera used by the game stage
        SideScrollingCamera camera = new SideScrollingCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        FitViewport viewp = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);

        gameStage = new Stage(viewp);
        gameStage.getBatch().enableBlending();
        screenStage = new Stage(new ScreenViewport());

        world = new World(Vector2.Zero, false);
        renderer = new Box2DDebugRenderer(true, true, true, true, true, true);
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.1f, 0.1f, 0.1f, 0.2f);
        rayHandler.setBlurNum(3);

        new PointLight(rayHandler, 20, Color.BLUE, 150, 400, 200);

        shader = new ShaderProgram(Gdx.files.internal("shaders/vertex_test.vs"), Gdx.files.internal("shaders/fragmant_test.fs"));
        ShaderProgram.pedantic = false;
        //gameStage.getBatch().setShader(shader);
        Gdx.app.log("Shader", shader.isCompiled() ? "Compiled" : shader.getLog());
        //gameStage.getBatch().getShader().setUniformMatrix("u_projTrans", gameStage.getCamera().projection);

        //Limit the input implementation to the game and screen stages
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(screenStage);
        multiplexer.addProcessor(gameStage);
        Gdx.input.setInputProcessor(multiplexer);

        //Player init
        if(player == null) {
            ObjectMap<String, Animation<TextureRegion>> temp = new ObjectMap<String, Animation<TextureRegion>>();
            for(int i = 0; i < Assets.animationList.size; i++) {
                if(Assets.animationList.getKeyAt(i).contains(Assets.Animations.HERO)) {
                    temp.put(Assets.animationList.getKeyAt(i), Assets.animationList.getValueAt(i));
                }

            }
            player = new Player(temp, new Vector2(0, gameStage.getHeight() / 2 - 100));
            player.setZIndex(10000);
        }
        //player.createBody(world);

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
        setPlayer(startingPosX);
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

    //Update the logic every frame
    @Override
    public void update(float delta) {
        super.update(delta);
        world.step(delta, 6, 2);
        player.playerTranslation.setZero();

        this.proccessInput();
        this.fireGun();
        this.playerZombieInteraction();
        this.decorCollision();
        this.fireplaceCheck();

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
        gameStage.getViewport().apply();
        gameStage.draw();

        screenStage.act(Gdx.graphics.getDeltaTime());
        screenStage.getViewport().apply();
        screenStage.draw();

        rayHandler.setCombinedMatrix((SideScrollingCamera)gameStage.getCamera());
        rayHandler.updateAndRender();
        renderer.render(world, gameStage.getCamera().combined.cpy());

    }

    @Override
    public void dispose() {
        super.dispose();
        gameStage.dispose();
        screenStage.dispose();
        rayHandler.dispose();
        world.dispose();
        shader.dispose();
        parallaxBackground.dispose();
        renderer.dispose();
    }


    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void proccessInput() {
        parallaxBackground.setSpeed(0);
        player.changeAnimation(Assets.Animations.HERO + "_idle");

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.playerTranslation.x = -3;
            if(((SideScrollingCamera)gameStage.getCamera()).getUpdateCamera())
                parallaxBackground.setSpeed(-0.2f);
            if(!player.isxFlip())
                player.flip(true);
            player.changeAnimation(Assets.Animations.HERO + "_walking");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.playerTranslation.x = 3;
            if(((SideScrollingCamera)gameStage.getCamera()).getUpdateCamera())
                parallaxBackground.setSpeed(0.2f);
            if(player.isxFlip())
                player.flip(true);
            player.changeAnimation(Assets.Animations.HERO + "_walking");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W))
            player.playerTranslation.y = 3;
        if(Gdx.input.isKeyPressed(Input.Keys.S))
            player.playerTranslation.y = -3;
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
            for (Actor bullet : this.bullets.getChildren()) {
                if (((Bullet)bullet).getRectangle().overlaps(currentEnemy.getRectangle())) {

                    currentEnemy.subHealth(20);
                    currentEnemy.clearActions();
                    currentEnemy.addAction(getHitAction(player.getX(), player.getY(), currentEnemy.getX(), currentEnemy.getY()));
                    ((Bullet) bullet).setRemove();
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

    //Checks if the player fired the gun, and checks for the boundries of the bullets
    private void fireGun() {
        if(this.bullets.getChildren().size > 0) {
            if (((Bullet)this.bullets.getChildren().get(0)).getTimeToLive() < 0) {
                ((Bullet)this.bullets.getChildren().get(0)).setRemove();
            }
        }
        ImageEx[] array = enemies.getChildren().toArray(ImageEx.class);
        for(NPC follower : (NPC[])followers.getChildren().toArray(NPC.class)) {
            if(enemies.getChildren().size > 0) {
                Vector2 closest = SideScroll.closestTo(array, follower).getOrigin().cpy().sub(follower.getOrigin()).nor();
                switch (follower.getWeapon()) {
                    case Pistol:
                        if (follower.getCooldown() % 50 == 0) {
                            this.bullets.addActor(new Bullet(world, follower.getX(), follower.getY(), closest));
                            follower.resetCooldown();
                        }
                        break;
                    case SMG:
                        if (follower.getCooldown() % 30 == 0) {
                            this.bullets.addActor(new Bullet(world, follower.getX(), follower.getY(), closest));
                            follower.resetCooldown();
                        }
                        break;
                }
            }
        }
    }

    public void decorCollision() {
        for(int i = 0; i < decor.getChildren().size; i++) {
            Rectangle decorRec = Rectangle.tmp;
            if(decor.getChildren().get(i) instanceof ImageEx)
                decorRec = ((ImageEx)decor.getChildren().get(i)).getRectangle();
            else if(decor.getChildren().get(i) instanceof Group)
                decorRec = ((WatchTower)decor.getChildren().get(i)).getRectangle();
            if(player.getRectangle().overlaps(decorRec)) {
                Vector2 translation = new Vector2(MathUtils.clamp(player.getX() - decorRec.getX(), -1, 1),  MathUtils.clamp(player.getY() - decorRec.getY(), -1, 1));
                player.playerTranslation.set(translation);
            }
        }
    }

    public void fireplaceCheck() {
        for(int i = 0; i < bonfires.getChildren().size; i++) {
            if(((Bonfire)bonfires.getChildren().get(i)).getTimeout() < 0)
                bonfires.removeActor(bonfires.getChildren().get(i));
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
