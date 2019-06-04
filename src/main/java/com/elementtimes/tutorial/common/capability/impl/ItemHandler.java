package com.elementtimes.tutorial.common.capability.impl;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.function.BiPredicate;

/**
 * 自定义实现的 IItemHandler
 * 对其 insertItem 做了重写，防止管道输入错误物品
 *
 * @author luqin2007
 */
public class ItemHandler extends ItemStackHandler {
    public static final ItemHandler EMPTY = new ItemHandler(0);

    public ItemHandler() {
        this(1);
    }

    public ItemHandler(int size) {
        this(NonNullList.withSize(size, ItemStack.EMPTY));
    }

    public ItemHandler(NonNullList<ItemStack> stacks) {
        this(stacks, (integer, itemStack) -> true);
    }

    public ItemHandler(BiPredicate<Integer, ItemStack> check) {
        this(1, check);
    }

    public ItemHandler(int size, BiPredicate<Integer, ItemStack> check) {
        this(NonNullList.withSize(size, ItemStack.EMPTY), check);
    }

    public ItemHandler(NonNullList<ItemStack> stacks, BiPredicate<Integer, ItemStack> check) {
        super(stacks);
        this.mInputValid = check;
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
}
