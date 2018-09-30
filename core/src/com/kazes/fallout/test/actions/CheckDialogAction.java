package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.dialogues.DialogueManager;

public class CheckDialogAction extends Action {
    DialogueManager dialogueManager;

    public CheckDialogAction(DialogueManager manager) {
        dialogueManager = manager;
    }
    @Override
    public boolean act(float delta) {
        return dialogueManager.isCompleted();
    }
}
