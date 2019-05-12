package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.network.PulMsg;
import com.elementtimes.tutorial.util.PowderDictionary;
import net.minecraft.item.Item;

import java.util.Map;

/**
 * @author KSGFK create in 2019/5/6
 */
public class TilePulverizer extends TileOneToOne {
    public TilePulverizer() {
        super(ElementtimesConfig.pul.pulMaxEnergy, ElementtimesConfig.pul.pulMaxReceive, ElementtimesConfig.pul.pulMaxExtract);
        setPerItemOutItem(ElementtimesConfig.pul.pulPowderCount);
        setPerTime(ElementtimesConfig.pul.pulPowderEnergy);
    }

    @Override
    protected void sendNetworkMessage() {
        Elementtimes.getNetwork().sendTo(new PulMsg(storage.getEnergyStored(), storage.getMaxEnergyStored(), schedule), player);
    }

    @Override
    protected Map<Item, Map<Integer, Item>> setOrePowder() {
        return PowderDictionary.getInstance().get("pul");
    }

    @Override
    public String getName() {
        return "666";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }
}
