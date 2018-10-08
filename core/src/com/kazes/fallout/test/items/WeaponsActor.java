package com.kazes.fallout.test.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.kazes.fallout.test.Assets;

public class WeaponsActor extends Actor {
    static TextureAtlas weaponAtlas = Assets.getAsset(Assets.Atlases.items_icons, TextureAtlas.class);
    private TextureAtlas.AtlasRegion weaponTexture;
    private Weapons weapon;

    public WeaponsActor() {
        setCurrentWeapon(Weapons.Pistol);
    }

    public void setCurrentWeapon(Weapons weapon) {
        this.weapon = weapon;
        weaponTexture = weaponAtlas.findRegion(weapon.getTextureRegion());
        weaponTexture.packedWidth = 40;
        weaponTexture.packedHeight = 40;
    }

    public Weapons getCurrentWeapon() {
        return weapon;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(weaponTexture, getX() - weaponTexture.packedWidth / 2, getY(), weaponTexture.packedWidth, weaponTexture.packedHeight);
    }
}
