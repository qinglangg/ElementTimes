package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.network.ElementGenerater;
import com.elementtimes.tutorial.network.PulMsg;
import com.elementtimes.tutorial.util.BaseFactory;
import com.elementtimes.tutorial.util.OreMapFromOreDictFactory;
import com.elementtimes.tutorial.util.RedStoneEnergy;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author KSGFK create in 2019/5/6
 */
public class TilePulverizer extends TileMachine {
    private SlotItemHandler inputSlot = new SlotItemHandler(items, 0, 56, 30) {
        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return super.isItemValid(stack);
        }
    };
    private SlotItemHandler outputSlot = new SlotItemHandler(items, 1, 110, 30) {
        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return false;
        }
    };

    private Map<String, NonNullList<ItemStack>> canPutOres;

    public TilePulverizer() {
        super(new RedStoneEnergy(320000, 20, 20), new ItemStackHandler(2));
        canPutOres = new OreMapFromOreDictFactory(
                "oreIron",
                "oreRedstone",
                "oreGold",
                "oreDiamond",
                "oreLapis",
                "oreEmerald",
                "oreCopper").get();
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            //Elementtimes.getLogger().info("hello,pul");
        }
        if (isOpenGui) {
            Elementtimes.getLogger().info("hello,pul");
            Elementtimes.getNetwork().sendTo(new PulMsg(storage.getEnergyStored(), storage.getMaxEnergyStored(), 50), player);
        }
    }

    public SlotItemHandler getInputSlot() {
        return inputSlot;
    }

    public SlotItemHandler getOutputSlot() {
        return outputSlot;
    }
}
