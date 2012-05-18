package com.stanleycai.smartsort.simple2;

import java.util.HashSet;
import java.util.Set;

import com.stanleycai.utils.StringUtils;

public class Item {
    private static final int MAX_VOTES = 10000;

    private String mName;
    private Set<String> mTags;
    private int mUpVotes;
    private int mDownVotes;

    public Item(String name) {
        mName = name;
        mTags = new HashSet<String>();
        mUpVotes = mDownVotes = 0;
    }

    public Item(String name, Item item) {
        this(name);
        for (String tag : item.getTags())
            mTags.add(tag);
    }

    public void addTag(String tag) {
        mTags.add(tag);
    }

    public void removeTag(String tag) {
        mTags.remove(tag);
    }

    public String[] getTags() {
        return mTags.toArray(new String[0]);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(mName);
        buf.append(" :[");
        buf.append(StringUtils.join(", ", mTags));
        buf.append("] ");
        buf.append("up ");
        buf.append(mUpVotes);
        buf.append(" down ");
        buf.append(mDownVotes);
        return buf.toString();
    }

    public void voteUp() {
        mUpVotes = Math.min(MAX_VOTES, mUpVotes + 1); 
    }

    public void voteDown() {
        mDownVotes = Math.min(MAX_VOTES, mDownVotes + 1); 
    }
    
    public int getUpVotes() {
        return mUpVotes;
    }
    
    public int getDownVotes() {
        return mDownVotes;
    }

    /* Calculate rating from up and down votes by the items.
     * Tuning is used to adjust a small number for total votes the item get.
     * rating = up / (up + down) + u
     */
    public double getRating() {
        double tuning = (mUpVotes + mDownVotes) * 2.0d / 1.0e5;
        if (mUpVotes == mDownVotes)
            return 0.5d + tuning;
        return mUpVotes * 1.0d / (mUpVotes + mDownVotes)  + tuning;
    }
}
