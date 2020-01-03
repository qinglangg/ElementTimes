package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.FluidMachineLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.WorldReplaceMachineLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.items.SlotItemHandler;
import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.annotation.Nonnull;

/**
 * 液泵
 * @author luqin2007
 */
public class TilePumpFluid extends BaseTileEntity {

    public static MachineRecipeHandler RECIPE =
            new MachineRecipeHandler(0, 0 ,0 , 1).newRecipe().addCost(10).endAdd();

    public TilePumpFluid() {
        super(1000, 1, 1, 0, 0, 1, 16000);
        addLifeCycle(new WorldReplaceMachineLifecycle(this, this::replace, this::collect,
                20, 10, 20));
        addLifeCycle(new FluidMachineLifecycle(this));
    }

    private Fluid search = null;

    private IBlockState replace(IBlockState old) {
        search = FluidRegistry.lookupFluidForBlock(old.getBlock());
        if (search != null) {
            return ElementtimesBlocks.fr.getDefaultState();
        }
        return null;
    }

    private ImmutablePair<Integer, Object> collect(IBlockState bs) {
        if (search != null) {
            return ImmutablePair.of(0, search);
        }
        return null;
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.PumpFluid.id();
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public int getEnergyTick() {
        return 10;
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 56, 58),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 98, 58)
        };
    }

    @Nonnull
    @Override
    public FluidSlotInfo[] getFluids() {
        return new FluidSlotInfo[] {
                FluidSlotInfo.createOutput(0, 77, 28)
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/pump.png");
    }

    @Override
    public GuiSize getSize() {
        return GUI_SIZE_176_204_122.copy().withTitleY(85).withEnergy(43, 108, 24, 204, 90, 4);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.pumpFluid.getLocalizedName();
    }
}
