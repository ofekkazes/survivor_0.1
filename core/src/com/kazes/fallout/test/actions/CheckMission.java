package com.kazes.fallout.test.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.kazes.fallout.test.Mission;
import com.kazes.fallout.test.MissionActor;

public class CheckMission extends Action {
    MissionActor mission;

    public CheckMission(MissionActor missionActor) {
        this.mission = missionActor;
    }

    @Override
    public boolean act(float delta) {
        if(mission.isCompleted()) {
            ((Group) getActor()).removeActor(mission);
            return true;
        }
        return false;
    }
}
