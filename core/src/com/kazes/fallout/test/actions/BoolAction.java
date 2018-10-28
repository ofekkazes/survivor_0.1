package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;

/**
 * Boolean action for Scene2d screens management
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
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
