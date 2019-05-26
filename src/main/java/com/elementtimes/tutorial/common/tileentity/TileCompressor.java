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
 * 压缩机
 * @author lq2007 create in 2019/5/19
 */
public class TileCompressor extends TileOneToOne {
    public TileCompressor() {
        super(ElementtimesConfig.compressor.maxEnergy,
                ElementtimesConfig.compressor.maxReceive);
    }

    // key: OreDictionaryName or Item
    public static Map<Object, ItemStack> recipes = new HashMap<>();

    public static void init() {
        if (recipes.isEmpty()) {
            recipes.put("logWood", new ItemStack(ElementtimesItems.platewood));
            recipes.put(ElementtimesItems.sucroseCharCoal, new ItemStack(ElementtimesItems.plateCarbon));
            recipes.put("ingotCopper", new ItemStack(ElementtimesItems.plateCopper));
            recipes.put("gemDiamond", new ItemStack(ElementtimesItems.plateDiamond));
            recipes.put("ingotGold", new ItemStack(ElementtimesItems.plateGold));
            recipes.put("ingotIron", new ItemStack(ElementtimesItems.plateIron));
            recipes.put("ingotPlatinum", new ItemStack(ElementtimesItems.platePlatinum));
            recipes.put("gemQuartz", new ItemStack(ElementtimesItems.plateQuartz));
            recipes.put("ingotSteel", new ItemStack(ElementtimesItems.plateSteel));
            recipes.put("stone", new ItemStack(ElementtimesItems.plateStone));
        }
    }

    @Override
    protected ItemStack getOutput(ItemStackHandler handler, boolean simulate) {
        ItemStack input = handler.extractItem(0, 1, simulate);
        if (input.isEmpty()) return ItemStack.EMPTY;
        // item
        if (recipes.containsKey(input.getItem()))
            return recipes.get(input.getItem()).copy();
        // OreDictionary
        int[] oreIDs = OreDictionary.getOreIDs(input);
        for (int oreID : oreIDs) {
            String name = OreDictionary.getOreName(oreID);
            ItemStack stack = recipes.get(name);
            if (stack != null) return stack;
        }
        // Item
        return ItemStack.EMPTY;
    }

    @Override
    protected ItemStack getInput(ItemStackHandler handler) {
        return handler.extractItem(0, 1, true);
    }

    @Override
    protected boolean isInputItemValid(int slot, @Nonnull ItemStack itemStack) {
        // Item
        if (recipes.containsKey(itemStack.getItem())) return true;
        // OreDictionary
        for (int oreID : OreDictionary.getOreIDs(itemStack)) {
            if (recipes.containsKey(OreDictionary.getOreName(oreID))) return true;
        }
        return false;
    }

    @Override
    protected int getTotalEnergyCost(ItemStackHandler handler) {
        return ElementtimesConfig.compressor.powderEnergy;
    }

    @Override
    protected int getEnergyCostPerTick(ItemStackHandler handler) {
        return ElementtimesConfig.compressor.maxExtract;
    }
}
