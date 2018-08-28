package com.kazes.fallout.test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

class B2DBodyBuilder {

    static Body createBody(World world, float xPos, float yPos, float width, float height) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2, new Vector2(width / 2, height / 2), 0f);
        return createBody(world, shape, xPos, yPos, width, height);
    }
    static Body createBody(World world, Shape shape, float xPos, float yPos, float width, float height) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((xPos + width / 2) , (yPos + height / 2));
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }
}
