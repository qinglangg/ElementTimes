package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.tools.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.EnergyGeneratorLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.recipe.IngredientPart;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.plugin.elementcore.JeiRecipe;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
            addRecipe(ElementtimesConfig.GENERAL.generaterFive, ElementtimesItems.fiveElements);
            addRecipe(ElementtimesConfig.GENERAL.generaterEnd, ElementtimesItems.endElement);
            addRecipe(ElementtimesConfig.GENERAL.generaterSoilGen, ElementtimesItems.soilElement);
            addRecipe(ElementtimesConfig.GENERAL.generaterWoodGen, ElementtimesItems.woodElement);
            addRecipe(ElementtimesConfig.GENERAL.generaterWaterGen, ElementtimesItems.waterElement);
            addRecipe(ElementtimesConfig.GENERAL.generaterFireGen, ElementtimesItems.fireElement);
            addRecipe(ElementtimesConfig.GENERAL.generaterSun, ElementtimesItems.photoElement);
            addRecipe(ElementtimesConfig.GENERAL.generaterGoldGen, ElementtimesItems.goldElement);
        }
    }

    private static void addRecipe(int energy, Item element) {
        IngredientPart<ItemStack> input = IngredientPart.forItem(element, 1).withStrings("Total " + energy + " FE");
        RECIPE.newRecipe().addCost(energy).addItemInput(input).endAdd();
    }

    public TileGeneratorElement() {
        super(ElementtimesConfig.GENERAL.generaterMaxEnergy, 1, 0);
        addLifeCycle(new EnergyGeneratorLifecycle<>(this));
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
