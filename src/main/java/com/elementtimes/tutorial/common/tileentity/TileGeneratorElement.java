package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.old.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.EnergyGeneratorLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.plugin.elementcore.JeiRecipe;
import net.minecraft.inventory.Slot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 元素发电机的 TileEntity
 * @author KSGFK create in 2019/2/17
 */
@ModInvokeStatic("init")
public class TileGeneratorElement extends BaseTileEntity {

    @JeiRecipe.MachineRecipe(block = "elementtimes:elementgenerater", gui = TileGeneratorElement.class, u = 40, v = 21, w = 95, h = 39)
    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler(1, 0, 0, 0);

    public static void init() {
        if (RECIPE.getMachineRecipes().isEmpty()) {
            RECIPE.add(r -> -ElementtimesConfig.GENERAL.generaterFive, ElementtimesItems.fiveElements)
                    .add(r -> -ElementtimesConfig.GENERAL.generaterEnd, ElementtimesItems.endElement)
                    .add(r -> -ElementtimesConfig.GENERAL.generaterSoilGen, ElementtimesItems.soilElement)
                    .add(r -> -ElementtimesConfig.GENERAL.generaterWoodGen, ElementtimesItems.woodElement)
                    .add(r -> -ElementtimesConfig.GENERAL.generaterWaterGen, ElementtimesItems.waterElement)
                    .add(r -> -ElementtimesConfig.GENERAL.generaterFireGen, ElementtimesItems.fireElement)
                    .add(r -> -ElementtimesConfig.GENERAL.generaterSun, ElementtimesItems.photoElement)
                    .add(r -> -ElementtimesConfig.GENERAL.generaterGoldGen, ElementtimesItems.goldElement);
        }
    }

    public TileGeneratorElement() {
        super(ElementtimesConfig.GENERAL.generaterMaxEnergy, 1, 0);
        addLifeCycle(new EnergyGeneratorLifecycle<>(this));
    }

    @Override
    public SideHandlerType getEnergyType(EnumFacing facing) {
        return SideHandlerType.OUTPUT;
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public void applyConfig() {
        setEnergyTransfer(ElementtimesConfig.GENERAL.generaterMaxExtract);
    }

    @Override
    public int getEnergyTick() {
        return ElementtimesConfig.GENERAL.generaterMaxReceive;
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 80, 30)
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/0.png");
    }

    @Override
    public GuiSize getSize() {
        return GUI_SIZE_176_156_74.copy().withTitleY(60).withEnergy(43, 55, 0, 156, 89, 4);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.elementGenerator.getLocalizedName();

    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.ElementGenerator.id();
    }
}
