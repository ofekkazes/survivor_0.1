package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.dialogues.DialogueManager;

public class ShowDialogue extends Action {
    DialogueManager dialogueManager;
    String node;
    boolean jobDone = false;

    public ShowDialogue(DialogueManager manager, String node) {
        dialogueManager = manager;
        this.node = node;
    }

    @Override
    public boolean act(float delta) {
        if(!jobDone) {
            dialogueManager.start(node);
            jobDone = true;
        }
        return true;
    }
}
