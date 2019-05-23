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
    protected void onUpdate(boolean isProc, int schedule, int perTime) {

    }
}
