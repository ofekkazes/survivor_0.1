package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class AnimationActor extends ImageEx {

    ObjectMap<String, Animation<TextureRegion>> animationsByNames;
    private String currentKey;
    private float stateTime = 0;

    public AnimationActor(ObjectMap<String, Animation<TextureRegion>> animations, String name, float xPos, float yPos) {
        super((TextureRegion)animations.get(animations.keys().toArray().get(0)).getKeyFrame(0), xPos, yPos);
        this.setName(name);
        this.setPosition(xPos, yPos);

        this.animationsByNames = new ObjectMap<String, Animation<TextureRegion>>();
        this.currentKey = Assets.Animations.HERO + "_idle";

        this.animationsByNames = animations;

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
        if(this.currentKey == keyName)
            return;

        this.setDrawable(new TextureRegionDrawable(this.animationsByNames.get(keyName).getKeyFrame(0)));
        this.currentKey = keyName;
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
        batch.draw(tmp, xFlipped ? getX() + getWidth() : getX(), (this.currentKey.contains("walking")) ? getY() + 3 : getY(), xFlipped ? -getWidth() : getWidth(), getHeight());
    }

    @Override
    public void flip(boolean xFlip, boolean yFlip) {
        this.xFlipped = (xFlip) ? !xFlipped : xFlipped;
        this.yFlipped = yFlip;
    }

    private static TextureRegion getFirstFrame(Texture spritesheet) {
        TextureRegion[][] tmp = TextureRegion.split(spritesheet,
                spritesheet.getWidth() / 8,
                spritesheet.getHeight() / 5);
        return tmp[0][0];
    }

}
