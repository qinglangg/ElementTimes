package com.elementtimes.tutorial.common.block.pipeline;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

/**
 * 物品（ItemStack）类型的 Element
 * @author luqin2007
 */
public class FluidElement extends BaseElement<FluidStack> {

    public static ElementType<FluidElement> TYPE = new ElementType<FluidElement>() {

        @Override
        public String type() {
            return "fluid_ec";
        }

        @Override
        public FluidElement newInstance() {
            return new FluidElement();
        }
    };

    private FluidElement() {
        super(TYPE.type());
    }

    @Override
    public boolean isEmpty() {
        return element == null || element.amount == 0;
    }

    @Override
    public FluidElement copy() {
        FluidElement e = new FluidElement();
        e.path = path;
        e.type = type;
        e.element = element.copy();
        e.posIndex = posIndex;
        e.totalTick = totalTick;
        return e;
    }

    @Override
    protected NBTTagCompound elementSerializer() {
        return element.writeToNBT(new NBTTagCompound());
    }

    @Override
    protected void elementDeserializer(NBTTagCompound compound) {
        element = FluidStack.loadFluidStackFromNBT(compound);
    }

    @Override
    public void drop(World world, BlockPos pos) {
        if (element != null && element.amount >= 1000) {
            int count = element.amount / 1000;
            IBlockState state = element.getFluid().getBlock().getDefaultState();
            for (EnumFacing direction : EnumFacing.values()) {
                BlockPos offset = pos.offset(direction);
                IBlockState s = world.getBlockState(offset);
                if (s.getBlock().isAir(s, world, pos)) {
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
