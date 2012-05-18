package com.stanleycai.smartsort.simple2;

import java.util.HashMap;

import com.stanleycai.utils.StringUtils;

public class User {
    String mName;
    /*
     * Assume the preference is in the range from -1.0d to 1.0d -1.0d means very
     * not preferred 1.0d means very preferred So, the default value 0.0d is the
     * mid point.
     */
    HashMap<String, Double> mPreferences;

    public User(String name) {
        mName = name;
        mPreferences = new HashMap<String, Double>();
    }

    public User(String name, User user) {
        this(name);
        for (String tag : user.getAllPreferences().keySet())
            mPreferences.put(tag, user.getPreference(tag));
    }

    /*
     * This is a simple implementation for this algorithm.
     * 
     * Items has tags and with users' up or down votes 
     */
    public double eval(Item item) {
        double res = 0.0d;
        for (String tag : item.getTags())
            res += getPreference(tag);
        return res * item.getRating();
    }
    
    public void setPreference(String tag, double pref) {
        mPreferences.put(tag,
                (pref > 1.0d ? 1.0d : ((pref < -1.0d) ? -1.0d : pref)));
    }

    public double getPreference(String tag) {
        if (!mPreferences.containsKey(tag))
            mPreferences.put(tag, 0.0d); // Need tune this value.
        return mPreferences.get(tag);
    }

    public HashMap<String, Double> getAllPreferences() {
        return mPreferences;
    }

    private static final double delta = 0.01d;

    public void vote(Item item, boolean feedback) {
        double d = feedback ? delta : -delta;
        if (feedback)
            item.voteUp();
        else
            item.voteDown();
        for (String tag : item.getTags())
            setPreference(tag, getPreference(tag) + d);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(mName);
        buf.append(" :{");
        String[] s = new String[mPreferences.size()];
        int index = 0;
        for (String tag : mPreferences.keySet())
            s[index++] = String.format("\"%s\": %4.4f", tag,
                    mPreferences.get(tag));
        buf.append(StringUtils.join(", ", s));
        buf.append("}");
        return buf.toString();
    }
}
