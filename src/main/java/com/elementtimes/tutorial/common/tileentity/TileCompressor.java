package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.network.PulMsg;
import com.elementtimes.tutorial.util.PowderDictionary;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;

import java.util.Map;

/**
 * 打粉机[WIP]
 * 还不知道机器相关的完整框架，只实现了 Block，TileBlock，Config，GUI
 * 通信部份没做 感觉。。。可复用的东西还是有点多
 *
 * @author lq2007 create in 2019/5/19
 */
public class TileCompressor extends TileOneToOne {
    public TileCompressor() {
        super(ElementtimesConfig.compressor.compressorMaxEnergy,
                ElementtimesConfig.compressor.compressorMaxReceive,
                ElementtimesConfig.compressor.compressorMaxExtract);
        setPerItemOutItem(ElementtimesConfig.compressor.compressorPowderCount);
        setPerTime(ElementtimesConfig.compressor.compressorPowderEnergy);
    }

    @Override
    protected void sendNetworkMessage() {
        Elementtimes.getNetwork().sendTo(new PulMsg(storage.getEnergyStored(), storage.getMaxEnergyStored(), schedule), player);
    }

    @Override
    protected Map<Item, Map<Integer, Item>> setOrePowder() {
        return PowderDictionary.getInstance().get("compressor");
    }

    @Override
    public String getName() {
        return I18n.format(ElementtimesBlocks.Compressor.getUnlocalizedName());
    }

    @Override
    public boolean hasCustomName() { return false; }
}
