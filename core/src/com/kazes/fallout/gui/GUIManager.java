package com.kazes.fallout.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.ArrayList;

public class GUIManager
{
    public static ArrayList<Button> buttons;

    public static void init() {
        GUIManager.buttons = new ArrayList<Button>();
    }

    public static void update() {
        for(Button b : GUIManager.buttons)
            b.update();
    }

    public static void render(OrthographicCamera c) {
        for(Button b : GUIManager.buttons)
            b.render(c);
    }

    public static void addButton(Button b) {
        GUIManager.buttons.add(b);
    }

    public static boolean deleteButton(Button b) {
        return GUIManager.buttons.remove(b);
    }

}
