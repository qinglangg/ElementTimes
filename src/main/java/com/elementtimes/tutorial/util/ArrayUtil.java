package com.elementtimes.tutorial.util;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class ArrayUtil {

    public static int[] create(int size, int fillValue) {
        int[] arr = new int[size];
        Arrays.fill(arr, fillValue);
        return arr;
    }
}
