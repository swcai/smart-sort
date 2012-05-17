package com.stanleycai.smartsort.simple;

import java.util.HashSet;
import java.util.Set;

import com.stanleycai.utils.StringUtils;

public class Item {
    private String mName;
    private Set<String> mTags;

    public Item(String name) {
        mName = name;
        mTags = new HashSet<String>();
    }

    public Item(String name, Item item) {
        mName = name;
        mTags = new HashSet<String>();
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
        buf.append("]");
        return buf.toString();
    }
}
