package com.kazes.fallout.test;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kazes.fallout.SuperObject;
import com.kyper.yarn.Dialogue;
import com.kyper.yarn.Library;
import com.kyper.yarn.UserData;
import com.kyper.yarn.Value;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
/*
import com.kyper.yarn.Dialogue;
import com.kyper.yarn.Dialogue.CommandResult;
import com.kyper.yarn.Dialogue.LineResult;
import com.kyper.yarn.Dialogue.NodeCompleteResult;
import com.kyper.yarn.Dialogue.OptionResult;
import com.kyper.yarn.Library.Function;
import com.kyper.yarn.UserData;
import com.kyper.yarn.Value;*/

public class SideScroll extends AbstractScreen {

    private ParallaxBackground parallaxBackground;
    public static Texture texture; //temp pikachu texture for testing
    private Player player; //player actor
    float stateTime;
    private Array<Zombie> enemies; //enemy actors
    private Array<NPC> npcs; //Friendly foes
    private Stage gameStage; //game container
    private Stage screenStage; // screen container
    private Skin skin; //default game skin
    private Array<Carryable> items; //Scattered supplies
    private Window dialogWindow;
    private String line;
    Group followers;
    NPC talkingTo;


    ///////////////////////////////
    //Dialogue vars
    int[] OP_KEYS = { Input.Keys.NUM_1, Input.Keys.NUM_2, Input.Keys.NUM_3, Input.Keys.NUM_4, Input.Keys.NUM_5 };
    String ship_file = "dialogues/ship.json";
    String sally_file = "dialogues/sally.json";
    Dialogue test_dialogue;
    Dialogue.LineResult current_line = null;
    Dialogue.OptionResult current_options = null;
    Dialogue.CommandResult current_command = null;
    Dialogue.NodeCompleteResult node_complete = null;
    StringBuilder option_string;
    boolean complete = true;


    public SideScroll(Survivor game) {
        super(game);
        create(game);
    }
    @Override
    public void pauseLogic() {}

    @Override
    public void resumeLogic(){}

    @Override
    public void show() {}

    @Override
    public void hide() {}

