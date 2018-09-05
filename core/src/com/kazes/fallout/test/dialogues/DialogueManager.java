package com.kazes.fallout.test.dialogues;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;
import com.kazes.fallout.test.Assets;
import com.kyper.yarn.Dialogue;
import com.kyper.yarn.UserData;

//Future updates: Dialogue window with Images of the person speaking
public class DialogueManager {
    private int[] OP_KEYS = { Input.Keys.NUM_1, Input.Keys.NUM_2, Input.Keys.NUM_3, Input.Keys.NUM_4, Input.Keys.NUM_5 };

    Window dialogWindow;

    public Dialogue dialogue;
    public Dialogue.LineResult current_line = null;
    private Dialogue.OptionResult current_options = null;
    private Dialogue.CommandResult current_command = null;
    private Dialogue.NodeCompleteResult node_complete = null;
    private StringBuilder option_string;
    private boolean complete;

    private String line;

    private Array<Var> variables;

    public DialogueManager() {
        option_string = new StringBuilder(400);
        this.dialogue = new Dialogue(new UserData("data"));

        line = "";
        this.complete = true;
        this.variables = new Array<Var>();

        dialogWindow = new Window("Dialog", Assets.getAsset(Assets.UI_SKIN, Skin.class));
        dialogWindow.setSize(Gdx.graphics.getWidth(), 200);
        dialogWindow.setVisible(false);
        dialogWindow.add(new Label("",  Assets.getAsset(Assets.UI_SKIN, Skin.class)));
    }

    public void update() {
        // if we currently dont have any command available check if next result is a
        // command
        if (current_command == null && dialogue.isNextCommand()) {
            // assign it
            current_command = dialogue.getNextAsCommand();
        }
        // if we dont have a line - check if next result is a line
        else if (current_line == null && dialogue.isNextLine()) {
            // if there is a command result - execute it before the next line
            executeCommand();
            // assign the line
            current_line = dialogue.getNextAsLine();
            if(variables.size > 0)
                checkVars();
        }
        // if we dont have any options check if the next result is options
        else if (current_options == null && dialogue.isNextOptions()) {
            // assign the options
            current_options = dialogue.getNextAsOptions();
        }
        // if the node has not found a complete result - check if next result is a node
        // complete result
        else if (node_complete == null && dialogue.isNextComplete()) {
            // assign node complete result
            node_complete = dialogue.getNextAsComplete();
        } else {
            // waiting to proccess line or no results available

            // check if the current line is proccessed(null) and that we have a node
            // complete result
            if (current_line == null && node_complete != null) {
                // execute any lingering commands
                executeCommand();
                // set complete to true
                complete = true;

                // lets clean up the results
                resetAllResults();

                // stop the dialogue
                dialogue.stop();
                //dialogWindow.addAction(parallel(scaleBy(Gdx.graphics.getWidth(), 200, 1f, Interpolation.sine), moveTo(0,0, 1f)));
                dialogWindow.setVisible(false);
            }
        }
    }

    public void input() {
        // space goes to next line unless there is options
        if (current_line != null && current_options == null) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                current_line = null;
            }
        }

        // there is options so check all corresponding keys(1-5)
        if (current_options != null) {
            // check to see what is less - the amount of options or the size of keys we are
            // using to accept options
            int check_limit = Math.min(current_options.getOptions().size, OP_KEYS.length); // we do this to avoid array
            // index exceptions
            for (int i = 0; i < check_limit; i++) {
                // loop to see if any of the corresponding keys to options is pressed
                if (Gdx.input.isKeyJustPressed(OP_KEYS[i])) {
                    // if yes then choose
                    current_options.choose(i);

                    // then clear options and current line - break out of for loop
                    current_options = null;
                    current_line = null;
                    break;
                }
            }
        }
    }

    public void render() {
        if (!complete) {
            if(!dialogWindow.isVisible())
                dialogWindow.setVisible(true);

            // draw dialogue
            if (current_line != null) {
                line = current_line.getText();
            }

            // draw options
            if (current_options != null) {
                int check_limit = Math.min(current_options.getOptions().size, OP_KEYS.length); // we do this to avoid
                // array
                // index exceptions
                for (int i = 0; i < check_limit; i++) {
                    String option = current_options.getOptions().get(i);
                    option_string.setLength(0);
                    option_string.append('[').append(i + 1).append(']').append(':').append(' ').append(option);
                    if(!line.isEmpty())
                        line += "\n" + option_string.toString();
                    else
                        line = option_string.toString();
                }
            }
            //Gdx.app.log("Dialogue", line);

            ((Label)dialogWindow.getCells().get(0).getActor()).setText(line);
        }
    }

    public void start(String node) {
        this.dialogue.start(node);
        this.complete = false;
        this.resetAllResults();
        dialogWindow.setVisible(true);
    }

    private void executeCommand() {
        if (current_command != null) {
            String params[] = current_command.getCommand().split("\\s+"); // commands are space delimited-- any space
            for (int i = 0; i < params.length; i++) {
                params[i] = params[i].trim(); // just trim to make sure
            }
            current_command = null;
        }
    }

    public void addVar(Var var) {
        variables.add(var);
    }

    public boolean updateVar(String varName, int value) {
        for(int i = 0; i < variables.size; i++) {
            if(variables.get(i).varName.compareTo(varName) == 0) {
                variables.get(i).value = value;
                return true;
            }
        }
        return false;
    }

    private void checkVars() {
        String newLine = current_line.getText();
        for(Var var : variables) {
            if (newLine.contains(var.varName)) {
                newLine = newLine.replace(var.varName, var.value + "");

            }
        }
        current_line = new Dialogue.LineResult(newLine);
    }

    private void resetAllResults() {
        current_line = null;
        current_options = null;
        node_complete = null;
        current_command = null;
    }

    public Window getWindow() {
        return this.dialogWindow;
    }

    public boolean isCompleted() {
        return this.complete;
    }
}

