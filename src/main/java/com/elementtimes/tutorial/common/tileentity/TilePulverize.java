package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.common.tileentity.base.TileOneToOne;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * @author KSGFK create in 2019/5/6
 */
public class TilePulverize extends TileOneToOne {
    public TilePulverize() {
        super(ElementtimesConfig.pul.pulMaxEnergy,
                ElementtimesConfig.pul.pulMaxReceive,
                ElementtimesConfig.pul.pulMaxExtract,
                ElementtimesConfig.pul.pulPowderEnergy);

        dict.put("oreIron", ElementtimesItems.ironPower);
        dict.put("oreRedstone", ElementtimesItems.redstonePowder);
        dict.put("oreGold", ElementtimesItems.goldPowder);
        dict.put("oreDiamond", ElementtimesItems.diamondPowder);
        dict.put("oreLapis", ElementtimesItems.bluestonePowder);
        dict.put("oreEmerald", ElementtimesItems.greenstonePowder);
        dict.put("oreCopper", ElementtimesItems.copperPowder);
        dict.put("oreCoal", ElementtimesItems.coalPowder);
        dict.put("orePlatinum", ElementtimesItems.platinumOrePowder);
    }

    private Map<String, Item> dict = new HashMap<>();

    @Override
    protected ItemStack getOutput(ItemStack input) {
        if (input.isEmpty()) return ItemStack.EMPTY;
        for (int id : OreDictionary.getOreIDs(input)) {
            String name = OreDictionary.getOreName(id);
            if (dict.containsKey(name)) {
                return new ItemStack(dict.get(name), ElementtimesConfig.pul.pulPowderCount);
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean onUpdate() {
        return true;
    }
}