    //Load the content
    public void create (Survivor game) {
        //SuperObject.init();

        skin = game.assetManager.get(Assets.UI_SKIN, Skin.class);

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


        Array<Texture> textures = new Array<Texture>();
        textures.add(game.assetManager.get(Assets.Images.PARALLAX_1, Texture.class));
        textures.add(game.assetManager.get(Assets.Images.PARALLAX_2, Texture.class));
        textures.add(game.assetManager.get(Assets.Images.PARALLAX_3, Texture.class));
        textures.add(game.assetManager.get(Assets.Images.PARALLAX_4, Texture.class));
        textures.add(game.assetManager.get(Assets.Images.PARALLAX_5, Texture.class));
        textures.add(game.assetManager.get(Assets.Images.PARALLAX_6, Texture.class));

        for(int i = 1; i <=6;i++){

            textures.get(textures.size-1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        parallaxBackground = new ParallaxBackground(textures);
        parallaxBackground.setSize(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2);
        gameStage.addActor(parallaxBackground);

        gameStage.addActor(new ImageEx(game.assetManager.get(Assets.Images.MAP, Texture.class)));



        ObjectMap<String, Animation<TextureRegion>> temp = new ObjectMap<String, Animation<TextureRegion>>();
        for(int i = 0; i < Assets.animationList.size; i++) {
            if(Assets.animationList.getKeyAt(i).contains(Assets.Animations.HERO)) {
                temp.put(Assets.animationList.getKeyAt(i), Assets.animationList.getValueAt(i));
            }

        }
        player = new Player(temp, new Vector2(250, gameStage.getHeight() / 2 - 100));
        player.setZIndex(10000);
        gameStage.addActor(player);


        texture = game.assetManager.get(Assets.Images.PIKACHU, Texture.class);
        enemies = new Array<Zombie>();
        for(int i = 0; i < 25; i++)
            enemies.add(new Zombie(texture, MathUtils.random(1000, 4000), MathUtils.random(500), true));

        for (int i = 0; i < enemies.size; i++) {
            enemies.get(i).setName("Zombie " + i);
            //enemies.get(i).addAction(forever(moveBy(MathUtils.random(-150, 150), MathUtils.random(-50, 50), 3f)));
            gameStage.addActor(enemies.get(i));//Make movement random
        }
        npcs = new Array<NPC>();
        npcs.add(new NPC(game.assetManager.get(Assets.Images.NPC_TEMP_2, Texture.class), "Harambe", 50, 50, false));
        npcs.add(new NPC(game.assetManager.get(Assets.Images.NPC_TEMP_1, Texture.class), "Yilfa", 450, 50, true));
        for (int i = 0; i < npcs.size; i++) {
            npcs.get(i).setSize(100, 150);
            gameStage.addActor(npcs.get(i));

        }
        followers = new Group();



        //Level start sequence
        Action action = sequence(
                moveBy(50f, 0, 1f),
                delay(1f),
                moveBy(-50f, 0, 1f),
                delay(1f),
                moveBy(50f, 0, 1f),
                delay(1f),
                moveBy(-50f, 0, 1f),
                delay(.5f),
                moveTo(150f, player.getY(), 2f)
                );
        //player.addAction(action);

        screenStage.addActor(player.bag);

        items = new Array<Carryable>();
        for(int i = 0; i < 7; i++) {
            items.add(new Medicine(texture, MathUtils.random(0, 4000), MathUtils.random(0, 280)));
            ((Medicine)(items.get(i))).setName("Medicine number "+i);
        }
        for(int i = 0; i < 7; i++) {
            items.add(new Wood(Assets.getAsset(Assets.Images.TREELOG, Texture.class), MathUtils.random(0, 4000), MathUtils.random(0, 280)));
            ((Wood)(items.peek())).setName("Wood number "+i);
        }
        for(int i = 0; i < 3; i++) {
            items.add(new Tuna(MathUtils.random(0, 4000), MathUtils.random(0, 280)));
            ((Tuna)(items.peek())).setName("Tuna number "+i);
        }
        for(int i = 0; i < 3; i++) {
            items.add(new Water(MathUtils.random(0, 4000), MathUtils.random(0, 280)));
            ((Water)(items.peek())).setName("Water number "+i);
        }
        for(int i = 0; i < items.size; i++)
            gameStage.addActor((ImageEx)items.get(i));


        player.bag.items.addListener(new ClickListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //BAG>DESCRIPTION>ADD(ITEM)
                //COLOR>GLOW
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                //BAG>DESCRIPTION>REMOVE(ITEM)
                //COLOR>DEFAULT
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(player.bag.isVisible()) {
                    Actor temp;
                    for (int i = 0; i < player.bag.items.getCells().size; i++) {
                        temp = player.bag.items.getCells().get(i).getActor();
                        if (temp != null) {
                            if (((ImageEx) temp).getRectangle().contains(x, y)) {
                                //player.bag.items.removeActor(temp);

                                player.bag.changeDescription(temp);
                                return;
                            }
                        }
                    }
                }
            }
        });
        player.bag.useButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(player.bag.isVisible()) {
                    Actor descItem;
                    descItem = player.bag.description.getCells().get(0).getActor();
                    if (descItem != null) {
                        if (descItem instanceof Carryable) {
                            Actor bagItem;
                            for (int i = 0; i < player.bag.items.getCells().size; i++) {
                                bagItem = player.bag.items.getCells().get(i).getActor();
                                if(bagItem != null) {
                                    if (bagItem.getName().compareTo(descItem.getName()) == 0) {
                                        if (((Carryable) descItem).useItem(player)) {
                                            player.bag.items.removeActor(player.bag.items.getCells().get(i).getActor());
                                            Gdx.app.log("Bag", descItem.getName() + " removed from bag");
                                            player.bag.changeDescription(null);
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        createDialogue();
        //test_dialogue.start("Start");
        complete = true;
        resetAllResults();

        dialogWindow = new Window("Dialog", Assets.getAsset(Assets.UI_SKIN, Skin.class));
        dialogWindow.setSize(Gdx.graphics.getWidth(), 200);
        dialogWindow.setVisible(false);
        dialogWindow.add(new Label("",  Assets.getAsset(Assets.UI_SKIN, Skin.class)));
        screenStage.addActor(dialogWindow);

        line = "";
    }

    //Update the logic every frame
    public void update() {
        //SuperObject.updateBegin(gameStage.getCamera());
        this.processInput();

        this.fireGun();
        this.playerZombieInteraction();
        this.pickItem();

        for(int i = 0; i < npcs.size; i++) {
            if (player.getRectangle().overlaps(npcs.get(i).getRectangle()) && Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT)) {
                Gdx.app.log("Interaction occurred", "Player interacted with " + npcs.get(i).getName());
                return;
            }
        }
        for(Zombie zombie : enemies) {
            zombie.setX(MathUtils.clamp(zombie.getX(), 0, game.assetManager.get(Assets.Images.MAP, Texture.class).getWidth()));
            zombie.setY(MathUtils.clamp(zombie.getY(), 0, game.assetManager.get(Assets.Images.MAP, Texture.class).getHeight() - 200));
        }

        if(player.getY() > 280)
            player.setY(280);
        if(player.getY() < 0)
            player.setY(0);

        if(player.getHealth() == 0) {
            Gdx.app.log("Survivor", "Game over");
            Gdx.app.exit();
        }

        updateDialogue();
        //SuperObject.updateEnd();
    }

    //Draws every frame
    @Override
    public void render (float delta) {
        this.update();
        Gdx.gl.glClearColor(.22f, .69f, .87f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        ((SideScrollingCamera)gameStage.getCamera()).followPos(player.getRectangle());
        parallaxBackground.setXPos(gameStage.getCamera().position.x - gameStage.getCamera().viewportWidth / 2);

        gameStage.act(Gdx.graphics.getDeltaTime());
        gameStage.draw();

        screenStage.act(Gdx.graphics.getDeltaTime());
        screenStage.draw();

        renderDialogue();

        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
    }

    //Every input given from the player is processed here
    private void processInput() {
        parallaxBackground.setSpeed(0);
        player.changeAnimation(Assets.Animations.HERO + "_idle");

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.translateX(-3);
            if(((SideScrollingCamera)gameStage.getCamera()).getUpdateCamera())
                parallaxBackground.setSpeed(-0.2f);
            if(!player.xFlipped)
                player.flip(true, false);
            player.changeAnimation(Assets.Animations.HERO + "_walking");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.translateX(3);
            if(((SideScrollingCamera)gameStage.getCamera()).getUpdateCamera())
                parallaxBackground.setSpeed(0.2f);
            if(player.xFlipped)
                player.flip(true, false);
            player.changeAnimation(Assets.Animations.HERO + "_walking");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            player.translateY(3);
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            player.translateY(-3);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ALT_LEFT)) {
            for(int i = 0; i < npcs.size; i++) {
                if(player.getRectangle().overlaps(npcs.get(i).getRectangle())) {
                    talkingTo = npcs.get(i);
                    test_dialogue.start("TalkTo");
                    complete = false;
                    resetAllResults();
                    dialogWindow.setVisible(true);
                    break;
                }
            }
        }


        if(Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            Actor bagItem;
            for (int i = 0; i < player.bag.items.getCells().size; i++) {
                bagItem = player.bag.items.getCells().get(i).getActor();
                if(bagItem != null) {
                    if (bagItem instanceof Wood) {
                        player.bag.items.removeActor(player.bag.items.getCells().get(i).getActor());
                        gameStage.addActor(new Bonfire(player.getX(), player.getY()));
                        Gdx.app.log("Survivor", "Bonfire set");
                        break;
                    }
                }
            }
        }
        inputDialogue();
    }

    //Checks if the player fired the gun, and checks for the boundries of the bullets
    private void fireGun() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT)) {
            this.player.bullets.add(new ImageEx(texture));
            this.player.bullets.get(player.bullets.size - 1).setPosition(this.player.getX(), this.player.getY());
            this.player.bullets.get(player.bullets.size - 1).addAction(forever(moveBy((player.xFlipped) ? -60f : 60f, 0)));
            gameStage.addActor(this.player.bullets.get(player.bullets.size - 1));
        }
        if(this.player.bullets.size > 0) {
            if (this.player.getX() + 650 < this.player.bullets.get(this.player.bullets.size - 1).getX() ||
                    this.player.getX() - 650 > this.player.bullets.get(this.player.bullets.size - 1).getX()) {
                gameStage.getActors().removeValue(this.player.bullets.removeIndex(this.player.bullets.size - 1), true);
            }
        }
    }

    //Player, Enemy and Bullets collision check, plus the most sophisticated AI the world has ever seen for a zombie
    private void playerZombieInteraction() {
        for (int j = 0; j < enemies.size; j++) {
            if(!enemies.get(j).hasActions())
                enemies.get(j).addAction(moveTo(player.getX(), player.getY(), (forcePositive(enemies.get(j).getX() - player.getX())) / 50, Interpolation.pow2In)); //add y to time calculation

            if(player.getRectangle().overlaps(enemies.get(j).getRectangle())) {
                player.addAction(getHitAction(enemies.get(j).getX(), enemies.get(j).getY(), player.getX(), player.getY()));
                player.subHealth(30);
            }
            for (int i = 0; i < this.player.bullets.size; i++) {
                if (this.player.bullets.get(i).getRectangle().overlaps(enemies.get(j).getRectangle())) {

                    enemies.get(j).subHealth(20);
                    enemies.get(j).clearActions();
                    enemies.get(j).addAction(getHitAction(player.getX(), player.getY(), enemies.get(j).getX(), enemies.get(j).getY()));
                    gameStage.getActors().removeValue(this.player.bullets.removeIndex(i), true);
                    if (enemies.get(j).getHealth() == 0) {
                        gameStage.getActors().removeValue(this.enemies.removeIndex(j), true);
                    }
                    break;
                }
            }
        }
    }

    //Checks if the player picked up an item
    private void pickItem() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            for (int i = 0; i < items.size; i++) {
                if (((ImageEx) items.get(i)).getRectangle().overlaps(player.getRectangle())) {
                    if(items.get(i) instanceof Medicine)
                        this.player.bag.addItem(((Medicine) this.items.removeIndex(i)));
                    else if(items.get(i) instanceof Wood)
                        this.player.bag.addItem(((Wood) this.items.removeIndex(i)));
                    else if(items.get(i) instanceof Tuna)
                        this.player.bag.addItem(((Tuna) this.items.removeIndex(i)));
                    else if(items.get(i) instanceof Water)
                        this.player.bag.addItem(((Water) this.items.removeIndex(i)));
                }
            }
        }
    }

