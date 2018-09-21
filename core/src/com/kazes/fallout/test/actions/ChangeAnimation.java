package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.AnimationActor;

public class ChangeAnimation extends Action {
    String keyname;

    public ChangeAnimation(String keyName) {
        this.keyname = keyName;
    }

    @Override
    public boolean act(float delta) {
        //this.actor.changeAnimation(keyname);
        if(getActor() instanceof AnimationActor) {
            ((AnimationActor)getActor()).changeAnimation(keyname);
            return true;
        }
        return false;

    }
}
