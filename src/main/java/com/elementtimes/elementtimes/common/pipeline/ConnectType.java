package com.elementtimes.elementtimes.common.pipeline;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;



public enum ConnectType implements IStringSerializable, IConnectType {

    /**
     * 普通管道连接
     */
    CONNECTED("connect", true),

    /**
     * 无连接
     */
    BAN("ban", false),

    /**
     * 无连接
     */
    NONE("none", false),

    /**
     * 输入输出端口
     */
    INPUT("input", true),

    /**
     * 输入输出端口
     */
    OUTPUT("output", true);

    private final String name;
    private final boolean connected;

    ConnectType(String name, boolean connected) {
        this.name = name;
        this.connected = connected;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isConnected() {
        return connected;
    }
}
