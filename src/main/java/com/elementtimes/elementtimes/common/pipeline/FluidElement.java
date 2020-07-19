package com.elementtimes.elementtimes.common.pipeline;

import com.elementtimes.elementtimes.misc.MCPNames;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;

/**
 * 物品（FluidStack）类型的 Element

 */
public class FluidElement extends BaseElement<FluidStack> {

    public static ElementType<FluidStack> TYPE = new ElementType<FluidStack>() {

        @Override
        public String type() {
            return "fluid_ec";
        }

        @Override
        public FluidElement newInstance(FluidStack object) {
            return new FluidElement(object);
        }

        @Override
        public FluidElement newInstance() {
            return newInstance(FluidStack.EMPTY);
        }
    };

    public static final BaseElement<FluidStack> EMPTY = TYPE.newInstance();

    private FluidElement(FluidStack stack) {
        super(TYPE.type());
        element = stack;
    }

    public FluidStack getFluid() {
        return element;
    }

    @Override
    public boolean isEmpty() {
        return getFluid().isEmpty();
    }

    @Override
    public FluidElement copy() {
        FluidElement e = new FluidElement(getFluid().copy());
        e.path = path;
        e.type = type;
        e.posIndex = posIndex;
        e.totalTick = totalTick;
        return e;
    }

    @Override
    public Class<FluidStack> getType() {
        return FluidStack.class;
    }

    @Override
    protected CompoundNBT elementSerializer() {
        return getFluid().writeToNBT(new CompoundNBT());
    }

    @Override
    protected void elementDeserializer(CompoundNBT compound) {
        element = FluidStack.loadFluidStackFromNBT(compound);
    }

    @Override
    public void drop(World world, BlockPos pos) {
        if (MCPNames.isNetease()) {
            return;
        }
        FluidStack fluidStack = getFluid();
        if (fluidStack.isEmpty()) {
            return;
        }
        Fluid fluid = fluidStack.getFluid();
        int amount = fluidStack.getAmount();
        if (element != null && amount >= FluidAttributes.BUCKET_VOLUME) {
            int count = amount / FluidAttributes.BUCKET_VOLUME;
            BlockState state = fluid.getDefaultState().getBlockState();
            for (Direction direction : Direction.values()) {
                BlockPos offset = pos.offset(direction);
                BlockState s = world.getBlockState(offset);
                if (s.getBlock().isAir(s, world, pos)) {
                    world.destroyBlock(pos, true);
                    world.setBlockState(offset, state);
                    count--;
                }
                if (count <= 0) {
                    break;
                }
            }
        }
    }
}
