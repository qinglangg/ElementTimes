package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.tileentity.base.TileGenerator;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.interface_.item.IGeneratorElement;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * 发电机的TileEntity
 *
 * @author KSGFK create in 2019/2/17
 */
public class TileElementGenerator extends TileGenerator {

    public TileElementGenerator() {
        super(ElementtimesConfig.general.generaterMaxExtract, ElementtimesConfig.general.generaterMaxReceive);
    }

    @Override
    protected int getRFFromItem(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (item instanceof IGeneratorElement)
            return ((IGeneratorElement) item).getEnergy();
        return 0;
    }
}
