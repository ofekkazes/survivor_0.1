package com.kazes.fallout.test.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.kazes.fallout.test.Assets;

public class FastZombie extends Enemy {
    public FastZombie(float xPos, float yPos, World world) {
        super(Assets.getAsset(Assets.Atlases.zombie3, TextureAtlas.class), xPos, yPos, world);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(this.closestInteractingObject != null) {
            float distatnce = this.getX() - closestInteractingObject.getX();
            if(distatnce < 14 && distatnce > -14) {
                float xTrans;
                float yTrans;
                if(distatnce > 0) {
                    xTrans = -1;
                } else
                    xTrans = 1;
                if(this.getY() - closestInteractingObject.getY() > 0)
                    yTrans = -1;
                else yTrans = 1f;

                body.setLinearVelocity(xTrans * 1.5f, yTrans * 1.5f);
            }
        }
    }
}
