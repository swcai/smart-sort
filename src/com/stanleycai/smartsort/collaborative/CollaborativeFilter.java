package com.stanleycai.smartsort.collaborative;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.BitSet;

/* CF-based recommendation
 * - Item-based
 * 
 * The data used are from movielens.org, so some data structures are designed
 * actually for a movie. :)
 * 
 */
public abstract class CollaborativeFilter {
    private static final String USERFILE = "dat/ml-100k/u.user";
    private static final String ITEMFILE = "dat/ml-100k/u.item";
    private static final String USERITEMFILE = "dat/ml-100k/u1.base";
    protected User[] mUsers;
    protected Movie[] mMovies;
    protected BitSet[] mUserMoviesMatrix;
    protected BitSet[] mMovieUsersMatrix;

    protected CollaborativeFilter() {
        System.out.println("timestamp #1:" + System.currentTimeMillis());
        mUsers = User.loadUsersFromFile(USERFILE);
        System.out.println("timestamp #2:" + System.currentTimeMillis());
        mMovies = Movie.loadMoviesFromFile(ITEMFILE);
        System.out.println("timestamp #3:" + System.currentTimeMillis());
        loadUserMoviesMatrix();
        System.out.println("timestamp #4:" + System.currentTimeMillis());
        loadMovieUsersMatrix();
    }

    public abstract int[] estimate(User user, int k);
    
    private void loadMovieUsersMatrix() {
        int rows = mMovies.length + 1;
        mMovieUsersMatrix = new BitSet[rows];
        for (int i = 0; i < rows; ++i)
            mMovieUsersMatrix[i] = new BitSet();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(USERITEMFILE)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\s");
                int userId = Integer.valueOf(fields[0]);
                int movieId = Integer.valueOf(fields[1]);
                assert (movieId < rows);
                /*
                 * A simplification to convert rating to access to make all of
                 * the films users rated as accessed. so we could make sure the
                 * accessed items will not be recommended.
                 */
                boolean rating = (Integer.valueOf(fields[2]) >= 3) ? true
                        : false;
                if (rating)
                    mMovieUsersMatrix[movieId].set(userId);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUserMoviesMatrix() {
        int rows = mUsers.length + 1;
        mUserMoviesMatrix = new BitSet[rows];
        for (int i = 0; i < rows; ++i)
            mUserMoviesMatrix[i] = new BitSet();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(USERITEMFILE)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\s");
                int userId = Integer.valueOf(fields[0]);
                int movieId = Integer.valueOf(fields[1]);
                assert (userId < rows);
                /*
                 * A simplification to convert rating to access to make all of
                 * the films users rated as accessed. so we could make sure the
                 * accessed items will not be recommended.
                 */
                boolean rating = true; // (Integer.valueOf(fields[2]) >= 3) ?
                                       // true : false;
                if (rating)
                    mUserMoviesMatrix[userId].set(movieId);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
