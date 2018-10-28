package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Progress bar own implementation
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class Progress extends ProgressBar {
    public Progress(float min, float max, float stepSize, boolean vertical) {
        super(min, max, stepSize, vertical, Assets.getAsset(Assets.UI_SKIN, Skin.class));
    }

    public float reduceValue(float amount) {
        this.setValue(this.getValue() - amount);
        if(getValue() < getMinValue())
            setValue(getMinValue());
        return this.getValue();
    }

    public void addValue(float amount) {
        this.setValue(this.getValue() + amount);
        if(getValue() > getMaxValue())
            setValue(getMaxValue());
    }

    private static ProgressBarStyle getStyleBar() {
        Skin skin = Assets.getAsset(Assets.UI_SKIN, Skin.class);
        Pixmap pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();

        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        ProgressBar.ProgressBarStyle barStyle = new ProgressBar.ProgressBarStyle(skin.newDrawable("white", Color.DARK_GRAY), textureRegionDrawable);
        barStyle.knobBefore = barStyle.knob;

        return barStyle;
    }
}
