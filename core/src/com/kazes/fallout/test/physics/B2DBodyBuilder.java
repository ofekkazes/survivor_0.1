package com.kazes.fallout.test.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class B2DBodyBuilder {

    public static Body createBody(World world, float xPos, float yPos, float width, float height, BodyDef.BodyType type, CollisionCategory categoryBits, CollisionCategory maskBits) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2, new Vector2(width / 2, height / 2), 0f);
        return createBody(world, shape, xPos, yPos, type, categoryBits, maskBits);
    }
    public static Body createBody(World world, Shape shape, float xPos, float yPos, BodyDef.BodyType type, CollisionCategory categoryBits, CollisionCategory maskBits) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(xPos, yPos);
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.filter.maskBits = maskBits.cat();
        fixtureDef.filter.categoryBits = categoryBits.cat();
        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }
}
