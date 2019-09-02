package com.elementtimes.tutorial.other.pipeline.interfaces;

import com.elementtimes.elementcore.api.common.ECUtils;
import com.elementtimes.tutorial.common.block.Pipeline;
import com.elementtimes.tutorial.common.tileentity.TilePipeline;
import com.elementtimes.tutorial.interfaces.tileentity.ITileFluidHandler;
import com.elementtimes.tutorial.interfaces.tileentity.ITileItemHandler;
import com.elementtimes.tutorial.other.SideHandlerType;
import com.elementtimes.tutorial.other.pipeline.PLConnType;
import com.elementtimes.tutorial.other.pipeline.PLElement;
import com.elementtimes.tutorial.other.pipeline.PLInfo;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 某一类管道的特质，行为
 * @author luqin2007
 */
public interface PathAction {
    Map<String, PathAction> ACTIONS = new HashMap<>();

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
     * @return 所有可选邻接管道
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
     * 向某个位置发送
     * @param world 所在世界
     * @param pl 下一节管道
     * @param element 传输内容
     * @param similar 是否模拟传输
     * @return 尝试传输后剩余的内容
     */
    default Object send(World world, int pl, PLElement element, boolean similar) {
        return element.path.path.get(pl).action.receive(world, element, pl, similar);
    }

    /**
     * 向某个位置输出
     * @param world 所在世界
     * @param thisPos 当前位置
     * @param nextPos 输出位置
     * @param element 输出内容
     * @param similar 是否模拟操作
     * @return 剩余物品
     */
    Object output(World world, BlockPos thisPos, BlockPos nextPos, PLElement element, boolean similar);

    /**
     * 接收来自外部的内容
     * @param world 所在世界
     * @param element 传输内容
     * @param pl 当前管道 pos
     * @param similar 是否模拟操作
     * @return 尝试传输后剩余的内容
     */
    Object receive(World world, PLElement element, int pl, boolean similar);

    /**
     * 检查是否可以连接
     * @param world 所在世界
     * @param thisPos 当前位置
     * @param facing 连接方向
     * @param element 传输内容
     * @return 是否可连接
     */
    PLConnType connectType(World world, BlockPos thisPos, EnumFacing facing, PLElement element);

    /**
     * 获取传递的冷却时间
     * 当 output 方法调用且返回的物品非 null 且与原物品不同时调用
     * 用于无法一次完全传输时两次尝试的冷却
     *
     * @param element 传递物品
     * @return 冷却时间 tick
     */
    default int coldDown(@SuppressWarnings("unused") PLElement element) {
        return 0;
    }

    class ItemLink implements PathAction {

        static String NAME = "_ITEM_";
        public static PathAction instance() {
            PathAction pathAction = ACTIONS.get(NAME);
            if (pathAction == null) {
                pathAction = new ItemLink();
                ACTIONS.put(NAME, pathAction);
            }
            return pathAction;
        }

        @Override
        public String name() {
            return NAME;
        }

        @Override
        public Object output(World world, BlockPos thisPos, BlockPos nextPos, PLElement element, boolean similar) {
            return element.element;
        }

        @Override
        public Object receive(World world, PLElement element, int pl, boolean similar) {
            Object e = element.element;
            if (e instanceof ItemStack || e instanceof Item || e instanceof Block) {
                if (!similar) {
                    element.moveTo(world, pl);
                }
                return null;
            }
            return e;
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

    class ItemIn extends ItemLink {

        static String NAME = "_ITEM_IN_";
        public static PathAction instance() {
            PathAction action = ACTIONS.get(NAME);
            if (action == null) {
                action = new ItemIn();
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

    class ItemOut extends ItemLink {

        static String NAME = "_ITEM_OUT_";
        public static PathAction instance() {
            PathAction action = ACTIONS.get(NAME);
            if (action == null) {
                action = new ItemOut();
                ACTIONS.put(NAME, action);
            }
            return action;
        }

        @Override
        public String name() {
            return NAME;
        }

        @Override
        public Object output(World world, BlockPos thisPos, BlockPos nextPos, PLElement element, boolean similar) {
            if (element.element instanceof ItemStack) {
                TileEntity te = world.getTileEntity(nextPos);
                if (te != null) {
                    EnumFacing facing = ECUtils.block.getPosFacing(thisPos, nextPos);
                    IItemHandler capability;
                    if (te instanceof ITileItemHandler) {
                        capability = ((ITileItemHandler) te).getItemHandler(SideHandlerType.OUTPUT);
                    } else {
                        capability = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
                    }
                    if (capability != null) {
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

    class FluidLink implements PathAction {

        static String NAME = "_FLUID_";
        public static PathAction instance() {
            PathAction pathAction = ACTIONS.get(NAME);
            if (pathAction == null) {
                pathAction = new FluidLink();
                ACTIONS.put(NAME, pathAction);
            }
            return pathAction;
        }

        @Override
        public String name() {
            return NAME;
        }

        @Override
        public Object output(World world, BlockPos thisPos, BlockPos nextPos, PLElement element, boolean similar) {
            return element.element;
        }

        @Override
        public Object receive(World world, PLElement element, int pl, boolean similar) {
            Object e = element.element;
            if (e instanceof FluidStack || e instanceof Fluid) {
                if (!similar) {
                    element.moveTo(world, pl);
                }
                return null;
            }
            return e;
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

    class FluidIn extends FluidLink {

        static String NAME = "_FLUID_IN_";
        public static PathAction instance() {
            PathAction pathAction = ACTIONS.get(NAME);
            if (pathAction == null) {
                pathAction = new FluidIn();
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

    class FluidOut extends FluidLink {

        static String NAME = "_FLUID_OUT_";
        public static PathAction instance() {
            PathAction action = ACTIONS.get(NAME);
            if (action == null) {
                action = new FluidOut();
                ACTIONS.put(NAME, action);
            }
            return action;
        }

        @Override
        public String name() {
            return NAME;
        }

        @Override
        public Object output(World world, BlockPos thisPos, BlockPos nextPos, PLElement element, boolean similar) {
            if (element.element instanceof FluidStack) {
                TileEntity te = world.getTileEntity(nextPos);
                if (te != null) {
                    EnumFacing facing = ECUtils.block.getPosFacing(thisPos, nextPos);
                    IFluidHandler capability;
                    if (te instanceof ITileFluidHandler) {
                        capability = ((ITileFluidHandler) te).getTanks(SideHandlerType.OUTPUT);
                    } else {
                        capability = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
                    }
                    if (capability != null) {
                        FluidStack fluid = (FluidStack) element.element;
                        FluidStack fluidCopy = new FluidStack(fluid, fluid.amount);
                        int amount = capability.fill(fluidCopy, !similar);
                        return new FluidStack(fluidCopy, fluidCopy.amount - amount);
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
