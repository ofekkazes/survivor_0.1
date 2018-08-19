package com.kazes.fallout.test;

public enum Weapons {
    NULL(1),
    Knife(2),
    Sword(3),
    Pistol(4),
    SMG(5);

    private final int value;
    private Weapons(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
