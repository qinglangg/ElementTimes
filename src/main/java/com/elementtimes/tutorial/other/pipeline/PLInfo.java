package com.elementtimes.tutorial.other.pipeline;

import com.elementtimes.tutorial.common.block.Pipeline;
import com.elementtimes.tutorial.common.tileentity.TilePipeline;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;

public class PLInfo implements INBTSerializable<NBTTagCompound> {

    private static final Map<String, PathAction> ACTIONS;
    static {
        ACTIONS = new HashMap<>();
        PathActionItem.instance();
        PathActionItemIn.instance();
        PathActionItemOut.instance();
        PathActionFluid.instance();
        PathActionFluidIn.instance();
        PathActionFluidOut.instance();
    }

    private static final String BIND_NBT_PIPELINE_TYPE_INFO = "_nbt_pipeline_type_info_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_TICK = "_nbt_pipeline_type_info_tick_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_TYPE = "_nbt_pipeline_type_info_type_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_SELECTOR = "_nbt_pipeline_type_info_selector_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_SELECTOR_CLASS = "_nbt_pipeline_type_info_selector_class_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_IN = "_nbt_pipeline_type_info_in_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_OUT = "_nbt_pipeline_type_info_out_";
    private static final String BIND_NBT_PIPELINE_TYPE_INFO_POS = "_nbt_pipeline_type_info_pos_";

    public static final PLInfo ITEM = new PLInfoBuilder()
            .setAction(PathActionItem.instance().name(), "com.elementtimes.tutorial.other.pipeline.PLInfo$PathActionItem")
            .setType(Pipeline.TYPE_ITEM).setKeepTick(20).build();
    public static final PLInfo ITEM_IN = new PLInfoBuilder()
            .setAction(PathActionItemIn.instance().name(), "com.elementtimes.tutorial.other.pipeline.PLInfo$PathActionItemIn")
            .setType(Pipeline.TYPE_ITEM_IN).setKeepTick(20).build();
    public static final PLInfo ITEM_OUT = new PLInfoBuilder()
            .setAction(PathActionItemOut.instance().name(), "com.elementtimes.tutorial.other.pipeline.PLInfo$PathActionItemOut")
            .setType(Pipeline.TYPE_ITEM_OUT).setKeepTick(20).build();
    public static final PLInfo FLUID = new PLInfoBuilder()
            .setAction(PathActionFluid.instance().name(), "com.elementtimes.tutorial.other.pipeline.PLInfo$PathActionFluid")
            .setType(Pipeline.TYPE_FLUID).setKeepTick(20).build();
    public static final PLInfo FLUID_IN = new PLInfoBuilder()
            .setAction(PathActionFluidIn.instance().name(), "com.elementtimes.tutorial.other.pipeline.PLInfo$PathActionFluidIn")
            .setType(Pipeline.TYPE_FLUID_IN).setKeepTick(20).build();
    public static final PLInfo FLUID_OUT = new PLInfoBuilder()
            .setAction(PathActionFluidOut.instance().name(), "com.elementtimes.tutorial.other.pipeline.PLInfo$PathActionFluidOut")
            .setType(Pipeline.TYPE_FLUID_OUT).setKeepTick(20).build();

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
    public PathAction action = PathActionItem.instance();
    /**
     * 可连接的输入输出位置
     */
    public LinkedList<BlockPos> listIn = new LinkedList<>(), listOut = new LinkedList<>();
    /**
     * 当前管道所在位置
     */
    public BlockPos pos = BlockPos.ORIGIN;
    private String selectorClass = "";
    private long signal; // 遍历标记

    private PLInfo() {}

    public PLInfo copy() {
        PLInfo info = new PLInfo();
        info.keepTick = keepTick;
        info.type = type;
        info.action = action;
        info.selectorClass = selectorClass;
        info.listIn = new LinkedList<>();
        info.listOut = new LinkedList<>();
        info.pos = pos;

        Collections.copy(info.listIn, listIn);
        Collections.copy(info.listOut, listOut);

        return info;
    }

