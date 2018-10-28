package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.AnimationActor;

/**
 * Action to change the animation key of a character
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-10-28
 */
public class ChangeAnimation extends Action {
    String keyname;

    public ChangeAnimation(String keyName) {
        this.keyname = keyName;
    }

    @Override
    public boolean act(float delta) {
        if(getActor() instanceof AnimationActor) {
            ((AnimationActor)getActor()).changeAnimation(keyname);
            return true;
        }
        return false;

    }
}
