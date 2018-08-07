package com.kazes.fallout.test;

import com.badlogic.gdx.scenes.scene2d.Actor;

public interface Carryable {
    String getDescription();

    boolean useItem(Player usedOn);
}
