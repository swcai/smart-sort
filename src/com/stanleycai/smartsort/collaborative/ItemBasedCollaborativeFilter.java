package com.stanleycai.smartsort.collaborative;

import java.util.BitSet;

import com.stanleycai.utils.TopN;

/* CF-based recommendation
 * - Item-based
 * 
 * The data used are from movielens.org, so some data structures are designed
 * actually for a movie. :)
 * 
 */
public class ItemBasedCollaborativeFilter extends CollaborativeFilter {
    private double[][] mMovieMoviesMatrix;

    public ItemBasedCollaborativeFilter() {
        super();
        System.out.println("timestamp #5:" + System.currentTimeMillis());
        buildMovieMoviesMatrix();
        System.out.println("timestamp #6:" + System.currentTimeMillis());
    }

    public int[] estimate(User user, int k) {
        int rows = mMovies.length + 1;
        double[] res = new double[rows];
        BitSet userItemRow = mUserMoviesMatrix[user.getId()];
        // skip 0 anyway, mItemItemMatrix[x][y] is zero always, when either
        // x or y is zero.
        for (int i = 1; i < rows; ++i)
            if (!userItemRow.get(i)) {
                double value = 0.0d;
                for (int j = 1; j < rows; ++j)
                    value += mMovieMoviesMatrix[i][j]
                            * (userItemRow.get(j) ? 1.0d : 0.0d);
                res[i] = value;
            }

        return TopN.apply(res, k);
    }

    /* Cosine-based Similarity */
    private double similarity(BitSet rowa, BitSet rowb) {
        double res = Math.sqrt(rowa.cardinality()) * Math.sqrt(rowb.cardinality());
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

    private void buildMovieMoviesMatrix() {
        int rows = mMovies.length + 1;
        mMovieMoviesMatrix = new double[rows][rows];
        for (int i = 1; i < rows; ++i)
            mMovieMoviesMatrix[i] = new double[rows];

        for (int i = 1; i < rows; ++i)
            for (int j = 1; j < rows; ++j)
                if (i != j)
                    mMovieMoviesMatrix[i][j] = similarity(mMovieUsersMatrix[i],
                            mMovieUsersMatrix[j]);
    }
}
