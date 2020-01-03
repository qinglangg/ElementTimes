package com.elementtimes.tutorial.interfaces;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/**
 * 可输入/输出管道接口
 * @author luqin2007
 */
public interface ITilePipelineIO extends ITilePipeline {

    /**
     * 测试某一位置是否可以连接
     * @param pos 位置
     * @return 是否可链接
     */
    boolean canConnectIO(BlockPos pos, EnumFacing direction);

    /**
     * 是否可进行输入输出操作（是否已经连接）
     * @param pos 位置
     * @return 是否可输入输出
     */
    boolean isConnectedIO(BlockPos pos, EnumFacing direction);

    /**
     * 是否可以进行输入输出操作（是否已有连接）
     * @return 是否可输入输出
     */
    boolean isConnectedIO();

    /**
     * 将管道与某个位置连接
     * @param pos 位置
     */
    void connectIO(BlockPos pos, EnumFacing direction);

    /**
     * 将管道与某个位置的链接断开
     * @param pos 位置
     */
    void disconnectIO(BlockPos pos, EnumFacing direction);
}
