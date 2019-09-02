package com.elementtimes.tutorial;

import javax.annotation.Nullable;

public class AAA {

    /**
     * Cohen-Sutherland 算法描述
     * Java 版，假定 Window 为矩形
     * @param windowXMax 矩形右 X 坐标
     * @param windowXMin 矩形左 X 坐标
     * @param windowYMax 矩形上 Y 坐标
     * @param windowYMin 矩形下 Y 坐标
     * @param lineX1 线段端点1 X 坐标
     * @param lineY1 线段端点1 Y 坐标
     * @param lineX2 线段端点2 X 坐标
     * @param lineY2 线段端点2 Y 坐标
     * @param limit 查找精度
     * @return 若窗口内存在线段或线段的一部分，返回剪取后的两点坐标，否则返回 null
     */
    @Nullable
    public int[][] cohenSutherland(int windowXMax, int windowXMin, int windowYMax, int windowYMin,
                                   int lineX1, int lineY1, int lineX2, int lineY2, int limit) {
        int code1 = code(windowXMax, windowXMin, windowYMax, windowYMin, lineX1, lineY1);
        int code2 = code(windowXMax, windowXMin, windowYMax, windowYMin, lineX1, lineY1);
        while (true) {
            if (code1 == code2 && code1 == 0b0000) {
                // code1=code2=0b0000 -> 线段在窗口内
                return new int[][] {{lineX1, lineY1}, {lineX2, lineY2}};
            } else if (Math.abs(lineX1 - lineX2) <= limit && Math.abs(lineY1 - lineY2) <= limit) {
                // 范围缩减到剪取精度，丢弃剩余未裁剪部分
                return null;
            } else if ((code1 & code2) != 0b0000) {
                // code1&code2=0b0000 -> 某条窗口边在同外侧，线段在窗口外
                return null;
            } else {
                // 线段可能部分在窗口内，通过取中点查找
                if (code1 == 0b0000) {
                    int cX = (lineX1 + lineX2) >> 1;
                    int cY = (lineY1 + lineY2) >> 1;
                    int[][] part1 = cohenSutherland(windowXMax, windowXMin, windowYMax, windowYMin,
                            lineX1, lineY1, cX, cY, limit);
                    int[][] part2 = cohenSutherland(windowXMax, windowXMin, windowYMax, windowYMin,
                            cX, cY, lineX2, lineY2, limit);
                    if (part1 == null && part2 == null) {
                        return null;
                    }
                    if (part1 == null) {
                        return part2;
                    } else if (part2 == null) {
                        return part1;
                    } else {
                        // 截取了两段 合并
                        part1[1] = part2[1];
                        return part1;
                    }
                }
            }
        }
    }

    private int code(int windowXMax, int windowXMin, int windowYMax, int windowYMin, int x, int y) {
        int code = 0b0000;
        if (x >= windowXMax) {
            code |= 0b0010;
        }
        if (x <= windowXMin) {
            code |= 0b0001;
        }
        if (y >= windowYMax) {
            code |= 0b1000;
        }
        if (y <= windowYMin) {
            code |= 0b0100;
        }
        return code;
    }
}
