package com.stanleycai.smartsort.collaborative;

import java.util.BitSet;

/* CF-based recommendation
 * - Item-based
 * 
 * The data used are from movielens.org, so some data structures are designed
 * actually for a movie. :)
 * 
 */
public class UserBasedCollaborativeFilter extends CollaborativeFilter {
    private BitSet[] mFriendList;
    
    private UserBasedCollaborativeFilter() {
        super();
        System.out.println("timestamp #5:" + System.currentTimeMillis());
        buildFriendList(10);
        System.out.println("timestamp #6:" + System.currentTimeMillis());
    }

    public double[] estimate(User user) {
        return new double[0];
    }

    /* Cosine-based Similarity */
    private double similarity(BitSet rowa, BitSet rowb) {
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
         * skip 0 anyway, bit #0 always false for (int i=1;
         * i<Math.max(rowa.length(), rowb.length()); ++i) if (rowa.get(i) &&
         * rowb.get(i)) count ++;
         */
        return count / res;
    }

    private int[] topN(double[] row, int n) {
        return new int[0];
    }
    
    private void buildFriendList(int k) {
        int rows = mUsers.length + 1;
        double[][] matrix = new double[rows][rows];
        for (int i=1; i < rows; ++i)
            matrix[i] = new double[rows];
        
        /* If similarity function is symmetric, we could improve the performance
         * twice by calculating the diag.
         */
        for (int i = 1; i < rows; ++i)
            for (int j = 1; j < rows; ++j)
                if (i != j)
                    matrix[i][j] = similarity(mUserMoviesMatrix[i],
                            mUserMoviesMatrix[j]);
        
        mFriendList = new BitSet[rows];
        for (int i=1; i<rows; ++i) {
            mFriendList[i] = new BitSet();
            for (int pos : topN(matrix[i], k))
                mFriendList[i].set(pos);
        }
    }
}
