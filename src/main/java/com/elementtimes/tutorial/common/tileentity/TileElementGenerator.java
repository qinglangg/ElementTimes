package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.tileentity.base.TileGenerator;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.interface_.item.IGeneratorElement;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

/**
 * 发电机的TileEntity
 *
 * @author KSGFK create in 2019/2/17
 */
public class TileElementGenerator extends TileGenerator {

    public TileElementGenerator() {
        super(ElementtimesConfig.general.generaterMaxEnergy);
    }

    @Override
    protected int getRFFromItem(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (item instanceof IGeneratorElement)
            return ((IGeneratorElement) item).getEnergy();
        return 0;
    }

    @Override
    protected int getMaxGenerateRFPerTick() {
        return ElementtimesConfig.general.generaterMaxReceive;
    }

    @Override
    protected int getMaxExtractRFPerTick(EnumFacing facing) {
        return ElementtimesConfig.general.generaterMaxExtract;
    }
}
