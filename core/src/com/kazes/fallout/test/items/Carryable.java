package com.kazes.fallout.test.items;

import com.badlogic.gdx.utils.Array;


public interface Carryable {
    String getDescription();

    <T> boolean  useItem(T usedOn, Array<Float> objects);
}
