package com.elementtimes.tutorial.util;

import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * 矿物（不一定是矿物）对应粉末的字典。单例。
 *
 * @author KSGFK create in 2019/5/6
 */
public class PowderDictionary {
    private final Map<String, Item> dict = new HashMap<>();

    private Map<Item, Item> pulCanInItemMap = new HashMap<>();
    private final Map<Item, Map<Item, String>> pulPowderLinkOre = new HashMap<>();

    private PowderDictionary() {
        dict.put("oreIron", ElementtimesItems.Ironpower);
        dict.put("oreRedstone", ElementtimesItems.Redstonepowder);
        dict.put("oreGold", ElementtimesItems.Goldpowder);
        dict.put("oreDiamond", ElementtimesItems.Diamondpowder);
        dict.put("oreLapis", ElementtimesItems.Bluestonepowder);
        dict.put("oreEmerald", ElementtimesItems.Greenstonepowder);
        dict.put("oreCopper", ElementtimesItems.Copperpowder);
        dict.put("oreCoal", ElementtimesItems.Coalpowder);

        InitPul();
    }

    /**
     * 初始化打粉机数据
     */
    private void InitPul() {
        Map<String, Item> powderForOreDict = dict;
        /*
        Map<String, NonNullList<ItemStack>> pulCanInOresDict = new OreMapFromOreDictFactory(
                "oreIron",
                "oreRedstone",
                "oreGold",
                "oreDiamond",
                "oreLapis",
                "oreEmerald",
                "oreCopper",
                "oreCoal").get();

         */
        Map<String, NonNullList<ItemStack>> pulCanInOresDict = new OreMapFromOreDictFactory(ElementtimesConfig.pul.pulCanPutIn).get();
        for (Map.Entry<String, NonNullList<ItemStack>> ore : pulCanInOresDict.entrySet()) {
            String oreName = ore.getKey();
            NonNullList<ItemStack> list = ore.getValue();
            if (powderForOreDict.containsKey(oreName)) {
                Item powder = powderForOreDict.get(oreName);
                for (ItemStack oreS : list) {
                    pulCanInItemMap.put(oreS.getItem(), powder);
                }
            }
        }
        for (Map.Entry<Item, Item> i : pulCanInItemMap.entrySet()) {
            Item powder = i.getValue();
            Item ore = i.getKey();
            if (pulPowderLinkOre.containsKey(powder)) {
                pulPowderLinkOre.get(powder).put(ore, OreDictionary.getOreName(OreDictionary.getOreIDs(new ItemStack(ore))[0]));
            } else {
                Map<Item, String> oresName = new HashMap<>();
                oresName.put(ore, OreDictionary.getOreName(OreDictionary.getOreIDs(new ItemStack(ore))[0]));
                pulPowderLinkOre.put(powder, oresName);
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

    public Map<Item, Map<Item, String>> getPulPowderLinkOre() {
        return pulPowderLinkOre;
    }

    public Map<Item, Item> getPulCanInItemMap() {
        return pulCanInItemMap;
    }
}
