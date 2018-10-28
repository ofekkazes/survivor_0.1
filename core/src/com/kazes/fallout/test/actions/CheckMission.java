package com.kazes.fallout.test.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.kazes.fallout.test.Mission;
import com.kazes.fallout.test.MissionActor;

/**
 * Action to check if a mission was completed
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-10-28
 */
public class CheckMission extends Action {
    Mission mission;

    public CheckMission(Mission missionActor) {
        this.mission = missionActor;
    }

    @Override
    public boolean act(float delta) {
        if(mission.isCompleted()) {
            return true;
        }
        return false;
    }
}
