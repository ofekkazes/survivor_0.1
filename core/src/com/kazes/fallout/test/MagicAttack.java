package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.physics.CollisionCategory;

public class MagicAttack extends Actor {
    private ParticleEffect effect;


    public MagicAttack(ParticleEffect effect, float xPos, float yPos) {
        super();
        setPosition(xPos, yPos);
        this.effect = new ParticleEffect(effect);
        this.effect.scaleEffect(0.02f);
        this.effect.reset(false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        effect.setPosition(getX(), getY());
        effect.update(delta);

    }

    public void setPos(float x, float y) {
        effect.setPosition(getX(), getY());
    }
    public void start() {
        effect.start();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        effect.draw(batch);
        if (effect.isComplete())
            effect.reset(false);
    }
}
