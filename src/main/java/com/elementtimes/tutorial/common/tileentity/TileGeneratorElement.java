package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.ModInvokeStatic;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.other.machineRecipe.MachineRecipeHandler;

import javax.annotation.Nonnull;

/**
 * 元素发电机的 TileEntity
 * @author KSGFK create in 2019/2/17
 */
@ModInvokeStatic("init")
public class TileGeneratorElement extends BaseGenerator {

    public TileGeneratorElement() {
        super(ElementtimesConfig.GENERAL.generaterMaxEnergy);
    }

    public static MachineRecipeHandler sRecipeHandler;

    public static void init() {
        sRecipeHandler = new MachineRecipeHandler()
                .add("0", r -> -ElementtimesConfig.GENERAL.generaterFive, ElementtimesItems.fiveElements)
                .add("1", r -> -ElementtimesConfig.GENERAL.generaterEnd, ElementtimesItems.endElement)
                .add("2", r -> -ElementtimesConfig.GENERAL.generaterSoilGen, ElementtimesItems.soilElement)
                .add("3", r -> -ElementtimesConfig.GENERAL.generaterWoodGen, ElementtimesItems.woodElement)
                .add("4", r -> -ElementtimesConfig.GENERAL.generaterWaterGen, ElementtimesItems.waterElement)
                .add("5", r -> -ElementtimesConfig.GENERAL.generaterFireGen, ElementtimesItems.fireElement)
                .add("6", r -> -ElementtimesConfig.GENERAL.generaterSun, ElementtimesItems.photoElement)
                .add("7", r -> -ElementtimesConfig.GENERAL.generaterGoldGen, ElementtimesItems.goldElement);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler createRecipe() {
        return sRecipeHandler;
    }

    @Override
    public void applyConfig() {
        setMaxTransfer(ElementtimesConfig.GENERAL.generaterMaxExtract);
    }

    @Override
    public int getMaxEnergyChange() {
        return ElementtimesConfig.GENERAL.generaterMaxReceive;
    }

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.ElementGenerator;
    }
}
