package com.elementtimes.tutorial.common.tileentity.stand.capability;

import com.elementtimes.tutorial.common.tileentity.stand.TileSupportStand;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ModuleItems implements IItemHandler {

    protected final TileSupportStand mTileEntity;
    protected final EnumFacing mFacing;
    protected final List<ModuleCap<IItemHandler>> mCapabilities;
    protected final List<Slot> mSlots = new ArrayList<>();
    protected int mMaxSlot;

    public ModuleItems(TileSupportStand tss, EnumFacing facing) {
        mTileEntity = tss;
        mFacing = facing;
        mCapabilities = mTileEntity.getCapabilities(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, mFacing);
        update();
    }

    public void update() {
        mSlots.clear();
        mMaxSlot = 0;
        for (int i = 0; i < mCapabilities.size(); i++) {
            ModuleCap<IItemHandler> cap = mCapabilities.get(i);
            IItemHandler moduleCap = cap.capability;
            int slots = moduleCap.getSlots();
            mMaxSlot += slots;
            for (int idx = 0; idx < slots; idx++) {
                mSlots.add(new Slot(moduleCap, cap, idx, i));
            }
        }
    }

    @Override
    public int getSlots() {
        return mMaxSlot;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return mSlots.get(slot).getStackInSlot();
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return mSlots.get(slot).insertItem(stack, simulate);
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return mSlots.get(slot).extractItem(amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return mSlots.get(slot).getSlotLimit();
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return mSlots.get(slot).isItemValid(stack);
    }

    class Slot {
        final IItemHandler handler;
        final ModuleCap<IItemHandler> cap;
        final int slot;
        final int handlerIndex;

        public Slot(IItemHandler handler, ModuleCap<IItemHandler> cap, int slot, int handlerIndex) {
            this.handler = handler;
            this.cap = cap;
            this.slot = slot;
            this.handlerIndex = handlerIndex;
        }

        public ItemStack getStackInSlot() {
            return handler.getStackInSlot(slot);
        }

        public ItemStack insertItem(@Nonnull ItemStack stack, boolean simulate) {
            return handler.insertItem(slot, stack, simulate);
        }

        public ItemStack extractItem(int amount, boolean simulate) {
            return handler.extractItem(slot, amount, simulate);
        }

        public int getSlotLimit() {
            return handler.getSlotLimit(slot);
        }

        public boolean isItemValid(@Nonnull ItemStack stack) {
            return handler.isItemValid(slot, stack);
        }
    }
}
