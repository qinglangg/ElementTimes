package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.annotations.ModElement;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.other.SideHandlerType;
import com.elementtimes.tutorial.other.recipe.IngredientPart;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 固体离心机
 * @author luqin2007
 */
@ModElement
@ModElement.ModInvokeStatic("init")
public class TileSolidCentrifuge extends BaseMachine {

    public static MachineRecipeHandler RECIPE = null;
    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler()
        	.newRecipe("slag")
            .addCost(100)
            .addItemInput(IngredientPart.forItem(ElementtimesItems.slag,1))
            .addItemOutput(IngredientPart.forItem(ElementtimesItems.stonepowder,1))
            .addItemOutput(IngredientPart.forItem(ElementtimesItems.sandpowder,1))
            .endAdd();
            }
    }

    TileSolidCentrifuge() {
        super(100000, 1, 3);
    }

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.SolidCentrifuge;
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
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 55, 31),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 109, 15),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 109, 33),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 109, 51)
        };
    }

    @Override
    public int getMaxEnergyChange() {
        return 30;
    }
}