package com.kazes.fallout.test;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class MissionActor extends Actor {
    Mission mission;

    public MissionActor(Mission mission) {
        this.mission = mission;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        mission.update();
    }

    public boolean isCompleted() {
        return mission.isCompleted();
    }

    public Mission getMission() {
        return mission;
    }
}
