package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.block.machine.FuelGenerator;
import com.elementtimes.tutorial.common.tileentity.base.TileGenerator;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;

/**
 * 发电机的TileEntity
 * @author lq2007 create in 2019/5/22
 */
public class TileFuelGenerator extends TileGenerator {

    public TileFuelGenerator() {
        super(ElementtimesConfig.fuelGeneral.generaterMaxEnergy);
    }

    @Override
    protected int getRFFromItem(ItemStack item) {
        int burnTime = TileEntityFurnace.getItemBurnTime(item);
        return burnTime * 10;
    }

    @Override
    protected int getMaxGenerateRFPerTick() {
        return ElementtimesConfig.fuelGeneral.generaterMaxReceive;
    }

    @Override
    protected int getMaxExtractRFPerTick(EnumFacing facing) {
        return ElementtimesConfig.fuelGeneral.generaterMaxExtract;
    }

    @Override
    protected IBlockState updateState(IBlockState old) {
        if (old.getValue(FuelGenerator.BURNING) == isClosed()) {
            return old.withProperty(FuelGenerator.BURNING, !isClosed());
        }
        return super.updateState(old);
    }
}
