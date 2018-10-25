package com.kazes.fallout.test.items;

import com.badlogic.gdx.math.MathUtils;
import com.kazes.fallout.test.inventory.Item;

/**
 * An enum defining all items
 * note: if you add a story item, add in the last slot and decrease from the total length in the random function,
 * if you add a normal item, add it before any story item.
 * @author Ofek Kazes
 */
public enum Items {
    AmmoCrate(0) {
        @Override
        public Item getItem() {
            return new AmmoCrate();
        }
    },
    SmallMedkit(1) {
        @Override
        public Item getItem() {
            return new SmallMedkit();
        }
    },
    LargeMedkit(2) {
        @Override
        public Item getItem() {
            return new LargeMedkit();
        }
    },
    TunaCan(3) {
        @Override
        public Item getItem() {
            return new TunaCan();
        }
    },
    Boots(4) {
        @Override
        public Item getItem() {
            return new Boots();
        }
    },
    Cap(5) {
        @Override
        public Item getItem() {
            return new Cap();
        }
    },
    Perfume(6) {
        @Override
        public Item getItem() {
            return new Perfume();
        }
    },
    RunningShoes(7) {
        @Override
        public Item getItem() {
            return new RunningShoes();
        }
    },
    Sneakers(8) {
        @Override
        public Item getItem() {
            return new Sneakers();
        }
    },
    WaterBottle(9) {
        @Override
        public Item getItem() {
            return new WaterBottle();
        }
    },
    Axe_Story(10) {
        @Override
        public Item getItem() {
            return new Axe_Story();
        }
    };

    private final int value;
    Items(int value) {
        this.value = value;
    }

    public abstract Item getItem();

    public static Item getRandom() {
        return Items.values()[MathUtils.random(Items.values().length - 2)].getItem();
    }
}
