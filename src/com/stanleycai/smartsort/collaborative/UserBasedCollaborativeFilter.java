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
public class UserBasedCollaborativeFilter extends CollaborativeFilter {
    private static final int NUMOFFRIENDS = 20;
    
    private BitSet[] mFriendList;
    
    public UserBasedCollaborativeFilter() {
        super();
        System.out.println("timestamp #5:" + System.currentTimeMillis());
        buildFriendList(NUMOFFRIENDS);
        System.out.println("timestamp #6:" + System.currentTimeMillis());
    }

    public int[] estimate(User user, int k) {
        double[] votes = new double[mMovies.length + 1];
        int p = 0;
        while (-1 != (p = mFriendList[user.getId()].nextSetBit(p+1))) {
            int q = 0;
            while (-1 != (q = mUserMoviesMatrix[p].nextSetBit(q+1)))
                votes[q] += 1.0d;
        }
        
        // remove the movies user has watched
        p = 0;
        while (-1 != (p = mUserMoviesMatrix[user.getId()].nextSetBit(p+1)))
            votes[p] = 0.0d; 
        
        return new TopN().apply(votes, k);
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
         * skip 0 anyway, bit #0 always false for (int i=1;
         * i<Math.max(rowa.length(), rowb.length()); ++i) if (rowa.get(i) &&
         * rowb.get(i)) count ++;
         */
        return count / res;
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
            for (int pos : new TopN().apply(matrix[i], k))
                mFriendList[i].set(pos);
        }
    }
}
