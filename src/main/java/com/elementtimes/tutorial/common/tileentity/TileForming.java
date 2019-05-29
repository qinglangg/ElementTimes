package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.common.tileentity.base.TileOneToOne;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.util.RecipeUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Map;

public class TileForming extends TileOneToOne {

    private Mode mode = Mode.Gear;

    public static Map<ItemStack, ItemStack> recipeForming = new HashMap<>();
    public static Map<String, Item> recipeGear = new HashMap<>();

    public static void init() {
        // 原木
        RecipeUtil.collectOneBlockCraftingResult("logWood", recipeForming);
        // 齿轮
        recipeGear.put("plankWood", ElementtimesItems.gearWood);
        recipeGear.put("gemQuartz", ElementtimesItems.gearQuartz);
        recipeGear.put("stone", ElementtimesItems.gearStone);
        recipeGear.put("coal", ElementtimesItems.gearCarbon);
        recipeGear.put("ingotGold", ElementtimesItems.gearGold);
        recipeGear.put("ingotSteel", ElementtimesItems.gearSteel);
        recipeGear.put("gemDiamond", ElementtimesItems.gearDiamond);
        recipeGear.put("ingotIron", ElementtimesItems.gearIron);
        recipeGear.put("ingotPlatinum", ElementtimesItems.gearPlatinum);
        recipeGear.put("ingotCopper", ElementtimesItems.gearCopper);
    }

    public TileForming() {
        super(ElementtimesConfig.forming.maxEnergy, ElementtimesConfig.forming.maxReceive);
    }

    @Override
    protected ItemStack getOutput(ItemStackHandler handler, boolean simulate) {
        if (mode == Mode.Gear) {
            ItemStack input = handler.extractItem(0, 4, true);
            if (input.getCount() != 4) return ItemStack.EMPTY;
            for (int oreID : OreDictionary.getOreIDs(input)) {
                String oreName = OreDictionary.getOreName(oreID);
                if (recipeGear.containsKey(oreName)) {
                    return new ItemStack(recipeGear.get(oreName));
                }
            }
        } else {
            ItemStack input = handler.extractItem(0, 1, true);
            if (input.isEmpty()) return ItemStack.EMPTY;
            for (Map.Entry<ItemStack, ItemStack> entry : recipeForming.entrySet()) {
                if (entry.getKey().isItemEqual(input)) {
                    handler.extractItem(0, 1, simulate);
                    return entry.getValue();
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    protected ItemStack getInput(ItemStackHandler handler) {
        if (mode == Mode.Gear) {
            ItemStack copy = handler.getStackInSlot(0).copy();
            copy.setCount(4);
            return copy;
        } else {
            ItemStack copy = handler.getStackInSlot(0).copy();
            copy.setCount(1);
            return copy;
        }
    }

    @Override
    protected int getTotalEnergyCost(ItemStackHandler handler) {
        return ElementtimesConfig.forming.totalTime * getEnergyCostPerTick(handler);
    }

    @Override
    protected int getEnergyCostPerTick(ItemStackHandler handler) {
        return ElementtimesConfig.forming.maxExtract;
    }

    @Override
    protected boolean isInputItemValid(int slot, ItemStack stack) {
        if (mode == Mode.Gear) {
            for (int oreID : OreDictionary.getOreIDs(stack)) {
                String oreName = OreDictionary.getOreName(oreID);
                if (recipeGear.containsKey(oreName)) return true;
            }
        } else if (mode == Mode.Forming) {
            return recipeForming.keySet().stream().anyMatch(itemStack -> itemStack.isItemEqual(stack));
        }
        return false;
    }



    public void setMode(Mode mode) {
        if (this.mode != mode) {
            interrupt();
            this.mode = mode;
        }
    }

    enum Mode {
        Forming, Gear
    }
}
