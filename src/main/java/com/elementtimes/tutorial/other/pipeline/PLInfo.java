package com.elementtimes.tutorial.other.pipeline;

import com.elementtimes.tutorial.common.block.Pipeline;
import com.elementtimes.tutorial.common.tileentity.TilePipeline;
import com.elementtimes.tutorial.util.BlockUtil;
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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;

/**
 * 记录管道信息和功能
 * @author luqin2007
 */
@SuppressWarnings("WeakerAccess")
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

    /**
     * 遍历标记
     */
    private long signal;

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
     * @param element 传输内容
     * @return 所有可能的路径
     */
    public Map<BlockPos, PLPath> allValidOutput(World world, PLElement element, BlockPos from) {
        Map<BlockPos, PLPath> pathMap = new HashMap<>(16);
        findWay(world, element, pathMap, System.currentTimeMillis(), new LinkedList<>(), this, from);
        return pathMap;
    }

    private void findWay(World world, PLElement element, Map<BlockPos, PLPath> paths, long signal,
                         LinkedList<PLInfo> before, PLInfo pos, BlockPos from) {
        if (pos.signal != signal) {
            pos.signal = signal;
            findOutput(world, pos, before, paths, element, from);
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

    private void findOutput(World world, PLInfo next, LinkedList<PLInfo> before, Map<BlockPos, PLPath> paths, PLElement element, BlockPos from) {
        for (BlockPos blockPos : next.listOut) {
            Object e = next.action.output(world, blockPos, element, true);
            if (!e.equals(element.element)) {
                paths.put(blockPos, new PLPath(from, blockPos, before, next));
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
                System.out.println(inList.getCompoundTagAt(i));
                listIn.add(i, NBTUtil.getPosFromTag(inList.getCompoundTagAt(i)));
            }
        }

        NBTTagList outList = (NBTTagList) pipelineTypeInfo.getTag(BIND_NBT_PIPELINE_TYPE_INFO_OUT);
        listOut.clear();
        if (outList.tagCount() > 0) {
            for (int i = 0; i < outList.tagCount(); i++) {
                System.out.println(outList.getCompoundTagAt(i));
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
        default PLInfo[] select(World world, PLInfo pipeline, @SuppressWarnings("unused") PLElement element) {
            TileEntity tileEntity = world.getTileEntity(pipeline.pos);
            if (tileEntity instanceof TilePipeline) {
                TilePipeline tp = (TilePipeline) tileEntity;
                return Arrays.stream(EnumFacing.values())
                        .filter(tp::isConnected)
                        .map(f -> {
                            BlockPos pos = tileEntity.getPos().offset(f);
                            TileEntity te = world.getTileEntity(pos);
                            if ((te instanceof TilePipeline) && ((TilePipeline) te).isConnected(f.getOpposite())) {
                                return ((TilePipeline) te).getInfo();
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .toArray(PLInfo[]::new);
            } else {
                return new PLInfo[0];
            }
        }

        /**
         * 向某个位置尝试传输
         * @param world 所在世界
         * @param pl 下一节管道
         * @param element 传输内容
         * @param similar 是否模拟传输
         * @return 尝试传输后的结果
         */
        Object send(World world, PLInfo pl, PLElement element, boolean similar);

        /**
         * 管道输出
         * @param world 所在世界
         * @param pos 输出位置
         * @param element 输出内容
         * @param similar 是否模拟操作
         * @return 剩余物品
         */
        Object output(World world, BlockPos pos, PLElement element, boolean similar);

        /**
         * 检查是否可以连接
         * @param world 所在世界
         * @param thisPos 当前位置
         * @param facing 连接方向
         * @param element 传输内容
         * @return 是否可连接
         */
        PLConnType connectType(World world, BlockPos thisPos, EnumFacing facing, PLElement element);
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

        public PLInfoBuilder setAction(String key, String clazz) {
            pl.action = ACTIONS.get(key);
            if (pl.action == null) {
                setAction(clazz);
            } else {
                pl.selectorClass = pl.action.getClass().getCanonicalName();
            }
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

        static String NAME = "_ITEM_";
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
        public Object send(World world, PLInfo pl, PLElement element, boolean similar) {
            boolean canSend = world.isBlockLoaded(pl.pos)
                    && world.getTileEntity(pl.pos) instanceof TilePipeline
                    && (pl.type.equals(Pipeline.TYPE_ITEM)
                        || pl.type.equals(Pipeline.TYPE_ITEM_OUT));
            if (canSend) {
                if (similar && element != null && world.isBlockLoaded(pl.pos)) {
                    element.path.moveTo(world, pl);
                }
                return null;
            }
            return element.element;
        }

        @Override
        public Object output(World world, BlockPos pos, PLElement element, boolean similar) {
            return element.element;
        }

        @Override
        public PLConnType connectType(World world, BlockPos thisPos, EnumFacing facing, PLElement element) {
            TileEntity te = world.getTileEntity(thisPos.offset(facing));
            boolean canLinkPipeline = te instanceof TilePipeline
                    && (((TilePipeline) te).getType().equals(Pipeline.TYPE_ITEM)
                    || ((TilePipeline) te).getType().equals(Pipeline.TYPE_ITEM_IN)
                    || ((TilePipeline) te).getType().equals(Pipeline.TYPE_ITEM_OUT));
            if (canLinkPipeline) {
                return PLConnType.PIPELINE;
            }
            return PLConnType.NULL;
        }
    }

    public static class PathActionItemIn extends PathActionItem {

        static String NAME = "_ITEM_IN_";
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
        public PLConnType connectType(World world, BlockPos thisPos, EnumFacing facing, PLElement element) {
            TileEntity te = world.getTileEntity(thisPos.offset(facing));
            if ((te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite()))) {
                return PLConnType.IN;
            }
            return super.connectType(world, thisPos, facing, element);
        }
    }

    public static class PathActionItemOut extends PathActionItem {

        static String NAME = "_ITEM_OUT_";
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
        public Object output(World world, BlockPos pos, PLElement element, boolean similar) {
            if (element.element instanceof ItemStack) {
                TileEntity te = world.getTileEntity(pos);
                if (te != null) {
                    EnumFacing facing = BlockUtil.getPosFacing(element.path.plPos.pos, pos);
                    if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)) {
                        IItemHandler capability = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
                        ItemStack items = (ItemStack) element.element;
                        ItemStack itemsCopy = items.copy();
                        return ItemHandlerHelper.insertItem(capability, itemsCopy, similar);
                    }
                }
            }
            return element.element;
        }

        @Override
        public PLConnType connectType(World world, BlockPos thisPos, EnumFacing facing, PLElement element) {
            TileEntity te = world.getTileEntity(thisPos.offset(facing));
            if ((te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite()))) {
                return PLConnType.OUT;
            }
            return super.connectType(world, thisPos, facing, element);
        }
    }

    public static class PathActionFluid implements PathAction {

        static String NAME = "_FLUID_";
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
        public Object send(World world, PLInfo pl, PLElement element, boolean similar) {
            boolean canSend = world.isBlockLoaded(pl.pos)
                    && world.getTileEntity(pl.pos) instanceof TilePipeline
                    && (pl.type.equals(Pipeline.TYPE_FLUID)
                    || pl.type.equals(Pipeline.TYPE_FLUID_OUT));
            if (canSend) {
                if (similar && element != null && world.isBlockLoaded(pl.pos)) {
                    element.path.moveTo(world, pl);
                }
                return null;
            }
            return element.element;
        }

        @Override
        public Object output(World world, BlockPos pos, PLElement element, boolean similar) {
            return element.element;
        }

        @Override
        public PLConnType connectType(World world, BlockPos thisPos, EnumFacing facing, PLElement element) {
            TileEntity te = world.getTileEntity(thisPos.offset(facing));
            boolean canLinkPipeline = te instanceof TilePipeline
                    && (((TilePipeline) te).getType().equals(Pipeline.TYPE_FLUID)
                    || ((TilePipeline) te).getType().equals(Pipeline.TYPE_FLUID_IN)
                    || ((TilePipeline) te).getType().equals(Pipeline.TYPE_FLUID_OUT));
            if (canLinkPipeline) {
                return PLConnType.PIPELINE;
            }
            return PLConnType.NULL;
        }
    }

    public static class PathActionFluidIn extends PathActionFluid {

        static String NAME = "_FLUID_IN_";
        public static PathAction instance() {
            PathAction pathAction = ACTIONS.get(NAME);
            if (pathAction == null) {
                pathAction = new PathActionFluidIn();
                ACTIONS.put(NAME, pathAction);
            }
            return pathAction;
        }

        @Override
        public String name() {
            return NAME;
        }

        @Override
        public PLConnType connectType(World world, BlockPos thisPos, EnumFacing facing, PLElement element) {
            TileEntity te = world.getTileEntity(thisPos.offset(facing));
            if ((te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite()))) {
                return PLConnType.IN;
            }
            return super.connectType(world, thisPos, facing, element);
        }
    }

    public static class PathActionFluidOut extends PathActionFluid {

        static String NAME = "_FLUID_OUT_";
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
        public Object output(World world, BlockPos pos, PLElement element, boolean similar) {
            if (element.element instanceof FluidStack) {
                TileEntity te = world.getTileEntity(pos);
                if (te != null) {
                    EnumFacing facing = BlockUtil.getPosFacing(element.path.plPos.pos, pos);
                    if (te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)) {
                        IFluidHandler capability = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
                        if (capability != null) {
                            FluidStack fluid = (FluidStack) element.element;
                            FluidStack fluidCopy = new FluidStack(fluid, fluid.amount);
                            int amount = capability.fill(fluidCopy, !similar);
                            return new FluidStack(fluidCopy, fluidCopy.amount - amount);
                        }
                    }
                }
            }
            return element.element;
        }

        @Override
        public PLConnType connectType(World world, BlockPos thisPos, EnumFacing facing, PLElement element) {
            TileEntity te = world.getTileEntity(thisPos.offset(facing));
            if ((te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite()))) {
                return PLConnType.OUT;
            }
            return super.connectType(world, thisPos, facing, element);
        }
    }
}
