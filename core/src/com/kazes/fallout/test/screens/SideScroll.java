package com.kazes.fallout.test.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.Array;
import com.kazes.fallout.test.*;
import com.kazes.fallout.test.enemies.Zombie;
import com.kazes.fallout.test.items.*;
import com.kazes.fallout.test.physics.CollisionCategory;
import com.kyper.yarn.Library;
import com.kyper.yarn.Value;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Game logic testing screen
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public class SideScroll extends GameScreen {
    public static Texture texture; //temp pikachu texture for testing

    private Group injuredNPCS;

    private NPC talkingTo;

    public SideScroll(Survivor game) {
        super(game, "Prologue", 0);
        create(game);
    }
    public SideScroll(Survivor game, float startingPosX) {
        super(game, "Prologue", startingPosX);
        create(game);
    }
    @Override
    public void pauseLogic() {}

    @Override
    public void resumeLogic(){}

    @Override
    public void show() {}

    @Override
    public void hide() {}

    //Load the content
    private void create (final Survivor game) {
        nextScreen = Screens.Tribe;

        dialogueManager.dialogue.getLibrary().registerFunction("setSallyAction", 1, new Library.Function() {
            @Override
            public void invoke(com.kyper.yarn.Value... params) {
                // this function only has one parameter so check like so
                //Value action = params[0];// this parameter will be the name of the action sprite to set sally to


            }
        });
        dialogueManager.dialogue.getLibrary().registerFunction("recruitAction", 0, new Library.Function() {
            @Override
            public void invoke(Value... params) {
                if(talkingTo != null) {
                    npcs.removeActor(talkingTo);
                    //gameStage.getActors().removeValue(talkingTo, false);
                    Gdx.app.log("Followers", talkingTo.getName() + " was added");
                    followers.addActor(talkingTo);
                }
            }
        });
        dialogueManager.dialogue.loadFile(Assets.Dialogues.SALLY, false, false, null);
        dialogueManager.dialogue.loadFile(Assets.Dialogues.SHIP, false, false, null);
        dialogueManager.dialogue.loadFile(Assets.Dialogues.EXAMPLE, false, false, null);

        screenStage.addActor(dialogueManager.getWindow());

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        //SuperObject.updateBegin(gameStage.getCamera());
        //this.processInput();
        //this.pickItem();
        this.followPlayer();


        if(player.getHealth() == 0) {
            Gdx.app.log("Survivor", "Game over");
            Gdx.app.exit();
        }

        //SuperObject.updateEnd();
    }

    //Draws every frame
    @Override
    public void render (float delta) {
        super.render(delta);
        ((SideScrollingCamera)gameStage.getCamera()).followPos(player.getOrigin());
        //parallaxBackground.setXPos(gameStage.getCamera().position.x - gameStage.getCamera().viewportWidth / 2);
    }

    //Every input given from the player is processed here
    @Override
    public void proccessInput() {
        super.proccessInput();


        if(Gdx.input.isKeyJustPressed(Input.Keys.ALT_LEFT)) {
            for(int i = 0; i < npcs.getChildren().size; i++) {
                if(player.getRectangle().overlaps(((NPC)npcs.getChildren().items[i]).getRectangle())) {
                    talkingTo = (NPC)npcs.getChildren().items[i];
                    dialogueManager.start("TalkTo");
                    break;
                }
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
        if(dialogueManager.isCompleted()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
                // talk to ship
                dialogueManager.start("Ship");
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
                // talk to sally
                dialogueManager.start("Sally");
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            for(int i = 0; i < injuredNPCS.getChildren().size; i++) {
                if(player.getRectangle().overlaps(((ImageEx)injuredNPCS.getChildren().get(i)).getRectangle()))
                    ((InjuredNPC)injuredNPCS.getChildren().get(i)).save();
            }
        }
    }


    private void followPlayer() {
        for(Actor follower : followers.getChildren()) {
            if(!follower.hasActions())
                follower.addAction(moveTo(player.getX() + ((player.isxFlip()) ? Survivor.getInMeters(50) + ( Survivor.getInMeters(50) * followers.getChildren().indexOf(follower, false)) : Survivor.getInMeters(-50) - ( Survivor.getInMeters(70) * followers.getChildren().indexOf(follower, false))), player.getY(), 1f));
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        gameStage.dispose();
        screenStage.dispose();
    }

    public static float forcePositive(float number) {
        return (number < 0) ? number * -1 : number;
    }


    private static Vector2 getRange(float xOrigin, float yOrigin, float xTarget, float yTarget) {
        return new Vector2(xOrigin - xTarget, yOrigin - yTarget);
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
    public void setDecor() {
        decor.addActor(new ImageEx(game.assetManager.get(Assets.Images.HOUSE1, Texture.class), Survivor.getInMeters(500), Survivor.getInMeters(300), world, BodyDef.BodyType.StaticBody, CollisionCategory.DECORATION, CollisionCategory.DECORATION_COLLIDER));
        decor.addActor(new ImageEx(game.assetManager.get(Assets.Images.HOUSE2, Texture.class), Survivor.getInMeters(1000), Survivor.getInMeters(300), world, BodyDef.BodyType.StaticBody, CollisionCategory.DECORATION, CollisionCategory.DECORATION_COLLIDER));
    }

    @Override
    public void setPlayer(float startingPointX) {
        player.setX(startingPointX);
        weaponsAllowed = true;
    }

    @Override
    public void setNPCS() {
        npcs.addActor(new NPC(game.assetManager.get(Assets.Images.NPC_TEMP_2, Texture.class), "Harambe", Survivor.getInMeters(50), Survivor.getInMeters(50), Weapons.Pistol));
        npcs.addActor(new NPC(game.assetManager.get(Assets.Images.NPC_TEMP_1, Texture.class), "Yilfa", Survivor.getInMeters(450), Survivor.getInMeters(50), Weapons.SMG));
        for (int i = 0; i < npcs.getChildren().size; i++) {
            npcs.getChildren().items[i].setSize(Survivor.getInMeters(100), Survivor.getInMeters(150));
        }
        this.injuredNPCS = new Group();
        npcs.addActor(this.injuredNPCS);
        for(int i = 0; i < MathUtils.random(6); i++)
            injuredNPCS.addActor(new InjuredNPC(world, MathUtils.random(1,Survivor.getInMeters(map.getWidth())), MathUtils.random(1,9),  Weapons.Pistol));
    }

    @Override
    public void setEnemies() {
        texture = game.assetManager.get(Assets.Images.PIKACHU, Texture.class);
        for(int i = 0; i < 35; i++) {
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

/*Ideas:
1. if you eat too much your speed will decrease
2. if you drink too much will speed will increase drastically (you need to piss)
3. if you use too much medkits you will get sick (but with full health)
4. getting sick causes loss of focus (shooting will get hard, and the screen will get bobbly)


 */