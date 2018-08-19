package com.kazes.fallout.test;

import com.badlogic.gdx.scenes.scene2d.Action;

public class WanderAction extends Action {
    @Override
    public boolean act(float delta) {
        ((Zombie)actor).wander = false;
        return true;
    }

}
