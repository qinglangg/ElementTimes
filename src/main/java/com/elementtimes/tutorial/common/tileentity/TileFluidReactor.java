package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.other.SideHandlerType;
import com.elementtimes.tutorial.other.lifecycle.FluidMachineLifecycle;
import com.elementtimes.tutorial.other.recipe.IngredientPart;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class TileFluidReactor extends BaseMachine {

    public static MachineRecipeHandler RECIPES = null;
    public static void init() {
        if (RECIPES == null) {
            RECIPES = new MachineRecipeHandler()
                    .newRecipe("0")
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.H, Fluid.BUCKET_VOLUME))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.acetylene, Fluid.BUCKET_VOLUME))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.ethylene, Fluid.BUCKET_VOLUME))
                    .endAdd()
                    .newRecipe("1")
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.H, Fluid.BUCKET_VOLUME))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.ethylene, Fluid.BUCKET_VOLUME))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.ethane, Fluid.BUCKET_VOLUME))
                    .endAdd()
                    .newRecipe("2")
                    .addCost(5000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.waterDistilled, Fluid.BUCKET_VOLUME))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.ethylene, Fluid.BUCKET_VOLUME))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.ethanol, Fluid.BUCKET_VOLUME))
                    .endAdd();
        }
    }

    public TileFluidReactor() {
        super(10000, 6, 5, 2, 16000, 3, 16000);
        init();
        addLifeCycle(new FluidMachineLifecycle(this,
                new Int2ObjectArrayMap<>(new int[] {0, 1}, new int[][]{new int[]{1, 0}, new int[]{2, 1}}),
                new Int2ObjectArrayMap<>(new int[] {0, 1, 2}, new int[][]{new int[]{3, 2}, new int[]{4, 3}, new int[]{5, 4}})));
        markBucketInput(1, 2, 3, 4, 5);
    }

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.SolidReactor;
    }

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
        if (RECIPES == null) {
            init();
        }
        return RECIPES;
    }

    @Nonnull
    @Override
    public Slot[] createSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 88, 30),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 1, 18, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 2, 36, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 3, 106, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 4, 124, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 5, 142, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 18, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 36, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 2, 106, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 3, 124, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 4, 142, 84)
        };
    }

    @Nonnull
    @Override
    public Map<SideHandlerType, Int2ObjectMap<int[]>> createFluids() {
        HashMap<SideHandlerType, Int2ObjectMap<int[]>> map = new HashMap<>(5);
        Int2ObjectMap<int[]> input = new Int2ObjectArrayMap<>(new int[] {0, 1}, new int[][] {
                new int[] {18, 15, 16, 46}, new int[] {36, 15, 16, 46}
        });
        Int2ObjectMap<int[]> output = new Int2ObjectArrayMap<>(new int[] {0, 1, 2}, new int[][] {
                new int[] {106, 15, 16, 46}, new int[] {124, 15, 16, 46}, new int[] {142, 15, 16, 46}
        });
        map.put(SideHandlerType.INPUT, input);
        map.put(SideHandlerType.OUTPUT, output);
        return map;
    }

    @Override
    public int getMaxEnergyChange() {
        return 10;
    }
}
