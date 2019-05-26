package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.common.tileentity.base.TileOneToOne;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author KSGFK create in 2019/5/6
 */
public class TilePulverize extends TileOneToOne {

    public static Map<String, Item> dict = new HashMap<>();

    public static void init() {
        if (!dict.isEmpty()) return;
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

    public TilePulverize() {
        super(ElementtimesConfig.pul.pulMaxEnergy, ElementtimesConfig.pul.pulMaxReceive);
        init();
    }

    @Override
    protected ItemStack getInput(ItemStackHandler handler) {
        return handler.extractItem(0, 1, true);
    }

    @Override
    protected ItemStack getOutput(ItemStackHandler handler, boolean simulate) {
        ItemStack input = handler.extractItem(0, 1, simulate);
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
    protected int getTotalEnergyCost(ItemStackHandler handler) {
        return ElementtimesConfig.pul.pulPowderEnergy;
    }

    @Override
    protected int getEnergyCostPerTick(ItemStackHandler handler) {
        return ElementtimesConfig.pul.pulMaxExtract;
    }

    @Override
    protected boolean isInputItemValid(int slot, @Nonnull ItemStack stack) {
        for (int oreID : OreDictionary.getOreIDs(stack)) {
            if (dict.containsKey(OreDictionary.getOreName(oreID)))
                return true;
        }
        return false;
    }
}
