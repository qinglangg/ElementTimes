package com.elementtimes.tutorial.common.block.interfaces;

import com.elementtimes.elementcore.api.template.tileentity.interfaces.IGuiProvider;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileTESR;
import com.elementtimes.tutorial.common.block.SupportStand;
import com.elementtimes.tutorial.common.tileentity.TileSupportStand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

/**
 * 酒精灯配件
 * @author luqin2007
 */
public interface ISupportStandModule {

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
        if (te instanceof TileSupportStand) {
            return ((TileSupportStand) te).isRender(getKey());
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
    void addModule(World worldIn, BlockPos pos, IBlockState state,
                      EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                      float hitX, float hitY, float hitZ);

    /**
     * 从铁架台中移除并掉落物品
     * @param worldIn 所在世界
     * @param pos 所处位置
     */
    void dropModule(World worldIn, BlockPos pos);

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
     * GUI 相关，提供一个 IGuiProvider
     * 返回 null 表示没有 GUI
     * @see IGuiProvider
     * @param worldIn 所在世界
     * @param pos 所处位置
     * @return GUI 对象
     */
    @Nullable
    default IGuiProvider getGuiProvider(World worldIn, BlockPos pos) {
        return null;
    }

    /**
     * 获取酒精灯组件对应物品，用于向铁架台添加渲染物品使用
     * @return 对应物品
     */
    ItemStack getModelItem();
}
