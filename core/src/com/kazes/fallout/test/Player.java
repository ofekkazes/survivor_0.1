package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import org.omg.CORBA.ValueBaseHolder;

enum Weapons {
    NULL(1),
    Knife(2),
    Sword(3),
    Pistol(4),
    SMG(5);

    private final int value;
    private Weapons(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

class Bag extends Window {
    int x;
    Table items;
    Table description;
    Button useButton;
    public Bag(String title, Skin skin) {
        super(title, skin);
        this.setSize(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 4);
        this.setPosition(Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 8);
        items = new Table();
        this.add(items.left()).width(Value.percentWidth(.75F, this)).expandY().top();
        description = new Table();
        this.add(description).width(Value.percentWidth(.25F, this)).expandY().top();
        items.setDebug(true);
        description.setDebug(true);
        this.setDebug(true);
        useButton = new Button(Assets.getAsset(Assets.UI_SKIN, Skin.class));
        useButton.add("Use");

        x = 0;
    }

    public boolean addItem(Actor item) {
        if(item instanceof Carryable) {
            //this.add(item);
            /*Group temp = new Group();
            temp.addActor(new Label("Med", SideScroll.skin));
            temp.addActor(item);
            items.add(temp); IDEA*/
            for (int i = 0; i < items.getCells().size; i++) {
                if(!items.getCells().get(i).hasActor()) {
                    items.getCells().get(i).setActor(item);
                    return true;
                }
            }
            items.add(item).pad(10);
            x++;
            if(x > 6) {
                x = 0;
                items.row();
            }

            Gdx.app.log("Bag", item.getName() + " added to the bag");

            return true;
        }
        return false;
    }

    public void changeDescription(final Actor item) {
        description.clearChildren();
        if(item != null) {
            if (item instanceof Medicine) {
                Medicine clone = new Medicine(((Medicine) item).texture, item.getX(), item.getY());
                clone.setName(item.getName());
                description.add(clone).grow().fillY().pad(Value.percentHeight(1f, item)).row();
                description.add(new Label(clone.getName(), Assets.getAsset(Assets.UI_SKIN, Skin.class))).row();
                description.add(new Label(clone.getDescription(), Assets.getAsset(Assets.UI_SKIN, Skin.class))).row();
                description.add(useButton);

            }
            if (item instanceof Wood) {
                Wood clone = new Wood(((Wood) item).texture, item.getX(), item.getY());
                clone.setName(item.getName());
                description.add(clone).grow().fillY().pad(Value.percentHeight(1f, item)).row();
                description.add(new Label(clone.getName(), Assets.getAsset(Assets.UI_SKIN, Skin.class))).row();
                description.add(new Label(clone.getDescription(), Assets.getAsset(Assets.UI_SKIN, Skin.class))).row();
                description.add(useButton);

            }
            if (item instanceof Tuna) {
                Tuna clone = new Tuna(item.getX(), item.getY());
                clone.setName(item.getName());
                description.add(clone).grow().fillY().pad(Value.percentHeight(1f, item)).row();
                description.add(new Label(clone.getName(), Assets.getAsset(Assets.UI_SKIN, Skin.class))).row();
                description.add(new Label(clone.getDescription(), Assets.getAsset(Assets.UI_SKIN, Skin.class))).row();
                description.add(useButton);

            }
            if (item instanceof Water) {
                Water clone = new Water(item.getX(), item.getY());
                clone.setName(item.getName());
                description.add(clone).grow().fillY().pad(Value.percentHeight(1f, item)).row();
                description.add(new Label(clone.getName(), Assets.getAsset(Assets.UI_SKIN, Skin.class))).row();
                description.add(new Label(clone.getDescription(), Assets.getAsset(Assets.UI_SKIN, Skin.class))).row();
                description.add(useButton);

            }
        }

    }
    public void removeIndex(int index) {
        items.getCells().get(index).getActor().remove();
    }
    public void removeActorFromTable(Actor actor) {
        items.getCell(actor).getActor().remove();
    }
}
//rabbit valve gauge
//find way project
//assist truth blur
//void pulp shadow

public class Player extends AnimationActor {

    int weapon;
    Array<ImageEx> bullets;
    private float health;
    Bag bag;
    private float hunger;
    float time;
    float thirst;

    public Player(ObjectMap<String, Animation<TextureRegion>> t, Vector2 position) {
        super(t, "Player", position.x, position.y);
        this.setPosition(position.x, position.y);
        weapon = Weapons.NULL.getValue();
        bullets = new Array<ImageEx>();
        bag = new Bag("Bag", Assets.getAsset(Assets.UI_SKIN, Skin.class));
        bag.setVisible(false);
        this.health = 100;
        this.hunger = 100;
        this.time = 0;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            bag.setVisible(!bag.isVisible());
        }
        time += delta;

        if(time % 1 > 0.9f)
            time += 1-(time % 1);
        if((time % 60) == 0) {
            hunger--;
            Gdx.app.log("Hunger", hunger + "");
        }
        if((time % 120) == 0) {
            thirst--;
            Gdx.app.log("Thirst", thirst + "");
        }
    }

    public void addHealth(float amount) {
        this.health = (amount + this.health > 100) ? 100 : amount + this.health;
    }

    public boolean subHealth(float amount) {
        this.health = (0 > this.health - amount) ? 0 : this.health - amount;
        return this.health == 0;
    }

    public float getHealth() { return this.health; }

    public Texture getTexture() {
        return super.texture;
    }

    public int getWeapon() {
        return weapon;
    }

    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }

    public void eat(float amount) {
        hunger += amount;
    }

    public void drink(float amount) {
        thirst += amount;
    }
}
