package com.elementtimes.tutorial.other.pipeline;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

/**
 * 管道类型
 * @author luqin2007
 */
public enum PLType implements IStringSerializable {
    /**
     * 物品传输管道
     */
    Item("item", 0b000),
    /**
     * 物品输入管道
     */
    ItemIn("item_in", 0b001),
    /**
     * 物品抽出管道
     */
    ItemOut("item_out", 0b010),
    /**
     * 物品输入-输出管道
     */
    ItemInOut("item_in_out", 0b011),
    /**
     * 流体传输管道
     */
    Fluid("fluid", 0b100),
    /**
     * 物品输入管道
     */
    FluidIn("fluid_in", 0b101),
    /**
     * 流体抽出管道
     */
    FluidOut("fluid_out", 0b110),
    /**
     * 流体输入-抽出管道
     */
    FluidInOut("fluid_in_out", 0b111);

    PLType(String name, int i) {
        this.name = name;
        this.i = i;
    }

    private String name;

    /**
     * 可转化为 0bXXXX 的形式
     *           || 传输类型
     *             | 是否可输出
     *              | 是否可输入
     * @return 转化为 int 形式
     */
    private int i;

    @Override
    public String getName() {
        return name;
    }

    public int toInt() {
        return i;
    }

    /**
     * @return 传输内容，item/fluid
     */
    public PLElementType type() {
        switch (((i >> 2) & 0b11)) {
            case 0b00: return PLElementType.Item;
            case 0b01: return PLElementType.Fluid;
            default: return null;
        }
    }

    /**
     * @return 是否为输入管道
     */
    public boolean in() {
        return (i & 0b0001) == 0b0001;
    }

    /**
     * @return 是否为输出管道
     */
    public boolean out() {
        return (i & 0b0010) == 0b0010;
    }

    @Nonnull
    public static PLType get(int code) {
        int rCode = code & 0b0111;
        switch (rCode) {
            case 0b0000: return Item;
            case 0b0001: return ItemIn;
            case 0b0010: return ItemOut;
            case 0b0011: return ItemInOut;
            case 0b0100: return Fluid;
            case 0b0101: return FluidIn;
            case 0b0110: return FluidOut;
            case 0b0111: return FluidInOut;
            default: return null;
        }
    }
}
