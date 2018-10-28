package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.kazes.fallout.test.Mission;
import com.kazes.fallout.test.MissionActor;
import com.kazes.fallout.test.Notification;
import com.kazes.fallout.test.screens.GameScreen;

/**
 * Action to add missions to the player
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-10-28
 */
public class AddMission extends Action {
    Mission mission;
    Object object;
    public AddMission(Mission mission, Object object) {
        this.mission = mission;
        this.object = object;
    }
    @Override
    public boolean act(float delta) {
        GameScreen.getObjectiveWindow().addMission(mission, object);
        getActor().addAction(new AddNotification("Mission log updated"));
        return true;
    }
}
