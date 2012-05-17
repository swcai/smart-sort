package com.stanleycai.utils;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

/**
 * Helper class for String manipulation
 *
 * @since 1.0
 * @author Stanley Cai
 */
public class StringUtils {
    /** A String Join function, similar to Python String.join */
    @SuppressWarnings("rawtypes")
    public static String join(String joiner, Iterable iter) {
        Iterator iterator = iter.iterator();
        if (!iterator.hasNext())
            return "";

        StringBuilder res = new StringBuilder(iterator.next().toString());
        while (iterator.hasNext()) {
            res.append(joiner);
            res.append(iterator.next().toString());
        }
        return res.toString();
    }

    public static String join(String joiner, String[] array) {
        if (array == null || array.length == 0)
            return "";

        StringBuilder res = new StringBuilder(array[0]);
        for (int i = 1; i < array.length; ++i) {
            res.append(joiner);
            res.append(array[i]);
        }
        return res.toString();
    }
    
    /** A handy function to convert byte array into String */
    public static String convertToHex(byte[] input) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < input.length; ++i)
            res.append(String.format("%x", input[i]));
        return res.toString();
    }

    /**
     * A handy function to convert a String into a short SHA1 string
     *
     * use the function wisely to avoid potential conflicts
     *
     * @param orig
     * @return
     */
    public static String makeSHA1(String orig) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.update(orig.getBytes("iso-8859-1"), 0, orig.length());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
        return convertToHex(md.digest());
    }
}
