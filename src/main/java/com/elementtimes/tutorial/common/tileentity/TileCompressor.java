package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.tileentity.base.TileOneToOne;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * 压缩机
 * @author lq2007 create in 2019/5/19
 */
public class TileCompressor extends TileOneToOne {
    public TileCompressor() {
        super(ElementtimesConfig.compressor.maxEnergy,
                ElementtimesConfig.compressor.maxReceive);

        // TODO: 添加打粉机合成表
    }

    // key: OreDictionaryName or Item
    private Map<Object, Item> dustMap = new HashMap<>();

    @Override
    protected ItemStack getOutput(ItemStack input) {
        if (input.isEmpty()) return ItemStack.EMPTY;
        int[] oreIDs = OreDictionary.getOreIDs(input);
        if (oreIDs.length == 0) {
            if (dustMap.containsKey(input.getItem()))
                return new ItemStack(dustMap.get(input.getItem()), ElementtimesConfig.compressor.powderCount);
            else return ItemStack.EMPTY;
        }
        for (int oreID : oreIDs) {
            String name = OreDictionary.getOreName(oreID);
            if (dustMap.containsKey(name)) {
                return new ItemStack(dustMap.get(name), ElementtimesConfig.compressor.powderCount);
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    protected int getTotalTime(ItemStack input) {
        return ElementtimesConfig.compressor.powderEnergy / ElementtimesConfig.compressor.maxExtract;
    }

    @Override
    protected int getEnergyConsumePerTick(ItemStack input) {
        return ElementtimesConfig.compressor.maxExtract;
    }
}