    /**
     * 获取所有可能的出口，用于确定起始点和路径
     * 每次调用都会遍历整个网络，因此若确定网络没有变化，尽量不要重复调用
     * @param world 所在世界
     * @param from 路径起点
     * @param element 传输内容
     * @return 所有可能的路径
     */
    public Map<BlockPos, PLPath> allValidOutput(World world, BlockPos from, PLElement element) {
        Map<BlockPos, PLPath> pathMap = new HashMap<>(16);
        findWay(world, element, pathMap, System.currentTimeMillis(), from, new LinkedList<>(), this);
        return pathMap;
    }

    private void findWay(World world, PLElement element, Map<BlockPos, PLPath> paths, long signal,
                         BlockPos from, LinkedList<PLInfo> before, PLInfo pos) {
        if (pos.signal != signal) {
            pos.signal = signal;
            findOutput(world, paths, from, before, pos, element);
            PLInfo[] plInfos = pos.action.select(world, pos, element);
            for (PLInfo pl : plInfos) {
                LinkedList<PLInfo> next = new LinkedList<>(before);
                next.add(pos);
                findWay(world, element, paths, signal, from, next, pl);
            }
        } else {
            LinkedList<PLInfo> l = new LinkedList<>(before);
            l.add(pos);
            long t = l.stream().mapToLong(p -> p.keepTick).sum();
            paths.values().forEach(p -> p.test(l, t));
        }
    }

