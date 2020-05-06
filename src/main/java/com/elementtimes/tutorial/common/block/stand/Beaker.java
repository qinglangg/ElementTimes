package com.elementtimes.tutorial.common.block.stand;

import com.elementtimes.tutorial.common.block.stand.module.ModuleBeaker;
import com.elementtimes.tutorial.common.tileentity.stand.TileBeaker;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 烧杯
 * @author luqin2007
 */
public class Beaker extends BaseModuleBlock<ModuleBeaker, TileBeaker> {

    public Beaker() {
        super(ModuleBeaker.KEY, new AxisAlignedBB(0.3125, 0, 0.3125, 0.6875, 0.4375, 0.6875));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileBeaker();
    }
}
