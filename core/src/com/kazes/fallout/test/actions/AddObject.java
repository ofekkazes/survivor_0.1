package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Action to add an actor to a group
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-10-28
 */
public class AddObject extends Action {
    private Actor object;

    public AddObject(Actor object) {
        this.object = object;
    }

    @Override
    public boolean act(float delta) {
        if(getActor() instanceof Group) {
            ((Group)getActor()).addActor(object);
            return true;
        }
        return false;
    }
}
