package com.stanleycai.smartsort.collaborative.test;

//import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;

import org.junit.Test;

import com.stanleycai.smartsort.collaborative.User;
import com.stanleycai.smartsort.collaborative.UserBasedCollaborativeFilter;

public class UserBasedCollaborativeFilterTest {

    @Test
    public void testEstimate() {
        User[] users = User.loadUsersFromFile("dat/ml-100k/u.user");
        UserBasedCollaborativeFilter cfilter = new UserBasedCollaborativeFilter();
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("out/u2.output")));
            for (int i = 0; i < users.length; ++i) {
                int[] res = cfilter.estimate(users[i], 3);
                writer.write(Integer.toString(users[i].getId()) + ":"
                        + Arrays.toString(res) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
