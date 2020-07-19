package com.elementtimes.elementtimes.common.pipeline;

import com.google.common.collect.ImmutableList;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;

/**
 * 管道传输内容信息

 */
public abstract class BaseElement<T> implements INBTSerializable<CompoundNBT> {

    protected int posIndex = 0;
    protected long totalTick = 0;
    protected int tick = 0;
    protected int pause = 0;
    protected List<BlockPos> path = Collections.emptyList();
    protected T element;
    protected String type;
    protected boolean back;

    public BaseElement(String type) {
        this.type = type;
        this.back = false;
    }

    public void tickIncrease() {
        totalTick++;
        tick++;
    }

    public BlockPos moveToNextPos() {
        posIndex++;
        tick = 0;
        return posIndex >= path.size() ? null : path.get(posIndex);
    }

    public BlockPos getNextPos() {
        int next = posIndex + 1;
        return next >= path.size() ? null : path.get(next);
    }

    public boolean isFinalPos() {
        return getNextPos() == null;
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

    public boolean isNotEmpty() {
        return !isEmpty();
    }

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
    public abstract BaseElement<T> copy();

    /**
     * 复制并设置不同物品
     * @return 复制
     */
    public BaseElement<T> copy(T element) {
        BaseElement<T> back = copy();
        back.element = element;
        return back;
    }

    /**
     * 复制并设置不同物品和路径
     * @return 复制
     */
    public BaseElement<T> copy(T element, ImmutableList<BlockPos> path, int index) {
        BaseElement<T> e = copy(element);
        e.posIndex = index;
        e.tick = 0;
        e.pause = 0;
        e.path = path;
        return e;
    }

    /**
     * 将 Element 保存到 NBT 数据
     * @return NBT
     */
    protected abstract CompoundNBT elementSerializer();

    /**
     * 从 NBT 数据中恢复 Element
     * @param compound NBT
     */
    protected abstract void elementDeserializer(CompoundNBT compound);

    /**
     * 获取该物品的泛型类型，多用于校验
     * @return 类型 Class
     */
    public abstract Class<T> getType();

    /**
     * 判断该 BaseElement 存储的物品预期是否为某种类型
     * @param type 预期类型
     * @return 是否为预期类型
     */
    public boolean is(Class<?> type) {
        Class<T> tClass = getType();
        return type == tClass || type.isAssignableFrom(tClass);
    }

    /**
     * 判断该 BaseElement 存储的物品实际是否为某种类型
     * @param type 物品类型
     * @return 是否为某类型
     */
    public boolean has(Class<?> type) {
        return type.isInstance(element);
    }

    /**
     * 获取物品
     * @return 物品
     */
    public T get() {
        return element;
    }

    /**
     * 尝试获取物品并转换为指定类型
     * @param type 目标类型 Class，用于校验
     * @param <ELEM> 目标类型
     * @return 物品
     */
    @SuppressWarnings("unchecked")
    public <ELEM> Optional<ELEM> get(Class<? extends ELEM> type) {
        return has(type) ? Optional.ofNullable((ELEM) element) : Optional.empty();
    }

    /**
     * 尝试进行泛型转换
     * @param type 目标类型 Class 用于校验
     * @param <ELEM> 目标类型
     * @return 转换结果
     */
    @SuppressWarnings("unchecked")
    public <ELEM> Optional<BaseElement<ELEM>> as(Class<? extends ELEM> type) {
        return is(type) ? Optional.of((BaseElement<ELEM>) this) : Optional.empty();
    }

    /**
     * 直接进行泛型强转
     * 注意：由于没有类型检查，所以请在确保安全的情况下执行该方法
     * @param <ELEM> 目标类型
     * @return 转换后类型
     */
    @SuppressWarnings("unchecked")
    public <ELEM> BaseElement<ELEM> cast() {
        return (BaseElement<ELEM>) this;
    }

    @Override public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("tick", tick);
        nbt.putLong("total", totalTick);
        nbt.putInt("pos", posIndex);
        nbt.putInt("pause", pause);
        ListNBT list = new ListNBT();
        for (BlockPos pos : path) {
            list.add(NBTUtil.writeBlockPos(pos));
        }
        nbt.put("pos", list);
        nbt.putString("type", type);
        nbt.put("element", elementSerializer());
        return nbt;
    }
    @Override public void deserializeNBT(CompoundNBT nbt) {
        tick = nbt.getInt("tick");
        totalTick = nbt.getLong("total");
        posIndex = nbt.getInt("pos");
        pause = nbt.getInt("pause");
        type = nbt.getString("type");
        ListNBT list = nbt.getList("pos", Constants.NBT.TAG_COMPOUND);
        path = new ArrayList<>();
        for (INBT inbt : list) {
            path.add(NBTUtil.readBlockPos((CompoundNBT) inbt));
        }
        elementDeserializer(nbt.getCompound("element"));
    }
}
