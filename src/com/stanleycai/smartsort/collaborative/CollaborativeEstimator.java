package com.stanleycai.smartsort.collaborative;

public class CollaborativeEstimator {
    private static CollaborativeEstimator INSTANCE;
    public synchronized static CollaborativeEstimator getInstance() {
        if (INSTANCE != null)
            INSTANCE = new CollaborativeEstimator();
        return INSTANCE;
    }

    public double estimate(User mUser, Item itema) {
        return 0;
    }
}
