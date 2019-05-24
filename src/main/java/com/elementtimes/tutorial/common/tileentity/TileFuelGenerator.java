package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.block.machine.FuelGenerator;
import com.elementtimes.tutorial.common.tileentity.base.TileGenerator;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

/**
 * 发电机的TileEntity
 *
 * @author lq2007 create in 2019/5/22
 */
public class TileFuelGenerator extends TileGenerator {

    public TileFuelGenerator() {
        super(ElementtimesConfig.general.generaterMaxExtract, ElementtimesConfig.general.generaterMaxReceive);
    }

    @Override
    protected int getRFFromItem(ItemStack item) {
        int burnTime = TileEntityFurnace.getItemBurnTime(item);
        return burnTime * 10;
    }

    @Override
    public boolean onUpdate() {
        IBlockState iBlockState = world.getBlockState(pos);
        if (iBlockState.getValue(FuelGenerator.BURNING) == isClosed()) {
            IBlockState iBlockState1 = iBlockState.withProperty(FuelGenerator.BURNING, !isClosed());
            world.setBlockState(pos, iBlockState1);
            return false;
        }
        return true;
    }
}
