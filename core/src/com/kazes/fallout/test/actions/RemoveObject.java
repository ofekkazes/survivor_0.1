package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

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
