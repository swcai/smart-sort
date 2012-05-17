package com.stanleycai.smartsort.simple.test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.stanleycai.smartsort.simple.User;

public class UserTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testUser() {
        User u = new User("user #0");
        assertEquals(u.getAllPreferences().size(), 0);
        assertEquals(u.getPreference("deadbeef"), 0.0d, 5);
        User u1 = new User("user #1");
        u1.setPreference("pref#1", 0.5d);
        u1.setPreference("pref#2", 0.3d);
        User u2 = new User("user #2", u1);
        assertEquals(u2.getPreference("pref#1"), 0.5d, 5);
        assertEquals(u2.getPreference("pref#2"), 0.3d, 5);
        assertEquals(u2.getPreference("pref#3"), 0.0d, 5);
    }

    @Test
    public void testSetPreference() {
        User u1 = new User("user #0");
        assertEquals(u1.getPreference("pref#1"), 0.0d, 5);
        u1.setPreference("pref#1", 0.5d);
        assertEquals(u1.getPreference("pref#1"), 0.5d, 5);
        u1.setPreference("pref#1", -1.0d);
        assertEquals(u1.getPreference("pref#1"), -1.0d, 5);
        u1.setPreference("pref#2", 100d);
        assertEquals(u1.getPreference("pref#2"), 1.0d, 5);
        u1.setPreference("pref#2", -101111d);
        assertEquals(u1.getPreference("pref#2"), -1.0d, 5);
    }

    @Test
    public void testGetPreference() {
        User u1 = new User("user #0");
        assertEquals(u1.getPreference("pref#1"), 0.0d, 5);
        u1.setPreference("pref#1", 0.5d);
        assertEquals(u1.getPreference("pref#1"), 0.5d, 5);
        u1.setPreference("pref#1", -1.0d);
        assertEquals(u1.getPreference("pref#1"), -1.0d, 5);
        u1.setPreference("pref#2", 100d);
        assertEquals(u1.getPreference("pref#2"), 1.0d, 5);
        u1.setPreference("pref#2", -101111d);
        assertEquals(u1.getPreference("pref#2"), -1.0d, 5);
    }

    @Test
    public void testGetAllPreferences() {
        User u1 = new User("user #0");
        u1.setPreference("pref#1", 0.5d);
        u1.setPreference("pref#2", -1.0d);
        u1.setPreference("pref#3", 100d);
        u1.setPreference("pref#4", -101111d);
        HashMap<String, Double> map = u1.getAllPreferences();
        assertEquals(map.get("pref#1"), 0.5d, 5);
        assertEquals(map.get("pref#2"), -1.0d, 5);
        assertEquals(map.get("pref#3"), 1.0d, 5);
        assertEquals(map.get("pref#4"), -1.0d, 5);
    }

    @Test
    public void testToString() {
        User u1 = new User("user #0");
        u1.setPreference("pref#1", 0.5d);
        u1.setPreference("pref#2", -1.0d);
        u1.setPreference("pref#3", 100d);
        u1.setPreference("pref#4", -101111d);
        System.out.println(u1.toString());
    }
}
