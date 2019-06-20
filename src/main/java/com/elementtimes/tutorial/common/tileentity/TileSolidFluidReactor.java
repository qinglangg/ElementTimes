package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.annotations.ModElement;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.other.SideHandlerType;
import com.elementtimes.tutorial.other.lifecycle.FluidMachineLifecycle;
import com.elementtimes.tutorial.other.recipe.IngredientPart;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

@ModElement
@ModElement.ModInvokeStatic("init")
public class TileSolidFluidReactor extends BaseMachine {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler()
                    .newRecipe("0")
                    .addCost(1000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.waterDistilled, 1000))
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.calciumacetylide , 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.calciumHydroxide, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.acetylene, 1000))
                    .endAdd();
        }
    }

    public TileSolidFluidReactor() {
        super(10000, 4, 5, 1, 16000, 2, 16000);
        markBucketInput(1, 2, 3);
        addLifeCycle(new FluidMachineLifecycle(this, new Int2ObjectArrayMap<>(new int[]{0}, new int[][]{new int[]{1, 2}}),
                new Int2ObjectArrayMap<>(new int[]{0, 1}, new int[][]{new int[]{2, 3}, new int[]{3, 4}})));
    }

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.SolidFluidReactor;
    }

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
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
        fluids.put(SideHandlerType.INPUT, new Int2ObjectArrayMap<>(new int[]{0}, new int[][]{new int[]{42, 15, 16, 46}}));
        fluids.put(SideHandlerType.OUTPUT, new Int2ObjectArrayMap<>(new int[]{0, 1}, new int[][]{
                new int[]{116, 15, 16, 46}, new int[]{137, 15, 16, 46}
        }));
        return fluids;
    }

    @Nonnull
    @Override
    public Slot[] createSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 22, 30),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 1, 43, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 2, 116, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 3, 137, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 95, 22),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 95, 34),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 2, 43, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 3, 116, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 4, 137, 84)
        };
    }
}
