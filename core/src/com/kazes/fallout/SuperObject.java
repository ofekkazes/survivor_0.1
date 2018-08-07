package com.kazes.fallout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/*
Static objects used all over the game
 */
public class SuperObject
{
    public static SpriteBatch windowBatch;
    public static SpriteBatch gameBatch;
    public static ShapeRenderer shapeRenderer;
    public static boolean shapeSelected;

    public static BitmapFont worldFont;
    public static BitmapFont windowFont;
    public static BitmapFont simpleFont;

    public static ArrayList<String> debug;
    public static boolean showDebug;

    public static Vector2 mouseInWorld2D = new Vector2();
    public static Vector2 lastMouse = new Vector2();
    public static Vector2 mouseChange2D = new Vector2();
    public static Vector3 mouseChange3D = new Vector3();
    public static Vector3 mouseInWorld3D = new Vector3();

    public static RandomXS128 randomizer;

    public static float alpha = 1;

    //Initialize static objects (mostly)
    public static void init() {
        windowBatch = new SpriteBatch();
        gameBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        shapeSelected = false;

        getFonts();

        debug = new ArrayList<String>();
        showDebug = true;

        randomizer = new RandomXS128();
    }

    //Load some basic fonts
    private static void getFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        parameter.borderWidth = 0;
        parameter.color = Color.WHITE;
        parameter.flip = true;
        parameter.shadowColor = new Color(0, 0.5f, 0, 0.75f);
        SuperObject.worldFont = generator.generateFont(parameter);
        parameter.flip = false;
        SuperObject.windowFont = generator.generateFont(parameter);
        generator.dispose();

        SuperObject.simpleFont = new BitmapFont();
    }

    //Configure required objects
    public static void updateBegin(Camera camera) {
        debug.clear();

        //Mouse calculations in game world
        mouseInWorld3D.x = Gdx.input.getX();
        mouseInWorld3D.y = Gdx.input.getY();
        mouseInWorld3D.z = 0;
        camera.unproject(mouseInWorld3D);
        mouseInWorld2D.x = mouseInWorld3D.x;
        mouseInWorld2D.y = mouseInWorld3D.y;
        mouseChange2D = (lastMouse.sub(Gdx.input.getX(), Gdx.input.getY()));
        mouseChange3D.x = mouseChange2D.x;
        mouseChange3D.y = -mouseChange2D.y;
        mouseChange3D.z = 0;

        //TEMP;
        //Used to implement button usage
        if(shapeSelected) { shapeRenderer.setColor(0.078f, 0.192f, 0.235f, alpha); windowFont.setColor(Color.LIGHT_GRAY); }
        else { shapeRenderer.setColor(0.122f, 0.275f, 0.333f, alpha); windowFont.setColor(Color.WHITE); }
    }

    //Reset for a fresh start
    public static void updateEnd() {
        if(!showDebug) debug.clear();

        lastMouse.x = Gdx.input.getX();
        lastMouse.y = Gdx.input.getY();
    }

    //Draw a rounded rectangle to screen coordinates
    public static void roundedRect(float x, float y, float width, float height, float radius, ShapeRenderer shape){
        // Central rectangle
        shape.rect(x + radius, y + radius, width - 2*radius, height - 2*radius);

        // Four side rectangles, in clockwise order
        shape.rect(x + radius, y, width - 2*radius, radius);
        shape.rect(x + width - radius, y + radius, radius, height - 2*radius);
        shape.rect(x + radius, y + height - radius, width - 2*radius, radius);
        shape.rect(x, y + radius, radius, height - 2*radius);

        // Four arches, clockwise too
        shape.arc(x + radius, y + radius, radius, 180f, 90f);
        shape.arc(x + width - radius, y + radius, radius, 270f, 90f);
        shape.arc(x + width - radius, y + height - radius, radius, 0f, 90f);
        shape.arc(x + radius, y + height - radius, radius, 90f, 90f);
    }

    //Empty the memory of all objects
    public static void dispose() {
        shapeRenderer.dispose();
        windowBatch.dispose();
        gameBatch.dispose();
        worldFont.dispose();
        windowFont.dispose();
        simpleFont.dispose();
        debug.clear();
    }
}
