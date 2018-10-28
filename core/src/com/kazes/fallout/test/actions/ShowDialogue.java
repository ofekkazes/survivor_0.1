package com.kazes.fallout.test.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.kazes.fallout.test.dialogues.DialogueManager;

/**
 * Action to add a node to the dialogue manager
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-10-28
 */
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
