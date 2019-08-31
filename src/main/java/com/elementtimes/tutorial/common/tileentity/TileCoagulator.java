package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.annotations.ModInvokeStatic;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
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
 * 凝固器
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileCoagulator extends BaseMachine {

    public static MachineRecipeHandler RECIPE = null;
    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler()
            		.newRecipe("na")
                    .addCost(20000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.Na, 1000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.na,1))
                    .endAdd()
                    .newRecipe("al")
                    .addCost(20000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.Al, 1000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.Al,1))
                    .endAdd()
            		.newRecipe("Na3AlF6")
            		.addCost(20000)
            		.addFluidInput(IngredientPart.forFluid(ElementtimesFluids.Na3AlF6, 1000))
            		.addItemOutput(IngredientPart.forItem(ElementtimesItems.Na3AlF6,1))
            		.endAdd();
        }
    }

    TileCoagulator() {
        super(100000, 1, 2, 0, 0, 1, 16000);
        addLifeCycle(new FluidMachineLifecycle(this, 0, 1));
        markBucketInput(0);
    }

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.Coagulator;
    }

    @Nonnull
    @Override
    public MachineRecipeHandler createRecipe() {
        return RECIPE;
    }

    @Override
    public int getMaxEnergyChange() {
        return 30;
    }

    @Nonnull
    @Override
    public Slot[] createSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 47, 61),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 108, 30),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 65, 61)
        };
    }

    @Nonnull
    @Override
    public Map<SideHandlerType, Int2ObjectMap<int[]>> createFluids() {
        Map<SideHandlerType, Int2ObjectMap<int[]>> fluidMaps = new HashMap<>(1);
        fluidMaps.put(SideHandlerType.OUTPUT, new Int2ObjectArrayMap<>(new int[] {0}, new int[][] {{56, 10, 16, 46}}));
        return fluidMaps;
    }
}
