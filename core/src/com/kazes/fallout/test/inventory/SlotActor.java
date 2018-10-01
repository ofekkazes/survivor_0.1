/* Copyright (c) 2014 PixelScientists
 * 
 * The MIT License (MIT)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.kazes.fallout.test.inventory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kazes.fallout.test.Assets;

/**
 * @author Daniel Holderbaum
 */
public class SlotActor extends ImageButton implements SlotListener {

	private Slot slot;
	private Label amount;
	BitmapFont font;

	private Skin skin;

	public SlotActor(Skin skin, Slot slot, Stage parent, boolean tooltipEnabled) {
		super(createStyle(skin, slot));
		this.slot = slot;
		this.skin = skin;
		Label.LabelStyle style = new Label.LabelStyle(Assets.getFont(Assets.Fonts.DEFAULT, Assets.FontSizes.TWO_HUNDRED), Color.WHITE);
		amount = new Label(getSlot().getAmount() + "", style);
		font = Assets.getFont(Assets.Fonts.DEFAULT, Assets.FontSizes.TWO_HUNDRED);
		slot.addListener(this);
		if(tooltipEnabled) {
			SlotTooltip tooltip = new SlotTooltip(slot, skin);
			tooltip.setTouchable(Touchable.disabled); // allows for mouse to hit tooltips in the top-right corner of the screen without flashing
			parent.addActor(tooltip);
			addListener(new TooltipListener(tooltip, true));
		}

		Image image = getImage();
		this.removeActor(getImage());
		Stack stack = new Stack();
		stack.add(image);
		stack.add(amount);
		this.add(stack);
	}


	private static ImageButtonStyle createStyle(Skin skin, Slot slot) {
		TextureAtlas icons = Assets.getAsset(Assets.Atlases.items_new, TextureAtlas.class);
		TextureRegion image;
		if (slot.getItem() != null) {
			image = icons.findRegion(slot.getItem().getTextureRegion());
			//image = icons.findRegion("crate");
		} else {
			image = icons.findRegion("backpack");
		}
		ImageButtonStyle style = new ImageButtonStyle(skin.get(ButtonStyle.class));
		style.imageUp = new TextureRegionDrawable(image);
		style.imageDown = new TextureRegionDrawable(image);

		return style;
	}

	public Slot getSlot() {
		return slot;
	}

	@Override
	public void hasChanged(Slot slot) {
		setStyle(createStyle(skin, slot));
		this.amount.setText(getSlot().getAmount() + "");
	}

}
