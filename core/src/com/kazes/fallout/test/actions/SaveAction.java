package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.InjuredNPC;

/**
 * Action to save an NPC
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-10-28
 */
public class SaveAction extends Action {

    @Override
    public boolean act(float delta) {
        if(getActor() instanceof InjuredNPC) {
            ((InjuredNPC)getActor()).save();
            return true;
        }
        return false;
    }
}
