package com.stanleycai.smartsort.simple2;

import java.util.Comparator;

public class Simple2FeedbackComparator implements Comparator<Item> {
    private User mUser;

    public Simple2FeedbackComparator(User user) {
        this.mUser = user;
    }

    public int compare(Item itema, Item itemb) {
        double resOfItemA = mUser.eval(itema);
        double resOfItemB = mUser.eval(itemb);
        return (resOfItemA > resOfItemB) ? -1 : ((resOfItemA < resOfItemB) ? 1
                : 0);
    }
}
