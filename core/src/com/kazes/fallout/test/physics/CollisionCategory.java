package com.kazes.fallout.test.physics;

/**
 * Box2d colliders definition
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public enum CollisionCategory {
    BOUNDARY ((short)0x0001),
    BULLET ((short)0x0002),
    ENEMY ((short)0x0004),
    FRIENDLY ((short)0x0008),

    BOUNDARY_COLLIDER ((short)(ENEMY.cat | FRIENDLY.cat)),
    ENEMY_COLLIDER ((short)(BOUNDARY.cat | FRIENDLY.cat | BULLET.cat)),
    FRIENDLY_COLLIDER((short)(BOUNDARY.cat | ENEMY.cat)),
    BULLET_COLLIDER ((ENEMY.cat));

    private final short cat;
    CollisionCategory(short category) {
        this.cat = category;
    }

    public short cat() { return cat; }
}
