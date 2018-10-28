package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.screens.GameScreen;

/**
 * Action to change weapons usage
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-10-28
 */
public class ChangeWeaponPrivilege extends Action {
    boolean changeTo;
    GameScreen screen;

    public ChangeWeaponPrivilege(GameScreen screen, boolean input) {
        this.changeTo = input;
        this.screen = screen;
    }

    @Override
    public boolean act(float delta) {
        screen.setAllowWeapons(changeTo);
        return true;
    }
}
