package com.kazes.fallout.test.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.kazes.fallout.test.Assets;

public class WeaponsActor extends Actor {
    static TextureAtlas weaponAtlas = Assets.getAsset(Assets.Atlases.items_icons, TextureAtlas.class);
    private Weapons weapon;

    public WeaponsActor() {
        weapon = Weapons.NULL;
    }

    public void setCurrentWeapon(Weapons weapon) {
        this.weapon = weapon;
    }

    public Weapons getCurrentWeapon() {
        return weapon;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(weaponAtlas.findRegion(weapon.getTextureRegion()), getX(), getY());
    }
}
