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
    private final Map<String, Map<Item, Map<Integer, Item>>> machineDict = new HashMap<>();

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
    }

    /**
     * 需要映射矿物和矿粉的机器方块
     */
    public void Init() {
        machineDict.put("pul", InitOrePowderDicts(new ExtractOreDictFactory(ElementtimesConfig.pul.pulCanPutIn).get()));
        machineDict.put("compressor", InitOrePowderDicts(new ExtractOreDictFactory(ElementtimesConfig.compressor.compressorCanPutIn).get()));
    }

    public Map<Item, Map<Integer, Item>> get(String blockName) {
        Map<Item, Map<Integer, Item>> map = machineDict.get(blockName);
        if (map != null)
            return map;
        throw new IllegalArgumentException("先添加机器名字，再查找");
    }

    private Map<Item, Map<Integer, Item>> InitOrePowderDicts(Map<String, NonNullList<ItemStack>> oreLists) {
        Map<Item, Map<Integer, Item>> result = new HashMap<>();
        for (Map.Entry<String, NonNullList<ItemStack>> ores : oreLists.entrySet()) {
            String oreDictName = ores.getKey();
            NonNullList<ItemStack> oreItemList = ores.getValue();
            Item powder = dict.get(oreDictName);
            if (powder != null) {
                for (ItemStack oreItem : oreItemList) {
                    if (result.containsKey(oreItem.getItem())) {
                        result.get(oreItem.getItem()).put(oreItem.getItemDamage(), powder);
                    } else {
                        Map<Integer, Item> oreDamageForPowder = new HashMap<>();
                        oreDamageForPowder.put(oreItem.getItemDamage(), powder);
                        result.put(oreItem.getItem(), oreDamageForPowder);
                    }
                }
            }
        }
        return result;
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
}