    //Create an hit action using the coordinates given
    private ParallelAction getHitAction(float fromPosX, float fromPosY, float toPosX, float toPosY) {
        return parallel(moveBy(((toPosX - fromPosX > 0)? 50 : -50) * 2, (toPosY - fromPosY), .3f),
                sequence(color(Color.RED), color(Color.WHITE, 1f)));
    }

    private void createDialogue() {
        option_string = new StringBuilder(400);
        test_dialogue = new Dialogue(new UserData("test_data"));
        test_dialogue.getLibrary().registerFunction("setSallyAction", 1, new Library.Function() {
            @Override
            public void invoke(com.kyper.yarn.Value... params) {
                // this function only has one parameter so check like so
                Value action = params[0];// this parameter will be the name of the action sprite to set sally to


            }
        });
        test_dialogue.getLibrary().registerFunction("recruitAction", 0, new Library.Function() {
            @Override
            public void invoke(Value... params) {
                if(talkingTo != null) {
                    followers.addActor(talkingTo);
                    npcs.removeValue(talkingTo, false);
                    gameStage.getActors().removeValue(talkingTo, false);
                    Gdx.app.log("Followers", talkingTo.getName() + " was added");
                    return;
                }
            }
        });
        test_dialogue.loadFile(ship_file, false, false, null);
        test_dialogue.loadFile(sally_file, false, false, null);
        test_dialogue.loadFile("dialogues/example.json", false, false, null);
    }

