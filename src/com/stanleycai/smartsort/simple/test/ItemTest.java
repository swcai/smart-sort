package com.stanleycai.smartsort.simple.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.stanleycai.smartsort.simple.Item;

public class ItemTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testItem() {
        Item item = new Item("item #0");
        assertNotNull(item);
        item.addTag("sports");
        item.addTag("book");
        item.addTag("travel");
        assertEquals(item.getTags().length, 3);
        Item newItem = new Item("item #1", item);
        assertEquals(newItem.getTags().length, 3);
        assertNotSame(item, newItem);
    }

    @Test
    public void testAddTag() {
        Item item = new Item("item #0");
        assertNotNull(item);
        item.addTag("sports");
        item.addTag("book");
        item.addTag("travel");
        item.addTag("book");
        System.out.println(item.getTags().length);
        assertEquals(item.getTags().length, 3);
    }

    @Test
    public void testRemoveTag() {
        Item item = new Item("item #0");
        assertNotNull(item);
        item.addTag("sports");
        item.addTag("book");
        item.addTag("travel");
        assertEquals(item.getTags().length, 3);
        item.removeTag("www");
        assertEquals(item.getTags().length, 3);
        item.removeTag("travel");
        assertEquals(item.getTags().length, 2);
    }

    @Test
    public void testGetTags() {
        assertTrue(true);
    }

    @Test
    public void testToString() {
        assertTrue(true);
    }

}
