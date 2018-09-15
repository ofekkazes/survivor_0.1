package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Window without title bar
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class WindowEx extends Window {

    public WindowEx(Skin skin) {
        super("title", skin);

        this.getTitleTable().clearChildren();
        this.removeActor(this.getTitleTable());


    }
}
