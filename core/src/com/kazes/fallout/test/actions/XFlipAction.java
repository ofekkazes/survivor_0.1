package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.ImageEx;

public class XFlipAction extends Action {

    public XFlipAction() {

    }

    @Override
    public boolean act(float delta) {
        if(getActor() instanceof ImageEx) {
            ((ImageEx)getActor()).setXflip(!((ImageEx)getActor()).getXflip());
            return true;
        }
        return false;
    }
}
