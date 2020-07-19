package com.elementtimes.elementtimes.common.block.stand.module;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

import static com.elementtimes.elementtimes.common.block.stand.te.TileSupportStand.BIND_KEY;

/**
 * 铁架台配件

 */
public interface ISupportStandModule extends ICapabilityProvider, INBTSerializable<CompoundNBT> {

    /**
     * 铁架台配件对应 key
     * @return key
     */
    @Nonnull
    String getKey();

    /**
     * 获取组件对应物品
     * @return 对应物品
     */
    ItemStack getModuleItem();

    /**
     * 在 Ter 中渲染
     */
    @OnlyIn(Dist.CLIENT)
    void onRender();

    /**
     * 右键响应
     * @see net.minecraft.block.Block#onBlockActivated(BlockState, World, BlockPos, PlayerEntity, Hand, BlockRayTraceResult)
     * @param playerIn 交互玩家
     * @param hand 手
     * @param trace 包含方向、点击位置等
     * @return 返回 true 时，中断之后的模块调用
     */
    default boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockRayTraceResult trace) { return false; }

    /**
     * tick 方法中，作为 randomTick 调用
     * @see net.minecraft.block.Block#tick(BlockState, World, BlockPos, Random)
     * @param stateIn state？
     * @param worldIn 所在世界
     * @param pos 所处位置
     * @param rand 随即对象
     */
    default void randomTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {}

    /**
     * animateTick 中调用
     *
     * @param stateIn
     * @param worldIn
     * @param pos
     * @param rand
     */
    default void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) { }

    /**
     * 服务端每 tick 调用。返回 true 时触发 markDirty
     */
    default boolean onTick(World world, BlockPos pos) { return false; }

    /**
     * 获取方块亮度
     * @param light 原本亮度
     * @return 新亮度
     */
    default int getLight(int light) {
        return light;
    }

    /**
     * 客户端每 tick 调用
     * 在该方法中处理 getUpdateData 同步的数据
     * 若返回 true，则该数据处理后不会清除，下次仍会传入，否则下次若没有输入传入，data 为 null
     * @param data 同步数据
     * @return 是否保留
     */
    @OnlyIn(Dist.CLIENT)
    default boolean onTickClient(World world, BlockPos pos, CompoundNBT data) { return false; }

    /**
     * 同步更新数据
     * 若需要将数据同步到客户端，返回需要同步的值，否则返回 null
     * 若返回非 null，会触发方块更新
     * @return 同步数据
     */
    @Nullable
    default CompoundNBT getUpdateData(World world, BlockPos pos) { return null; }

    @Nonnull
    @Override
    default <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return LazyOptional.empty();
    }

    @Override
    default CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString(BIND_KEY, getKey());
        return nbt;
    }
}
