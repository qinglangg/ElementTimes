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
import net.minecraft.inventory.Slot;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * 流体反应器
 * @author luqin2007
 */
@ModElement
@ModElement.ModInvokeStatic("init")
public class TileFluidReactor extends BaseMachine {

    public static MachineRecipeHandler RECIPES = null;
    public static void init() {
        if (RECIPES == null) {
            RECIPES = new MachineRecipeHandler()
            		
              		//CO+H2O(g)=co2+H2
            		.newRecipe("co2 h2")
                    .addCost(5000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.co, 1000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.waterDistilled,1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co2, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.H, 1000))
                    .endAdd()
                    
                    
                  //CO+3H2=CH4+H20(g)
            		.newRecipe("ch4")
                    .addCost(1000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.co, 1000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.H,3000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.methane, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.waterDistilled, 1000))
                    .endAdd()
                    
            		//精致硅单质获得
            		.newRecipe("Si")
                    .addCost(5000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.Sicl4, 1000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.H,2000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.HCl, 4000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.Silicon , 1))
                    .endAdd()
            		
            		
            		
					.newRecipe("0")
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.H,1000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.acetylene, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.ethylene, 2000))
                    .endAdd()
					.newRecipe("1")
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.H,1000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.ethylene, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.ethane, 2000))
                    .endAdd()
					.newRecipe("2")
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.waterDistilled,1000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.ethylene, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.ethanol, 2000))
                    .endAdd()
                    
                    
                    //NH3
                    .newRecipe("NH3")
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.H,3000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.nitrogen, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.ammonia, 1000))
                    .endAdd()
                    
                    
                    //Co2+Ca(OH)2=CaCO3+H2O
                    .newRecipe("CaCO3 H2O")
                    .addCost(0)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.co2, Fluid.BUCKET_VOLUME))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.calciumHydroxide, Fluid.BUCKET_VOLUME))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.calciumCarbonate , 1))
                    .addFluidOutput(IngredientPart.forFluid(FluidRegistry.WATER, Fluid.BUCKET_VOLUME))
                    .endAdd();
        }
    }

    public TileFluidReactor() {
        super(10000, 6, 5, 2, 16000, 3, 16000);
        addLifeCycle(new FluidMachineLifecycle(this,
                new Int2ObjectArrayMap<>(new int[] {0, 1}, new int[][]{new int[]{1, 0}, new int[]{2, 1}}),
                new Int2ObjectArrayMap<>(new int[] {0, 1, 2}, new int[][]{new int[]{3, 2}, new int[]{4, 3}, new int[]{5, 4}})));
        markBucketInput(1, 2, 3, 4, 5);
    }

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.FluidReactor;
    }

    @Nonnull
    @Override
    public MachineRecipeHandler createRecipe() {
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
