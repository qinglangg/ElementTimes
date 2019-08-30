package com.elementtimes.tutorial.other.pipeline;

import com.elementtimes.tutorial.common.block.Pipeline;
import com.elementtimes.tutorial.other.pipeline.interfaces.PathAction;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static com.elementtimes.tutorial.other.pipeline.interfaces.PathAction.ACTIONS;

/**
 * 记录管道信息和功能
 * @author luqin2007
 */
@SuppressWarnings("WeakerAccess")
public class PLInfo implements INBTSerializable<NBTTagCompound> {

    static {
        PathAction.ItemLink.instance();
        PathAction.ItemIn.instance();
        PathAction.ItemOut.instance();
        PathAction.FluidLink.instance();
        PathAction.FluidIn.instance();
        PathAction.FluidOut.instance();
    }

    private static final String BIND_NBT_PIPELINE_TYPE_INFO = "_nbt_pipeline_type_info_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_TICK = "_nbt_pipeline_type_info_tick_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_TYPE = "_nbt_pipeline_type_info_type_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_SELECTOR = "_nbt_pipeline_type_info_selector_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_SELECTOR_CLASS = "_nbt_pipeline_type_info_selector_class_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_IN = "_nbt_pipeline_type_info_in_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_OUT = "_nbt_pipeline_type_info_out_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_POS = "_nbt_pipeline_type_info_pos_";

    public static final PLInfo ITEM = new PLInfoBuilder().setAction(PathAction.ItemLink.instance()).setType(Pipeline.TYPE_ITEM).setKeepTick(20).build();
    public static final PLInfo ITEM_IN = new PLInfoBuilder().setAction(PathAction.ItemIn.instance()).setType(Pipeline.TYPE_ITEM_IN).setKeepTick(20).build();
    public static final PLInfo ITEM_OUT = new PLInfoBuilder().setAction(PathAction.ItemOut.instance()).setType(Pipeline.TYPE_ITEM_OUT).setKeepTick(20).build();
    public static final PLInfo FLUID = new PLInfoBuilder().setAction(PathAction.FluidLink.instance()).setType(Pipeline.TYPE_FLUID).setKeepTick(20).build();
    public static final PLInfo FLUID_IN = new PLInfoBuilder().setAction(PathAction.FluidIn.instance()).setType(Pipeline.TYPE_FLUID_IN).setKeepTick(20).build();
    public static final PLInfo FLUID_OUT = new PLInfoBuilder().setAction(PathAction.FluidOut.instance()).setType(Pipeline.TYPE_FLUID_OUT).setKeepTick(20).build();

    /**
     * 当前段管道传输需要时间
     */
    public int keepTick = 20;
    /**
     * 管道种类
     */
    public String type = Pipeline.TYPE_ITEM;
    /**
     * 管道传输行为，包含选择可能管道，检查下一节是否有效，输入输出等
     */
    public PathAction action = PathAction.ItemLink.instance();
    /**
     * 可连接的输入输出位置
     */
    public LinkedList<BlockPos> listIn = new LinkedList<>(), listOut = new LinkedList<>();
    /**
     * 当前管道所在位置
     */
    public BlockPos pos = BlockPos.ORIGIN;
    private String selectorClass = "";

    /**
     * 遍历标记
     */
    private long signal;

    public PLInfo copy() {
        PLInfo info = new PLInfo();
        info.keepTick = keepTick;
        info.type = type;
        info.action = action;
        info.selectorClass = selectorClass;
        info.listIn = new LinkedList<>();
        info.listOut = new LinkedList<>();
        info.pos = pos;

        info.listIn.addAll(listIn);
        info.listOut.addAll(listOut);

        return info;
    }

