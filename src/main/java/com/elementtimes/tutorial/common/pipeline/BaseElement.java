package com.elementtimes.tutorial.common.pipeline;

import com.elementtimes.tutorial.common.pipeline.test.PLTestNetwork;
import com.elementtimes.tutorial.interfaces.ITilePipeline;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 管道传输内容信息
 * @author luqin2007
 */
public abstract class BaseElement implements INBTSerializable<NBTTagCompound> {

    // path
    public int posIndex = 0;
    public long totalTick = 0;
    public int tick = 0;
    public int pause = 0;
    public List<BlockPos> path = Collections.emptyList();
    // element
    public Object element;
    public String type;

    public BaseElement(String type) {
        this.type = type;
    }

    public void tickIncrease() {
        totalTick++;
        tick++;
    }

    public BlockPos nextPos() {
        posIndex++;
        return posIndex >= path.size() ? null : path.get(posIndex);
    }

    public boolean isFinalPos() {
        return posIndex == path.size() - 1;
    }

    public boolean send(World world, ITilePipeline pipeline, BlockPos container) {
        BlockPos from = pipeline.getPos();
        for (PLPath path : PLPath.find(world, this, from, container)) {
            if (!path.isEmpty()) {
                pipeline.addElement(this);
                posIndex = 1;
                tick = 0;
                pause = 0;
                this.path = new ArrayList<>();
                this.path.add(path.from);
                this.path.addAll(path.path);
                PLTestNetwork.sendMessage(PLTestNetwork.TestElementType.send, this, pipeline.getPos());
                return true;
            }
        }
        return false;
    }

    /**
     * 将当前传输物品掉落到世界中
     * @param world 所在世界
     * @param pos 所处位置
     */
    public abstract void drop(World world, BlockPos pos);

    /**
     * 传输物品是否为空
     * @return 物品为空
     */
    public abstract boolean isEmpty();

    /**
     * 传输物品是否相同
     * @param o 物品
     * @return 是否相同
     */
    public boolean isElementEqual(Object o) {
        return Objects.equals(element, o);
    }

    /**
     * 复制
     * @return 复制
     */
    public abstract BaseElement copy();

    /**
     * 将 Element 保存到 NBT 数据
     * @return NBT
     */
    protected abstract NBTTagCompound elementSerializer();

    /**
     * 从 NBT 数据中恢复 Element
     * @param compound NBT
     */
    protected abstract void elementDeserializer(NBTTagCompound compound);

    public <T> T get(Class<? extends T> type) {
        return getOrDefault(type, null);
    }

    public <T> T getOrDefault(Class<? extends T> type, T defValue) {
        if (type.isInstance(element)) {
            //noinspection unchecked
            return (T) element;
        }
        return defValue;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("tick", tick);
        nbt.setLong("total", totalTick);
        nbt.setInteger("pos", posIndex);
        nbt.setInteger("pause", pause);
        NBTTagList list = new NBTTagList();
        for (BlockPos pos : path) {
            list.appendTag(NBTUtil.createPosTag(pos));
        }
        nbt.setTag("pos", list);
        nbt.setString("type", type);
        nbt.setTag("element", elementSerializer());
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        tick = nbt.getInteger("tick");
        totalTick = nbt.getLong("total");
        posIndex = nbt.getInteger("pos");
        pause = nbt.getInteger("pause");
        type = nbt.getString("type");
        NBTTagList list = nbt.getTagList("pos", Constants.NBT.TAG_COMPOUND);
        path = new ArrayList<>(list.tagCount());
        for (NBTBase inbt : list) {
            path.add(NBTUtil.getPosFromTag((NBTTagCompound) inbt));
        }
        elementDeserializer(nbt.getCompoundTag("element"));
    }
}
