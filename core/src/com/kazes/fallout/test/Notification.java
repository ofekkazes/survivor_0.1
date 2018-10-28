package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;

public class Notification {
    String message;
    float time;

    public Notification(String message) {
        this.message = message;
        this.time = Gdx.graphics.getFramesPerSecond() * 10;
    }

    public float getTime() {
        return time;
    }

     public void decTime() {
        time--;
     }

    public String getMessage() {
        return message;
    }
}
