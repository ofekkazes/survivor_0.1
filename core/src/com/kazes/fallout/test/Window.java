package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * Window testing
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class Window extends Table {
    public Window(float width, float height, float xPos, float yPos, Color color) {
        super();
        setBackground(new SpriteDrawable(new Sprite(createTexture((int)width, (int)height, color, 1f))));
        setX(xPos);
        setY(yPos);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.drawBackground(batch, parentAlpha, getX(), getY());
        super.draw(batch, parentAlpha);
    }

    public static Texture createTexture(int width, int height, Color col,
                                        float alpha) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        Color color = col;
        pixmap.setColor(color.r, color.g, color.b, alpha);
        pixmap.fillRectangle(0, 0, width, height);

        Texture pixmaptexture = new Texture(pixmap);
        return pixmaptexture;
    }
}
