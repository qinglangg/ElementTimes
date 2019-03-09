package com.elementtimes.tutorial.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * 需要可以被热力扳手拆卸的方块时继承此类
 *
 * @author KSGFK create in 2019/2/17
 */
@Deprecated
public abstract class BlockCanDismantle extends BlockTileBase {

    protected BlockCanDismantle(Material materialIn) {
        super(materialIn,0);
    }
}
/*
public abstract class BlockCanDismantle extends BlockTileBase implements IDismantleable {
    protected BlockCanDismantle(Material materialIn) {
        super(materialIn);
    }

    @Override
    public ArrayList<ItemStack> dismantleBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, boolean returnDrops) {
        return null;
    }

    ArrayList<ItemStack> dismantleDelegate(NBTTagCompound nbt, World world, BlockPos pos, EntityPlayer player, boolean returnDrops, boolean simulate) {
        IBlockState state = world.getBlockState(pos);
        int meta = state.getBlock().getMetaFromState(state);
        ArrayList<ItemStack> ret = new ArrayList<>();

        if (state.getBlock() != this) {
            return ret;
        }
        ItemStack dropBlock = new ItemStack(this, 1, meta);

        if (nbt != null) {
            dropBlock.setTagCompound(nbt);
        }
        if (!simulate) {
            world.setBlockToAir(pos);

            if (!returnDrops) {
                float f = 0.3F;
                double x2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
                double y2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
                double z2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
                EntityItem dropEntity = new EntityItem(world, pos.getX() + x2, pos.getY() + y2, pos.getZ() + z2, dropBlock);
                dropEntity.setPickupDelay(10);
                world.spawnEntity(dropEntity);

                if (player != null) {
                    CoreUtils.dismantleLog(player.getName(), state.getBlock(), meta, pos);
                }
            }
        }
        ret.add(dropBlock);
        return ret;
    }

    @Override
    public boolean canDismantle(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return true;
    }
}
*/
