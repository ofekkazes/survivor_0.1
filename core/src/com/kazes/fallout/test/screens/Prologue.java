package com.kazes.fallout.test.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;
import com.kazes.fallout.test.*;
import com.kazes.fallout.test.enemies.Zombie;
import com.kyper.yarn.Dialogue;
import com.kyper.yarn.UserData;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Legacy Prologue for the game
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class Prologue extends GameScreen {

    private ParallaxBackground parallaxBackground;
    private Array<Bullet> bullets;

    private ImageEx npc1;
    private ImageEx npc2;
    boolean turn;
    private Array<Zombie> enemies;

    Actor cameraFollow;
    Actor fadeActor;

    int i  = 0;

    int dialogCounter = 0;

    //////
    Dialogue dialogue;
    Dialogue.LineResult current_line = null;
    Dialogue.OptionResult current_options = null;
    Dialogue.CommandResult current_command = null;
    Dialogue.NodeCompleteResult node_complete = null;
    StringBuilder option_string;
    boolean complete = true;
    private String line;
    private Window dialogWindow;

    boolean firstMove, secondMove, thirdMove;

    SpriteBatch batch;

    public Prologue(Survivor game) {
        super(game, "Prologue", 0);
        createGame(game);
        batch = new SpriteBatch();
    }

    public void createGame(Survivor game) {

        Array<Texture> parallaxTextures = new Array<Texture>();
        for(int i = 0; i < 6;i++){
            parallaxTextures.add(Assets.getAsset(Assets._Parallax1[i], Texture.class));
            parallaxTextures.get(i).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        parallaxBackground = new ParallaxBackground(parallaxTextures, gameStage.getCamera());
        parallaxBackground.setSize(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2);
        gameStage.addActor(parallaxBackground);

        gameStage.addActor(new ImageEx(game.assetManager.get(Assets.Images.MAP, Texture.class), 0, 0));

        bullets = new Array<Bullet>();

        npc1 = new ImageEx(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), 200, 200);
        npc2 = new ImageEx(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), 200, 100);
        turn = false;
        gameStage.addActor(npc1);
        gameStage.addActor(npc2);

        enemies = new Array<Zombie>();
        for(int i = 0; i < 150; i++) {
            enemies.add(new Zombie(MathUtils.random(1000, 3000), MathUtils.random(250), world));
            gameStage.addActor(enemies.get(i));
        }



        cameraFollow = new Actor();
        cameraFollow.setX(2500);
        gameStage.addActor(cameraFollow);


        createDialogue();
        dialogue.start("Start");
        complete = false;
        resetAllResults();

        dialogWindow = new Window("Dialog", Assets.getAsset(Assets.UI_SKIN, Skin.class));
        dialogWindow.setSize(Gdx.graphics.getWidth() / 2, 150);
        dialogWindow.setX(Gdx.graphics.getWidth() / 4);
        dialogWindow.add(new Label("",  Assets.getAsset(Assets.UI_SKIN, Skin.class)));
        ((Label)dialogWindow.getCells().get(0).getActor()).setFontScale(1.5f);
        screenStage.addActor(dialogWindow);

        line = "";

        firstMove = false;
        secondMove = false;
        thirdMove = false;

        fadeActor = new ImageEx(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), 0, 0);
        fadeActor.setColor(Color.BLACK);
        fadeActor.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        fadeActor.setBounds(-1500, -1000, 3200, 2100);
        screenStage.addActor(fadeActor);
        fadeActor.addAction(Actions.sequence(delay(4f), Actions.fadeOut(10f),Actions.hide ()));
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        inputDialogue();
        if(!thirdMove) {
            if (dialogCounter > 2) {
                if (!firstMove) {
                    firstMove = true;
                    cameraFollow.addAction(sequence(moveBy(-1000, 0, 4f, Interpolation.sine)));
                }
                if (dialogCounter > 7) {
                    if (firstMove && !secondMove) {
                        secondMove = true;
                        cameraFollow.addAction(sequence(moveBy(-1000, 0, 4f, Interpolation.sine)));
                    }

                }
                i++;
                fireGuns();
                zombieInteractions();
            }
        }
        updateDialogue();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ((SideScrollingCamera)gameStage.getCamera()).followPos(new Vector2(cameraFollow.getX(), cameraFollow.getY()));
        renderDialogue();
        if(thirdMove) {
            if(complete) {
                batch.begin();
                batch.draw(Assets.getAsset(Assets.Images.SPLASH, Texture.class), 0, 0);
                batch.end();
            }
        }
    }

    //Checks if the player fired the gun, and checks for the boundries of the bullets
    private void fireGuns() {
        if(i % 30 == 0) {
            turn = !turn;
            ImageEx position = (turn) ? npc1 : npc2;
            bullets.add(new Bullet(world, position.getX(), position.getY(), GameScreen.closestTo(new Array<ImageEx>(enemies), position).getOrigin().cpy().sub(position.getOrigin()).nor()));
            gameStage.addActor(this.bullets.get(bullets.size - 1));
        }
        if(this.bullets.size > 0) {
            if (this.bullets.get(this.bullets.size - 1).getTimeToLive() < 0) {
                gameStage.getActors().removeValue(this.bullets.removeIndex(this.bullets.size - 1), true);
            }
        }
    }

    private void zombieInteractions() {
        for (int i = 0; i < enemies.size; i++) {
            enemies.get(i).setY(MathUtils.clamp(enemies.get(i).getY(), 0, game.assetManager.get(Assets.Images.MAP, Texture.class).getHeight() - 200));

            float yTranslate = npc1.getY() - enemies.get(i).getY();
            float xTranslate = enemies.get(i).getX() - npc1.getX();
            if (!enemies.get(i).hasActions()) {
                if((xTranslate > -150 && xTranslate < 150))
                    yTranslate = (yTranslate > 0 ) ? .4f : -.4f;
                else yTranslate = 0;
                xTranslate = (xTranslate > 0) ? -.5f : .5f;
                enemies.get(i).clearActions();
                enemies.get(i).translate(xTranslate, yTranslate);
            }

            for (int j = 0; j < this.bullets.size; j++) {
                if (this.bullets.get(j).getRectangle().overlaps(enemies.get(i).getRectangle())) {
                    enemies.get(i).subHealth(20);
                    enemies.get(i).clearActions();
                    enemies.get(i).addAction(getHitAction(bullets.get(j).getX(), bullets.get(j).getY(), enemies.get(i).getX(), enemies.get(i).getY()));
                    gameStage.getActors().removeValue(this.bullets.removeIndex(j), true);
                    if (enemies.get(i).getHealth() == 0)
                        gameStage.getActors().removeValue(this.enemies.removeIndex(i), true);
                    break;
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
        dialogue = new Dialogue(new UserData("data"));

        dialogue.loadFile(Assets.Dialogues.PROLOGUE, false, false, null);
    }

    private void updateDialogue() {
        // if we currently dont have any command available check if next result is a
        // command
        if (current_command == null && dialogue.isNextCommand()) {
            // assign it
            current_command = dialogue.getNextAsCommand();
        }
        // if we dont have a line - check if next result is a line
        else if (current_line == null && dialogue.isNextLine()) {
            // if there is a command result - execute it before the next line
            executeCommand();
            // assign the line
            current_line = dialogue.getNextAsLine();

        }
        // if we dont have any options check if the next result is options
        else if (current_options == null && dialogue.isNextOptions()) {
            // assign the options
            current_options = dialogue.getNextAsOptions();
        }
        // if the node has not found a complete result - check if next result is a node
        // complete result
        else if (node_complete == null && dialogue.isNextComplete()) {
            // assign node complete result
            gameStage.clear();
            thirdMove = true;
            node_complete = dialogue.getNextAsComplete();
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
                dialogue.stop();
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


        // space goes to next line unless there is options
        if (current_line != null && current_options == null) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                current_line = null;
                dialogCounter++;
            }
        }

        // there is options so check all corresponding keys(1-5)
        if (current_options != null) {
        // then clear options and current line - break out of for loop
            current_options = null;
            current_line = null;
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
    public void setMap() {

    }

    @Override
    public void setDecor() {

    }


    @Override
    public void setNPCS() {

    }

    @Override
    public void setEnemies() {

    }

    @Override
    public void setItems() {

    }

}
