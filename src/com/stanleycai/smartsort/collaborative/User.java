package com.stanleycai.smartsort.collaborative;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class User {
    private int mId;
    private int mAge;
    private char mGender;
    private String mOccupation;
    private String mZip;

    private User(int id, int age, char gender, String occupation, String zip) {
        mId = id;
        mAge = age;
        mGender = gender;
        mOccupation = occupation;
        mZip = zip;
    }

    public int getId() {
        return mId;
    }

    public int getAge() {
        return mAge;
    }

    public char getGender() {
        return mGender;
    }

    public String getOccupation() {
        return mOccupation;
    }

    public String getZip() {
        return mZip;
    }

    public String toString() {
        return String.format("[id:%d age:%d gender:%c occupation:%s zip %s]",
                mId, mAge, mGender, mOccupation, mZip);
    }

    public static User[] loadUsersFromFile(String userFilename) {
        ArrayList<User> users = new ArrayList<User>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(userFilename)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|");
                users.add(new User(Integer.valueOf(fields[0]), Integer
                        .valueOf(fields[1]), fields[2].charAt(0), fields[3],
                        fields[4]));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users.toArray(new User[0]);
    }
}
