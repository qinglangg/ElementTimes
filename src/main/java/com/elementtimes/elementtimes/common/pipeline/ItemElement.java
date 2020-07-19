package com.elementtimes.elementtimes.common.pipeline;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 物品（ItemStack）类型的 Element

 */
public class ItemElement extends BaseElement<ItemStack> {

    public static ElementType<ItemStack> TYPE = new ElementType<ItemStack>() {

        @Override
        public String type() {
            return "item";
        }

        @Override
        public ItemElement newInstance(ItemStack object) {
            return new ItemElement(object);
        }

        @Override
        public BaseElement<ItemStack> newInstance() {
            return newInstance(ItemStack.EMPTY);
        }
    };

    public static final ItemElement EMPTY = new ItemElement(ItemStack.EMPTY);

    private ItemElement(ItemStack stack) {
        super(TYPE.type());
        element = stack;
    }

    public ItemStack getItem() {
        return element;
    }

    @Override
    public boolean isEmpty() {
        return element == null || getItem().isEmpty();
    }

    @Override
    public Class<ItemStack> getType() {
        return ItemStack.class;
    }

    @Override
    public ItemElement copy() {
        ItemElement e = new ItemElement(getItem().copy());
        e.path = path;
        e.type = type;
        e.posIndex = posIndex;
        e.totalTick = totalTick;
        return e;
    }

    @Override
    protected CompoundNBT elementSerializer() {
        return getItem().serializeNBT();
    }

    @Override
    protected void elementDeserializer(CompoundNBT compound) {
        element = ItemStack.read(compound);
    }

    @Override
    public void drop(World world, BlockPos pos) {
        if (!isEmpty()) {
            Block.spawnAsEntity(world, pos, getItem());
        }
    }
}
