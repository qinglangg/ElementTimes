package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.annotations.ModElement;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.other.SideHandlerType;
import com.elementtimes.tutorial.other.lifecycle.FluidMachineLifecycle;
import com.elementtimes.tutorial.other.recipe.IngredientPart;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

@ModElement
@ModElement.ModInvokeStatic("init")
public class TileCentrifuge extends BaseMachine {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler()
                    .newRecipe("air")
                    .addCost(1000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.air, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.nitrogen, 780))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.oxygen, 210))
                    .addFluidOutput(IngredientPart.forFluid(FluidRegistry.WATER, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.rareGases, 8))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co2, 1))
                    .endAdd();
        }
    }

    public TileCentrifuge() {
        super(100000, 6, 6, 1, 16000, 5, 16000);
        markBucketInput(0, 1, 2, 3, 4, 5);
        addLifeCycle(new FluidMachineLifecycle(this, 1, 5));
    }

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.Centrifuge;
    }

    @Nonnull
    @Override
    public MachineRecipeHandler createRecipe() {
        return RECIPE;
    }

    @Nonnull
    @Override
    public Slot[] createSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 18, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 1, 70, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 2, 88, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 3, 106, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 4, 124, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 5, 142, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 18, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 70, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 2, 88, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 3, 106, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 4, 124, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 5, 142, 84)
        };
    }

    @Nonnull
    @Override
    public Map<SideHandlerType, Int2ObjectMap<int[]>> createFluids() {
        Map<SideHandlerType, Int2ObjectMap<int[]>> fluids = new HashMap<>(6);
        fluids.put(SideHandlerType.INPUT, new Int2ObjectArrayMap<>(new int[] {0}, new int[][] {new int[]{18, 15, 16, 46}}));
        fluids.put(SideHandlerType.OUTPUT, new Int2ObjectArrayMap<>(new int[] {0,1,2,3,4}, new int[][] {
                new int[]{70, 15, 16, 46},
                new int[]{88, 15, 16, 46},
                new int[]{106, 15, 16, 46},
                new int[]{124, 15, 16, 46},
                new int[]{142, 15, 16, 46}
        }));
        return fluids;
    }
}