    private void findOutput(World world, Map<BlockPos, PLPath> pathMap, BlockPos from,
                            LinkedList<PLInfo> before, PLInfo next, PLElement element) {
        for (BlockPos blockPos : next.listOut) {
            if (next.action.canOutput(world, next, blockPos, element)) {
                PLPath path = new PLPath(from, blockPos, before, next);
                PLPath pathExist = pathMap.get(blockPos);
                if (pathExist != null && pathExist.calcTick() > path.calcTick()) {
                    pathMap.put(blockPos, path);
                }
            }
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
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTTagCompound pipelineTypeInfo = nbt.getCompoundTag(BIND_NBT_PIPELINE_TYPE_INFO);
        keepTick = pipelineTypeInfo.getInteger(BIND_NBT_PIPELINE_TYPE_INFO_TICK);
        type = pipelineTypeInfo.getString(BIND_NBT_PIPELINE_TYPE_INFO_TYPE);
        selectorClass = pipelineTypeInfo.getString(BIND_NBT_PIPELINE_TYPE_INFO_SELECTOR_CLASS);
        pos = NBTUtil.getPosFromTag(pipelineTypeInfo.getCompoundTag(BIND_NBT_PIPELINE_TYPE_INFO_POS));
        String nameSelector = pipelineTypeInfo.getString(BIND_NBT_PIPELINE_TYPE_INFO_SELECTOR);

        NBTTagList inList = (NBTTagList) pipelineTypeInfo.getTag(BIND_NBT_PIPELINE_TYPE_INFO_IN);
        listIn = new LinkedList<>();
        for (int i = 0; i < inList.tagCount(); i++) {
            listIn.add(i, NBTUtil.getPosFromTag(inList.getCompoundTagAt(i)));
        }

        NBTTagList outList = (NBTTagList) pipelineTypeInfo.getTag(BIND_NBT_PIPELINE_TYPE_INFO_OUT);
        listOut = new LinkedList<>();
        for (int i = 0; i < inList.tagCount(); i++) {
            listOut.add(i, NBTUtil.getPosFromTag(outList.getCompoundTagAt(i)));
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
                action = PathActionItem.instance();
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

    public interface PathAction {
        /**
         * 获取名称，用于从 NBT 恢复时避免重复创建对象
         * @return 名称
         */
        String name();

        /**
         * 可选管道传输方向，仅包含管道连接
         * 用于路径查询
         * @param world 所在世界
         * @param pipeline 所在管道
         * @param element 传输内容
         * @return 所有可选方向
         */
        default PLInfo[] select(World world, PLInfo pipeline, @Nullable PLElement element) {
            TileEntity tileEntity = world.getTileEntity(pipeline.pos);
            if (tileEntity instanceof TilePipeline) {
                TilePipeline tp = (TilePipeline) tileEntity;
                ArrayList<PLInfo> faces = new ArrayList<>(6);
                for (EnumFacing facing : EnumFacing.values()) {
                    if (tp.isConnected(facing)) {
                        BlockPos pos = tileEntity.getPos().offset(facing);
                        TileEntity te = world.getTileEntity(pos);
                        if (te instanceof TilePipeline && ((TilePipeline) te).isConnected(facing.getOpposite())) {
                            faces.add(((TilePipeline) te).getInfo());
                        }
                    }
                }
                return faces.toArray(new PLInfo[0]);
            } else {
                return new PLInfo[0];
            }
        }

        /**
         * 检查当前位置管道是否有效
         * @param world 所在世界
         * @param pipeline 管道
         * @param element 传输内容
         * @return 是否有效
         */
        boolean isValid(World world, PLInfo pipeline, @Nullable PLElement element);

        /**
         * 某位置是否可以输出
         * 用于检查输出
         * @param world 所在世界
         * @param pos 待连接位置
         * @param element 传输内容
         * @return 是否可以连接
         */
        boolean canOutput(World world, PLInfo path, BlockPos pos, @Nullable PLElement element);

        /**
         * 某位置是否可以传输
         * @param world 所在世界
         * @param pl 下一节管道
         * @param element 传输内容
         * @return 是否可以传输
         */
        boolean canSend(World world, PLInfo pl, @Nullable PLElement element);

        /**
         * 管道输出
         * @param world 所在世界
         * @param pos 输出位置
         * @param element 输出内容
         * @return 剩余物品
         */
        PLElement output(World world, BlockPos pos, @Nullable PLElement element);
    }

    public static PLInfo fromNBT(NBTTagCompound nbt) {
        PLInfo info = new PLInfo();
        info.deserializeNBT(nbt);
        return info;
    }

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

        public PLInfoBuilder setAction(String key, String clazz) {
            pl.action = ACTIONS.get(key);
            if (pl.action == null) {
                setAction(clazz);
            } else {
                pl.selectorClass = pl.action.getClass().getCanonicalName();
            }
            return this;
        }

        public PLInfoBuilder setAction(String clazz) {
            try {
                Class c = Class.forName(clazz);
                for (Constructor constructor : c.getDeclaredConstructors()) {
                    constructor.setAccessible(true);
                    if (constructor.getParameterCount() == 0) {
                        Object obj = constructor.newInstance();
                        if (obj instanceof PathAction) {
                            pl.action = (PathAction) obj;
                            pl.selectorClass = clazz;
                            ACTIONS.put(pl.action.name(), pl.action);
                            break;
                        }
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                pl.action = PathActionItem.instance();
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

    public static class PathActionItem implements PathAction {

        public static String NAME = "_ITEM_LINK_";
        public static PathAction instance() {
            PathAction pathAction = ACTIONS.get(NAME);
            if (pathAction == null) {
                pathAction = new PathActionItem();
                ACTIONS.put(NAME, pathAction);
            }
            return pathAction;
        }

        @Override
        public String name() {
            return NAME;
        }

        @Override
        public boolean isValid(World world, PLInfo pipeline, PLElement element) {
            TileEntity te = world.getTileEntity(pipeline.pos);
            return world.getBlockState(pipeline.pos) instanceof Pipeline
                    && te instanceof TilePipeline
                    && ((TilePipeline) te).getInfo().type.equals(Pipeline.TYPE_ITEM);
        }

        @Override
        public boolean canOutput(World world, PLInfo path, BlockPos pos, @Nullable PLElement element) {
            return false;
        }

        @Override
        public boolean canSend(World world, PLInfo pl, @Nullable PLElement element) {
            return pl.action.isValid(world, pl, element)
                    && pl.type.equals(Pipeline.TYPE_ITEM)
                    && pl.type.equals(Pipeline.TYPE_ITEM_IN)
                    && pl.type.equals(Pipeline.TYPE_ITEM_OUT);
        }

        @Override
        public PLElement output(World world, BlockPos pos, @Nullable PLElement element) {
            return element;
        }
    }

    public static class PathActionItemIn extends PathActionItem {

        public static String NAME = "_ITEM_IN_";
        public static PathAction instance() {
            PathAction action = ACTIONS.get(NAME);
            if (action == null) {
                action = new PathActionItemIn();
                ACTIONS.put(NAME, action);
            }
            return action;
        }

        @Override
        public String name() {
            return NAME;
        }

        @Override
        public boolean isValid(World world, PLInfo pipeline, PLElement element) {
            TileEntity te = world.getTileEntity(pipeline.pos);
            return world.getBlockState(pipeline.pos) instanceof Pipeline
                    && te instanceof TilePipeline
                    && ((TilePipeline) te).getInfo().type.equals(Pipeline.TYPE_ITEM_IN);
        }
    }

    public static class PathActionItemOut extends PathActionItem {

        public static String NAME = "_ITEM_OUT_";
        public static PathAction instance() {
            PathAction action = ACTIONS.get(NAME);
            if (action == null) {
                action = new PathActionItemOut();
                ACTIONS.put(NAME, action);
            }
            return action;
        }

        @Override
        public String name() {
            return NAME;
        }

        @Override
        public boolean isValid(World world, PLInfo pipeline, PLElement element) {
            TileEntity te = world.getTileEntity(pipeline.pos);
            return world.getBlockState(pipeline.pos) instanceof Pipeline
                    && te instanceof TilePipeline
                    && ((TilePipeline) te).getInfo().type.equals(Pipeline.TYPE_ITEM_OUT);
        }

        @Override
        public boolean canOutput(World world, PLInfo path, BlockPos pos, PLElement element) {
            if (element.element instanceof ItemStack) {
                TileEntity te = world.getTileEntity(pos);
                if (te != null) {
                    BlockPos before = path.pos;
                    // face
                    EnumFacing facing;
                    int zeroCount = 0;
                    int dx = pos.getX() - before.getX();
                    if (dx == 0) {
                        zeroCount++;
                    }
                    int dy = pos.getY() - before.getY();
                    if (dy == 0) {
                        zeroCount++;
                    }
                    int dz = pos.getZ() - before.getZ();
                    if (dz == 0) {
                        zeroCount++;
                    }
                    if (zeroCount == 2) {
                        if (dx == 1) {
                            facing = EnumFacing.EAST;
                        } else if (dx == -1) {
                            facing = EnumFacing.WEST;
                        } if (dy == 1) {
                            facing = EnumFacing.UP;
                        } else if (dy == -1) {
                            facing = EnumFacing.DOWN;
                        } if (dz == 1) {
                            facing = EnumFacing.SOUTH;
                        } else if (dz == -1) {
                            facing = EnumFacing.NORTH;
                        } else {
                            facing = null;
                        }
                    } else {
                        facing = null;
                    }
                    if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)) {
                        IItemHandler capability = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
                        ItemStack items = (ItemStack) element.element;
                        ItemStack itemsCopy = items.copy();
                        ItemStack itemStack = ItemHandlerHelper.insertItem(capability, itemsCopy, true);
                        return !itemStack.isItemEqual(items) || itemStack.getCount() != items.getCount();
                    }
                }
            }
            return false;
        }

        @Override
        public boolean canSend(World world, PLInfo pl, PLElement element) {
            return pl.action.isValid(world, pl, element)
                    && pl.type.equals(Pipeline.TYPE_ITEM)
                    && pl.type.equals(Pipeline.TYPE_ITEM_IN)
                    && pl.type.equals(Pipeline.TYPE_ITEM_OUT);
        }

        @Override
        public PLElement output(World world, BlockPos pos, PLElement element) {
            return element;
        }
    }

    public static class PathActionFluid implements PathAction {

        public static String NAME = "_FLUID_LINK_";
        public static PathAction instance() {
            PathAction pathAction = ACTIONS.get(NAME);
            if (pathAction == null) {
                pathAction = new PathActionFluid();
                ACTIONS.put(NAME, pathAction);
            }
            return pathAction;
        }

        @Override
        public String name() {
            return NAME;
        }

        @Override
        public boolean isValid(World world, PLInfo pipeline, PLElement element) {
            TileEntity te = world.getTileEntity(pipeline.pos);
            return world.getBlockState(pipeline.pos) instanceof Pipeline
                    && te instanceof TilePipeline
                    && ((TilePipeline) te).getInfo().type.equals(Pipeline.TYPE_FLUID);
        }

        @Override
        public boolean canOutput(World world, PLInfo path, BlockPos pos, @Nullable PLElement element) {
            return false;
        }

        @Override
        public boolean canSend(World world, PLInfo pl, @Nullable PLElement element) {
            return pl.action.isValid(world, pl, element)
                    && pl.type.equals(Pipeline.TYPE_FLUID)
                    && pl.type.equals(Pipeline.TYPE_FLUID_IN)
                    && pl.type.equals(Pipeline.TYPE_FLUID_OUT);
        }

        @Override
        public PLElement output(World world, BlockPos pos, @Nullable PLElement element) {
            return element;
        }
    }

    public static class PathActionFluidIn extends PathActionFluid {

        public static String NAME = "_FLUID_IN_";
        public static PathAction instance() {
            PathAction action = ACTIONS.get(NAME);
            if (action == null) {
                action = new PathActionFluidIn();
                ACTIONS.put(NAME, action);
            }
            return action;
        }

        @Override
        public String name() {
            return NAME;
        }

        @Override
        public boolean isValid(World world, PLInfo pipeline, PLElement element) {
            TileEntity te = world.getTileEntity(pipeline.pos);
            return world.getBlockState(pipeline.pos) instanceof Pipeline
                    && te instanceof TilePipeline
                    && ((TilePipeline) te).getInfo().type.equals(Pipeline.TYPE_FLUID_IN);
        }
    }

    public static class PathActionFluidOut extends PathActionFluid {

        public static String NAME = "_FLUID_OUT_";
        public static PathAction instance() {
            PathAction action = ACTIONS.get(NAME);
            if (action == null) {
                action = new PathActionFluidOut();
                ACTIONS.put(NAME, action);
            }
            return action;
        }

        @Override
        public String name() {
            return NAME;
        }

        @Override
        public boolean isValid(World world, PLInfo pipeline, PLElement element) {
            TileEntity te = world.getTileEntity(pipeline.pos);
            return world.getBlockState(pipeline.pos) instanceof Pipeline
                    && te instanceof TilePipeline
                    && ((TilePipeline) te).getInfo().type.equals(Pipeline.TYPE_ITEM_OUT);
        }

        @Override
        public boolean canOutput(World world, PLInfo path, BlockPos pos, PLElement element) {
            if (element.element instanceof FluidStack) {
                TileEntity te = world.getTileEntity(pos);
                if (te != null) {
                    BlockPos before = path.pos;
                    // face
                    EnumFacing facing;
                    int zeroCount = 0;
                    int dx = pos.getX() - before.getX();
                    if (dx == 0) {
                        zeroCount++;
                    }
                    int dy = pos.getY() - before.getY();
                    if (dy == 0) {
                        zeroCount++;
                    }
                    int dz = pos.getZ() - before.getZ();
                    if (dz == 0) {
                        zeroCount++;
                    }
                    if (zeroCount == 2) {
                        if (dx == 1) {
                            facing = EnumFacing.EAST;
                        } else if (dx == -1) {
                            facing = EnumFacing.WEST;
                        } if (dy == 1) {
                            facing = EnumFacing.UP;
                        } else if (dy == -1) {
                            facing = EnumFacing.DOWN;
                        } if (dz == 1) {
                            facing = EnumFacing.SOUTH;
                        } else if (dz == -1) {
                            facing = EnumFacing.NORTH;
                        } else {
                            facing = null;
                        }
                    } else {
                        facing = null;
                    }
                    if (te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)) {
                        IFluidHandler capability = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
                        FluidStack fluids = (FluidStack) element.element;
                        FluidStack fluidsCopy = new FluidStack(fluids, fluids.amount);
                        int fluidCount = capability.fill(fluidsCopy, false);
                        return fluidCount != 0;
                    }
                }
            }
            return false;
        }

        @Override
        public boolean canSend(World world, PLInfo pl, PLElement element) {
            return pl.action.isValid(world, pl, element)
                    && pl.type.equals(Pipeline.TYPE_ITEM)
                    && pl.type.equals(Pipeline.TYPE_ITEM_IN)
                    && pl.type.equals(Pipeline.TYPE_ITEM_OUT);
        }

        @Override
        public PLElement output(World world, BlockPos pos, PLElement element) {
            return element;
        }
    }
}
