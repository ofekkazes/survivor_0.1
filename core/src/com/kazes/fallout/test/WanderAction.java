package com.kazes.fallout.test;

import com.badlogic.gdx.scenes.scene2d.Action;

/**
 * Enemy wandering enable
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 * @see Enemy
 */
public class WanderAction extends Action {
    @Override
    public boolean act(float delta) {
        ((Zombie)actor).wander = false;
        return true;
    }

}
