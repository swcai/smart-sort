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
public class ItemBasedCollaborativeFilter {
    private static ItemBasedCollaborativeFilter INSTANCE;

    public synchronized static ItemBasedCollaborativeFilter getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ItemBasedCollaborativeFilter();
        return INSTANCE;
    }

    private static final String USERFILE = "dat/ml-100k/u.user";
    private static final String ITEMFILE = "dat/ml-100k/u.item";
    private static final String USERITEMFILE = "dat/ml-100k/u1.base";
    private User[] mUsers;
    private Movie[] mMovies;
    private BitSet[] mUserMoviesMatrix;
    private BitSet[] mMovieUsersMatrix;
    private double[][] mMovieMoviesMatrix;

    private ItemBasedCollaborativeFilter() {
        System.out.println("timestamp #1:" + System.currentTimeMillis());
        mUsers = User.loadUsersFromFile(USERFILE);
        System.out.println("timestamp #2:" + System.currentTimeMillis());
        mMovies = Movie.loadMoviesFromFile(ITEMFILE);
        System.out.println("timestamp #3:" + System.currentTimeMillis());
        mUserMoviesMatrix = ItemBasedCollaborativeFilter.loadUserMoviesMatrix(
                USERITEMFILE, mUsers.length);
        System.out.println("timestamp #4:" + System.currentTimeMillis());
        mMovieUsersMatrix = ItemBasedCollaborativeFilter.loadMovieUsersMatrix(
                USERITEMFILE, mMovies.length);
        System.out.println("timestamp #5:" + System.currentTimeMillis());
        mMovieMoviesMatrix = ItemBasedCollaborativeFilter.buildMovieMoviesMatrix(
                mMovieUsersMatrix, mMovies.length);
        System.out.println("timestamp #6:" + System.currentTimeMillis());
    }

    public double[] filter(User user) {
        double[] res = new double[mMovies.length];
        BitSet userItemRow = mUserMoviesMatrix[user.getId()];
        // skip 0 anyway, mItemItemMatrix[x][y] is zero always, when either
        // x or y is zero.
        for (int i = 1; i < mMovies.length; ++i)
            if (!userItemRow.get(i)) {
                double value = 0d;
                for (int j = 1; j < mMovies.length; ++j)
                    value += mMovieMoviesMatrix[i][j]
                            * (userItemRow.get(j) ? 1.0d : 0.0d);
                res[i] = value;
            }

        return res;
    }

    /* Cosine-based Similarity */
    private static double similarity(BitSet rowa, BitSet rowb) {
        double res = rowa.cardinality() * rowb.cardinality();
        if (res == 0.0d)
            return 0.0d;

        int count = 0;
        int pos = 0;
        /* row is very sparse array. To use nextSetBit for performance */
        while ((pos = rowa.nextSetBit(pos + 1)) != -1)
            if (rowb.get(pos))
                count++;
        /*
         * // skip 0 anyway, bit #0 always false for (int i=1;
         * i<Math.max(rowa.length(), rowb.length()); ++i) if (rowa.get(i) &&
         * rowb.get(i)) count ++;
         */
        return count / res;
    }

    private static double[][] buildMovieMoviesMatrix(BitSet[] movieUsersMatrix,
            int rows) {
        double[][] matrix = new double[rows + 1][rows + 1];
        for (int i = 1; i <= rows; ++i)
            matrix[i] = new double[rows + 1];

        for (int i = 1; i <= rows; ++i)
            for (int j = 1; j <= rows; ++j)
                if (i != j)
                    matrix[i][j] = similarity(movieUsersMatrix[i],
                            movieUsersMatrix[j]);
        return matrix;
    }

    private static BitSet[] loadMovieUsersMatrix(String userMoviesFilename, int rows) {
        BitSet[] matrix = new BitSet[rows + 1];
        for (int i = 0; i <= rows; ++i)
            matrix[i] = new BitSet();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(userMoviesFilename)));
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
                    matrix[movieId].set(userId);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matrix;
    }

    private static BitSet[] loadUserMoviesMatrix(String userMoviesFilename, int rows) {
        BitSet[] matrix = new BitSet[rows + 1];
        for (int i = 0; i <= rows; ++i)
            matrix[i] = new BitSet();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(userMoviesFilename)));
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
                    matrix[userId].set(movieId);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matrix;
    }
}
