package com.kazes.fallout;

import java.util.Dictionary;

public class Soldier extends Survivor{
    public int weaponStrength;
    public Soldier() {
        weaponStrength = 1;
    }
    public void upgradeWeapon() {
        this.weaponStrength *= 2;
    }
}