    private void updateDialogue() {
        // if we currently dont have any command available check if next result is a
        // command
        if (current_command == null && test_dialogue.isNextCommand()) {
            // assign it
            current_command = test_dialogue.getNextAsCommand();
        }
        // if we dont have a line - check if next result is a line
        else if (current_line == null && test_dialogue.isNextLine()) {
            // if there is a command result - execute it before the next line
            executeCommand();
            // assign the line
            current_line = test_dialogue.getNextAsLine();

        }
        // if we dont have any options check if the next result is options
        else if (current_options == null && test_dialogue.isNextOptions()) {
            // assign the options
            current_options = test_dialogue.getNextAsOptions();
        }
        // if the node has not found a complete result - check if next result is a node
        // complete result
        else if (node_complete == null && test_dialogue.isNextComplete()) {
            // assign node complete result
            node_complete = test_dialogue.getNextAsComplete();
        } else {
            // waiting to proccess line or no results available

            // check if the current line is proccessed(null) and that we have a node
            // complete result
            if (current_line == null && node_complete != null) {
                // execute any lingering commands
                executeCommand();
                // set complete to true
                complete = true;

                // lets clean up the results
                resetAllResults();

                // stop the dialogue
                test_dialogue.stop();
                //dialogWindow.addAction(parallel(scaleBy(Gdx.graphics.getWidth(), 200, 1f, Interpolation.sine), moveTo(0,0, 1f)));
                dialogWindow.setVisible(false);
            }
        }

    }

