package com.stanleycai.smartsort.collaborative;

import java.util.Comparator;

public class CollaborativeComparator implements Comparator<Item>{
    private User mUser;

    public CollaborativeComparator(User user) {
        this.mUser = user;        
    }
    
    public int compare(Item itema, Item itemb) {
        double proba = CollaborativeEstimator.getInstance().estimate(mUser, itema);
        double probb = CollaborativeEstimator.getInstance().estimate(mUser, itemb);
        return (proba > probb ? -1 : (proba < probb) ? 1 : 0);
    }
}
