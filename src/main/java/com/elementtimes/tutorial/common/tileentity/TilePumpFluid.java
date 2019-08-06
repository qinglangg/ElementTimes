package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.other.SideHandlerType;
import com.elementtimes.tutorial.other.lifecycle.FluidMachineLifecycle;
import com.elementtimes.tutorial.other.lifecycle.WorldReplaceMachineLifecycle;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.items.SlotItemHandler;
import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * 液泵
 * @author luqin2007
 */
public class TilePumpFluid extends BaseMachine {

    public static MachineRecipeHandler RECIPE =
            new MachineRecipeHandler().newRecipe("e").addCost(10).endAdd();

    public TilePumpFluid() {
        super(1000, 1, 1, 0, 0, 1, 16000);
        addLifeCycle(new WorldReplaceMachineLifecycle(this, this::replace, this::collect,
                20, 10, 20));
        addLifeCycle(new FluidMachineLifecycle(this, 0, 1));
        markBucketInput(0);
    }

    private Fluid search = null;

    IBlockState replace(IBlockState old) {
        search = FluidRegistry.lookupFluidForBlock(old.getBlock());
        if (search != null) {
            return ElementtimesBlocks.fr.getDefaultState();
        }
        return null;
    }

    ImmutablePair<Integer, Object> collect(IBlockState bs) {
        if (search != null) {
            return ImmutablePair.of(0, search);
        }
        return null;
    }

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.PumpFluid;
    }

    @Nonnull
    @Override
    public MachineRecipeHandler createRecipe() {
        return RECIPE;
    }

    @Override
    public int getMaxEnergyChange() {
        return 10;
    }

    @Nonnull
    @Override
    public Slot[] createSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 56, 58),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 98, 58)
        };
    }

    @Nonnull
    @Override
    public Map<SideHandlerType, Int2ObjectMap<int[]>> createFluids() {
        HashMap<SideHandlerType, Int2ObjectMap<int[]>> map = new HashMap<>(1);
        map.put(SideHandlerType.OUTPUT, new Int2ObjectArrayMap<>(new int[]{0}, new int[][]{new int[]{77, 28, 16, 46}}));
        return map;
    }
}
