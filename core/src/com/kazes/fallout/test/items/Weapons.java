package com.kazes.fallout.test.items;

/**
 * All weapons the game will be using
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public enum Weapons {
    NULL(0, "backpack", true),
    Knife(1, "bayonet", true),
    Sword(2, "machete", true),
    Pistol(3, "pistol", true),
    SMG(4, "mp5", true),
    AKM(5, "akm", false),
    AXE(6, "axe", false),
    BAT(7, "baseball_bat", false),
    M4(8, "m4", false),
    SHOTGUN(9, "shotgun", false);


    private final int value;
    private String textureRegion;
    private boolean avalible;
    Weapons(int value, String textureRegion, boolean unlocked) {
        this.value = value;
        this.textureRegion = textureRegion;
        this.avalible = unlocked;
    }

    public int getValue() {
        return value;
    }

    public String getTextureRegion() {
        return textureRegion;
    }

    public boolean isUnlocked() {
        return avalible;
    }

    public void setUnlocked(boolean avalible) {
        this.avalible = avalible;
    }

}
