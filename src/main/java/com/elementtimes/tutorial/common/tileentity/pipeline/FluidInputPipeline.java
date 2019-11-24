package com.elementtimes.tutorial.common.tileentity.pipeline;

import com.elementtimes.tutorial.common.block.pipeline.BaseElement;
import com.elementtimes.tutorial.common.block.pipeline.FluidElement;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * 物品输入管道
 * 用于将物品从容器转移到管道网络
 * @author luqin
 */
public class FluidInputPipeline extends BaseItemPipeline {

    private int coldDown = 0;
    private int maxColdDown = 20;

    @Override
    public boolean canConnectTo(EnumFacing direction) {
        if (world == null) {
            return false;
        }
        if (isInterrupted(direction)) {
            return false;
        }
        TileEntity te = world.getTileEntity(pos.offset(direction));
        if (te != null) {
            if (te instanceof BaseTilePipeline) {
                return ((BaseTilePipeline) te).canConnectBy(pos, direction.getOpposite());
            } else {
                return te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction.getOpposite());
            }
        }
        return false;
    }

    @Override
    public boolean canOutput(BlockPos pos, BaseElement element) {
        return false;
    }

    @Override
    public BaseElement output(BlockPos pos, BaseElement element) {
        return element;
    }

    @Override
    public int getKeepTime(BaseElement element) {
        return 10;
    }

    @Override
    public void tick() {
        super.tick();
        markDirty();
        if (world == null) {
            return;
        }
        coldDown++;
        if (coldDown == maxColdDown) {
            coldDown = 0;
        }
        if (coldDown != 0) {
            return;
        }
        for (EnumFacing direction : EnumFacing.values()) {
            BlockPos offset = pos.offset(direction);
            TileEntity te = world.getTileEntity(offset);
            if (te != null) {
                IFluidHandler handler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction.getOpposite());
                if (handler != null) {
                    FluidStack drain = handler.drain(Integer.MAX_VALUE, false);
                    if (drain != null && drain.amount != 0) {
                        FluidElement element = FluidElement.TYPE.newInstance();
                        element.element = drain;
                        if (element.send(world, pos, offset)) {
                            handler.drain(drain, true);
                        }
                    }
                }
            }
        }
        markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        coldDown = compound.getInteger("coldDown");
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("coldDown", coldDown);
        return super.writeToNBT(compound);
    }
}
