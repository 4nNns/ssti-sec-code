package com.ha0l.sstiseccode.util;

/**
 * @author ha0
 * @date 2022-0920
 */
public class HashUtil {

    public static int hash(int seed, boolean value) {
        return (seed * 11) + (value ? 1 : 0);
    }

    public static int hash(int seed, int value) {
        return (seed * 11) + value;
    }

    public static int hash(int seed, long value) {
        return (int)((seed * 11) + value);
    }

    public static int hash(int seed, Object value) {
        return (seed * 11) + ((value == null) ? 0 : value.hashCode());
    }

}
