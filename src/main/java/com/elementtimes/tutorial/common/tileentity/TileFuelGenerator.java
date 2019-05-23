package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.tileentity.base.TileGenerator;
import com.elementtimes.tutorial.config.ElementtimesConfig;
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
}
