package com.kazes.fallout.test;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CutsceneManager {
    com.badlogic.gdx.utils.Queue<ActorAction> actions;
    Actor lastUsedActor;

    public CutsceneManager(String filepath) {
        this();
    }
    public CutsceneManager() {
        actions = new com.badlogic.gdx.utils.Queue<ActorAction>();
        lastUsedActor = new Actor();
    }

    public void add(ActorAction action) {
        actions.addLast(action);
    }

    public void add(Actor actor, Action action) {
        actions.addLast(new ActorAction(actor, action));
    }

    public ActorAction take() {
        lastUsedActor = peekFirst().assignedActor;
        return actions.removeFirst();
    }

    public ActorAction peekFirst() {
        return actions.first();
    }

    public boolean isEmpty() {
        return actions.size == 0;
    }

    public Actor getLastUsedActor() {
        return lastUsedActor;
    }

}
