package com.kazes.fallout.test;

import com.badlogic.gdx.scenes.scene2d.Action;

public class BoolAction extends Action {
    public Boolean[] actionTarget;
    public BoolAction(Boolean[] target) {
        actionTarget = target;
    }
    @Override
    public boolean act(float delta) {
        actionTarget[0] = true;
        return true;
    }
}
