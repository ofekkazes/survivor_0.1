package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.ImageEx;

public class XFlipAction extends Action {

    ImageEx flipActor;

    public XFlipAction(ImageEx actor) {
        this.flipActor = actor;
    }

    @Override
    public boolean act(float delta) {
        this.flipActor.setXflip(!this.flipActor.getXflip());
        return true;
    }
}
