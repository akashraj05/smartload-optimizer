package com.smartload.util;

import java.util.ArrayList;
import java.util.List;

public class BitmaskUtils {

    public static List<Integer> getSetBitIndices(int mask) {
        List<Integer> indices = new ArrayList<>();
        int index = 0;

        while (mask > 0) {
            if ((mask & 1) == 1) {
                indices.add(index);
            }
            mask >>= 1;
            index++;
        }
        return indices;
    }

    public static int getLowestSetBit(int mask) {
        return mask & -mask;
    }

    public static int getBitIndex(int bit) {
        return Integer.numberOfTrailingZeros(bit);
    }
}