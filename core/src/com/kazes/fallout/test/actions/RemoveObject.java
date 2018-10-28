package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Action to remove an actor from a group
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-10-28
 */
public class RemoveObject extends Action {
    private Actor object;

    public RemoveObject(Actor object) {
        this.object = object;
    }

    @Override
    public boolean act(float delta) {
        if(getActor() instanceof Group) {
            ((Group)getActor()).removeActor(object);
            return true;
        }
        return false;
    }
}
