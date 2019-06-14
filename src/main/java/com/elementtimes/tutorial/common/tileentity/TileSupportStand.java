package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.interfaces.tileentity.ITESRSupport;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KSGFK create in 2019/6/12
 */
public class TileSupportStand extends BaseMachine implements ITESRSupport {
    public final ItemStack alcoholLamp = new ItemStack(Item.getItemFromBlock(ElementtimesBlocks.alcoholLamp));
    public final ItemStack evaporatingDish = new ItemStack(Item.getItemFromBlock(ElementtimesBlocks.evaporatingDish));

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
    public void update() {
        //TODO:暂时不需要逻辑
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
        ItemStack willAdd;
        switch (e) {
            case AlcoholLamp:
                willAdd = alcoholLamp;
                break;
            case EvaporatingDish:
                willAdd = evaporatingDish;
                break;
            default:
                ElementTimes.getLogger().error("what the fuck...");
                return false;
        }

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
        ItemStack willRemove = null;
        switch (e) {
            case AlcoholLamp:
                willRemove = alcoholLamp;
                break;
            case EvaporatingDish:
                willRemove = evaporatingDish;
                break;
        }

        return tesr.remove(willRemove);
    }

    @Override
    public boolean containsRenderItem(Enum<?> itemEnum) {
        if (!(itemEnum instanceof CanPutInItem)) {
            return false;
        }

        CanPutInItem e = (CanPutInItem) itemEnum;
        ItemStack will = null;
        switch (e) {
            case AlcoholLamp:
                will = alcoholLamp;
                break;
            case EvaporatingDish:
                will = evaporatingDish;
                break;
        }

        return tesr.contains(will);
    }

    @Override
    public void initCanRendItems() {
        NBTTagCompound pos = new NBTTagCompound();
        pos.setFloat("x", 0.5F);
        pos.setFloat("y", -0.13F);
        pos.setFloat("z", 0.5F);
        alcoholLamp.setTagCompound(pos.copy());
        pos.setFloat("y", 0.375F);
        evaporatingDish.setTagCompound(pos);
    }

    public enum CanPutInItem {
        AlcoholLamp,
        EvaporatingDish
    }
}
