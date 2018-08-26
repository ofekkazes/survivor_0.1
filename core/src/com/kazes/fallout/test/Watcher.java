package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class Watcher extends NPC {

    private boolean ascending;
    private float startX, startY, endX, endY;
    private float time;
    private Watcher follow;
    public Watcher(float startX, float startY, float endX, float endY, float time) {
        super(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), "Watcher", startX, startY, Weapons.Pistol);
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.time = time;
        this.ascending = true;
    }
    public Watcher(Watcher follow) {
        super(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), "Watcher", follow.getX(), follow.getY(), Weapons.Pistol);
        this.follow = follow;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(follow == null) {
            if (!this.hasActions()) {
                if (ascending) {
                    this.addAction(sequence(delay(1.5f), Actions.moveTo(endX, endY, time, Interpolation.smooth)));
                    ascending = false;
                } else {
                    this.addAction(sequence(delay(1.5f), Actions.moveTo(startX, startY, time, Interpolation.smooth)));
                    ascending = true;
                }
            }
        }
        else {
            if(!this.hasActions()) {
                this.addAction(Actions.moveTo(follow.getX() + ((follow.ascending) ? 50 : -50), follow.getY(), 1f));
                this.ascending = follow.ascending;
            }
        }
    }
}