    /**
     * 获取所有可能的出口，用于确定起始点和路径
     * 每次调用都会遍历整个网络，因此若确定网络没有变化，尽量不要重复调用
     * @param world 所在世界
     * @param element 传输内容
     * @return 所有可能的路径
     */
    public Map<BlockPos, PLPath> allValidOutput(World world, PLElement element, BlockPos from) {
        Map<BlockPos, PLPath> pathMap = new LinkedHashMap<>();
        if (PLEvent.onPathFindPre(element, pathMap, from, world)) {
            findWay(world, element, pathMap, System.currentTimeMillis(), new LinkedList<>(), this, from);
        }
        PLEvent.onPathFindPost(element, pathMap, from, world);
        return pathMap;
    }

    private void findWay(World world, PLElement element, Map<BlockPos, PLPath> paths, long signal,
                         LinkedList<PLInfo> before, PLInfo pos, BlockPos from) {
        if (pos.signal != signal) {
            pos.signal = signal;
            for (BlockPos blockPos : pos.listOut) {
                Object e = pos.action.output(world, from, blockPos, element, true);
                if (!element.serializer.isObjectEqual(e, element.element)) {
                    paths.put(blockPos, new PLPath(from, blockPos, before, pos));
                }
            }
            PLInfo[] plInfos = pos.action.select(world, pos, element);
            for (PLInfo pl : plInfos) {
                LinkedList<PLInfo> next = new LinkedList<>(before);
                next.add(pos);
                findWay(world, element, paths, signal, next, pl, from);
            }
        } else {
            LinkedList<PLInfo> l = new LinkedList<>(before);
            l.add(pos);
            long t = l.stream().mapToLong(p -> p.keepTick).sum();
            paths.values().forEach(p -> p.test(l, t));
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagCompound pipelineTypeInfo = new NBTTagCompound();
        pipelineTypeInfo.setInteger(BIND_NBT_PIPELINE_TYPE_INFO_TICK, keepTick);
        pipelineTypeInfo.setString(BIND_NBT_PIPELINE_TYPE_INFO_TYPE, type);
        pipelineTypeInfo.setString(BIND_NBT_PIPELINE_TYPE_INFO_SELECTOR, action.name());
        pipelineTypeInfo.setString(BIND_NBT_PIPELINE_TYPE_INFO_SELECTOR_CLASS, selectorClass);
        pipelineTypeInfo.setTag(BIND_NBT_PIPELINE_TYPE_INFO_POS, NBTUtil.createPosTag(pos));
        NBTTagList in = new NBTTagList();
        for (BlockPos pos : listIn) {
            in.appendTag(NBTUtil.createPosTag(pos));
        }
        NBTTagList out = new NBTTagList();
        for (BlockPos pos : listOut) {
            out.appendTag(NBTUtil.createPosTag(pos));
        }

        pipelineTypeInfo.setTag(BIND_NBT_PIPELINE_TYPE_INFO_IN, in);
        pipelineTypeInfo.setTag(BIND_NBT_PIPELINE_TYPE_INFO_OUT, out);
        nbt.setTag(BIND_NBT_PIPELINE_TYPE_INFO, pipelineTypeInfo);
        return nbt;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTTagCompound pipelineTypeInfo = nbt.getCompoundTag(BIND_NBT_PIPELINE_TYPE_INFO);

        keepTick = pipelineTypeInfo.getInteger(BIND_NBT_PIPELINE_TYPE_INFO_TICK);
        type = pipelineTypeInfo.getString(BIND_NBT_PIPELINE_TYPE_INFO_TYPE);
        selectorClass = pipelineTypeInfo.getString(BIND_NBT_PIPELINE_TYPE_INFO_SELECTOR_CLASS);
        pos = NBTUtil.getPosFromTag(pipelineTypeInfo.getCompoundTag(BIND_NBT_PIPELINE_TYPE_INFO_POS));
        String nameSelector = pipelineTypeInfo.getString(BIND_NBT_PIPELINE_TYPE_INFO_SELECTOR);

        NBTTagList inList = (NBTTagList) pipelineTypeInfo.getTag(BIND_NBT_PIPELINE_TYPE_INFO_IN);
        listIn.clear();
        if (inList.tagCount() > 0) {
            for (int i = 0; i < inList.tagCount(); i++) {
                listIn.add(i, NBTUtil.getPosFromTag(inList.getCompoundTagAt(i)));
            }
        }

        NBTTagList outList = (NBTTagList) pipelineTypeInfo.getTag(BIND_NBT_PIPELINE_TYPE_INFO_OUT);
        listOut.clear();
        if (outList.tagCount() > 0) {
            for (int i = 0; i < outList.tagCount(); i++) {
                listOut.add(i, NBTUtil.getPosFromTag(outList.getCompoundTagAt(i)));
            }
        }

        action = ACTIONS.get(nameSelector);
        if (action == null) {
            try {
                Class c = Class.forName(selectorClass);
                for (Constructor constructor : c.getDeclaredConstructors()) {
                    constructor.setAccessible(true);
                    if (constructor.getParameterCount() == 0) {
                        Object obj = constructor.newInstance();
                        if (obj instanceof PathAction) {
                            this.action = (PathAction) obj;
                            ACTIONS.put(this.action.name(), this.action);
                            break;
                        }
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                action = PathAction.ItemLink.instance();
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof PLInfo)
                && ((PLInfo) obj).keepTick == keepTick
                && ((PLInfo) obj).pos.equals(pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keepTick, pos, action.name(), listIn, listOut);
    }

    public static PLInfo fromNBT(NBTTagCompound nbt) {
        PLInfo info = new PLInfo();
        info.deserializeNBT(nbt);
        return info;
    }

    @SuppressWarnings("unused")
    public static class PLInfoBuilder {
        private PLInfo pl = new PLInfo();

        public PLInfoBuilder setPos(BlockPos pos) {
            pl.pos = pos;
            return this;
        }

        public PLInfoBuilder setKeepTick(int keepTick) {
            pl.keepTick = keepTick;
            return this;
        }

        public PLInfoBuilder setType(String type) {
            pl.type = type;
            return this;
        }

        public PLInfoBuilder setAction(String name, String clazz) {
            pl.action = ACTIONS.get(name);
            if (pl.action == null) {
                setAction(clazz);
            } else {
                pl.selectorClass = pl.action.getClass().getName();
            }
            return this;
        }

        public PLInfoBuilder setAction(PathAction action) {
            pl.action = action;
            pl.selectorClass = action.getClass().getName();
            ACTIONS.put(pl.action.name(), pl.action);
            return this;
        }

        @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
        public PLInfoBuilder setAction(String clazz) {
            try {
                Class c = Class.forName(clazz);
                for (Constructor constructor : c.getDeclaredConstructors()) {
                    constructor.setAccessible(true);
                    if (constructor.getParameterCount() == 0) {
                        Object obj = constructor.newInstance();
                        if (obj instanceof PathAction) {
                            setAction((PathAction) obj);
                            break;
                        }
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                pl.action = PathAction.ItemLink.instance();
                pl.selectorClass = "";
            }
            return this;
        }

        public PLInfoBuilder addLinkedIn(BlockPos pos) {
            pl.listIn.add(pos);
            return this;
        }

        public PLInfoBuilder removeLinkedIn(BlockPos pos) {
            pl.listIn.remove(pos);
            return this;
        }

        public PLInfoBuilder editLinkedIn(Function<LinkedList<BlockPos>, LinkedList<BlockPos>> editor) {
            pl.listIn = editor.apply(pl.listIn);
            return this;
        }

        public PLInfoBuilder addLinkedOut(BlockPos pos) {
            pl.listOut.add(pos);
            return this;
        }

        public PLInfoBuilder removeLinkedOut(BlockPos pos) {
            pl.listOut.remove(pos);
            return this;
        }

        public PLInfoBuilder editLinkedOut(Function<LinkedList<BlockPos>, LinkedList<BlockPos>> editor) {
            pl.listOut = editor.apply(pl.listOut);
            return this;
        }

        public PLInfo build() {
            return pl;
        }
    }
}
