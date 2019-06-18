package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.other.SideHandlerType;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class TileFluidHeater extends BaseMachine {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler()
                    .add("test", 1000, FluidRegistry.WATER, ElementtimesFluids.steam);
        }
    }

    public TileFluidHeater() {
        super(0, 2, 2, 1, 16000, 1, 16000);
        markBucketInput(0, 1);
        init();
    }

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.FluidHeater;
    }

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
        init();
        return RECIPE;
    }

    @Override
    public int getMaxEnergyChange() {
        return 10;
    }

    @Nonnull
    @Override
    public Map<SideHandlerType, Int2ObjectMap<int[]>> createFluids() {
        Map<SideHandlerType, Int2ObjectMap<int[]>> fluids = new HashMap<>(2);
        fluids.put(SideHandlerType.INPUT, new Int2ObjectArrayMap<>(new int[]{0}, new int[][]{new int[]{17, 25, 16, 46}}));
        fluids.put(SideHandlerType.OUTPUT, new Int2ObjectArrayMap<>(new int[]{0}, new int[][]{new int[]{143, 25, 16, 46}}));
        return fluids;
    }

    @Nonnull
    @Override
    public Slot[] createSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 8, 83),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 1, 136, 83),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 26, 83),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 152, 83)
        };
    }
}
