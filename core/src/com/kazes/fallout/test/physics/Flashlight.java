package com.kazes.fallout.test.physics;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kazes.fallout.test.screens.Screens;

public class Flashlight extends ConeLight {
    private float distance;
    private boolean visible;

    /**
     * Creates light shaped as a circle's sector with given radius, direction and arc angle
     *
     * @param rayHandler      not {@code null} instance of RayHandler
     * @param rays            number of rays - more rays make light to look more realistic
     *                        but will decrease performance, can't be less than MIN_RAYS
     * @param color           color, set to {@code null} to use the default color
     * @param distance        distance of cone light
     * @param x               axis position
     * @param y               axis position
     * @param directionDegree direction of cone light
     * @param coneDegree
     */
    public Flashlight(RayHandler rayHandler, int rays, Color color, float distance, float x, float y, float directionDegree, float coneDegree) {
        super(rayHandler, rays, color, distance, x, y, directionDegree, coneDegree);
        this.distance = distance;
        this.visible = true;
    }

    public void setVisible(boolean visible) {
        if(!visible) {
            super.setActive(false);
            distance = getDistance();
            super.setDistance(0);
        }
        else {
            super.setActive(true);
            super.setDistance(distance);
        }
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setDistance(float distance) {
        super.setDistance(distance);
        this.distance = distance;
    }

    public void update(Vector2 position) {
        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        mousePos = Screens.getCurrent().getGameStage().screenToStageCoordinates(mousePos);

        double degrees = Math.atan2(
                mousePos.y - position.y,
                mousePos.x - position.x
        ) * 180.0d / Math.PI;
        this.setDirection((float)degrees);
        this.setPosition(position);
    }
}
