package com.elementtimes.tutorial.common.pipeline;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

/**
 * 管道接口
 * @author luqin2007
 */
public interface ITilePipeline {

    BlockPos getPos();

    // ===========================================================================
    // 世界交互相关

    /**
     * 当某一方向方块发生变化时调用
     * @param neighbor 位置
     */
    void onNeighborChanged(BlockPos neighbor);

    /**
     * 管道被移除时调用
     */
    void onRemoved(IBlockState state);

    /**
     * 管道放置时调用
     * @param stack 物品栈
     * @param placer 放置者
     */
    void onPlace(ItemStack stack, EntityLivingBase placer);

    /**
     * 设置方块 ActualState
     * @param state IBlockState
     * @param pos 方块位置
     * @return ActualState
     */
    @Nonnull
    IBlockState onBindActualState(@Nonnull IBlockState state, BlockPos pos);

    // ===========================================================================
    // 管道行为相关

    /**
     * 某一方向是否可以连接
     * @param pos 位置
     * @param direction 方向
     * @return 可连接
     */
    boolean canConnectTo(BlockPos pos, EnumFacing direction);

    /**
     * 某一方向是否可以连接
     * 该方法主要是用于需要验证相邻管道是否可以连接，防止双方互相调用
     * @param pos 另一位置
     * @param direction 方向
     * @return 可连接
     */
    boolean canConnectBy(BlockPos pos, EnumFacing direction);

    /**
     * 尝试连接某一方向
     * @param pos 位置
     * @param direction 方向
     * @return 是否成功连接
     */
    boolean connect(BlockPos pos, EnumFacing direction);

    /**
     * 从某一位置断开连接
     * @param pos 位置
     * @param direction 方向
     */
    void disconnect(BlockPos pos, EnumFacing direction);

    /**
     * 某位置是否已连接
     * @param pos 位置
     * @param direction 方向
     * @return 连接
     */
    boolean isConnected(BlockPos pos, EnumFacing direction);

    /**
     * 某位置是否被阻断
     * @param pos 位置
     * @param direction 方向
     * @return 是否被阻断
     */
    boolean isInterrupted(BlockPos pos, EnumFacing direction);

    /**
     * 阻断某位置
     * @param direction 方向
     * @param pos 位置
     */
    void setInterrupt(BlockPos pos, EnumFacing direction);

    /**
     * 是否可向某方向发送物品
     * @param pos 位置
     * @param element 物品
     * @param direction 方向
     * @return 是否可发送
     */
    boolean canPost(BlockPos pos, EnumFacing direction, BaseElement element);

    /**
     * 是否可接收某方向的物品
     * @param pos 位置
     * @param element 物品
     * @return 某方向物品
     */
    boolean canReceive(BlockPos pos, BaseElement element);

    /**
     * 向某方向发的管道送物品
     * @param pos 发送的位置
     * @param element 物品
     * @param direction 方向
     * @return 如果不能完全发送，则返回未能发送的物品
     */
    BaseElement post(BlockPos pos, EnumFacing direction, BaseElement element);

    /**
     * 从某方向接收物品
     * @param pos 位置
     * @param element 物品
     * @return 如果不能完全接收，则返回未能接受的物品
     */
    BaseElement onReceive(BlockPos pos, BaseElement element);

    /**
     * 移除某物品
     * @param element 被移除物品
     */
    void removeElement(BaseElement element);

    /**
     * 获取某物品在当前管道中停留的时间
     * @param element 传输物品
     * @return 停留时间(tick)
     */
    int getKeepTime(BaseElement element);

    /**
     * 向管道中添加物品
     * @param element 物品
     */
    void addElement(BaseElement element);

    /**
     * 从管道中掉落物品到世界
     * @param element 物品
     */
    void dropElement(BaseElement element);
}
