package com.stanleycai.utils;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Move the implementation to non-static functions
 * 
 * TopN algorithm is a sort problem researched by Donald. Here I use heap-based
 * implementation, instead of so-called tournament algorithm.
 * 
 * http://en.wikipedia.org/wiki/Selection_algorithm
 */
public class TopN {
    private double[] array;
    private int length;
    private Integer[] indexArray;
    public int[] apply(double[] arr, int k) {
        array = arr;
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<arr.length; ++i)
            if (arr[i] != 0.0d)
                list.add(i);
        indexArray = list.toArray(new Integer[0]);
        length = indexArray.length;
        int[] res = new int[k];
        Arrays.fill(res, -1);

        buildheap();
        int n = Math.min(k, length);
        for (int i=0; i<n; ++i) {
            res[i] = indexArray[0];
            length --;
            indexArray[0] = indexArray[length];
            downheap(0);
        }

        return res;
    }

    private void buildheap() {
        for(int v=length/2-1; v >= 0; v--)
            downheap(v);
    }

    private void downheap(int v) {
        int w = 2*v+1;
        while(w < length) {
            if ((w+1 < length) && (array[indexArray[w+1]] > array[indexArray[w]]))
                w++;

            if (array[indexArray[v]] >= array[indexArray[w]])
                return;

            exchange(v,w);
            v = w;
            w = 2 * v + 1;
        }
    }

    private void exchange(int i, int j) {
        int t = indexArray[i];
        indexArray[i] = indexArray[j];
        indexArray[j] = t;
    }
}
