package com.elementtimes.tutorial.common.block.stand;

import com.elementtimes.tutorial.common.block.stand.module.ModuleEvaporatingDish;
import com.elementtimes.tutorial.common.tileentity.stand.TileEvaporatingDish;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 蒸发皿
 * @author luqin2007
 */
public class EvaporatingDish extends BaseModuleBlock<ModuleEvaporatingDish, TileEvaporatingDish> {

    public EvaporatingDish() {
        super(ModuleEvaporatingDish.KEY, new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.25D, 0.75D));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileEvaporatingDish();
    }
}