package com.kazes.fallout.test;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class CamFollowActor extends Actor {
    private Actor actor;

    public CamFollowActor(Actor follow) {
        this.actor = follow;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }
}
