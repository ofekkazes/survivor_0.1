package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.kazes.fallout.test.Mission;
import com.kazes.fallout.test.MissionActor;

public class AddMission extends Action {
    Mission mission;

    public AddMission(Mission mission, Object object) {
        this.mission = mission;
        this.mission.addRequirment(object);
    }
    @Override
    public boolean act(float delta) {
        ((Group)getActor()).addActor(new MissionActor(mission));
        return true;
    }
}
