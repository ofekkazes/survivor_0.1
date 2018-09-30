package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.*;
import com.kazes.fallout.test.screens.GameScreen;

/**
 * Camera for following the player
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class SideScrollingCamera extends OrthographicCamera {

    private float updateX;
    private float updateY;

    float leftBoundary;
    float rightBoundary;

    boolean firstRun = true;

    public SideScrollingCamera(float viewportWidth, float viewportHeight, float leftBoundary, float rightBoundary) {
        super(viewportWidth, viewportHeight);

        updateX = Survivor.getInMeters(50);
        updateY = Survivor.getInMeters(50);

        this.leftBoundary = leftBoundary;
        this.rightBoundary = rightBoundary;

        position.y = viewportHeight / 2;
    }

    public void followPos(Vector2 playerPos) {
        if(firstRun) {
            firstRun = false;
            position.y = viewportHeight / 2;
            if (playerPos.x < viewportWidth / 2) {
                position.x = MathUtils.lerp(position.x, viewportWidth / 2, 1f);
            }

            if (playerPos.x > rightBoundary - viewportWidth / 2) {
                position.x = MathUtils.lerp(position.x, rightBoundary - viewportWidth / 2, 1f);
            }
        }

        if(playerPos.x >= leftBoundary + viewportWidth / 2 && playerPos.x <= rightBoundary - viewportWidth / 2)
            position.x = MathUtils.lerp(position.x, playerPos.x, 0.09f);


        update();

        /*if (playerPos.y - position.y > updateY) {
            position.y = playerPos.y - updateY;
            update();
        } else if (playerPos.y - position.y < -updateY) {
            position.y = playerPos.y + updateY;
            update();
        }*/
    }
}
