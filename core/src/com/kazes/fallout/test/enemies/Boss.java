package com.kazes.fallout.test.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.kazes.fallout.test.Assets;
import com.kazes.fallout.test.Progress;
import com.kazes.fallout.test.screens.GameScreen;

/**
 * An advance type of enemy
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-10-28
 */
public class Boss extends Enemy {
    Progress healthBar;
    boolean flag;
    public Boss(float xPos, float yPos, World world) {
        super(Assets.getAsset(Assets.Atlases.zombie2, TextureAtlas.class), xPos, yPos, world);
        healthBar = new Progress(0, 200f, 1f, false);
        healthBar.setWidth(GameScreen.getFastInventoryActor().getWidth() / 1.5f);
        healthBar.setPosition(GameScreen.getFastInventoryActor().getX(), Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 7);
        GameScreen.getScreenStage().addActor(healthBar);
        addInteractingObject(GameScreen.player);
        health = 200;
        setName("Giorgio");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        healthBar.setValue(this.health);
        if(this.health <= 0)
            GameScreen.getScreenStage().getActors().removeValue(healthBar, false);


    }

    @Override
    protected void doLateUpdateTicker() {
        super.doLateUpdateTicker();
        if(flag)
            runTo();
        else roar();
        flag = !flag;
    }

    private void runTo() {
        if(this.closestInteractingObject != null) {
            float distatnce = this.getX() - closestInteractingObject.getX();

                float xTrans;
                float yTrans;
                if(distatnce > 0) {
                    xTrans = -4;
                } else
                    xTrans = 4;
                if(this.getY() - closestInteractingObject.getY() > 0)
                    yTrans = -4;
                else yTrans = 4f;

                body.setLinearVelocity(xTrans * 1.5f, yTrans * 1.5f);

        }
    }

    private void roar() {}
}
