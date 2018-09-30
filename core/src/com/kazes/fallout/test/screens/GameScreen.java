package com.kazes.fallout.test.screens;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kazes.fallout.test.*;
import com.kazes.fallout.test.dialogues.DialogueManager;
import com.kazes.fallout.test.enemies.Enemy;
import com.kazes.fallout.test.enemies.Zombie;
import com.kazes.fallout.test.inventory.FastInventoryActor;
import com.kazes.fallout.test.inventory.Inventory;
import com.kazes.fallout.test.inventory.InventoryActor;
import com.kazes.fallout.test.items.ItemActor;
import com.kazes.fallout.test.items.SmallMedkit;
import com.kazes.fallout.test.physics.B2DBodyBuilder;
import com.kazes.fallout.test.physics.CollisionCategory;
import com.kazes.fallout.test.physics.ContactListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.color;

/**
 * Most of the game's logic will be coded here.
 * @author Ofek Kazes
 * @version 1.01
 * @since 2018-09-15
 */
public abstract class GameScreen extends AbstractScreen implements GameScreenInterface {
    public static final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    public static final int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    public static final int VIRTUAL_HEIGHT = 400;

    Screens nextScreen;
    Screens lastScreen;
    Boolean[] screenChange = {false};

    public static Player player; //Game player
    static InventoryActor inventoryActor;
    static FastInventoryActor fastInventoryActor;
    static DragAndDrop dragAndDrop;

    Stage gameStage; //Game container
    static Stage screenStage; //Screen container
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
    boolean allowInput = true;
    boolean weaponsAllowed;

    World world;
    RayHandler rayHandler;
    Box2DDebugRenderer renderer;
    private static float ambientAlpha = 0.5f;
    public static float time = 0.5f;
    private static boolean day = true;

    DialogueManager dialogueManager;

    public static Array<String> notifications = new Array<String>();

    GameScreen(Survivor game, String name, float startingPosX) {
        super(game);
        stateTime = 0;

        //Changing the camera used by the game stage
        SideScrollingCamera camera = new SideScrollingCamera(30, 20);
        StretchViewport viewp = new StretchViewport(30.6f, 17, camera);

        gameStage = new Stage(viewp);
        gameStage.getBatch().enableBlending();
        gameStage.addAction(sequence(Actions.alpha(0), Actions.delay(.2f), Actions.fadeIn(.25f)));

        if(screenStage == null) {
            screenStage = new Stage(new ScreenViewport());
            if(inventoryActor == null && fastInventoryActor == null) {
                DragAndDrop dragAndDrop = new DragAndDrop();
                inventoryActor = new InventoryActor(new Inventory(20), dragAndDrop, Assets.getAsset(Assets.UI_SKIN, Skin.class), screenStage);
                fastInventoryActor = new FastInventoryActor(new Inventory(5), dragAndDrop, Assets.getAsset(Assets.UI_SKIN, Skin.class), screenStage);

            }
            screenStage.addActor(inventoryActor);
            screenStage.addActor(fastInventoryActor);

            //Player init
            if(player == null) {
                ObjectMap<String, Animation<TextureRegion>> temp = new ObjectMap<String, Animation<TextureRegion>>();
                for(int i = 0; i < Assets.animationList.size; i++) {
                    if(Assets.animationList.getKeyAt(i).contains(Assets.Animations.HERO)) {
                        temp.put(Assets.animationList.getKeyAt(i), Assets.animationList.getValueAt(i));
                    }

                }
                player = new Player(temp, new Vector2(0, 6));
                player.setZIndex(10000);

            }

            setGUI();
        }

        world = new World(Vector2.Zero, false);
        world.setContactListener(new ContactListener());

        renderer = new Box2DDebugRenderer(true, true, true, true, true, true);
        rayHandler = new RayHandler(world);

        rayHandler.setAmbientLight(0.1f, 0.1f, 0.1f, ambientAlpha);
        rayHandler.setBlurNum(3);

        //Limit the input implementation to the game and screen stages
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(gameStage);
        multiplexer.addProcessor(screenStage);
        Gdx.input.setInputProcessor(multiplexer);

        weaponsAllowed = false;

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

        //Create Boundaries
        B2DBodyBuilder.createBody(world, map.getX(), map.getHeight() - 3, map.getWidth(), 10, BodyDef.BodyType.StaticBody, CollisionCategory.BOUNDARY, CollisionCategory.BOUNDARY_COLLIDER);
        B2DBodyBuilder.createBody(world, map.getX(), 0-9.99f, map.getWidth(), 10, BodyDef.BodyType.StaticBody, CollisionCategory.BOUNDARY, CollisionCategory.BOUNDARY_COLLIDER);
        B2DBodyBuilder.createBody(world, map.getX() - 2f, 0, 0.15f, map.getHeight() - 3, BodyDef.BodyType.StaticBody, CollisionCategory.BOUNDARY, CollisionCategory.BOUNDARY_COLLIDER);
        B2DBodyBuilder.createBody(world, map.getWidth() + 2f, 0, 0.15f, map.getHeight() - 3, BodyDef.BodyType.StaticBody, CollisionCategory.BOUNDARY, CollisionCategory.BOUNDARY_COLLIDER);

        if(player != null) {
            player.setX(startingPosX);
            gameStage.addActor(player);
            player.initPhysics(world);
            //magic = new MagicAttack(Assets.getAsset(Assets.Images.FIRE, Texture.class), Assets.getAsset(Assets.ParticleEffects.fire, ParticleEffect.class), player.getX(), player.getY());

        }

        for(Actor zombie : enemies.getChildren())
            ((Zombie)zombie).addInteractingObject(player);

        completed = false;

        this.dialogueManager = new DialogueManager();
        screenStage.addActor(this.dialogueManager.getWindow());
    }

