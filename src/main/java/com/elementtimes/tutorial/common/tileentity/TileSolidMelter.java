package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.FluidMachineLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.recipe.IngredientPart;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 固体熔化机
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileSolidMelter extends BaseTileEntity {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler(1, 0, 0, 1)
                    .newRecipe()
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.salt, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.NaCl, Fluid.BUCKET_VOLUME))
                    .endAdd()
                    .newRecipe()
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.Al2O3_Na3AlF6, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.Al2O3_Na3AlF6, Fluid.BUCKET_VOLUME))
                    .endAdd();
        }
    }

    public TileSolidMelter() {
        super(100000, 2, 1, 0, 0, 1, 16000);
        addLifeCycle(new FluidMachineLifecycle(this, new Int2ObjectArrayMap<>(), new Int2ObjectArrayMap<>(new int[]{0}, new int[][] {new int[] {1, 0}})));
        markBucketInput(1);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/solidmelter.png");
    }

    @Override
    public GuiSize getSize() {
        return GUI_SIZE_176_166_84.copy().withTitleY(4).withProcess(65, 31).withEnergy(43, 72);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.solidMelter.getLocalizedName();
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.SolidMelter.id();
    }

    @Nonnull
    @Override
    public FluidSlotInfo[] getFluids() {
        return new FluidSlotInfo[] {
                FluidSlotInfo.createOutput(0, 95, 16)
        };
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 45, 31),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 1, 116, 28),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 116, 46)
        };
    }

    @Override
    public int getEnergyTick() {
        return 10;
    }

    @Override
    public void update() {
        update(this);
    }
}
