package com.kazes.fallout.test.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.AnimationActor;

public class ChangeAnimation extends Action {
    AnimationActor actor;
    String keyname;

    public ChangeAnimation(AnimationActor animationActor, String keyName) {
        this.actor = animationActor;
        this.keyname = keyName;
    }

    @Override
    public boolean act(float delta) {
        this.actor.changeAnimation(keyname);

        return true;
    }
}
