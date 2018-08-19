package com.kazes.fallout.test;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;


public interface Carryable {
    String getDescription();

    <T> boolean  useItem(T usedOn, Array<Float> objects);
}
