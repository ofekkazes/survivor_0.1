package com.kazes.fallout.test;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;

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
