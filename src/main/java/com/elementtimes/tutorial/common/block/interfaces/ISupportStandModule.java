package com.elementtimes.tutorial.common.block.interfaces;

import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileTESR;
import com.elementtimes.tutorial.common.block.SupportStand;
import com.elementtimes.tutorial.common.tileentity.interfaces.ISSMTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

/**
 * 酒精灯配件
 * @author luqin2007
 */
public interface ISupportStandModule {

    String BIND_SSM = "_nbt_ssm_";

    /**
     * 酒精灯配件对应 key
     * @return key
     */
    @Nonnull
    String getKey();

    /**
     * 创建酒精灯渲染对象
     * @return 渲染
     */
    @Nullable
    ITileTESR.RenderObject createRender();

    /**
     * 该配件是否已被添加到铁架台中
     * @param worldIn 所在世界
     * @param pos 所处位置
     * @return 已经添加
     */
    default boolean isAdded(World worldIn, BlockPos pos) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof ISSMTileEntity && te instanceof ITileTESR) {
            return ((ITileTESR) te).isRender(getKey());
        }
        return false;
    }

    /**
     * 向铁架台添加组件
     * @param worldIn 所在世界
     * @param pos 所处位置
     * @param state 没用的东西
     * @param playerIn 玩家
     * @param hand 手
     * @param facing 朝向
     * @param hitX X 位置
     * @param hitY Y 位置
     * @param hitZ Z 位置
     */
    default void addModule(World worldIn, BlockPos pos, IBlockState state,
                      EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                      float hitX, float hitY, float hitZ) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof ISSMTileEntity) {
            ISSMTileEntity tss = (ISSMTileEntity) te;
            tss.putModule(this);
            // nbt
            ItemStack heldItem = playerIn.getHeldItem(hand);
            NBTTagCompound nbt = heldItem.getSubCompound(BIND_SSM);
            Object o = tss.getModuleObject(getKey());
            if (o instanceof INBTSerializable && nbt != null) {
                ((INBTSerializable) o).deserializeNBT(nbt.getTag(BIND_SSM));
            }
            heldItem.shrink(1);
            // active
            if (isActive(worldIn, pos)) {
                tss.setActiveModule(getKey());
            }
        }
    }

    /**
     * 从铁架台中移除并掉落物品
     * @param worldIn 所在世界
     * @param pos 所处位置
     */
    default void dropModule(World worldIn, BlockPos pos) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof ISSMTileEntity) {
            ItemStack stack = getModelItem();
            Object o = ((ISSMTileEntity) te).getModuleObject(getKey());
            if (o instanceof INBTSerializable) {
                stack.getOrCreateSubCompound(BIND_SSM).setTag(BIND_SSM, ((INBTSerializable) o).serializeNBT());
            }
            ((ISSMTileEntity) te).removeModule(getKey());
            Block.spawnAsEntity(worldIn, pos, stack);
        }
    }

    /**
     * 右键铁架台的行为
     * @see SupportStand#onBlockActivated(World, BlockPos, IBlockState, EntityPlayer, EnumHand, EnumFacing, float, float, float)
     * @param worldIn 所在世界
     * @param pos 所处位置
     * @param state 没用的东西
     * @param playerIn 玩家
     * @param hand 手
     * @param facing 朝向
     * @param hitX X 位置
     * @param hitY Y 位置
     * @param hitZ Z 位置
     */
    boolean onActivated(World worldIn, BlockPos pos, IBlockState state,
                        EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                        float hitX, float hitY, float hitZ);

    /**
     * 在 SupportStand 的 randomDisplayTick 方法中调用
     * 客户端方法，用于渲染等
     * @see SupportStand#randomDisplayTick(IBlockState, World, BlockPos, Random)
     * @param stateIn static？
     * @param worldIn 所在世界
     * @param pos 所处位置
     * @param rand 随即对象
     */
    @SideOnly(Side.CLIENT)
    default void randomDisplayTickClient(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {}

    /**
     * 获取酒精灯组件对应物品，用于向铁架台添加渲染物品使用
     * @return 对应物品
     */
    ItemStack getModelItem();

    /**
     * 获取对应对象创建器
     * @return 对象构造
     */
    @Nonnull
    Supplier getActiveObject(World worldIn, BlockPos pos);

    /**
     * 该类 Module 是否设置为活动
     * 活动：将 TileSupportStand 的 GUI，MachineRecipe 相关，Capability 与 ActiveObject 对象关联
     * @return 是否活动
     */
    boolean isActive(World worldIn, BlockPos pos);
}
