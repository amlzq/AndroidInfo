package com.amlzq.androidinfo;

import java.util.Arrays;

/**
 * Created by amlzq on 2021/7/7.
 */

public class Util {

    public static String toString(int[] a) {
        if (a == null) return "null";
        return Arrays.toString(a);
    }

    public static String toString(String[] a) {
        if (a == null) return "null";
        return Arrays.toString(a);
    }

    public static int getLength(Object[] a) {
        if (a == null) return 0;
        return a.length;
    }

}
