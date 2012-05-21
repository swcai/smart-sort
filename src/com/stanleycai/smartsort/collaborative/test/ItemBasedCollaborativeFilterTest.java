package com.stanleycai.smartsort.collaborative.test;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;

import org.junit.Test;

import com.stanleycai.smartsort.collaborative.ItemBasedCollaborativeFilter;
import com.stanleycai.smartsort.collaborative.User;

public class ItemBasedCollaborativeFilterTest {

    @Test
    public void testEstimate() {
        User[] users = User.loadUsersFromFile("dat/ml-100k/u.user");
        ItemBasedCollaborativeFilter cfilter = new ItemBasedCollaborativeFilter();
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("out/u1.output")));
            for (int i = 0; i < users.length; ++i) {
                double[] res = cfilter.estimate(users[i]);
                writer.write(Integer.toString(users[i].getId()) + ":"
                        + Arrays.toString(res) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
