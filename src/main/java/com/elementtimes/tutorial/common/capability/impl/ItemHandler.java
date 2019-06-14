package com.elementtimes.tutorial.common.capability.impl;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/**
 * 自定义实现的 IItemHandler
 * 对其 insertItem 做了重写，防止管道输入错误物品
 *
 * @author luqin2007
 */
public class ItemHandler extends ItemStackHandler {
    public static final ItemHandler EMPTY = new ItemHandler(0);

    private int mSize;

    private boolean replaced = false;

    public ItemHandler() {
        this(1);
    }

    public ItemHandler(int size) {
        this(size, (integer, itemStack) -> true);
    }

    public ItemHandler(BiPredicate<Integer, ItemStack> check) {
        this(1, check);
    }

    public ItemHandler(int size, BiPredicate<Integer, ItemStack> check) {
        super(size);
        mSize = size;
        mInputValid = check;
    }

    public void setSlotIgnoreChangeListener(int slot, ItemStack stack) {
        validateSlotIndex(slot);
        this.stacks.set(slot, stack);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        unbindAll();
        return super.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        unbindAll();
        super.deserializeNBT(nbt);
    }

    /**
     * 用于判断一个物品是否可以插入到对应槽位中去
     */
    private BiPredicate<Integer, ItemStack> mInputValid;

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return mInputValid.test(slot, stack);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (!isItemValid(slot, stack)) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }

    /**
     * 直接调用 insertItem 而忽视 isItemValid，用于机器输出产物
     */
    @Nonnull
    public ItemStack insertItemIgnoreValid(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return super.insertItem(slot, stack, simulate);
    }

    public int bind(ItemStack itemStack) {
        // 防止 stacks 不允许 add
        if (!replaced) {
            NonNullList<ItemStack> newStacks = NonNullList.create();
            for (int i = 0; i < stacks.size(); i++) {
                newStacks.add(i, stacks.get(i));
            }
            stacks = newStacks;
        }
        int index = stacks.size();
        stacks.add(itemStack);
        return index;
    }

    public void unbindAll() {
        // 防止 stacks 不允许 add
        if (!replaced) {
            NonNullList<ItemStack> newStacks = NonNullList.create();
            for (int i = 0; i < stacks.size(); i++) {
                newStacks.add(i, stacks.get(i));
            }
            stacks = newStacks;
        }
        int expend = stacks.size() - mSize;
        if (expend >= 0) {
            ItemStack[] itemStacks = new ItemStack[expend];
            int pointer = expend - 1;
            while (stacks.size() > mSize) {
                itemStacks[pointer] = stacks.remove(stacks.size() - 1);
                pointer--;
            }
            onUnbindAllListener.forEach(consumer -> consumer.accept(itemStacks));
        }
    }

    public final List<IntConsumer> onItemChangeListener = new LinkedList<>();
    public final List<Consumer<ItemStack[]>> onUnbindAllListener = new LinkedList<>();

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        onItemChangeListener.forEach(i -> i.accept(slot));
    }
}
