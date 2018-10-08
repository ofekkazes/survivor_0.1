package com.kazes.fallout.test.items;

/**
 * All weapons the game will be using
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public enum Weapons {
    NULL(0, "backpack"),
    Knife(1, "bayonet"),
    Sword(2, "machete"),
    Pistol(3, "pistol"),
    SMG(4, "mp5");

    private final int value;
    private String textureRegion;
    Weapons(int value, String textureRegion) {
        this.value = value;
        this.textureRegion = textureRegion;
    }

    public int getValue() {
        return value;
    }

    public String getTextureRegion() {
        return textureRegion;
    }

}
