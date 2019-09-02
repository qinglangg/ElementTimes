package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.ModInvokeStatic;
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
 * 电解池
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileElectrolyticCell extends BaseMachine {

    public static MachineRecipeHandler RECIPE = null;
    public static void init() {
        RECIPE = new MachineRecipeHandler()
                .newRecipe("0")
                .addCost(20000)
                .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.steam, 2000))
                .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.H, 2000))
                .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.oxygen, 1000))
                .endAdd()
                .newRecipe("1")
                .addCost(20000)
                .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.NaClSolutionConcentrated, 2000))
                .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.Naoh, 2000))
                .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.chlorine, 1000))
                .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.H, 1000))
                .endAdd()
                .newRecipe("2")
                .addCost(40000)
                .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.NaCl,2000))
                .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.Na, 2000))
                .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.chlorine, 1000))
                .endAdd()
                .newRecipe("3")
                .addCost(50000)
                .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.Al2O3_Na3AlF6,2000))
                .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.Al, 4000))
                .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.oxygen, 3000))
                .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.Na3AlF6, 2000))
                .endAdd();
    }

    public TileElectrolyticCell() {
        super(10000, 4, 4, 1, 16000, 3, 16000);
        addLifeCycle(new FluidMachineLifecycle(this,
                new Int2ObjectArrayMap<>(new int[]{0}, new int[][]{new int[]{0, 0}}),
                new Int2ObjectArrayMap<>(new int[]{0, 1, 2}, new int[][]{new int[]{1,1},new int[]{2,2},new int[]{3,3}})));
        markBucketInput(0, 1, 2, 3);
    }

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.ElectrolyticCell;
    }

    @Nonnull
    @Override
    public MachineRecipeHandler createRecipe() {
        init();
        return RECIPE;
    }

    @Nonnull
    @Override
    public Slot[] createSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 36, 63),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 1, 88, 63),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 2, 106, 63),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 3, 124, 63),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 36, 81),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 88, 81),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 2, 106, 81),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 3, 124, 81)
        };
    }

    @Nonnull
    @Override
    public Map<SideHandlerType, Int2ObjectMap<int[]>> createFluids() {
        HashMap<SideHandlerType, Int2ObjectMap<int[]>> map = new HashMap<>(2);
        map.put(SideHandlerType.INPUT, new Int2ObjectArrayMap<>(new int[] {0}, new int[][] {new int[] {36, 12, 16, 46}}));
        map.put(SideHandlerType.OUTPUT, new Int2ObjectArrayMap<>(new int[] {0, 1, 2}, new int[][] {
                new int[] {88, 12, 16, 46}, new int[] {106, 12, 16, 46}, new int[] {124, 12, 16, 46}
        }));
        return map;
    }

    @Override
    public int getMaxEnergyChange() {
        return 10;
    }
}
