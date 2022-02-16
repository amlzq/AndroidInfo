package com.amlzq.androidinfo;

import android.os.Bundle;

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

    public static String toString(Bundle bundle) {
        if (bundle == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder("Bundle[");
        for (String key : bundle.keySet()) {
            builder.append(" ").append(key).append("=").append(bundle.get(key)).append(";");
        }
        builder.append("]");
        return builder.toString();
    }
}
