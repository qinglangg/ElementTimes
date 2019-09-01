package com.elementtimes.tutorial.other;

import java.util.Comparator;

/**
 * @author luqin2007
 */
public enum SideHandlerType {

    /**
     * 输入槽
     */
    INPUT(0),

    /**
     * 输出槽
     */
    OUTPUT(1),

    /**
     * 暂未使用
     * 同时可以输入/输出
     */
    IN_OUT(2),

    /**
     * 不可输入输出
     * 但可以读出存储物品
     * 仅电量存储实现
     */
    READONLY(3),

    /**
     * 不可访问 IO
     */
    NONE(4);

    public final int index;

    SideHandlerType(int index) {
        this.index = index;
    }

    public static Comparator<SideHandlerType> comparator() {
        return Comparator.comparingInt(type -> type.index);
    }
}
