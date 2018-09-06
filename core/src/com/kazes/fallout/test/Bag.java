package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.kazes.fallout.test.items.*;

public class Bag extends com.badlogic.gdx.scenes.scene2d.ui.Window {
    int x;
    public Table items;
    public Table description;
    public Button useButton;
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
                Medicine clone = new Medicine(item.getX(), item.getY());
                clone.setName(item.getName());
                description.add(clone).grow().fillY().pad(Value.percentHeight(1f, item)).row();
                description.add(new Label(clone.getName(), Assets.getAsset(Assets.UI_SKIN, Skin.class))).row();
                description.add(new Label(clone.getDescription(), Assets.getAsset(Assets.UI_SKIN, Skin.class))).row();
                description.add(useButton);

            }
            if (item instanceof Wood) {
                Wood clone = new Wood(item.getX(), item.getY());
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
            if (item instanceof BearTrap) {
                BearTrap clone = new BearTrap(item.getX(), item.getY());
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
