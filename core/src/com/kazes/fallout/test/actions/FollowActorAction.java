package com.kazes.fallout.test.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.kazes.fallout.test.CamFollowActor;

public class FollowActorAction extends Action {
    Actor following;

    public FollowActorAction(Actor followTarget) {
        this.following = followTarget;
    }

    @Override
    public boolean act(float delta) {
        if(getActor() instanceof CamFollowActor) {
            ((CamFollowActor)getActor()).setActor(this.following);
            return true;
        }
        return false;
    }
}
