package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.annotations.ModElement;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.other.SideHandlerType;
import com.elementtimes.tutorial.other.lifecycle.FluidMachineLifecycle;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * 冷凝机
 * @author luqin2007
 */
@ModElement
@ModElement.ModInvokeStatic("init")
public class TileCondenser extends BaseMachine {

    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler();

    public static void init() {
        if (RECIPE.getMachineRecipes().isEmpty()) {
            RECIPE = new MachineRecipeHandler()
                    .add("0", 1000, ElementtimesFluids.steam, FluidRegistry.WATER);
        }
    }

    public TileCondenser() {
        super(100000, 2, 2, 1, 16000, 1, 16000);
        init();
        addLifeCycle(new FluidMachineLifecycle(this,
                new Int2ObjectArrayMap<>(new int[] {0}, new int[][] {new int[] {0, 0}}),
                new Int2ObjectArrayMap<>(new int[] {0}, new int[][] {new int[] {1, 1}})));
        markBucketInput(0, 1);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
        return RECIPE;
    }

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.Condenser;
    }

    @Nonnull
    @Override
    public Map<SideHandlerType, Int2ObjectMap<int[]>> createFluids() {
        HashMap<SideHandlerType, Int2ObjectMap<int[]>> map = new HashMap<>(1);
        Int2ObjectArrayMap<int[]> valueInput = new Int2ObjectArrayMap<>();
        valueInput.put(0, new int[] {17, 25, 16, 46});
        map.put(SideHandlerType.INPUT, valueInput);
        Int2ObjectArrayMap<int[]> valueOutput = new Int2ObjectArrayMap<>();
        valueOutput.put(0, new int[] {143, 25, 16, 46});
        map.put(SideHandlerType.OUTPUT, valueOutput);
        return map;
    }

    @Nonnull
    @Override
    public Slot[] createSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 8, 83),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 1, 134, 83),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 26, 83),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 151, 83)
        };
    }

    @Override
    public int getMaxEnergyChange() {
        return 10;
    }
}
