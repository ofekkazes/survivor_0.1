package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class SideScrollingCamera extends OrthographicCamera {

    float updateX;
    float updateY;

    boolean updateCamera;

    public SideScrollingCamera() {
        super();

        updateX = 50;
        updateY = 50;
    }
    public SideScrollingCamera(float viewportWidth, float viewportHeight) {
        super(viewportWidth, viewportHeight);

        updateX = 50;
        updateY = 50;
    }

    public void followPos(Vector2 playerPos) {
        updateCamera = false;

        if (playerPos.x - position.x > updateX) {
            //position.x = playerPos.x - updateX;
            Vector3 target = new Vector3(playerPos.x - updateX, Gdx.graphics.getHeight() / 2, 0);
            position.scl(0.9f);
            target.scl(0.1f);
            position.add(target);
            updateCamera = true;
        } else if (playerPos.x - position.x < -updateX) {
            //position.x = playerPos.x + updateX;
            Vector3 target = new Vector3(playerPos.x + updateX, Gdx.graphics.getHeight() / 2, 0);
            position.scl(0.9f);
            target.scl(0.1f);
            position.add(target);
            updateCamera = true;
        }


        if (position.x < -0 + viewportWidth / 2) {
            position.x = (int)(viewportWidth / 2);
            updateCamera = false;
        }

        if (position.x > 4048 - viewportWidth / 2) {
            position.x = 4048 - (int)(viewportWidth / 2);
            updateCamera = false;
        }

        if(updateCamera) update();

        /*if (playerPos.y - position.y > updateY) {
            position.y = playerPos.y - updateY;
            update();
        } else if (playerPos.y - position.y < -updateY) {
            position.y = playerPos.y + updateY;
            update();
        }*/
    }


    public boolean getUpdateCamera() { return this.updateCamera; }
}
