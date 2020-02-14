package com.elementtimes.tutorial.common.pipeline;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/**
 * 可输出管道接口
 * @author luqin2007
 */
public interface IPipelineOutput extends ITilePipeline {

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

    /**
     * 将物品输出到某个方向
     * @param element 物品
     * @return 未输出的物品
     */
    BaseElement output(BaseElement element);

    /**
     * 判断物品是否可以输出
     * @param element 传输乳品
     * @return 是否可输出
     */
    boolean canOutput(BaseElement element);
}
