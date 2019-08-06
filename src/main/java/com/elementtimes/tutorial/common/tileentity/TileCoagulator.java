package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.annotations.ModElement;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.other.SideHandlerType;
import com.elementtimes.tutorial.other.lifecycle.FluidMachineLifecycle;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;
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
@ModElement
@ModElement.ModInvokeStatic("init")
public class TileCoagulator extends BaseMachine {

    public static MachineRecipeHandler RECIPE = null;
    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler();
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
