package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.tileentity.base.TileOneToOne;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import net.minecraft.item.ItemStack;

/**
 * 压缩机
 * @author lq2007 create in 2019/5/19
 */
public class TileCompressor extends TileOneToOne {
    public TileCompressor() {
        super(ElementtimesConfig.compressor.compressorMaxEnergy,
                ElementtimesConfig.compressor.compressorMaxReceive,
                ElementtimesConfig.compressor.compressorMaxExtract,
                ElementtimesConfig.compressor.compressorPowderEnergy);
    }

    @Override
    protected ItemStack getOutput(ItemStack input) {
        if (input.isEmpty()) return ItemStack.EMPTY;
        return ItemStack.EMPTY;
    }

    @Override
    public boolean onUpdate() {
        return true;
    }
}
