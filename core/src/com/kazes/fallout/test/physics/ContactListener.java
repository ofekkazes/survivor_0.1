package com.kazes.fallout.test.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.kazes.fallout.test.Bullet;
import com.kazes.fallout.test.ImageEx;
import com.kazes.fallout.test.Player;
import com.kazes.fallout.test.enemies.Enemy;
import com.kazes.fallout.test.screens.GameScreen;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {


    @Override
    public void beginContact(Contact contact) {
        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        Body friendlyBody = null;
        Body bulletBody = null;
        Body enemyBody = null;

        short f1Category = f1.getFilterData().categoryBits;
        short f2Category = f2.getFilterData().categoryBits;


        if(f1Category == CollisionCategory.BULLET.cat() && f2Category == CollisionCategory.ENEMY.cat()) {
            bulletBody = f1.getBody();
            enemyBody = f2.getBody();

        }
        else if((f2Category == CollisionCategory.BULLET.cat() && f1Category == CollisionCategory.ENEMY.cat())) {
            bulletBody = f2.getBody();
            enemyBody = f1.getBody();
        }
        if(bulletBody != null && enemyBody != null) {
            Enemy enemy = (Enemy)enemyBody.getUserData();
            Bullet bullet = (Bullet)bulletBody.getUserData();
            enemy.wander = true;
            bullet.setRemove();
            if(bulletBody.getAngle() == 0) {
                Vector2 dst = bulletBody.getPosition().cpy().sub(GameScreen.player.getBody().getPosition().cpy());
                enemyBody.setLinearVelocity(dst);
            }
            else enemyBody.setLinearVelocity(enemyBody.getPosition().cpy().scl(bullet.getPower() / 20).sub(bulletBody.getPosition().cpy()));
            enemyBody.setLinearDamping(bullet.getPower() / 4);
            enemy.subHealth(bullet.getPower());
            GameScreen.bitcoin += 0.0003f;
        }
        if(f1Category == CollisionCategory.FRIENDLY.cat() && f2Category == CollisionCategory.ENEMY.cat()) {
            friendlyBody = f1.getBody();
            enemyBody = f2.getBody();

        }
        else if((f2Category == CollisionCategory.FRIENDLY.cat() && f1Category == CollisionCategory.ENEMY.cat())) {
            friendlyBody = f2.getBody();
            enemyBody = f1.getBody();
        }
        if(friendlyBody != null && enemyBody != null) {
            ImageEx friendly = (ImageEx)friendlyBody.getUserData();
            if(friendly instanceof Player) {
                ((Player)friendly).subHealth(.2f);
                ((Player)friendly).hit = true;
            }
            friendlyBody.setLinearVelocity(friendlyBody.getPosition().cpy().sub(enemyBody.getPosition().cpy()).scl(16f));
            friendlyBody.setLinearDamping(5f);

        }

        /*else if(f1Category == CollisionCategory.ENEMY.cat()) {

        }
        else if(f1Category == CollisionCategory.FRIENDLY.cat()) {

        }
        else
            return;

        if(f2Category == CollisionCategory.BULLET.cat()) {

        }
        else if(f2Category == CollisionCategory.ENEMY.cat()) {

        }
        else if(f2Category == CollisionCategory.FRIENDLY.cat()) {

        }
        else
            return;
*/
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
