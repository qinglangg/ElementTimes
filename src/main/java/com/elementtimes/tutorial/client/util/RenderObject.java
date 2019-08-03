package com.elementtimes.tutorial.client.util;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Objects;

/**
 * 暂时不支持直接渲染Block
 * @author KSGFK create in 2019/6/19
 */
public class RenderObject implements INBTSerializable<NBTTagCompound> {

    public static final RenderObject EMPTY = RenderObject.create(Items.AIR, 0, 0, 0);

    public ItemStack obj;
    public Vec3d vector;
    private boolean isRender = false;
    private boolean isBlock;

    private static final String BIND_NBT_TESR_RENDER = "_nbt_tesr_render_";
    private static final String BIND_NBT_TESR_RENDER_OBJ = "_nbt_tesr_render_obj_";
    private static final String BIND_NBT_TESR_RENDER_VECTOR_X = "_nbt_tesr_render_vector_x_";
    private static final String BIND_NBT_TESR_RENDER_VECTOR_Y = "_nbt_tesr_render_vector_y_";
    private static final String BIND_NBT_TESR_RENDER_VECTOR_Z = "_nbt_tesr_render_vector_z_";
    private static final String BIND_NBT_TESR_RENDER_IS_RENDER = "_nbt_tesr_render_is_render_";
    private static final String BIND_NBT_TESR_RENDER_IS_BLOCK = "_nbt_tesr_render_is_block_";

    public static RenderObject create(Item item, double x, double y, double z) {
        return new RenderObject(new ItemStack(item), new Vec3d(x, y, z)).setRender(false);
    }

    public static RenderObject create(NBTTagCompound nbt) {
        RenderObject copy = EMPTY.copy();
        copy.deserializeNBT(nbt);
        return copy;
    }

    public static RenderObject create(Block block, double x, double y, double z) {
        return new RenderObject(new ItemStack(Item.getItemFromBlock(block)), new Vec3d(x, y, z)).setRender(false);
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

    public RenderObject copy() {
        return new RenderObject(obj.copy(), new Vec3d(vector.x, vector.y, vector.z));
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof RenderObject)
                && ((RenderObject) obj).obj.equals(this.obj)
                && ((RenderObject) obj).vector.equals(this.vector);

    }

    @Override
    public int hashCode() {
        return Objects.hash(obj, vector);
    }

    public NBTTagCompound writeNBT(NBTTagCompound nbt) {
        NBTTagCompound nbtRender = new NBTTagCompound();
        nbtRender.setTag(BIND_NBT_TESR_RENDER_OBJ, obj.writeToNBT(new NBTTagCompound()));
        nbtRender.setDouble(BIND_NBT_TESR_RENDER_VECTOR_X, vector.x);
        nbtRender.setDouble(BIND_NBT_TESR_RENDER_VECTOR_Y, vector.y);
        nbtRender.setDouble(BIND_NBT_TESR_RENDER_VECTOR_Z, vector.z);
        nbtRender.setBoolean(BIND_NBT_TESR_RENDER_IS_RENDER, isRender);
        nbtRender.setBoolean(BIND_NBT_TESR_RENDER_IS_BLOCK, isBlock);
        nbt.setTag(BIND_NBT_TESR_RENDER, nbtRender);
        return nbt;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return writeNBT(new NBTTagCompound());
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTTagCompound nbtRender = nbt.getCompoundTag(BIND_NBT_TESR_RENDER);
        obj = new ItemStack(nbtRender.getCompoundTag(BIND_NBT_TESR_RENDER_OBJ));
        vector = new Vec3d(
                nbtRender.getDouble(BIND_NBT_TESR_RENDER_VECTOR_X),
                nbtRender.getDouble(BIND_NBT_TESR_RENDER_VECTOR_Y),
                nbtRender.getDouble(BIND_NBT_TESR_RENDER_VECTOR_Z)
        );
        isBlock = nbtRender.getBoolean(BIND_NBT_TESR_RENDER_IS_BLOCK);
        isRender = nbtRender.getBoolean(BIND_NBT_TESR_RENDER_IS_RENDER);
    }
}
