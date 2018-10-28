package com.kazes.fallout.test.stories;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.kazes.fallout.test.stories.ActorAction;

/**
 * Adds storytelling elements to a scene
 * @author Ofek Kazes
 */
public class CutsceneManager {
    com.badlogic.gdx.utils.Queue<ActorAction> actions; //The actions actors will do
    Actor lastUsedActor; //Last used actor by the manager

    public CutsceneManager() {
        actions = new com.badlogic.gdx.utils.Queue<ActorAction>();
        lastUsedActor = new Actor();
    }

    public void add(ActorAction action) {
        actions.addLast(action);
    }

    /**
     * Adds actions to an actor on a queue
     * @param actor The actor to do the action
     * @param action An action actors do
     */
    public void add(Actor actor, Action action) {
        actions.addLast(new ActorAction(actor, action));
    }

    /**
     * Use a the first action
     * @return An action and an actor
     */
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
