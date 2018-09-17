package com.kazes.fallout.test.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.kazes.fallout.test.Bullet;
import com.kazes.fallout.test.ImageEx;
import com.kazes.fallout.test.Player;
import com.kazes.fallout.test.enemies.Enemy;

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
            //enemyBody.setLinearVelocity(0,0);
            enemyBody.applyForceToCenter(enemyBody.getPosition().cpy().sub(bulletBody.getPosition().cpy()), true);
            enemyBody.setLinearDamping(5f);
            //enemyBody.applyLinearImpulse(enemyBody.getPosition().sub(bulletBody.getPosition()).scl(0.2f), enemyBody.getWorldCenter(), true);
            bullet.setRemove();
            enemy.subHealth(20);
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
            Gdx.app.log("asd", "fds");
            Enemy enemy = (Enemy)enemyBody.getUserData();
            ImageEx friendly = (ImageEx)friendlyBody.getUserData();
            //enemyBody.setLinearVelocity(0,0);
            friendlyBody.applyForceToCenter(friendlyBody.getPosition().cpy().sub(enemyBody.getPosition().cpy()).scl(8f), true);
            //enemyBody.applyLinearImpulse(enemyBody.getPosition().sub(bulletBody.getPosition()).scl(0.2f), enemyBody.getWorldCenter(), true);
            if(friendly instanceof Player) {
                ((Player)friendly).subHealth(.2f);
            }
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
        Body body1 = f1.getBody();
        Body body2 = f2.getBody();
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
