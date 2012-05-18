package com.stanleycai.smartsort.simple2.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.stanleycai.smartsort.simple2.Item;
import com.stanleycai.smartsort.simple2.Simple2FeedbackComparator;
import com.stanleycai.smartsort.simple2.User;

public class ComparatorTest {
    private static final int NUMBEROFUSERS = 10;
    private static final int NUMBEROFITEMS = 10;
    private String[] TAGS = { "TRAVEL", "MOVIE", "MUSIC", "BOOK", "WWW",
            "SPORTS", "NEWS", "BUSINESS", "HISTORY", "EE", "MATH", "FINANCE",
            "AUTO", "TECHNOLOGY", "ANDROID", "FUNCTIONAL PROGRAMMING",
            "CLOJURE", "SOCIAL NETWORKING", "BLOGGING", "TWITTER", "FACEBOOK",
            "GOOGLE", "MICROSOFT" };
    private static final Random RANDOM = new Random();
    private User[] users;
    private Item[] items;

    private void setRandomPreferences(User user) {
        int num = RANDOM.nextInt(TAGS.length / 3);
        for (int i = 0; i < num; ++i)
            user.setPreference(TAGS[RANDOM.nextInt(TAGS.length)],
                    RANDOM.nextDouble() * 2 - 1.0d);
    }

    private void initUsers() {
        users = new User[NUMBEROFUSERS];
        for (int i = 0; i < NUMBEROFUSERS; ++i) {
            users[i] = new User(String.format("user#%d", i));
            if (i != 0) /* make user#0 with no preference at all. */
                setRandomPreferences(users[i]);
        }
    }

    private void setRandomTags(Item item) {
        int num = RANDOM.nextInt(2 + TAGS.length >> 2);
        for (int i = 0; i < num; ++i)
            item.addTag(TAGS[RANDOM.nextInt(TAGS.length)]);
    }

    private void initItems() {
        items = new Item[NUMBEROFITEMS];
        for (int i = 0; i < NUMBEROFITEMS; ++i) {
            items[i] = new Item(String.format("item#%d", i));
            setRandomTags(items[i]);
        }
    }

    @Before
    public void setUp() throws Exception {
        initItems();
        initUsers();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCompare() {
        assertTrue(true);
    }

    private void printSingleUserWithItems(User user, Item[] items) {
        System.out.println(user);
        System.out.print("[");
        System.out.print(String.format("{%s} %f", items[0].toString(),
                user.eval(items[0])));
        for (int i = 1; i < items.length; ++i)
            System.out.print(String.format(", {%s} %f", items[i].toString(),
                    user.eval(items[i])));
        System.out.println("]");
    }
    
    private void testSingleUser(User user) {
        Arrays.sort(items, new Simple2FeedbackComparator(user));
        printSingleUserWithItems(user, items);
        for (int w = 0; w < 100; ++w) {
            user.vote(items[RANDOM.nextInt(items.length)], RANDOM.nextBoolean());
            Arrays.sort(items, new Simple2FeedbackComparator(user));
            printSingleUserWithItems(user, items);
        }
    }

    @Test
    public void testSingleUserTest() {
        testSingleUser(users[0]);
    }

    @Test
    public void testRandomizationTest() {
        for (User u : users)
            testSingleUser(u);
        assertTrue(true);
    }
}
