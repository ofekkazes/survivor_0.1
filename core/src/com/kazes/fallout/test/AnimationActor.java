package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * A sprite animation class for stage and Scene2d usage
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class AnimationActor extends ImageEx {

    private ObjectMap<String, Animation<TextureRegion>> animationsByNames;
    private String currentKey;
    private float stateTime = 0;
    private boolean xFlip;

    AnimationActor(ObjectMap<String, Animation<TextureRegion>> animations, String name, float xPos, float yPos) {
        super(animations.get(animations.keys().toArray().get(0)).getKeyFrame(0), xPos, yPos);
        this.setName(name);
        this.setPosition(xPos, yPos);

        this.animationsByNames = animations;
        this.currentKey = animations.keys().toArray().get(0);
        this.xFlip = false;

/*
        TextureRegion[][] tmp = TextureRegion.split(spritesheet,
                spritesheet.getWidth() / 8,
                spritesheet.getHeight() / 5);

        TextureRegion[] idleFrames = new TextureRegion[8];
        for (int j = 0; j < 8; j++) {
            idleFrames[j] = tmp[0][j];
        }
        Animation idle = new Animation<TextureRegion>(1/10f, idleFrames);
        idle.setPlayMode(Animation.PlayMode.LOOP);
        this.animationsByNames.put("idle", idle);

        TextureRegion[] walkingFrames= new TextureRegion[6];
        for (int j = 0; j < 6; j++) {
            walkingFrames[j] = tmp[1][j];
        }
        Animation walking = new Animation<TextureRegion>(1/10f, walkingFrames);
        walking.setPlayMode(Animation.PlayMode.LOOP);
        this.animationsByNames.put("walking", walking);*/

    }
    public void changeAnimation(String keyName) {
        if(this.currentKey.equals(keyName))
            return;

        this.setDrawable(new TextureRegionDrawable(this.animationsByNames.get(keyName).getKeyFrame(0)));
        this.currentKey = keyName;
    }

    public String getCurrentKey() {
        return currentKey;
    }

    @Override
    public void act(float delta)
    {
        super.act(delta);
        stateTime += delta;

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion tmp = this.animationsByNames.get(currentKey).getKeyFrame(stateTime);
        batch.draw(tmp, xFlip ? getX() + getWidth() : getX(), (this.currentKey.contains("walking")) ? getY() + Survivor.getInMeters(3) : getY(), xFlip ? -getWidth() : getWidth(), getHeight());
    }

    public void flip() {
        this.xFlip = !isxFlip();
    }

    public void setWalkSpeed(boolean running) {
        for (int i = 0; i < animationsByNames.size; i++) {
            if (animationsByNames.keys().toArray().get(i).contains("walking")) {
                animationsByNames.get(animationsByNames.keys().toArray().get(i)).setFrameDuration(running ? 1 / 15f : 1 / 10f);
            }
        }
    }

    private static TextureRegion getFirstFrame(Texture spritesheet) {
        TextureRegion[][] tmp = TextureRegion.split(spritesheet,
                spritesheet.getWidth() / 8,
                spritesheet.getHeight() / 5);
        return tmp[0][0];
    }

    public boolean isxFlip() {
        return this.xFlip;
    }
}
