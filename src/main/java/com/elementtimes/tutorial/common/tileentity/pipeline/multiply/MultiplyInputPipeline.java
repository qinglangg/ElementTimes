package com.elementtimes.tutorial.common.tileentity.pipeline.multiply;

import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileFluidHandler;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileItemHandler;
import com.elementtimes.tutorial.common.pipeline.FluidElement;
import com.elementtimes.tutorial.common.pipeline.IPipelineInput;
import com.elementtimes.tutorial.common.pipeline.ItemElement;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

/**
 * 物品输入管道
 * 用于将物品从容器转移到管道网络
 * @author luqin
 */
public class MultiplyInputPipeline extends MultiplyIOPipeline implements IPipelineInput {

    private int coldDown = 0;
    private int maxColdDown = 20;

    @Override
    public void update() {
        super.update();
        markDirty();
        if (world == null || !isConnectedIO()) {
            return;
        }
        coldDown++;
        if (coldDown == maxColdDown) {
            coldDown = 0;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        coldDown = compound.getInteger("coldDown");
        super.readFromNBT(compound);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("coldDown", coldDown);
        return super.writeToNBT(compound);
    }

    @Override
    public void input() {
        if (world != null && !world.isRemote && coldDown == 0 && isConnectedIO()) {
            EnumFacing ioSide = EnumFacing.VALUES[readByteValue(12, 3)];
            BlockPos target = pos.offset(ioSide);
            TileEntity te = world.getTileEntity(target);
            if (te != null) {
                inputItem(te, ioSide, target);
                inputFluid(te, ioSide, target);
            }
        }
    }

    @Override
    public boolean canConnectIO(BlockPos pos, EnumFacing direction) {
        if (world != null) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof ITileItemHandler) {
                return ((ITileItemHandler) te).getItemHandler(SideHandlerType.OUTPUT).getSlots() > 0;
            }
            if (te instanceof ITileFluidHandler) {
                return ((ITileFluidHandler) te).getTanks(SideHandlerType.OUTPUT).size() > 0;
            }
        }
        return super.canConnectIO(pos, direction);
    }

    private void inputItem(TileEntity te, EnumFacing ioSide, BlockPos target) {
        IItemHandler handler;
        if (te instanceof ITileItemHandler) {
            handler = ((ITileItemHandler) te).getItemHandler(SideHandlerType.OUTPUT);
        } else {
            handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, ioSide.getOpposite());
        }
        if (handler != null) {
            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack stack = handler.extractItem(i, Integer.MAX_VALUE, true);
                if (!stack.isEmpty()) {
                    ItemElement element = (ItemElement) ItemElement.TYPE.newInstance();
                    element.element = stack;
                    if (element.send(world, this, target)) {
                        handler.extractItem(i, stack.getCount(), false);
                        return;
                    }
                }
            }
        }
    }

    private void inputFluid(TileEntity te, EnumFacing ioSide, BlockPos target) {
        IFluidHandler handler;
        if (te instanceof ITileFluidHandler) {
            handler = ((ITileFluidHandler) te).getTanks(SideHandlerType.OUTPUT);
        } else {
            handler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, ioSide.getOpposite());
        }
        if (handler != null) {
            FluidStack drain = handler.drain(Integer.MAX_VALUE, false);
            if (drain != null && drain.amount != 0) {
                FluidElement element = (FluidElement) FluidElement.TYPE.newInstance();
                element.element = drain;
                if (element.send(world, this, target)) {
                    handler.drain(drain, true);
                }
            }
        }
    }
}
