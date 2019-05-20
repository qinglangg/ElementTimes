package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.tileentity.TileCompressor;
import com.elementtimes.tutorial.util.IDismantleBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * 压缩机
 * @author lq2007 create in 2019/5/19
 */
public class Compressor extends BlockTileBase implements IDismantleBlock {
    public Compressor() {
        super(Material.IRON, 2);
        setRegistryName("compressor");
        setUnlocalizedName("compressor");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileCompressor();
    }
}
