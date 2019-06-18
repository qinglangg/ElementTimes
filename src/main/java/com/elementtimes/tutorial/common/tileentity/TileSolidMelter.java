package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.annotations.ModElement;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.other.SideHandlerType;
import com.elementtimes.tutorial.other.lifecycle.FluidMachineLifecycle;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * 固体熔化机
 * @author luqin2007
 */
@ModElement
@ModElement.ModInvokeStatic("init")
public class TileSolidMelter extends BaseMachine {

    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler();

    public static void init() {
        if (RECIPE.getMachineRecipes().isEmpty()) {
            RECIPE = new MachineRecipeHandler()
                    .add("icetest", 1000, Blocks.ICE, FluidRegistry.WATER);
        }
    }

    public TileSolidMelter() {
        super(100000, 2, 1, 0, 0, 1, 16000);
        addLifeCycle(new FluidMachineLifecycle(this, new Int2ObjectArrayMap<>(), new Int2ObjectArrayMap<>(new int[]{0}, new int[][] {new int[] {1, 0}})));
        markBucketInput(1);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
        return RECIPE;
    }

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.SolidMelter;
    }

    @Nonnull
    @Override
    public Map<SideHandlerType, Int2ObjectMap<int[]>> createFluids() {
        HashMap<SideHandlerType, Int2ObjectMap<int[]>> map = new HashMap<>(1);
        Int2ObjectArrayMap<int[]> value = new Int2ObjectArrayMap<>();
        value.put(0, new int[] {95, 16, 16, 46});
        map.put(SideHandlerType.OUTPUT, value);
        return map;
    }

    @Nonnull
    @Override
    public Slot[] createSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 45, 31),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 1, 116, 28),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 116, 46)
        };
    }

    @Override
    public int getMaxEnergyChange() {
        return 10;
    }
}
