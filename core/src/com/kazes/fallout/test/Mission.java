package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.kazes.fallout.test.enemies.Enemy;
import com.kazes.fallout.test.inventory.Item;
import com.kazes.fallout.test.inventory.Slot;
import com.kazes.fallout.test.items.ItemActor;
import com.kazes.fallout.test.screens.GameScreen;
import com.kazes.fallout.test.screens.Screens;

public class Mission {
    private Objective objective;
    private boolean isCompleted;
    private int ticker;
    private String message;


    GameScreen screen;
    float timer;
    float counter;
    Item item;
    Jobs job;
    String name;

    public Mission(Objective objective, GameScreen gameScreen, String message) {
        this(objective, gameScreen);
        addMessage(message);
    }

    public Mission(Objective objective, GameScreen gameScreen) {
        this.objective = objective;
        isCompleted = false;
        ticker = 60;
        this.screen = gameScreen;
    }

    public void update() {
        if(ticker == 0 || objective == Objective.KillCount) {
            ticker = 60;
            if (!isCompleted) {
                switch (objective) {
                    case Timed:
                        timer--;
                        if (timer <= 0)
                            isCompleted = true;
                        break;
                    case AddNPCCount:
                        if (ClanProperties.getNPCCount() >= counter)
                            isCompleted = true;
                        break;
                    case RetrieveItem:
                        for (Slot currentItem : GameScreen.getInventoryActor().getInventory().getSlots())
                            if (currentItem.getItem() != null && currentItem.getItem().region == item.region) {
                                isCompleted = true;
                                break;
                            }
                        for (Slot currentItem : GameScreen.getFastInventoryActor().getInventory().getSlots())
                            if (currentItem.getItem() != null && currentItem.getItem().region == item.region) {
                                isCompleted = true;
                                break;
                            }
                        break;
                    case KillCount:
                        for (Actor enemy : screen.getEnemies().getChildren())
                            if (((Enemy) enemy).getHealth() == -1f)
                                counter--;
                        if (counter <= 0)
                            isCompleted = true;
                        break;
                    case AddNPCType:
                        if (ClanProperties.getValue(job) > 0)
                            isCompleted = true;
                        break;
                    case GoTo:
                        if(Screens.getCurrent().getName().equals(name))
                            isCompleted = true;
                        break;
                }
            }
        }
        ticker--;
    }

    public void addRequirment(Object object) {
        switch (objective) {
            case Timed: timer = (Integer)object; break;
            case KillCount: counter = (Integer)object; break;
            case RetrieveItem: item = (Item)object; break;
            case AddNPCCount: counter = (Integer)object; break;
            case AddNPCType: job = (Jobs)object; break;
            case GoTo: name = (String)object; break;
        }
    }

    public void addMessage(String message) {
        this.message = message;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getMessage() {
        return message;
    }
}
