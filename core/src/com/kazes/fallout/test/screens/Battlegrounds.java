package com.kazes.fallout.test.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.*;
import com.kazes.fallout.test.items.*;
import javafx.scene.PointLight;

public class Battlegrounds extends GameScreen {
    Group injuredNPCS;
    Battlegrounds(Survivor game, float startingPosX) {
        super(game, "Battlegrounds", startingPosX);
        lastScreen = Screens.Tribe;

        screenStage.addActor(player.bag);

        player.bag.items.addListener(new ClickListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //BAG>DESCRIPTION>ADD(ITEM)
                //COLOR>GLOW
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                //BAG>DESCRIPTION>REMOVE(ITEM)
                //COLOR>DEFAULT
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(player.bag.isVisible()) {
                    Actor temp;
                    for (int i = 0; i < player.bag.items.getCells().size; i++) {
                        temp = player.bag.items.getCells().get(i).getActor();
                        if (temp != null) {
                            if (((ImageEx) temp).getRectangle().contains(x, y)) {
                                //player.bag.items.removeActor(temp);

                                player.bag.changeDescription(temp);
                                return;
                            }
                        }
                    }
                }
            }
        });
        player.bag.useButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(player.bag.isVisible()) {
                    Actor descItem;
                    descItem = player.bag.description.getCells().get(0).getActor();
                    if (descItem != null) {
                        if (descItem instanceof Carryable) {
                            Actor bagItem;
                            for (int i = 0; i < player.bag.items.getCells().size; i++) {
                                bagItem = player.bag.items.getCells().get(i).getActor();
                                if(bagItem != null) {
                                    if (bagItem.getName().compareTo(descItem.getName()) == 0) {
                                        if(descItem instanceof Trap) {
                                            Array<Float> array = new Array<Float>();
                                            array.add(player.getX());
                                            array.add(player.getY());
                                            if (((Carryable) descItem).useItem(traps, array)) {
                                                player.bag.items.removeActor(player.bag.items.getCells().get(i).getActor());
                                                Gdx.app.log("Bag", descItem.getName() + " removed from bag");
                                                player.bag.changeDescription(null);
                                                return;
                                            }
                                        }
                                        else if (((Carryable) descItem).useItem(player, new Array<Float>())) {
                                            player.bag.items.removeActor(player.bag.items.getCells().get(i).getActor());
                                            Gdx.app.log("Bag", descItem.getName() + " removed from bag");
                                            player.bag.changeDescription(null);
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.pickItem();
        if(player.getHealth() == 0) {
            Gdx.app.log("Survivor", "Game over");
            Gdx.app.exit();
        }
        ((SideScrollingCamera)gameStage.getCamera()).followPos(player.getOrigin());
    }

    @Override
    public void proccessInput() {
        super.proccessInput();
        if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            for(int i = 0; i < injuredNPCS.getChildren().size; i++) {
                if(player.getRectangle().overlaps(((ImageEx)injuredNPCS.getChildren().get(i)).getRectangle()))
                    ((InjuredNPC)injuredNPCS.getChildren().get(i)).save();
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.K)) {
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
        }
    }

    private void pickItem() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            for (int i = 0; i < items.getChildren().size; i++) {
                ImageEx item = (ImageEx)items.getChildren().get(i);
                if (item.getRectangle().overlaps(player.getRectangle())) {
                    items.removeActor(item);
                    if(item instanceof Ammo) {
                        ((Ammo) item).useItem(player, null);
                    }
                    else {
                        player.bag.addItem(item);
                    }
                }
            }
        }
    }

    @Override
    public void setMap() {
        Array<Texture> parallaxTextures = new Array<Texture>();
        for(int i = 0; i < 6;i++){
            parallaxTextures.add(Assets.getAsset(Assets._Parallax1[i], Texture.class));
            parallaxTextures.get(i).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);

        }
        parallaxBackground = new ParallaxBackground(parallaxTextures, gameStage.getCamera().viewportWidth, gameStage.getCamera().viewportHeight);
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

            }
        }
    }

    public void addItem() {
        Carryable item = new Ammo(1,1);
        switch (MathUtils.random(1,10)) {
            case 9:
            case 10:
            case 1: item = new Medicine(Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))); break;
            case 6:
            case 7:
            case 8:
            case 2: item = new Ammo(Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))); break;
            case 3: item = new Tuna(Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))); break;
            case 4: item = new Water(Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))); break;
            case 5: item = new Wood(Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))); break;
        }
        items.addActor((Actor)item);
    }

    @Override
    public void setDecor() {

    }

    @Override
    public void setPlayer(float startingPointX) {
        player.setX(startingPointX);
        weaponsAllowed = true;
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
            items.addActor(new Medicine(Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))));
            items.getChildren().get(items.getChildren().size - 1).setName("Medicine number "+i);
        }
        for(int i = 0; i < 9; i++) {
            items.addActor(new Wood(Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))));
            items.getChildren().get(items.getChildren().size - 1).setName("Wood number "+i);
        }
        for(int i = 0; i < 3; i++) {
            items.addActor(new Tuna(Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))));
            items.getChildren().get(items.getChildren().size - 1).setName("Tuna number "+i);
        }
        for(int i = 0; i < 3; i++) {
            items.addActor(new Water(Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))));
            items.getChildren().get(items.getChildren().size - 1).setName("Water number "+i);
        }
        for(int i = 0; i < 5; i++) {
            items.addActor(new BearTrap(Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))));
            items.getChildren().get(items.getChildren().size - 1).setName("Bear Trap number "+i);
        }
        for(int i = 0; i < 5; i++) {
            items.addActor(new Ammo(Survivor.getInMeters(MathUtils.random(0, 4000)), Survivor.getInMeters(MathUtils.random(0, 280))));
            //items.getChildren().get(items.getChildren().size - 1).setName("Bear Trap number "+i);
        }
    }
}
