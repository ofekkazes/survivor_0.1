package com.kazes.fallout.test.dialogues;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import java.util.ArrayList;
@Deprecated
public class Dialog extends Window {
    Label label;
    Image image;
    int toIndex;
    int index = 0;
    boolean waitFlag;

    ArrayList<String> texts;
    @Deprecated
    public Dialog(String name, Skin skin, Texture img) {
        super( name, skin);
        this.setDebug(true);
        this.setSize(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 5, 200);
        this.setPosition(Gdx.graphics.getWidth() / 10, 0);
        //this.setColor(Color.DARK_GRAY);
        this.setVisible(false);

        label = new Label("", skin);
        label.setFontScale(1.7f);
        image = new Image(img);

        this.add(image).height(this.getHeight() - this.getHeight() / 4).width(this.getWidth() / 5).left().expandY();
        this.add(label).expand().left().top().pad(20);

        texts = new ArrayList<String>();
        waitFlag = false;

        index = 0;
        toIndex = -1;
    }
    @Deprecated
    public void makeStop(int toIndex) {
        this.toIndex = toIndex;
        waitFlag = true;
    }
    @Deprecated
    public void addLine(String text) {
        texts.add(text);
    }
    @Deprecated
    public void addLine(String text, Texture image) {//TO BE IMPROVED
        texts.add(text);
    }
    @Deprecated
    public void update() {
        if(toIndex >= index) {
            this.setVisible(true);
            this.setText(texts.get(index));
            if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
                index++;
        } //else
        else if(waitFlag) {
            if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
                waitFlag = false;
                this.setVisible(false);
            }
        }
    }
    @Deprecated
    public void setText(String text) { label.setText(text); }
    @Deprecated
    public void setImage(Texture img) { image = new Image(img);}

}

