package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.common.tileentity.base.TileOneToOne;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TileRebuild extends TileOneToOne {

    // key: OreDictionaryName or Item
    public static List<ImmutablePair<ItemStack, ItemStack>> rebuildMap = new ArrayList<>();
    private static List<ImmutablePair<ItemStack, Integer>> energyMap = new ArrayList<>();

    public TileRebuild() {
        super(ElementtimesConfig.rebuild.maxEnergy, ElementtimesConfig.rebuild.maxReceive);
        init();
    }

    public static int getEnergyCost(ItemStack itemStack) {
        return energyMap.stream()
                .filter(entry -> entry.left.isItemEqual(itemStack))
                .findFirst()
                .map(pair -> pair.right)
                .orElse(0);
    }

    public static void init() {
        if (rebuildMap.isEmpty()) {
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
    }

    @Override
    protected ItemStack getOutput(ItemStackHandler handler, boolean simulate) {
        ItemStack input = handler.extractItem(0, 1, true);
        if (input.isEmpty()) return ItemStack.EMPTY;
        Optional<ImmutablePair<ItemStack, ItemStack>> pair = rebuildMap.stream()
                .filter(entry -> entry.left.isItemEqual(input))
                .findFirst();
        return pair.map(pair1 -> {
            handler.extractItem(0, pair1.left.getCount(), simulate);
            return pair1.right;
        }).orElse(ItemStack.EMPTY);
    }

    @Override
    protected int getTotalEnergyCost(ItemStackHandler handler) {
        ItemStack input = handler.extractItem(0, 1, true);
        if (input.isEmpty()) return 0;
        return getEnergyCost(input);
    }

    @Override
    protected int getEnergyCostPerTick(ItemStackHandler handler) {
        return ElementtimesConfig.rebuild.maxExtract;
    }

    @Override
    protected boolean isInputItemValid(int slot, @Nonnull ItemStack stack) {
        return rebuildMap.stream().anyMatch(pair -> pair.left.isItemEqual(stack));
    }
}
