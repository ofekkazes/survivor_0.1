package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class SideScrollingCamera extends OrthographicCamera {

    float camSpeedX;
    float camSpeedY;

    boolean updateCamera;

    public SideScrollingCamera() {
        super();

        camSpeedX = 50;
        camSpeedY = 50;
    }

    public void followPos(Rectangle playerPos) {
        updateCamera = false;

        if (playerPos.x - position.x > camSpeedX) {
            position.x = playerPos.x - camSpeedX;
            updateCamera = true;
        } else if (playerPos.x - position.x < -camSpeedX) {
            position.x = playerPos.x + camSpeedX;
            updateCamera = true;
        }

        if (position.x < -0 + viewportWidth / 2) {
            position.x = -0 + (int)(viewportWidth / 2);
            updateCamera = false;
        }

        if (position.x > 4048 - viewportWidth / 2) {
            position.x = 4048 - (int)(viewportWidth / 2);
            updateCamera = false;
        }

        if(updateCamera) update();

        /*if (playerPos.y - position.y > camSpeedY) {
            position.y = playerPos.y - camSpeedY;
            update();
        } else if (playerPos.y - position.y < -camSpeedY) {
            position.y = playerPos.y + camSpeedY;
            update();
        }*/
    }


    public boolean getUpdateCamera() { return this.updateCamera; }
}
