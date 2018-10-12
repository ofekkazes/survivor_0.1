package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.kazes.fallout.test.Mission;
import com.kazes.fallout.test.MissionActor;
import com.kazes.fallout.test.screens.GameScreen;

public class AddMission extends Action {

    public AddMission(Mission mission, Object object) {
        GameScreen.getObjectiveWindow().addMission(mission, object);
    }
    @Override
    public boolean act(float delta) {
        return true;
    }
}
