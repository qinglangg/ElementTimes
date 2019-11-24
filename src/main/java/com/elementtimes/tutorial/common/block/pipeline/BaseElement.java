package com.elementtimes.tutorial.common.block.pipeline;

import com.elementtimes.tutorial.common.tileentity.pipeline.BaseTilePipeline;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;

/**
 * 管道传输内容信息
 * @author luqin2007
 */
public abstract class BaseElement<T> implements INBTSerializable<NBTTagCompound> {

    // path
    public int posIndex = 0;
    public long totalTick = 0;
    public int tick = 0;
    public int pause = 0;
    public List<BlockPos> path = Collections.emptyList();
    // element
    public T element;
    public String type;
    private Set<BlockPos> removePos = new HashSet<>();
    private boolean removeAll = false;

    public BaseElement(String type) {
        this.type = type;
    }

    public void onTick(World world) {
        totalTick++;
        tick++;
        if (!path.isEmpty() && path.size() > posIndex) {
            BlockPos pos = path.get(posIndex);
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof BaseTilePipeline) {
                BaseTilePipeline pipeline = (BaseTilePipeline) te;
                int tick = pipeline.getKeepTime(this);
                if (this.tick > tick) {
                    posIndex++;
                    BaseElement back;
                    if (posIndex == path.size() - 1) {
                        back = pipeline.output(pos, this);
                    } else {
                        back = pipeline.send(pos, this);
                    }
                    if (back != null) {
                        if (!send(world, pos, pos)) {
                            drop(world, pos);
                        }
                    }
                }
            } else {
                drop(world, pos);
            }
        }
    }

    public boolean send(World world, BlockPos from, BlockPos container) {
        for (PLPath path : PLPath.find(world, this, from, container).values()) {
            if (!path.isEmpty()) {
                BlockPos tePos = path.path.get(0);
                TileEntity te = world.getTileEntity(tePos);
                if (te instanceof BaseTilePipeline) {
                    ((BaseTilePipeline) te).addElement(this);
                    posIndex = 1;
                    tick = 0;
                    pause = 0;
                    this.path = new ArrayList<>(path.path.size() + 2);
                    this.path.add(path.from);
                    this.path.addAll(path.path);
                    this.path.add(path.to);
                    return true;
                }
            }
        }
        return false;
    }

    public void remove(BlockPos pos) {
        removePos.add(pos);
    }

    public void destory() {
        removeAll = true;
    }

    public boolean isRemoved(BlockPos pos) {
        boolean b = removeAll || removePos.contains(pos);
        removePos.remove(pos);
        return b;
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
    public boolean isElementEqual(T o) {
        return Objects.equals(element, o);
    }

    /**
     * 复制
     * @return 复制
     */
    public abstract BaseElement<T> copy();

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
