package com.elementtimes.tutorial.util;

import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.HashMap;
import java.util.Map;

/**
 * 矿词对应粉末映射表。单例。
 *
 * @author KSGFK create in 2019/5/6
 */
public class PowderDictionary {
    private final Map<String, Item> dict = new HashMap<>();

    private final Map<Item, Map<Integer, Item>> pulOrePowder = new HashMap<>();

    private PowderDictionary() {
        dict.put("oreIron", ElementtimesItems.Ironpower);
        dict.put("oreRedstone", ElementtimesItems.Redstonepowder);
        dict.put("oreGold", ElementtimesItems.Goldpowder);
        dict.put("oreDiamond", ElementtimesItems.Diamondpowder);
        dict.put("oreLapis", ElementtimesItems.Bluestonepowder);
        dict.put("oreEmerald", ElementtimesItems.Greenstonepowder);
        dict.put("oreCopper", ElementtimesItems.Copperpowder);
        dict.put("oreCoal", ElementtimesItems.Coalpowder);
        dict.put("orePlatinum", ElementtimesItems.Platinumorepowder);

        InitPul();
    }

    private void InitPul() {
        Map<String, NonNullList<ItemStack>> pulCanPutInOres = new OreMapFromOreDictFactory(ElementtimesConfig.pul.pulCanPutIn).get();
        for (Map.Entry<String, NonNullList<ItemStack>> ores : pulCanPutInOres.entrySet()) {
            String oreDictName = ores.getKey();
            NonNullList<ItemStack> oreItemList = ores.getValue();
            Item powder = dict.get(oreDictName);
            if (powder != null) {
                for (ItemStack oreItem : oreItemList) {
                    if (pulOrePowder.containsKey(oreItem.getItem())) {
                        pulOrePowder.get(oreItem.getItem()).put(oreItem.getItemDamage(), powder);
                    } else {
                        Map<Integer, Item> oreDamageForPowder = new HashMap<>();
                        oreDamageForPowder.put(oreItem.getItemDamage(), powder);
                        pulOrePowder.put(oreItem.getItem(), oreDamageForPowder);
                    }
                }
            }
        }
    }

    private static class Instance {
        private static final PowderDictionary instance = new PowderDictionary();
    }

    public static PowderDictionary getInstance() {
        return Instance.instance;
    }

    public Map<String, Item> getDict() {
        return dict;
    }

    public Map<Item, Map<Integer, Item>> getPulOrePowder() {
        return pulOrePowder;
    }
}
