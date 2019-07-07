package com.elementtimes.tutorial.util;

/**
 * 二进制运算，主要用于 meta 计算
 * @author luqin2007
 */
public class MathUtil {

    public static boolean fromByte(int code, int position) {
        int b = 0b1 << position;
        return (code & b) == b;
    }

    public static int setByte(int code, int position, boolean b) {
        if (fromByte(code, position) == b) {
            return code;
        }

        if (b) {
            return code | (0b1 << position);
        }
		return code & (~(0b1 << position));
    }
}
