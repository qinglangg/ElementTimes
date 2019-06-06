package com.elementtimes.tutorial.interfaces.tileentity;

import com.elementtimes.tutorial.common.capability.impl.ItemHandler;
import com.elementtimes.tutorial.other.SideHandlerType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * 对物品的接口
 * @author luqin2007
 */
public interface ITileItemHandler extends ICapabilityProvider, INBTSerializable<NBTTagCompound> {

    Map<EnumFacing, SideHandlerType> getItemTypeMap();
    Map<SideHandlerType, ItemHandler> getItemHandlerMap();

    String NBT_ITEMS = "_items_";
    String NBT_ITEMS_INPUT = "_inputs_";
    String NBT_ITEMS_OUTPUT = "_outputs_";
    String NBT_ITEMS_INPUT_OLD = "inputs";
    String NBT_ITEMS_OUTPUT_OLD = "outputs";

    @Nonnull
    default SideHandlerType getItemType(@Nonnull EnumFacing facing) {
        return getItemTypeMap().getOrDefault(facing, SideHandlerType.NONE);
    }

    @Nonnull
    default ItemHandler getItemHandler(@Nonnull SideHandlerType type) {
        return getItemHandlerMap().get(type);
    }

    @Nonnull
    default ItemHandler getItemHandler(EnumFacing facing) {
        return getItemHandler(getItemType(facing));
    }

    @Nullable
    default ItemStack getItemStack(SideHandlerType type, int slot) {
        return getItemHandler(type).getStackInSlot(slot);
    }

    @Nullable
    default ItemStack getItemStack(EnumFacing facing, int slot) {
        return getItemStack(getItemType(facing), slot);
    }
    
    @Override
    default boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && getItemType(facing) != SideHandlerType.NONE;
    }

    @Nullable
    @Override
    default <T2> T2 getCapability(@Nonnull Capability<T2> capability, @Nullable EnumFacing facing) {
        return capability.cast((T2) getItemHandler(getItemType(facing)));
    }

    @Override
    default void deserializeNBT(NBTTagCompound nbt) {
        // 旧版本兼容
        if (nbt.hasKey(NBT_ITEMS_INPUT_OLD)) {
            getItemHandler(SideHandlerType.INPUT).deserializeNBT(nbt.getCompoundTag("inputs"));
            nbt.removeTag(NBT_ITEMS_INPUT_OLD);
        }
        if (nbt.hasKey(NBT_ITEMS_OUTPUT)) {
            getItemHandler(SideHandlerType.OUTPUT).deserializeNBT(nbt.getCompoundTag(NBT_ITEMS_OUTPUT));
            nbt.removeTag(NBT_ITEMS_OUTPUT);
        }

        if (nbt.hasKey(NBT_ITEMS)) {
            NBTTagCompound nbtItems = nbt.getCompoundTag(NBT_ITEMS);
            if (nbtItems.hasKey(NBT_ITEMS_INPUT)) {
                getItemHandler(SideHandlerType.INPUT).deserializeNBT(nbt.getCompoundTag(NBT_ITEMS_INPUT));
            }
            if (nbtItems.hasKey(NBT_ITEMS_OUTPUT)) {
                getItemHandler(SideHandlerType.OUTPUT).deserializeNBT(nbt.getCompoundTag(NBT_ITEMS_OUTPUT));
            }
        }
    }

    default NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound) {
        NBTTagCompound nbt = new NBTTagCompound();
        // input
        ItemHandler inputs = getItemHandler(SideHandlerType.INPUT);
        if (inputs.getSlots() != 0) {
            nbt.setTag(NBT_ITEMS_INPUT, inputs.serializeNBT());
        }
        // output
        ItemHandler outputs = getItemHandler(SideHandlerType.OUTPUT);
        if (outputs.getSlots() != 0) {
            nbt.setTag(NBT_ITEMS_OUTPUT, outputs.serializeNBT());
        }
        if (!nbt.hasNoTags()) {
            nbtTagCompound.setTag(NBT_ITEMS, nbt);
        }
        return nbtTagCompound;
    }

    /**
     * 用于校验输入区是否可以放入某物品栈
     * @param stack 要输入的物品栈
     * @param slot 输入的槽位
     * @return true：可以放入
     */
    boolean isInputValid(int slot, ItemStack stack);
}
