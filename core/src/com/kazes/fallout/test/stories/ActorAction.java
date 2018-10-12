package com.kazes.fallout.test.stories;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorAction {
    public Actor assignedActor;
    public Action action;

    public ActorAction(Actor actor, Action action) {
        this.assignedActor = actor;
        this.action = action;
    }
}
