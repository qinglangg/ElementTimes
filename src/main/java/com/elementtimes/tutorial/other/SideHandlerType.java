package com.elementtimes.tutorial.other;

/**
 * @author luqin2007
 */
public enum SideHandlerType {

    /**
     * 输入槽
     */
    INPUT,

    /**
     * 输出槽
     */
    OUTPUT,

    /**
     * 不可访问 IO
     */
    NONE,

    /**
     * 暂未使用
     * 同时可以输入/输出
     */
    IN_OUT,

    /**
     * 不可输入输出
     * 但可以读出存储物品
     * 仅电量存储实现
     */
    READONLY
}
