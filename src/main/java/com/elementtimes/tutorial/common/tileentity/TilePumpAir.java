package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.annotations.ModInvokeStatic;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.other.SideHandlerType;
import com.elementtimes.tutorial.other.lifecycle.FluidMachineLifecycle;
import com.elementtimes.tutorial.other.machineRecipe.IngredientPart;
import com.elementtimes.tutorial.other.machineRecipe.MachineRecipeHandler;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * 气泵
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TilePumpAir extends BaseMachine {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler()
                    .newRecipe("air")
                    .addCost(100)
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.air, 1))
                    .endAdd();
        }
    }

    public TilePumpAir() {
        super(1000, 1, 1, 0, 0, 1, 16000);
        addLifeCycle(new FluidMachineLifecycle(this, 0, 1));
        markBucketInput(0);
    }

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.PumpAir;
    }

    @Nonnull
    @Override
    public MachineRecipeHandler createRecipe() {
        return RECIPE;
    }

    @Override
    public int getMaxEnergyChange() {
        return 1;
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
