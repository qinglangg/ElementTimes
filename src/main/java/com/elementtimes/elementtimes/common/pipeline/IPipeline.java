package com.elementtimes.elementtimes.common.pipeline;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;



public interface IPipeline {

    /**
     * 当附近的方块 BlockState 发生变化时调用
     * @param neighbor 变化位置
     */
    void onNeighborChanged(BlockPos neighbor);

    /**
     * 当管道方块被移除时调用
     * @param state 管道
     */
    void onRemoved(BlockState state);

    /**
     * 当管道被放置时调用
     * @param stack 管道物品
     * @param placer 放置者
     */
    void onPlace(ItemStack stack, LivingEntity placer);

    /**
     * 获取所有可以连接的管道位置
     * @return 可连接管道
     */
    List<BlockPos> allReachablePipelines();

    /**
     * 获取所有可以输出的位置
     * @return 可连接输出
     */
    List<BlockPos> allReachableOutputs();

    /**
     * 获取所有可以输入的位置
     * @return 可连接输入
     */
    List<BlockPos> allReachableInputs();

    /**
     * 设置管道与某一位置的连接状态
     * @param target 目标位置
     * @param types 可能连接的类型
     */
    void setConnectState(BlockPos target, IConnectType... types);

    /**
     * 测试管道是否可以从某个位置获取物品
     * @param from 位置
     * @return 是否可获取
     */
    boolean canInputElementFrom(BlockPos from);

    /**
     * 测试管道是否可以向某个位置输出物品
     * @param target 位置
     * @return 是否可输出
     */
    boolean canOutputElementTo(BlockPos target);

    /**
     * 当管道的另一端试图改变与该管道的连接状态时调用
     * 此方法中不应调用 setConnectState，否则会导致递归调用
     * @param from 另一端
     * @param type 更改后的状态
     * @param simulate 模拟
     * @return 若返回 false，则表示无法设置对应的状态
     */
    boolean connectedBy(IPipeline from, IConnectType type, boolean simulate);

    /**
     * 获取管道与某一位置的连接状态
     * 此处返回一个 IConnectType 接口对象。由于可能存在自定义的管道连接类型，故不直接使用枚举
     * @param target 目标
     * @return 连接状态
     */
    IConnectType getConnectState(BlockPos target);

    /**
     * 向某个方向输出物品
     * 当管道支持自动输出时应当重写该方法
     * @param target 目标
     * @param element 物品
     * @param simulate 模拟
     * @return 无法输出的剩余物品
     */
    <T> BaseElement<T> outputElement(BlockPos target, BaseElement<T> element, boolean simulate);

    /**
     * 向某一个方向发送物品
     * @param target 目标
     * @param element 物品
     * @param simulate 测试
     * @return 对方未能接受的物品
     */
    <T> BaseElement<T> sendElementTo(IPipeline target, BaseElement<T> element, boolean simulate);

    /**
     * 从周围管道中接收物品
     * @param from 物品来源
     * @param element 物品
     * @param simulate 测试
     * @return 未能接收的物品
     */
    <T> BaseElement<T> receiveElement(IPipeline from, BaseElement<T> element, boolean simulate);

    /**
     * 从所有已连接方块抽取物品，用于从外界获取元素到管道网络
     * 当一个管道可以从周围抽取物品时，重写该方法
     * @see #allReachableInputs()
     * @param simulate 是否模拟抽取
     */
    void extractElements(boolean simulate);

    /**
     * 从管道中移除物品
     * @param element 物品
     */
    void removeElement(BaseElement<?> element);

    /**
     * 从该管道发送物品
     * @param element 被发送物品
     * @param container 存储该物品的容器，将作为保存的路径起点
     * @param simulate 模拟发送
     * @return 未发送物品
     */
    <T> BaseElement<T> sendElement(BaseElement<T> element, BlockPos container, boolean simulate);

    /**
     * 手动向管道网络中添加物品
     * @param element 物品
     */
    void addElement(BaseElement<?> element);

    /**
     * 将管道中的物品释放到世界中
     * @param element 物品
     */
    void dropElement(BaseElement<?> element);

    /**
     * 获取一个物品在管道中保持的时间（tick）
     * @param element 物品
     * @return 时间
     */
    int getKeepTime(BaseElement<?> element);

    /**
     * 当无法按照原定路径继续传递时，使用此方法处理
     * @param element 物品
     */
    void backElement(BaseElement<?> element);

    /**
     * 获取管道所在位置
     * @return 管道位置
     */
    BlockPos getPipelinePos();

    /**
     * 获取管道所在世界
     * @return 所在世界
     */
    World getPipelineWorld();
}
