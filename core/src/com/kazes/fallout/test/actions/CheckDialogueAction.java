package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.dialogues.DialogueManager;

/**
 * Action to check whether a dialogue is present currently
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-10-28
 */
public class CheckDialogueAction extends Action {
    DialogueManager dialogueManager;

    public CheckDialogueAction(DialogueManager manager) {
        dialogueManager = manager;
    }
    @Override
    public boolean act(float delta) {
        return dialogueManager.isCompleted();
    }
}
