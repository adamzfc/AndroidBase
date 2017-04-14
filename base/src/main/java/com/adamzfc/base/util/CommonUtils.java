package com.adamzfc.base.util;

import android.support.annotation.NonNull;

/**
 * common utils
 * Created by adamzfc on 4/14/17.
 */

final public class CommonUtils {
    private CommonUtils() {
    }

    /**
     * check null
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NumberFormatException();
        } else {
            return reference;
        }
    }

    /**
     * check null
     */
    public static <T> T checkNotNull(T reference, @NonNull String message) {
        if (reference == null) {
            throw new NullPointerException(message);
        } else {
            return reference;
        }
    }

    /**
     * check equals
     */
    public static boolean equals(Object a, Object b) {
        return (a == null) ? (b == null) : a.equals(b);
    }
}
