package com.kazes.fallout.test;

public enum Jobs {
    Warrior(0),
    Doctor(1),
    Scientist(2),
    Merchant(3),
    PayedMercenary(4);

    int value;
    Jobs(int value) {
        this.value = value;
    }

    static Jobs setValue(int value) {
        return Jobs.values()[value];
    }
    static int getValue(Jobs job) {
        return job.value;
    }
}
