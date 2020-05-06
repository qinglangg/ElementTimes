package com.elementtimes.tutorial.common.block.stand.module;

import com.elementtimes.tutorial.common.block.stand.SupportStand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

import static com.elementtimes.tutorial.common.tileentity.stand.TileSupportStand.BIND_KEY;

/**
 * 铁架台配件
 * @author luqin2007
 */
public interface ISupportStandModule extends ICapabilityProvider, INBTSerializable<NBTTagCompound> {

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
     * 在 Tesr 中渲染
     */
    @SideOnly(Side.CLIENT)
    void onRender();

    /**
     * 右键响应
     * @param playerIn 交互玩家
     * @param hand 手
     * @param facing 方向
     * @param hitX X
     * @param hitY Y
     * @param hitZ Z
     * @return 返回 true 时，中断之后的模块调用
     */
    default boolean onBlockActivated(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                     float hitX, float hitY, float hitZ) { return false; }

    /**
     * randomDisplayTick 方法中调用
     * 客户端方法，用于渲染等
     * @see SupportStand#randomDisplayTick(IBlockState, World, BlockPos, Random)
     * @param stateIn state？
     * @param worldIn 所在世界
     * @param pos 所处位置
     * @param rand 随即对象
     */
    @SideOnly(Side.CLIENT)
    default void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {}

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
    @SideOnly(Side.CLIENT)
    default boolean onTickClient(World world, BlockPos pos, NBTTagCompound data) { return false; }

    /**
     * 同步更新数据
     * 若需要将数据同步到客户端，返回需要同步的值，否则返回 null
     * 若返回非 null，会触发方块更新
     * @return 同步数据
     */
    @Nullable
    default NBTTagCompound getUpdateData(World world, BlockPos pos) { return null; }

    @Override
    default boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return false;
    }

    @Nullable
    @Override
    default <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return null;
    }

    @Override
    default NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString(BIND_KEY, getKey());
        return nbt;
    }
}
