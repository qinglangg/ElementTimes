package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;
import com.elementtimes.tutorial.util.IDismantleBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Spanner extends Item {
    public Spanner() {
        setRegistryName("spanner");
        setUnlocalizedName("spanner");
        setCreativeTab(Elementtimestab.tabBlocks);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile != null) {
                Block b = worldIn.getBlockState(pos).getBlock();
                if (b instanceof IDismantleBlock) {
                    IDismantleBlock d = (IDismantleBlock) b;
                    ItemStack drop = d.dismantleBlock(worldIn, pos, worldIn.getBlockState(pos), true);
                    InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), drop);

                    worldIn.setBlockToAir(pos);
                    worldIn.removeTileEntity(pos);
                }
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }
}
