package com.elementtimes.tutorial.common.tileentity.pipeline.fluid;

import com.elementtimes.tutorial.common.pipeline.FluidElement;
import com.elementtimes.tutorial.interfaces.ITilePipelineInput;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;

/**
 * 物品输入管道
 * 用于将物品从容器转移到管道网络
 * @author luqin
 */
public class FluidInputPipeline extends FluidIOPipeline implements ITilePipelineInput {

    private int coldDown = 0;
    private int maxColdDown = 20;

    @Override
    public void input() {
        if (coldDown != 0 || !isConnectedIO()) {
            return;
        }
        EnumFacing ioSide = EnumFacing.VALUES[readByteValue(12, 3)];
        BlockPos target = pos.offset(ioSide);
        TileEntity te = world.getTileEntity(target);
        if (te != null) {
            IFluidHandler handler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, ioSide.getOpposite());
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

    @Override
    public void update() {
        super.update();
        if (world == null || !isConnectedIO()) {
            return;
        }
        coldDown++;
        if (coldDown == maxColdDown) {
            coldDown = 0;
        }
        markDirty();
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
}
