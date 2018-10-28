package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.ImageEx;

/**
 * Action to flip an image over the x axis
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-10-28
 */
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
