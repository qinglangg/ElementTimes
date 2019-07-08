package com.elementtimes.tutorial.other.pipeline;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;

/**
 * 管道类型
 * @author luqin2007
 */
public enum PLType implements IStringSerializable {
    /**
     * 物品传输管道
     */
    Item("item", 0b0000),
    /**
     * 物品输入管道
     */
    ItemIn("item_in", 0b0001),
    /**
     * 物品抽出管道
     */
    ItemOut("item_out", 0b0010),
    /**
     * 流体传输管道
     */
    Fluid("fluid", 0b0100),
    /**
     * 物品输入管道
     */
    FluidIn("fluid_in", 0b0101),
    /**
     * 流体抽出管道
     */
    FluidOut("fluid_out", 0b0110);

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
     */
    private int i;

    @Override
    public String getName() {
        return "pipeline_" + name;
    }

    public int toInt() {
        return i;
    }

    /**
     * @return 传输内容，item/fluid
     */
    public PLElementType type() {
        switch (((i >> 2) & 0b11)) {
            case 0b01: return PLElementType.Fluid;
            default: return PLElementType.Item;
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

    /**
     * 判断管道是否可以与方块连接
     * @param te 方块 TileEntity
     * @param facing 管道相对于方块的方向，用于检查 Capability
     * @return 是否可以链接
     */
    public boolean canConnect(TileEntity te, EnumFacing facing) {
        boolean canConnect = (in() || out()) && te != null;
        if (canConnect) {
            switch (type()) {
                case Fluid:
                    return te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
                case Item:
                    return te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
                default:
                    return false;
            }
        }
        return false;
    }

    public static PLType get(int code) {
        int rCode = code & 0b0111;
        switch (rCode) {
            case 0b0000: return Item;
            case 0b0001: return ItemIn;
            case 0b0010: return ItemOut;
            case 0b0100: return Fluid;
            case 0b0101: return FluidIn;
            case 0b0110: return FluidOut;
            default: return null;
        }
    }

    /**
     * 管道传输内容
     * @author luiqn2007
     */
    public enum PLElementType {
        /**
         * 管道传输内容
         */
        Fluid(0), Item(1);

        PLElementType(int code) {
            mCode = code;
        }

        private int mCode;

        public int toInt() {
            return mCode;
        }

        public static PLElementType fromInt(int code) {
            switch (code) {
                case 0: return Fluid;
                case 1: return Item;
                default: return null;
            }
        }
    }
}
