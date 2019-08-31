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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

@ModInvokeStatic("init")
public class TileSolidReactor extends BaseMachine {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler()
            		//CaO+3C=CaC2+CO
                    .newRecipe("CaC2")
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.calciumOxide,1))
                    .addItemInput(IngredientPart.forItem(Items.COAL, 3))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.calciumacetylide , 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co, Fluid.BUCKET_VOLUME))
                    .endAdd()
                    
                    
                    //铁矿石获得铁
                    .newRecipe("IRON_INGOT1")
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(Blocks.IRON_ORE,1))
                    .addItemInput(IngredientPart.forItem(Items.COAL, 1))
                    .addItemOutput(IngredientPart.forItem(Items.IRON_INGOT, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.slag, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co, Fluid.BUCKET_VOLUME))
                    .endAdd()   
                    
                    .newRecipe("IRON_INGOT2")
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.ironPowder,1))
                    .addItemInput(IngredientPart.forItem(Items.COAL, 1))
                    .addItemOutput(IngredientPart.forItem(Items.IRON_INGOT, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.slag, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co, Fluid.BUCKET_VOLUME))
                    .endAdd()
                    
                  //铜矿石获得铜
                    .newRecipe("oreCopper1")
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem("oreCopper",1))
                    .addItemInput(IngredientPart.forItem(Items.COAL, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.copper, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.slag, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co, Fluid.BUCKET_VOLUME))
                    .endAdd()   

                    .newRecipe("oreCopper2")
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.copperPowder,1))
                    .addItemInput(IngredientPart.forItem(Items.COAL, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.copper, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.slag, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co, Fluid.BUCKET_VOLUME))
                    .endAdd() 
                    
                    
                    //钢锭
                    .newRecipe("2")
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(Items.IRON_INGOT,1))
                    .addItemInput(IngredientPart.forItem(Items.COAL, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.steelIngot, 1))
                    .endAdd()

                    
                    .newRecipe("coarseSilicon")
                    .addCost(100000)
                    .addItemInput(IngredientPart.forItem(Items.COAL,1))
                    .addItemInput(IngredientPart.forItem(Blocks.SAND,1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.coarseSilicon,1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co2, Fluid.BUCKET_VOLUME))
                    .endAdd()
                    
                    .newRecipe("SiC")
                    .addCost(100000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.Silicon,1))
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.diamondIngot,1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.SiC,1))
                    .endAdd()
                    //铀锭的获得
                    .newRecipe("U")
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.UO2,1))
                    .addItemInput(IngredientPart.forItem(Items.COAL, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co2, Fluid.BUCKET_VOLUME))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.uranium, 1))
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
    public MachineRecipeHandler createRecipe() {
        return RECIPE;
    }
}
