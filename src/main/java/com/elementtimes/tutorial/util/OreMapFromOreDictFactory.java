package com.elementtimes.tutorial.util;

import com.elementtimes.tutorial.Elementtimes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用矿词对应字符串获取矿物列表
 *
 * @author KSGFK create in 2019/5/6
 */
public class OreMapFromOreDictFactory implements IFactory<Map<String, NonNullList<ItemStack>>> {
    private String[] args;

    public OreMapFromOreDictFactory(String... oreName) {
        args = oreName;
    }

    public Map<String, NonNullList<ItemStack>> get() {
        Map<String, NonNullList<ItemStack>> map = new HashMap<>();
        for (String name : args) {
            NonNullList<ItemStack> ores = OreDictionary.getOres(name);
            if (ores.size() == 0) {
                Elementtimes.getLogger().warn("没有矿词名为 " + name + " 的矿石,已自动忽略该矿石");
            } else {
                map.put(name, ores);
            }
            //Elementtimes.getLogger().info(name + ":" + ores.size());
        }
        return map;
    }
}
