package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.common.tileentity.base.TileOneToOne;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TileRebuild extends TileOneToOne {

    public TileRebuild(int maxEnergy, int maxReceive) {
        super(ElementtimesConfig.rebuild.maxEnergy, ElementtimesConfig.rebuild.maxReceive);

        rebuildMap.add(new ImmutablePair<>(new ItemStack(ElementtimesItems.starchPowder), new ItemStack(ElementtimesItems.sucroseCharCoal)));
        energyMap.add(new ImmutablePair<>(new ItemStack(ElementtimesItems.starchPowder), 4000));
        rebuildMap.add(new ImmutablePair<>(new ItemStack(ElementtimesItems.sucroseCharCoal, 2), new ItemStack(Items.COAL, 1, 1)));
        energyMap.add(new ImmutablePair<>(new ItemStack(ElementtimesItems.sucroseCharCoal, 2), 4000));
        rebuildMap.add(new ImmutablePair<>(new ItemStack(Items.COAL, 1, 1), new ItemStack(Items.COAL, 1, 0)));
        energyMap.add(new ImmutablePair<>(new ItemStack(Items.COAL, 1, 1), 4000));
        rebuildMap.add(new ImmutablePair<>(new ItemStack(Items.COAL, 1, 0), new ItemStack(Items.DIAMOND)));
        energyMap.add(new ImmutablePair<>(new ItemStack(Items.COAL, 1, 0), 50000));
        rebuildMap.add(new ImmutablePair<>(new ItemStack(Blocks.DIAMOND_BLOCK), new ItemStack(ElementtimesItems.diamondIngot)));
        energyMap.add(new ImmutablePair<>(new ItemStack(Blocks.DIAMOND_BLOCK), 50000));
        rebuildMap.add(new ImmutablePair<>(new ItemStack(Blocks.DIRT), new ItemStack(Blocks.FARMLAND)));
        energyMap.add(new ImmutablePair<>(new ItemStack(Blocks.DIRT),100000));
        rebuildMap.add(new ImmutablePair<>(new ItemStack(Blocks.GRASS), new ItemStack(Blocks.GRASS_PATH)));
        energyMap.add(new ImmutablePair<>(new ItemStack(Blocks.GRASS), 100000));
        rebuildMap.add(new ImmutablePair<>(new ItemStack(Blocks.SAND), new ItemStack(Blocks.SOUL_SAND)));
        energyMap.add(new ImmutablePair<>(new ItemStack(Blocks.SAND), 20000));
        rebuildMap.add(new ImmutablePair<>(new ItemStack(Blocks.GLASS), new ItemStack(Items.QUARTZ)));
        energyMap.add(new ImmutablePair<>(new ItemStack(Blocks.GLASS), 20000));
    }

    // key: OreDictionaryName or Item
    private List<ImmutablePair<ItemStack, ItemStack>> rebuildMap = new ArrayList<>();
    private List<ImmutablePair<ItemStack, Integer>> energyMap = new ArrayList<>();

    @Override
    protected ItemStack getOutput(ItemStack input) {
        if (input.isEmpty()) return ItemStack.EMPTY;
        Optional<ImmutablePair<ItemStack, ItemStack>> first = rebuildMap.stream()
                .filter(immutablePair -> immutablePair.left.isItemEqual(input)
                        && immutablePair.left.getCount() <= input.getCount()).findFirst();
        if (first.isPresent()) return first.get().right;
        return ItemStack.EMPTY;
    }

    @Override
    protected int getTotalTime(ItemStack input) {
        if (input.isEmpty()) return 0;
        Optional<ImmutablePair<ItemStack, Integer>> first = energyMap.stream()
                .filter(immutablePair -> immutablePair.left.isItemEqual(input)
                        && immutablePair.left.getCount() <= input.getCount()).findFirst();
        if (first.isPresent()) return first.get().right;
        return 0;
    }

    @Override
    protected int getEnergyConsumePerTick(ItemStack input) {
        return ElementtimesConfig.rebuild.maxExtract;
    }
}
