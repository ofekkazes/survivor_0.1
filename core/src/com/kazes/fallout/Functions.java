package com.kazes.fallout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

public class Functions {
    public static BitmapFont worldFont;
    public static BitmapFont windowFont;
    public static void init() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("truetypefont/roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        parameter.borderWidth = 0;
        parameter.color = Color.WHITE;
        parameter.flip = true;
        parameter.shadowColor = new Color(0, 0.5f, 0, 0.75f);
        Functions.worldFont = generator.generateFont(parameter);
        parameter.flip = false;
        Functions.windowFont = generator.generateFont(parameter);
        generator.dispose();
    }
}
