package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.screens.GameScreen;

public class AddNotification extends Action {
    String message;

    public  AddNotification(String message) {
        this.message = message;
    }

    @Override
    public boolean act(float delta) {
        GameScreen.notifications.add(message);
        return true;
    }
}
