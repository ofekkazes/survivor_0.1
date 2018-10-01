package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.dialogues.DialogueManager;

public class ShowDialogue extends Action {
    DialogueManager dialogueManager;
    String node;

    public ShowDialogue(DialogueManager manager, String node) {
        dialogueManager = manager;
        this.node = node;
    }

    @Override
    public boolean act(float delta) {
        if(dialogueManager.isCompleted()) {
            dialogueManager.start(node);
            return true;
        }
        return false;
    }
}
