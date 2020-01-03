package com.elementtimes.tutorial.common.pipeline;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 物品（ItemStack）类型的 Element
 * @author luqin2007
 */
public class ItemElement extends BaseElement {

    public static ElementType TYPE = new ElementType() {

        @Override
        public String type() {
            return "item";
        }

        @Override
        public ItemElement newInstance() {
            return new ItemElement();
        }
    };

    private ItemElement() {
        super(TYPE.type());
    }

    public ItemStack getItem() {
        return (ItemStack) element;
    }

    @Override
    public boolean isEmpty() {
        return element == null || getItem().isEmpty();
    }

    @Override
    public ItemElement copy() {
        ItemElement e = new ItemElement();
        e.path = path;
        e.type = type;
        e.element = getItem().copy();
        e.posIndex = posIndex;
        e.totalTick = totalTick;
        return e;
    }

    @Override
    protected NBTTagCompound elementSerializer() {
        return getItem().serializeNBT();
    }

    @Override
    protected void elementDeserializer(NBTTagCompound compound) {
        element = new ItemStack(compound);
    }

    @Override
    public void drop(World world, BlockPos pos) {
        if (!isEmpty()) {
            Block.spawnAsEntity(world, pos, getItem());
        }
    }
}
