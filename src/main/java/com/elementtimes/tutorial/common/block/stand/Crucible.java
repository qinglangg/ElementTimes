package com.elementtimes.tutorial.common.block.stand;

import com.elementtimes.tutorial.common.block.stand.module.ModuleCrucible;
import com.elementtimes.tutorial.common.tileentity.stand.TileCrucible;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 坩埚
 * @author luqin2007
 */
public class Crucible extends BaseModuleBlock<ModuleCrucible, TileCrucible> {

    public Crucible() {
        super(ModuleCrucible.KEY, new AxisAlignedBB(0, 0, 0, 1, 1, 1));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileCrucible();
    }
}