package com.elementtimes.tutorial.other.pipeline;

/**
 * 管道连接类型
 * @author luqin2007
 */
public enum PLConnType {

    /**
     * 连接输入
     */
    IN,
    /**
     * 连接输出
     */
    OUT,
    /**
     * 输入 && 输出
     */
    INOUT,
    /**
     * 连接管道
     */
    PIPELINE,
    /**
     * 无法连接
     */
    NULL
}
