package com.elementtimes.tutorial.other.pipeline;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;

public class PLInfo implements INBTSerializable<NBTTagCompound> {

    private static final Map<String, PathSelector> SELECTORS = new HashMap<>();

    private static final String BIND_NBT_PIPELINE_TYPE_INFO = "_nbt_pipeline_type_info_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_TYPE = "_nbt_pipeline_type_info_type_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_TICK = "_nbt_pipeline_type_info_tick_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_SELECTOR = "_nbt_pipeline_type_info_selector_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_SELECTOR_CLASS = "_nbt_pipeline_type_info_selector_class_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_IN = "_nbt_pipeline_type_info_in_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_OUT = "_nbt_pipeline_type_info_out_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_POS = "_nbt_pipeline_type_info_pos_";

    public int keepTick = 20;
    public String type;
    public PathSelector selector = defaultSelector();
    public String selectorClass = "";
    public List<BlockPos> listIn = new ArrayList<>(), listOut = new ArrayList<>();
    public BlockPos pos;

    private PLInfo() {}

    @SuppressWarnings("Duplicates")
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagCompound pipelineTypeInfo = new NBTTagCompound();
        pipelineTypeInfo.setInteger(BIND_NBT_PIPELINE_TYPE_INFO_TICK, keepTick);
        pipelineTypeInfo.setString(BIND_NBT_PIPELINE_TYPE_INFO_TYPE, type);
        pipelineTypeInfo.setString(BIND_NBT_PIPELINE_TYPE_INFO_SELECTOR, selector.name());
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
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTTagCompound pipelineTypeInfo = nbt.getCompoundTag(BIND_NBT_PIPELINE_TYPE_INFO);
        keepTick = pipelineTypeInfo.getInteger(BIND_NBT_PIPELINE_TYPE_INFO_TICK);
        type = pipelineTypeInfo.getString(BIND_NBT_PIPELINE_TYPE_INFO_TYPE);
        selectorClass = pipelineTypeInfo.getString(BIND_NBT_PIPELINE_TYPE_INFO_SELECTOR_CLASS);
        pos = NBTUtil.getPosFromTag(pipelineTypeInfo.getCompoundTag(BIND_NBT_PIPELINE_TYPE_INFO_POS));
        String nameSelector = pipelineTypeInfo.getString(BIND_NBT_PIPELINE_TYPE_INFO_SELECTOR);

        NBTTagList inList = (NBTTagList) pipelineTypeInfo.getTag(BIND_NBT_PIPELINE_TYPE_INFO_IN);
        listIn = new ArrayList<>(inList.tagCount());
        for (int i = 0; i < inList.tagCount(); i++) {
            listIn.add(i, NBTUtil.getPosFromTag(inList.getCompoundTagAt(i)));
        }

        NBTTagList outList = (NBTTagList) pipelineTypeInfo.getTag(BIND_NBT_PIPELINE_TYPE_INFO_OUT);
        listOut = new ArrayList<>(outList.tagCount());
        for (int i = 0; i < inList.tagCount(); i++) {
            listOut.add(i, NBTUtil.getPosFromTag(outList.getCompoundTagAt(i)));
        }

        selector = SELECTORS.get(nameSelector);
        if (selector == null) {
            try {
                Class c = Class.forName(selectorClass);
                for (Constructor constructor : c.getDeclaredConstructors()) {
                    constructor.setAccessible(true);
                    if (constructor.getParameterCount() == 0) {
                        Object obj = constructor.newInstance();
                        if (obj instanceof PathSelector) {
                            this.selector = (PathSelector) obj;
                            SELECTORS.put(this.selector.name(), this.selector);
                            break;
                        }
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                selector = defaultSelector();
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
        return Objects.hash(keepTick, pos, selector.name(), listIn, listOut);
    }

    public interface PathSelector {
        String name();

        @Nullable
        EnumFacing select(World world, PLInfo pipeline);

        boolean canConnectPipeline(World world, PLInfo pipeline, EnumFacing facing);

        boolean canConnectBlock(World world, BlockPos pos, EnumFacing facing);
    }

    public static PathSelector defaultSelector() {
        PathSelector selector = SELECTORS.get("_DEFAULT_");
        if (selector == null) {
            selector = new PathSelector() {
                @Override
                public String name() {
                    return "_DEFAULT_";
                }

                @Nullable
                @Override
                public EnumFacing select(World world, PLInfo pipeline) {
                    return null;
                }

                @Override
                public boolean canConnectPipeline(World world, PLInfo pipeline, EnumFacing facing) {
                    return false;
                }

                @Override
                public boolean canConnectBlock(World world, BlockPos pos, EnumFacing facing) {
                    return false;
                }
            };
            SELECTORS.put(selector.name(), selector);
        }
        return selector;
    }

    public static PLInfo fromNBT(NBTTagCompound nbt) {
        PLInfo info = new PLInfo();
        info.deserializeNBT(nbt);
        return info;
    }

    public static class PLInfoBuilder {
        private PLInfo pl = new PLInfo();

        public PLInfoBuilder(BlockPos pos) {
            pl.pos = pos;
        }

        public PLInfoBuilder setKeepTick(int keepTick) {
            pl.keepTick = keepTick;
            return this;
        }

        public PLInfoBuilder setSelector(String key, String clazz) {
            pl.selector = SELECTORS.get(key);
            if (pl.selector == null) {
                setSelector(clazz);
            } else {
                pl.selectorClass = pl.selector.getClass().getCanonicalName();
            }
            return this;
        }

        public PLInfoBuilder setSelector(String clazz) {
            try {
                Class c = Class.forName(clazz);
                for (Constructor constructor : c.getDeclaredConstructors()) {
                    constructor.setAccessible(true);
                    if (constructor.getParameterCount() == 0) {
                        Object obj = constructor.newInstance();
                        if (obj instanceof PathSelector) {
                            pl.selector = (PathSelector) obj;
                            pl.selectorClass = clazz;
                            SELECTORS.put(pl.selector.name(), pl.selector);
                            break;
                        }
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                pl.selector = defaultSelector();
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

        public PLInfoBuilder editLinkedIn(Function<List<BlockPos>, List<BlockPos>> editor) {
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

        public PLInfoBuilder editLinkedOut(Function<List<BlockPos>, List<BlockPos>> editor) {
            pl.listOut = editor.apply(pl.listOut);
            return this;
        }

        public PLInfo build() {
            return pl;
        }
    }
}
