package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Timer;



public class Mercenary extends ImageEx {
    private int numOfMercenaries;
    private int numberOfKills;
    private Group foundItem;
    private int riskPercentage;
    private boolean isDone;
    private float time;
    private boolean start;
    private int timeInSeconds;

    public Mercenary(float xPos, float yPos, int numOfMercenaries, int timeInSeconds, int riskPercentage) {
        super(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), xPos, yPos);
        this.numOfMercenaries = numOfMercenaries;
        this.riskPercentage = riskPercentage;
        this.time = 0;
        this.timeInSeconds = timeInSeconds;
        this.numberOfKills = 0;
        this.start = false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(start) {
            time += delta;
            if(time >= this.timeInSeconds) {
                this.start = false;
                this.isDone = true;
                this.time = 0;
                setVisible(true);
                int randPercentage = MathUtils.random(100);
                this.numOfMercenaries = (randPercentage - riskPercentage < 0) ? (int)((randPercentage - riskPercentage < -50) ? this.numOfMercenaries / 3 : this.numOfMercenaries / 1.5f) : this.numOfMercenaries;
                this.numberOfKills = ((riskPercentage * numOfMercenaries) / (randPercentage > 9 ? randPercentage / 10 : 1));
            }
        }
    }

    public void startTimer() {
        this.start = true;
        setVisible(false);
    }

    public boolean isDone() {
        return isDone;
    }
    public int getNumberOfKills() {
        return  numberOfKills;
    }
    public void addMercenary(int amount) {
        this.numOfMercenaries += amount;
    }

    public int getRisk() {
        return this.riskPercentage;
    }

    public int getTime() {
        return timeInSeconds;
    }

    public int getNumOfMercenaries() {
        return numOfMercenaries;
    }
}
