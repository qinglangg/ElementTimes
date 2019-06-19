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
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

@ModElement
@ModElement.ModInvokeStatic("init")
public class TileSolidReactor extends BaseMachine {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler()
                    .newRecipe("0")
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(Items.COAL, 1))
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.calciumOxide,1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.calciumAcetylide , 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co, Fluid.BUCKET_VOLUME))
                    .endAdd();
        }
    }

    public TileSolidReactor() {
        super(100000, 3, 3, 0, 0, 1, 16000);
        addLifeCycle(new FluidMachineLifecycle(this, new Int2ObjectArrayMap<>(0), new Int2ObjectArrayMap<>(new int[]{0}, new int[][]{new int[]{2, 2}})));
        markBucketInput(2);
    }

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.SolidReactor;
    }

    @Nonnull
    @Override
    public Slot[] createSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 35, 42),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 1, 53, 42),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 2, 126, 62),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 105, 33),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 105, 51),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 2, 126, 80)
        };
    }

    @Nonnull
    @Override
    public Map<SideHandlerType, Int2ObjectMap<int[]>> createFluids() {
        Map<SideHandlerType, Int2ObjectMap<int[]>> fluids = new HashMap<>(1);
        Int2ObjectMap<int[]> output = new Int2ObjectArrayMap<>(
                new int[] {0}, new int[][] {new int[] {126, 11, 16, 46}}
        );
        fluids.put(SideHandlerType.OUTPUT, output);
        return fluids;
    }

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
        return RECIPE;
    }
}
