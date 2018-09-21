package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.screens.GameScreen;

public class ChangeInputPrivilege extends Action {

    boolean changeTo;
    GameScreen screen;

    public ChangeInputPrivilege(GameScreen screen, boolean input) {
        this.changeTo = input;
        this.screen = screen;
    }

    @Override
    public boolean act(float delta) {
        screen.setAllowInput(changeTo);
        return true;
    }
}
