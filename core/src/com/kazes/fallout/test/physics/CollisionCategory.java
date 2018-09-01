package com.kazes.fallout.test.physics;

public enum CollisionCategory {
    BOUNDARY ((short)0x0001),
    DECORATION ((short)0x0002),
    ENEMY ((short)0x0004),
    FRIENDLY ((short)0x0008),
    BULLET ((short)0x0016),

    BOUNDARY_COLLIDER ((short)(ENEMY.cat | FRIENDLY.cat)),
    DECORATION_COLLIDER ((short)(ENEMY.cat | FRIENDLY.cat | BULLET.cat)),
    ENEMY_COLLIDER ((short)(BOUNDARY.cat | DECORATION.cat | FRIENDLY.cat)),
    FRIENDLY_COLLIDER((short)(BOUNDARY.cat | DECORATION.cat | ENEMY.cat)),
    BULLET_COLLIDER ((short)(DECORATION.cat | ENEMY.cat));

    private final short cat;
    CollisionCategory(short category) {
        this.cat = category;
    }

    public short cat() { return cat; }
}
