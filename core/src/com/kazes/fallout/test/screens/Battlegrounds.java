package com.kazes.fallout.test.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.*;
import com.kazes.fallout.test.enemies.FastZombie;
import com.kazes.fallout.test.enemies.Zombie;
import com.kazes.fallout.test.items.*;

/**
 * A game screen for 24/7 enemy spawning and items renewal
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class Battlegrounds extends GameScreen {
    Group injuredNPCS;

    Battlegrounds(Survivor game, float startingPosX) {
        super(game, "Battlegrounds", startingPosX);
        lastScreen = Screens.Niar;
        //nextScreen = Screens.SideScroll;
        weaponsAllowed = true;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(player.getHealth() == 0) {
            Gdx.app.log("Survivor", "Game over");
            Gdx.app.exit();
        }
    }

    @Override
    public void processInput() {
        super.processInput();
        if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            for(int i = 0; i < injuredNPCS.getChildren().size; i++) {
                if(player.getRectangle().overlaps(((ImageEx)injuredNPCS.getChildren().get(i)).getRectangle()))
                    ((InjuredNPC)injuredNPCS.getChildren().get(i)).save();
            }
        }

        /*if(Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            Actor bagItem;
            for (int i = 0; i < player.bag.items.getCells().size; i++) {
                bagItem = player.bag.items.getCells().get(i).getActor();
                if(bagItem != null) {
                    if (bagItem instanceof Wood) {
                        player.bag.items.removeActor(player.bag.items.getCells().get(i).getActor());
                        ImageEx bonfire = new Bonfire(player.getOrigin().x, player.getOrigin().y, rayHandler);
                        bonfire.setBounds(player.getX(), player.getY(), Survivor.getInMeters(200), Survivor.getInMeters(300));
                        bonfires.addActor(bonfire);

                        Gdx.app.log("Survivor", "Bonfire set");
                        break;
                    }
                }
            }
        }*/
    }

    @Override
    public void setMap() {
        Array<Texture> parallaxTextures = new Array<Texture>();
        for(int i = 0; i < 6;i++){
            parallaxTextures.add(Assets.getAsset(Assets._Parallax1[i], Texture.class));
            parallaxTextures.get(i).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);

        }
        parallaxBackground = new ParallaxBackground(parallaxTextures, gameStage.getCamera());
        //parallaxBackground.setSize(Survivor.getInMeters(Gdx.graphics.getWidth() / 2),Survivor.getInMeters(Gdx.graphics.getHeight() / 2));

        map = new ImageEx(game.assetManager.get(Assets.Images.MAP, Texture.class), 0, 0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (GameScreen.time < 0.25f) {
            for (int i = 0; i < 1 && this.stateTime % 7 == 0; i++) {
                if (enemies.getChildren().size < 80) {
                    enemies.addActor(new Zombie(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), Survivor.getInMeters(MathUtils.random(1000, 4000)), Survivor.getInMeters(MathUtils.random(500)), world));
                    ((Zombie) enemies.getChildren().items[enemies.getChildren().size - 1]).addInteractingObject(player);
                    break;
                }
            }
            if (this.stateTime % 30 == 0) {
                addItem();
            }
        } else {
            if (stateTime % 60 == 0)
                addItem();
            if (enemies.getChildren().size < 30) {
                enemies.addActor(new Zombie(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), Survivor.getInMeters(MathUtils.random(1000, 4000)), Survivor.getInMeters(MathUtils.random(500)), world));
                ((Zombie) enemies.getChildren().items[enemies.getChildren().size - 1]).addInteractingObject(player);
                enemies.addActor(new FastZombie(Assets.getAsset(Assets.Images.PIKACHU, Texture.class), Survivor.getInMeters(MathUtils.random(1000, 4000)), Survivor.getInMeters(MathUtils.random(500)), world));
                ((FastZombie) enemies.getChildren().items[enemies.getChildren().size - 1]).addInteractingObject(player);

            }
        }

    }

    public void addItem() {
        ItemActor item = new ItemActor(new AmmoCrate(), 1, 1);
        switch (MathUtils.random(1,10)) {
            case 9:
            case 10:
            case 5:
            case 1: item = new ItemActor(new SmallMedkit(), Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))); break;
            case 6:
            case 7:
            case 8:
            case 2: item = new ItemActor(new AmmoCrate(), Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))); break;
            case 3: item = new ItemActor(new TunaCan(), Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))); break;
            case 4: item = new ItemActor(new WaterBottle(), Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))); break;
            //case 5: item = new Wood(Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))); break;
        }
        items.addActor((Actor)item);
    }

    @Override
    public void setDecor() {

    }

    @Override
    public void setNPCS() {
        injuredNPCS = new Group();
        npcs.addActor(injuredNPCS);
        for(int i = 0; i < MathUtils.random(7); i++)
            injuredNPCS.addActor(new InjuredNPC(world, MathUtils.random(1,Survivor.getInMeters(map.getWidth())), MathUtils.random(1,9),  Weapons.Pistol));

    }

    @Override
    public void setEnemies() {
        Texture texture = game.assetManager.get(Assets.Images.PIKACHU, Texture.class);
        for(int i = 0; i < 60; i++) {
            enemies.addActor(new Zombie(texture, Survivor.getInMeters(MathUtils.random(1000, 4000)), Survivor.getInMeters(MathUtils.random(500)), world));
            enemies.getChildren().items[i].setName("Zombie " + i);
        }
    }

    @Override
    public void setItems() {
        for(int i = 0; i < 7; i++) {
            items.addActor(new ItemActor(new SmallMedkit(), Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))));
            items.getChildren().get(items.getChildren().size - 1).setName("Medicine number "+i);
        }
        /*for(int i = 0; i < 9; i++) {
            items.addActor(new Wood(Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))));
            items.getChildren().get(items.getChildren().size - 1).setName("Wood number "+i);
        }*/
        for(int i = 0; i < 3; i++) {
            items.addActor(new ItemActor(new TunaCan(), Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))));
            items.getChildren().get(items.getChildren().size - 1).setName("Tuna number "+i);
        }
        for(int i = 0; i < 3; i++) {
            items.addActor(new ItemActor(new WaterBottle(), Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))));
            items.getChildren().get(items.getChildren().size - 1).setName("Water number "+i);
        }
        /*for(int i = 0; i < 5; i++) {
            items.addActor(new BearTrap(Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))));
            items.getChildren().get(items.getChildren().size - 1).setName("Bear Trap number "+i);
        }*/
        for(int i = 0; i < 5; i++) {
            items.addActor(new ItemActor(new AmmoCrate(), Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))));
            //items.getChildren().get(items.getChildren().size - 1).setName("Bear Trap number "+i);
        }
    }
}
