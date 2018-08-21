package com.kazes.fallout.test.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.StringBuilder;
import com.kazes.fallout.test.*;
import com.kyper.yarn.Dialogue;
import com.kyper.yarn.Library;
import com.kyper.yarn.UserData;
import com.kyper.yarn.Value;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


public class SideScroll extends GameScreen {
    public static Texture texture; //temp pikachu texture for testing


    private Window dialogWindow;
    private String line;
    private NPC talkingTo;

    ///////////////////////////////
    //Dialogue vars
    private int[] OP_KEYS = { Input.Keys.NUM_1, Input.Keys.NUM_2, Input.Keys.NUM_3, Input.Keys.NUM_4, Input.Keys.NUM_5 };
    private Dialogue test_dialogue;
    private Dialogue.LineResult current_line = null;
    private Dialogue.OptionResult current_options = null;
    private Dialogue.CommandResult current_command = null;
    private Dialogue.NodeCompleteResult node_complete = null;
    private StringBuilder option_string;
    private boolean complete = true;


    public SideScroll(Survivor game) {
        super(game, "Prologue", 0);
        create(game);
    }
    public SideScroll(Survivor game, float startingPosX) {
        super(game, "Prologue", startingPosX);
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
    private void create (final Survivor game) {
        //SuperObject.init();
        //gameStage.setDebugAll(true);

        //gameStage.getBatch().setShader(new ShaderProgram(Gdx.files.internal("shaders/basicVertex120.vs").readString(), Gdx.files.internal("shaders/basicFragment120.fs").readString()));

        screenStage.addActor(player.bag);

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
                                        if(descItem instanceof Trap) {
                                            Array<Float> array = new Array<Float>();
                                            array.add(player.getX());
                                            array.add(player.getY());
                                            if (((Carryable) descItem).useItem(traps, array)) {
                                                player.bag.items.removeActor(player.bag.items.getCells().get(i).getActor());
                                                Gdx.app.log("Bag", descItem.getName() + " removed from bag");
                                                player.bag.changeDescription(null);
                                                return;
                                            }
                                        }
                                        else if (((Carryable) descItem).useItem(player, new Array<Float>())) {
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

    @Override
    public void update(float delta) {
        super.update(delta);
        //SuperObject.updateBegin(gameStage.getCamera());
        //this.processInput();
        this.pickItem();
        this.followPlayer();

        if(player.getY() > 280)
            player.setY(280);
        if(player.getY() < 0)
            player.setY(0);

        if(player.getHealth() == 0) {
            Gdx.app.log("Survivor", "Game over");
            Gdx.app.exit();
        }

        if(player.getX() > map.getWidth() + 1) {
            game.setScreen(new Tribe(game, 0));
        }

        updateDialogue();
        //SuperObject.updateEnd();
    }

    //Draws every frame
    @Override
    public void render (float delta) {
        super.render(delta);
        ((SideScrollingCamera)gameStage.getCamera()).followPos(player.getOrigin());
        parallaxBackground.setXPos(gameStage.getCamera().position.x - gameStage.getCamera().viewportWidth / 2);
        renderDialogue();
    }

    //Every input given from the player is processed here
    @Override
    public void proccessInput() {
        super.proccessInput();


        if(Gdx.input.isKeyJustPressed(Input.Keys.ALT_LEFT)) {
            for(int i = 0; i < npcs.getChildren().size; i++) {
                if(player.getRectangle().overlaps(((NPC)npcs.getChildren().items[i]).getRectangle())) {
                    talkingTo = (NPC)npcs.getChildren().items[i];
                    test_dialogue.start("TalkTo");
                    complete = false;
                    resetAllResults();
                    dialogWindow.setVisible(true);
                    break;
                }
            }
        }

        if(player.getWeapon() == Weapons.Pistol) {
            if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT)) {
                Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                mousePos = gameStage.screenToStageCoordinates(mousePos);
                this.bullets.addActor(new Bullet(player.getX(), player.getY(), mousePos.cpy().sub(player.getOrigin()).nor()));
                //gameStage.addActor(this.bullets.get(this.bullets.size - 1));
            }
        }
        if(player.getWeapon() == Weapons.SMG) {
            if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                if(player.cooldown > 17) {
                    Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                    mousePos = gameStage.screenToStageCoordinates(mousePos);
                    this.bullets.addActor(new Bullet(player.getX(), player.getY(), mousePos.cpy().sub(player.getOrigin()).nor()));
                    //gameStage.addActor(this.bullets.get(this.bullets.size - 1));
                    player.cooldown = 0;
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
                        ImageEx bonfire = new Bonfire(player.getOrigin().x, player.getOrigin().y);
                        bonfire.setBounds(player.getX(), player.getY(), 200, 300);
                        bonfires.addActor(bonfire);
                        Gdx.app.log("Survivor", "Bonfire set");
                        break;
                    }
                }
            }
        }
        inputDialogue();
    }





    //Checks if the player picked up an item
    private void pickItem() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            for (int i = 0; i < items.getChildren().size; i++) {
                ImageEx item = (ImageEx)items.getChildren().get(i);
                if (item.getRectangle().overlaps(player.getRectangle())) {
                    items.removeActor(item);
                    this.player.bag.addItem(item);
                }
            }
        }
    }

    private void followPlayer() {

        for(Actor follower : followers.getChildren()) {
            if(!follower.hasActions())
                follower.addAction(moveTo(player.getX() + ((player.xFlipped) ? 50 + ( 50 * followers.getChildren().indexOf(follower, false)) : -50 - ( 70 * followers.getChildren().indexOf(follower, false))), player.getY(), 1f));
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
                //Value action = params[0];// this parameter will be the name of the action sprite to set sally to


            }
        });
        test_dialogue.getLibrary().registerFunction("recruitAction", 0, new Library.Function() {
            @Override
            public void invoke(Value... params) {
                if(talkingTo != null) {
                    npcs.removeActor(talkingTo);
                    //gameStage.getActors().removeValue(talkingTo, false);
                    Gdx.app.log("Followers", talkingTo.getName() + " was added");
                    followers.addActor(talkingTo);
                }
            }
        });
        String ship_file = "dialogues/ship.json";
        String sally_file = "dialogues/sally.json";
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

    private void inputDialogue() {
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

    @Override
    public void setMap() {
        Array<Texture> parallaxTextures = new Array<Texture>();
        for(int i = 0; i < 6;i++){
            parallaxTextures.add(Assets.getAsset(Assets._Parallax1[i], Texture.class));
            parallaxTextures.get(i).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        parallaxBackground = new ParallaxBackground(parallaxTextures);
        parallaxBackground.setSize(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2);

        map = new ImageEx(game.assetManager.get(Assets.Images.MAP, Texture.class));
    }

    @Override
    public void setDecor() {
        decor.addActor(new ImageEx(game.assetManager.get(Assets.Images.HOUSE1, Texture.class), 500, 300));
        decor.addActor(new ImageEx(game.assetManager.get(Assets.Images.HOUSE2, Texture.class), 1000, 300));
    }

    @Override
    public void setPlayer(float startingPointX) {
        player.setX(startingPointX);
    }

    @Override
    public void setNPCS() {
        npcs.addActor(new NPC(game.assetManager.get(Assets.Images.NPC_TEMP_2, Texture.class), "Harambe", 50, 50, false, Weapons.Pistol));
        npcs.addActor(new NPC(game.assetManager.get(Assets.Images.NPC_TEMP_1, Texture.class), "Yilfa", 450, 50, true, Weapons.SMG));
        for (int i = 0; i < npcs.getChildren().size; i++) {
            npcs.getChildren().items[i].setSize(100, 150);
        }
    }

    @Override
    public void setEnemies() {
        texture = game.assetManager.get(Assets.Images.PIKACHU, Texture.class);
        for(int i = 0; i < 15; i++) {
            enemies.addActor(new Zombie(texture, MathUtils.random(1000, 4000), MathUtils.random(500), true));
            enemies.getChildren().items[i].setName("Zombie " + i);
        }
    }

    @Override
    public void setItems() {
        for(int i = 0; i < 7; i++) {
            items.addActor(new Medicine(MathUtils.random(0, 4000), MathUtils.random(0, 280)));
            items.getChildren().get(items.getChildren().size - 1).setName("Medicine number "+i);
        }
        for(int i = 0; i < 9; i++) {
            items.addActor(new Wood(Assets.getAsset(Assets.Images.TREELOG, Texture.class), MathUtils.random(0, 4000), MathUtils.random(0, 280)));
            items.getChildren().get(items.getChildren().size - 1).setName("Wood number "+i);
        }
        for(int i = 0; i < 3; i++) {
            items.addActor(new Tuna(MathUtils.random(0, 4000), MathUtils.random(0, 280)));
            items.getChildren().get(items.getChildren().size - 1).setName("Tuna number "+i);
        }
        for(int i = 0; i < 3; i++) {
            items.addActor(new Water(MathUtils.random(0, 4000), MathUtils.random(0, 280)));
            items.getChildren().get(items.getChildren().size - 1).setName("Water number "+i);
        }
        for(int i = 0; i < 5; i++) {
            items.addActor(new BearTrap(MathUtils.random(0, 4000), MathUtils.random(0, 280)));
            items.getChildren().get(items.getChildren().size - 1).setName("Bear Trap number "+i);
        }
    }
}

/*Ideas:
1. if you eat too much your speed will decrease
2. if you drink too much will speed will increase drastically (you need to piss)
3. if you use too much medkits you will get sick (but with full health)
4. getting sick causes loss of focus (shooting will get hard, and the screen will get bobbly)


 */