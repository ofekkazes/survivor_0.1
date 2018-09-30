package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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

    private boolean updateCamera;

    Matrix4 parallaxView = new Matrix4();
    Matrix4 parallaxCombined = new Matrix4();
    Vector3 tmp = new Vector3();
    Vector3 tmp2 = new Vector3();

    /*public SideScrollingCamera() {
        super();

        updateX = 50 / GameScreen.PPM;
        updateY = 50 / GameScreen.PPM;
    }*/
    public SideScrollingCamera(float viewportWidth, float viewportHeight) {
        super(viewportWidth, viewportHeight);

        updateX = Survivor.getInMeters(50);
        updateY = Survivor.getInMeters(50);
    }

    public void followPos(Vector2 playerPos) {
        updateCamera = false;

        if (playerPos.x - position.x > updateX) {
            //position.x = playerPos.x - updateX;
            Vector3 target = new Vector3(playerPos.x - updateX, viewportHeight / 2, 0);
            position.scl(0.9f);
            target.scl(0.1f);
            position.add(target);
            updateCamera = true;
        } else if (playerPos.x - position.x < -updateX) {
            //position.x = playerPos.x + updateX;
            Vector3 target = new Vector3(playerPos.x + updateX, viewportHeight / 2, 0);
            position.scl(0.9f);
            target.scl(0.1f);
            position.add(target);
            updateCamera = true;
        }


        if (position.x < viewportWidth / 2) {
            position.x = (int)(viewportWidth / 2);
            updateCamera = false;
        }

        if (position.x > Survivor.getInMeters(4048) - viewportWidth / 2) {
            position.x = Survivor.getInMeters(4048) - (int)(viewportWidth / 2);
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

    public Matrix4 calculateParallaxMatrix (float parallaxX, float parallaxY) {
        update();
        tmp.set(position);
        tmp.x *= parallaxX;
        tmp.y *= parallaxY;

        parallaxView.setToLookAt(tmp, tmp2.set(tmp).add(direction), up);
        parallaxCombined.set(projection);
        Matrix4.mul(parallaxCombined.val, parallaxView.val);
        return parallaxCombined;
    }


    public boolean getUpdateCamera() { return this.updateCamera; }
}
