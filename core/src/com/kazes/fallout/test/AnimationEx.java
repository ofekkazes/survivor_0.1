package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.kazes.fallout.test.physics.CollisionCategory;

import java.util.Comparator;

public class AnimationEx extends ImageEx {
    private String currentKey;
    private boolean xFlip;
    private float stateTime = 0;
    private ObjectMap<String, Animation<TextureRegion>> animationsByNames;

    public AnimationEx(TextureAtlas texture, float xPos, float yPos) {
        super(texture.findRegion("Zombie_1_Walking0001"), xPos, yPos);

        animationsByNames = new ObjectMap<String, Animation<TextureRegion>>();

        Animation<TextureRegion> walk = new Animation<TextureRegion>(1/12f, getAnimation("Walking", texture), Animation.PlayMode.LOOP);
        Animation<TextureRegion> run = new Animation<TextureRegion>(1/12f,  getAnimation("Running", texture), Animation.PlayMode.LOOP);
        Animation<TextureRegion> crouch = new Animation<TextureRegion>(1/12f, getAnimation("Crouching", texture), Animation.PlayMode.LOOP);
        Animation<TextureRegion> die = new Animation<TextureRegion>(1/12f, getAnimation("Dying", texture), Animation.PlayMode.NORMAL);
        Animation<TextureRegion> jump = new Animation<TextureRegion>(1/12f, getAnimation("Jumping", texture), Animation.PlayMode.NORMAL);
        Animation<TextureRegion> attack = new Animation<TextureRegion>(1/12f, getAnimation("Attacking", texture), Animation.PlayMode.LOOP);

        animationsByNames.put("walk", walk);
        animationsByNames.put("run", run);
        animationsByNames.put("crouch", crouch);
        animationsByNames.put("die", die);
        animationsByNames.put("jump", jump);
        animationsByNames.put("attack", attack);

        currentKey = "walk";
        xFlip = false;

        setWidth(Survivor.getInMeters(96));
        setHeight(Survivor.getInMeters(64));


    }

    public void changeAnimation(String keyName) {
        if(this.currentKey.equals(keyName))
            return;
        this.currentKey = keyName;
        stateTime = 0;
    }

    public String getCurrentKey() {
        return currentKey;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion tmp = this.animationsByNames.get(currentKey).getKeyFrame(stateTime);
        batch.draw(tmp, xFlip ? getX() + Survivor.getInMeters(tmp.getRegionWidth() / 1.5f) : getX(), getY(), xFlip ? -Survivor.getInMeters(tmp.getRegionWidth()/ 1.5f) : Survivor.getInMeters(tmp.getRegionWidth() / 1.5f), Survivor.getInMeters(tmp.getRegionHeight() / 1.5f));
    }

    public void flipAnimation(boolean flipX) {
        this.xFlip = flipX;
    }

    public boolean isxFlip() {
        return this.xFlip;
    }

    public boolean isAnimationFinished() {
        return animationsByNames.get(currentKey).isAnimationFinished(stateTime);
    }

    public void changeSpeed(float speed) {
        this.animationsByNames.get(currentKey).setFrameDuration(speed);
    }

    private static Array<TextureAtlas.AtlasRegion> getAnimation(String name, TextureAtlas textureAtlas) {
        Array<TextureAtlas.AtlasRegion> animations = new Array<TextureAtlas.AtlasRegion>();
        for (TextureAtlas.AtlasRegion region: textureAtlas.getRegions()) {
            if(region.name.contains(name))
                animations.add(region);
        }
        animations.sort(new Comparator<TextureAtlas.AtlasRegion>() {
            @Override
            public int compare(TextureAtlas.AtlasRegion o1, TextureAtlas.AtlasRegion o2) {
                return (Integer.parseInt(o1.name.substring(o1.name.length() - 2), o1.name.length()) - Integer.parseInt(o2.name.substring(o2.name.length() - 2, o2.name.length())));
            }
        });
        return animations;
    }

}