    private void setGUI() {
        Skin skin = Assets.getAsset(Assets.UI_SKIN, Skin.class);
        final TextButton menuButton = new TextButton("Menu", skin);
        final WindowEx playerStats = new WindowEx(skin);

        menuButton.setWidth(Gdx.graphics.getWidth() / 14.666f);
        menuButton.setHeight(Gdx.graphics.getHeight() / 16.216f);
        menuButton.setPosition(Gdx.graphics.getWidth() / 42, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 12);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pause();
            }
        });

        Table statsTable = new Table();
        statsTable.add(new Label("Health", skin)).left().row();
        statsTable.add(player.health).row();
        statsTable.add(new Label("Hunger", skin)).left().row();
        statsTable.add(player.hunger).row();
        statsTable.add(new Label("Thirst", skin)).left().row();
        statsTable.add(player.thirst);

        Table weaponTable = new Table();
        weaponTable.add(new Label("Weapon", skin)).expand().fillY();
        weaponTable.row();
        weaponTable.add(player.ammo);

        playerStats.setWidth(Gdx.graphics.getWidth() / 4.07f);
        playerStats.setHeight(Gdx.graphics.getHeight() / 4.5f);
        playerStats.setPosition(Gdx.graphics.getWidth() - playerStats.getX(), Gdx.graphics.getHeight() - playerStats.getY());
        playerStats.setMovable(false);
        playerStats.setDebug(true);

        playerStats.add(weaponTable).expand();
        playerStats.add(statsTable).expand();

        screenStage.addActor(menuButton);
        screenStage.addActor(playerStats);
    }

    //Update the logic every frame
    @Override
    public void update(float delta) {
        super.update(delta);
        world.step(delta, 6, 2);
        world.clearForces();
        player.playerTranslation.setZero();

        this.proccessInput();
        this.fireGun();
        this.playerZombieInteraction();
        this.fireplaceCheck();
        this.screenChange();
        this.checkNotifications();

        dialogueManager.update();

        stateTime += delta;
        if(stateTime % 1 > 0.9f)
            stateTime += 1-(stateTime % 1);
    }

    //Draws the current frame
    @Override
    public void render(float delta) {
        super.render(delta);

        gameStage.act(Gdx.graphics.getDeltaTime());
        gameStage.getViewport().apply();
        gameStage.draw();

        if(day) {
            time += 0.00005555555;
            if (time > 1)
                day = false;
        }
        else {
            time -= 0.00005555555;
            if (time < 0)
                day = true;
        }
        ambientAlpha = MathUtils.sin(time);
        ambientAlpha = MathUtils.clamp(ambientAlpha, 0.05f, 0.65f);
        rayHandler.setAmbientLight(ambientAlpha);
        rayHandler.setCombinedMatrix((SideScrollingCamera)gameStage.getCamera());
        rayHandler.updateAndRender();
        renderer.render(world, gameStage.getCamera().combined.cpy());

        dialogueManager.render();
        screenStage.act(Gdx.graphics.getDeltaTime());
        screenStage.getViewport().apply();
        screenStage.draw();

        screenStage.getBatch().begin();
        for(int i = 0; i < notifications.size; i++)
            Assets.getFont(Assets.Fonts.DEFAULT, Assets.FontSizes.TWO_HUNDRED).draw(screenStage.getBatch(), notifications.get(i) + "\n", 0, Gdx.graphics.getHeight() - 200 -  i * Assets.getFont(Assets.Fonts.DEFAULT, Assets.FontSizes.TWO_HUNDRED).getLineHeight());
        screenStage.getBatch().end();

    }

    @Override
    public void pause() {
        super.pause();
        Gdx.app.log(this.getName(), "Paused");
        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
            resume();
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
        dialogueManager.dialogue.unloadAll();
    }


    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        ((OrthographicCamera)gameStage.getCamera()).setToOrtho(false, VIRTUAL_HEIGHT * width / (float)height, VIRTUAL_HEIGHT);
        ((OrthographicCamera)gameStage.getCamera()).position.set(player.getX(), Survivor.getInMeters(height/2f), 0);
        gameStage.getCamera().update();
    }

    @Override
    public void proccessInput() {
        dialogueManager.input();
        player.changeAnimation(player.getCurrentKey());
        if(allowInput) {
            player.changeAnimation(Assets.Animations.HERO + "_idle");

            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                player.playerTranslation.x = -1 * player.walkSpeed;
                if (!player.isxFlip())
                    player.flip();
                player.changeAnimation(Assets.Animations.HERO + "_walking");
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                player.playerTranslation.x = player.walkSpeed;
                if (player.isxFlip())
                    player.flip();
                player.changeAnimation(Assets.Animations.HERO + "_walking");
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W))
                player.playerTranslation.y = 3;
            if (Gdx.input.isKeyPressed(Input.Keys.S))
                player.playerTranslation.y = -3;

            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                player.walkSpeed = 5f;
                player.setWalkSpeed(true);
            }
            else {
                player.walkSpeed = 3f;
                player.setWalkSpeed(false);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.F))
                for (Actor item : items.getChildren()) {
                    ItemActor itemActor = (ItemActor) item;
                    if (itemActor.getRectangle().overlaps(player.getRectangle())) {
                        if (!fastInventoryActor.getInventory().store(itemActor.getItem(), 1))
                            inventoryActor.getInventory().store(itemActor.getItem(), 1);
                    }

                }
            for (int i = 0; i < InputHelper.fastInventoryKeys.length; i++) {
                if (Gdx.input.isKeyJustPressed(InputHelper.fastInventoryKeys[i])) {
                    ImageButton button = ((ImageButton) fastInventoryActor.getCells().get(i).getActor());
                    InputEvent event1 = new InputEvent();
                    event1.setType(InputEvent.Type.touchDown);
                    button.fire(event1);

                    InputEvent event2 = new InputEvent();
                    event2.setType(InputEvent.Type.touchUp);
                    button.fire(event2);
                }
            }

            if (weaponsAllowed && !inventoryActor.isVisible()) {
                if (!player.isAmmoEmpty()) {
                    if (player.getWeapon() == Weapons.Pistol) {
                        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT)) {
                            player.shoot();
                            Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                            mousePos = gameStage.screenToStageCoordinates(mousePos);
                            this.bullets.addActor(new Bullet(world, player.getOrigin().x, player.getOrigin().y, mousePos.cpy().sub(player.getOrigin()).nor()));
                        }
                    }
                    if (player.getWeapon() == Weapons.SMG) {
                        if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                            player.shoot();
                            if (player.cooldown > 17) {
                                Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                                mousePos = gameStage.screenToStageCoordinates(mousePos);
                                this.bullets.addActor(new Bullet(world, player.getX(), player.getY(), mousePos.cpy().sub(player.getOrigin()).nor()));
                                player.cooldown = 0;
                            }
                        }
                    }
                }
            }


            if (Gdx.input.isKeyJustPressed(Input.Keys.N))
                inventoryActor.setVisible(!inventoryActor.isVisible());
            if (Gdx.input.isKeyJustPressed(Input.Keys.C))
                inventoryActor.getInventory().store(new SmallMedkit(), 2);

            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                if (inventoryActor.isVisible())
                    inventoryActor.setVisible(false);
                else {
                    //PAUSE SCREEN
                    //ROLL CREDITS
                }
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
                gameStage.addActor(new MagicAttack(Assets.getAsset(Assets.ParticleEffects.blood, ParticleEffect.class), player.getX(), player.getY()));
            }
        }
    }

    //Player, Enemy and Bullets collision check, plus the most sophisticated AI the world has ever seen for a zombie
    private void playerZombieInteraction() {
        for (int j = 0; j < enemies.getChildren().size; j++) {

            Enemy currentEnemy = (Enemy) enemies.getChildren().items[j];

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
                if((range.x < Survivor.getInMeters(200) && range.x > Survivor.getInMeters(-200)) && (range.y < Survivor.getInMeters(100) && range.y > Survivor.getInMeters(-100)))
                    currentEnemy.addAction(getHitAction(bonfire.getX(), bonfire.getY(), currentEnemy.getX(), currentEnemy.getY()));
            }
        }
    }

    //Checks if the player fired the gun, and checks for the boundries of the bullets
    private void fireGun() {
        if(this.bullets.getChildren().size > 0) {
            if (((Bullet)this.bullets.getChildren().get(0)).getTimeToLive() < 0) {
                ((Bullet)this.bullets.getChildren().get(0)).setRemove();
            }
        }
        for(NPC follower : (NPC[])followers.getChildren().toArray(NPC.class)) {
            if(enemies.getChildren().size > 0) {
                Vector2 closest = GameScreen.closestTo(enemies.getChildren(), follower).getOrigin().cpy().sub(follower.getOrigin()).nor();
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

    private void fireplaceCheck() {
        for(int i = 0; i < bonfires.getChildren().size; i++) {
            if(((Bonfire)bonfires.getChildren().get(i)).getTimeout() < 0)
                bonfires.removeActor(bonfires.getChildren().get(i));
        }
    }

    private void screenChange() {

        if(player.getX() + player.getWidth() / 2 < 0 && lastScreen != null) {
            gameStage.addAction(sequence(Actions.fadeOut(.25f), new BoolAction(screenChange)));
            if(screenChange[0]) game.setScreen(lastScreen.getScreen(game, map.getWidth() - 1.5f));
        }
        checkCompleteLevel();
        if(player.getX() + player.getWidth() / 2 > map.getWidth() + 0.5f && nextScreen != null && this.completed) {
            gameStage.addAction(sequence(Actions.fadeOut(.25f), new BoolAction(screenChange)));
            if(screenChange[0]) game.setScreen(nextScreen.getScreen(game, 0));
        }
    }

    private void checkNotifications() {
        if(notifications.size > 0 && stateTime > 1)
            if(stateTime % 15 == 0)
                notifications.removeIndex(0);

    }

    public static float forcePositive(float number) {
        return (number < 0) ? number * -1 : number;
    }

    public static ImageEx closestTo(Array<Actor> vectorsArray, ImageEx checkVector) {
        float shortestDist = 0;
        ImageEx closestActor = null;
        for(ImageEx point : (ImageEx[])vectorsArray.toArray(ImageEx.class)){
            float dst2 = checkVector.getOrigin().dst2(point.getOrigin());
            if(closestActor == null || dst2 < shortestDist){
                shortestDist = dst2;
                closestActor = point;
            }
        }
        return closestActor;
    }

    private static Vector2 getRange(float xOrigin, float yOrigin, float xTarget, float yTarget) {
        return new Vector2(xOrigin - xTarget, yOrigin - yTarget);
    }

    //Create an hit action using the coordinates given
    private static ParallelAction getHitAction(float fromPosX, float fromPosY, float toPosX, float toPosY) {
        return parallel(moveBy(((toPosX - fromPosX > 0)? Survivor.getInMeters(50) : Survivor.getInMeters(-50)) * 2, (toPosY - fromPosY), .3f),
                sequence(color(Color.RED), color(Color.WHITE, 1f)));
    }
    //Create an hit action using the coordinates given
    private static Vector2 getHitAction(Body attacker, Body defender, Actor defenderActor) {
        Vector2 fromDirection = attacker.getPosition().sub(defender.getPosition());
        fromDirection.scl(8f);
        //defender.setLinearVelocity(fromDirection);
        defender.setLinearDamping(1f);
        //defenderActor.clearActions();
        return fromDirection;
    }

    private void checkCompleteLevel() {
        if(enemies.getChildren().size == 0)
            completed = true;
    }

    public void setAllowInput(boolean input) {
        this.allowInput = input;
    }

    public void setAllowWeapons(boolean input) {
        this.weaponsAllowed = input;
    }
}
