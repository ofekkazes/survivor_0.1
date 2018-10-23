package com.kazes.fallout.test.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.kazes.fallout.test.Assets;
import com.kazes.fallout.test.SideScrollingCamera;
import com.kazes.fallout.test.Survivor;
import com.kazes.fallout.test.enemies.Enemy;
import com.kazes.fallout.test.enemies.Zombie;

public class SingletonRoad extends GameScreen {
    SingletonRoad(Survivor game, float startingPosX) {
        super(game, "Singleton Road", startingPosX);
        lastScreen = Screens.Basmati;
        nextScreen = Screens.Niar;

        weaponsAllowed = true;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ((SideScrollingCamera)gameStage.getCamera()).followPos(player.getOrigin());
    }

    @Override
    public void setMap() {

    }

    @Override
    public void setDecor() {

    }

    @Override
    public void setNPCS() {

    }

    @Override
    public void setEnemies() {
        for(int i = 0; i < 25; i++) {
            enemies.addActor(new Zombie(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), MathUtils.random(10, 100), MathUtils.random(6f), world));
            ((Enemy)enemies.getChildren().get(enemies.getChildren().size - 1)).addInteractingObject(player);
        }
    }

    @Override
    public void setItems() {

    }
}
