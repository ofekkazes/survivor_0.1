package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.Notification;
import com.kazes.fallout.test.screens.GameScreen;
/**
 * Action to add notification to the screen
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-10-28
 */
public class AddNotification extends Action {
    String message;

    public  AddNotification(String message) {
        this.message = message;
    }

    @Override
    public boolean act(float delta) {
        GameScreen.notifications.add(new Notification(message));
        return true;
    }
}
