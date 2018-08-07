package com.kazes.fallout.test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.sun.prism.paint.Color;

import java.util.Iterator;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


public class SceneTest implements ApplicationListener {
    public class MyActor extends Actor {
        Texture texture = new Texture(Gdx.files.internal("medieval-tavern_10000.png"));
        public boolean started = false;

        public MyActor(){
            setBounds(getX(),getY(),texture.getWidth(),texture.getHeight());
        }

        @Override
        public void draw(Batch batch, float alpha){
            batch.draw(texture,this.getX(),getY(),this.getOriginX(),this.getOriginY(),this.getWidth(),
                    this.getHeight(),this.getScaleX(), this.getScaleY(),this.getRotation(),0,0,
                    texture.getWidth(),texture.getHeight(),false,false);

        }

    }
    private Stage stage;

    @Override
    public void create() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        MyActor myActor = new MyActor();

        RotateToAction rotateAction = new RotateToAction();
        MoveToAction moveAction = new MoveToAction();
        ColorAction colorAction = new ColorAction();
        ScaleToAction scaleAction = new ScaleToAction();

        colorAction.setColor(new com.badlogic.gdx.graphics.Color().set(.4f,.6f,.3f,1));
        colorAction.setEndColor(new com.badlogic.gdx.graphics.Color().set(.2f,.1f,.1f,1));
        colorAction.setDuration(5f);
        moveAction.setPosition(300f, 0f);
        moveAction.setDuration(5f);
        rotateAction.setRotation(90f);
        rotateAction.setDuration(5f);
        scaleAction.setScale(0.5f);
        scaleAction.setDuration(5f);

        myActor.addAction(sequence(scaleTo(0.5f,0.5f,5f),rotateTo(90.0f,5f),moveTo(300.0f,0f,5f)));

        stage.addActor(myActor);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
