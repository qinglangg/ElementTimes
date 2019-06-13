package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.interfaces.tileentity.ITESRSupport;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KSGFK create in 2019/6/12
 */
public class TileSupportStand extends BaseMachine implements ITESRSupport {

    private List<ItemStack> tesr = new ArrayList<>();

    public TileSupportStand() {
        super(0, 0, 0);
        initCanRendItems();
    }

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
        return recipe;
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    @Override
    public Iterable<ItemStack> getRenderItems() {
        return tesr;
    }

    @Override
    public boolean addRenderItem(Enum<?> itemEnum) {
        if (!(itemEnum instanceof CanPutInItem)) {
            return false;
        }

        CanPutInItem e = (CanPutInItem) itemEnum;
        ItemStack willAdd = e.stack;

        if (tesr.contains(willAdd)) {
            return false;
        }

        return tesr.add(willAdd);
    }

    @Override
    public boolean removeRenderItem(Enum<?> itemEnum) {
        if (!(itemEnum instanceof CanPutInItem)) {
            return false;
        }

        CanPutInItem e = (CanPutInItem) itemEnum;
        ItemStack willRemove = e.stack;

        return tesr.remove(willRemove);
    }

    @Override
    public void initCanRendItems() {
        NBTTagCompound pos = new NBTTagCompound();
        pos.setFloat("x", 0.5F);
        pos.setFloat("y", -0.13F);
        pos.setFloat("z", 0.5F);
        CanPutInItem.AlcoholLamp.stack.setTagCompound(pos.copy());
        pos.setFloat("y", 0.375F);
        CanPutInItem.EvaporatingDish.stack.setTagCompound(pos.copy());
    }

    public enum CanPutInItem {
        /**
         * 酒精灯
         */
        AlcoholLamp(new ItemStack(ElementtimesBlocks.alcoholLamp)),

        /**
         * 蒸发皿
         */
        EvaporatingDish(new ItemStack(ElementtimesBlocks.evaporatingDish));

        CanPutInItem(ItemStack stack) {
            this.stack = stack;
        }

        public ItemStack stack;
    }
}
