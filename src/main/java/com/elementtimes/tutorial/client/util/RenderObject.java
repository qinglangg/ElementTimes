package com.elementtimes.tutorial.client.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

/**
 * @author KSGFK create in 2019/6/19
 */
public class RenderObject {
    public ItemStack obj;
    public Vec3d vector;
    private boolean isRender = false;
    private boolean isBlock;

    /**
     * @Deprecated 暂时不支持直接渲染Block
     */
    @Deprecated
    public RenderObject(Block block, int meta, Vec3d vector) {
        this(new ItemStack(block, 1, meta), vector);
        this.isBlock = true;
    }

    public RenderObject(Item item, Vec3d vector) {
        this(new ItemStack(item), vector);
        this.isBlock = false;
    }

    private RenderObject(ItemStack obj, Vec3d vector) {
        this.obj = obj;
        this.vector = vector;
        this.isBlock = false;
    }

    public RenderObject setItem(ItemStack obj) {
        this.obj = obj;
        return this;
    }

    public RenderObject setRender(boolean isRender) {
        this.isRender = isRender;
        return this;
    }

    public boolean isRender() {
        return isRender;
    }

    public boolean isBlock() {
        return isBlock;
    }
}