    private void executeCommand() {
        if (current_command != null) {
            String params[] = current_command.getCommand().split("\\s+"); // commands are space delimited-- any space
            for (int i = 0; i < params.length; i++) {
                params[i] = params[i].trim(); // just trim to make sure
            }
            current_command = null;
        }
    }

    public void inputDialogue() {
        if (complete) {

            if (Gdx.input.isKeyJustPressed(OP_KEYS[0])) {
                // talk to ship
                test_dialogue.start("Ship");
                complete = false;
                resetAllResults();
            }

            if (Gdx.input.isKeyJustPressed(OP_KEYS[1])) {
                // talk to sally
                test_dialogue.start("Sally");
                complete = false;
                resetAllResults();
            }

            return;

        }

        // space goes to next line unless there is options
        if (current_line != null && current_options == null) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                current_line = null;
            }
        }

        // there is options so check all corresponding keys(1-5)
        if (current_options != null) {
            // check to see what is less - the amount of options or the size of keys we are
            // using to accept options
            int check_limit = Math.min(current_options.getOptions().size, OP_KEYS.length); // we do this to avoid array
            // index exceptions
            for (int i = 0; i < check_limit; i++) {
                // loop to see if any of the corresponding keys to options is pressed
                if (Gdx.input.isKeyJustPressed(OP_KEYS[i])) {
                    // if yes then choose
                    current_options.choose(i);

                    // then clear options and current line - break out of for loop
                    current_options = null;
                    current_line = null;
                    break;
                }
            }
        }
    }

    private void renderDialogue() {
        if (!complete) {
            if(!dialogWindow.isVisible())
                dialogWindow.setVisible(true);
            //if(!dialogWindow.hasActions())
             //   dialogWindow.addAction(scaleBy(Gdx.graphics.getWidth(), 200, 1f, Interpolation.sine));
            // draw dialogue
            if (current_line != null) {
                line = current_line.getText();
            }

            // draw options
            if (current_options != null) {
                int check_limit = Math.min(current_options.getOptions().size, OP_KEYS.length); // we do this to avoid
                // array
                // index exceptions
                for (int i = 0; i < check_limit; i++) {
                    String option = current_options.getOptions().get(i);
                    option_string.setLength(0);
                    option_string.append('[').append(i + 1).append(']').append(':').append(' ').append(option);
                    if(!line.isEmpty())
                        line += "\n" + option_string.toString();
                    else
                        line = option_string.toString();
                }
            }
            ((Label)dialogWindow.getCells().get(0).getActor()).setText(line);
        }


    }

    private void resetAllResults() {
        current_line = null;
        current_options = null;
        node_complete = null;
        current_command = null;
    }

    @Override
    public void dispose() {
        super.dispose();
        gameStage.dispose();
        screenStage.dispose();
    }

    public float forcePositive(float number) {
        return (number < 0) ? number * -1 : number;
    }
}

