package com.elementtimes.tutorial.common.block.pipeline;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 物品（ItemStack）类型的 Element
 * @author luqin2007
 */
public class ItemElement extends BaseElement<ItemStack> {

    public static ElementType<ItemElement> TYPE = new ElementType<ItemElement>() {

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

    @Override
    public boolean isEmpty() {
        return element == null || element.isEmpty();
    }

    @Override
    public ItemElement copy() {
        ItemElement e = new ItemElement();
        e.path = path;
        e.type = type;
        e.element = element.copy();
        e.posIndex = posIndex;
        e.totalTick = totalTick;
        return e;
    }

    @Override
    protected NBTTagCompound elementSerializer() {
        return element.serializeNBT();
    }

    @Override
    protected void elementDeserializer(NBTTagCompound compound) {
        element = new ItemStack(compound);
    }

    @Override
    public void drop(World world, BlockPos pos) {
        if (!isEmpty()) {
            Block.spawnAsEntity(world, pos, element);
        }
    }
}
