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
import com.elementtimes.tutorial.config.ETConfig;
import com.elementtimes.tutorial.plugin.elementcore.JeiRecipe;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Collections;

/**
 * 元素发电机的 TileEntity
 * @author KSGFK create in 2019/2/17
 */
@ModInvokeStatic("init")
public class TileGeneratorElement extends BaseTileEntity {

    @JeiRecipe.MachineRecipe(block = "elementtimes:elementgenerater", gui = TileGeneratorElement.class, u = 40, v = 21, w = 95, h = 39)
    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler(1, 0, 0, 0);

    public static IngredientPart<ItemStack> FIVE  = IngredientPart.forItem(ElementtimesItems.fiveElements, 1).withStrings(() -> Collections.singletonList(ETConfig.GENERATOR_ELE.five  + " FE"));
    public static IngredientPart<ItemStack> END   = IngredientPart.forItem(ElementtimesItems.endElement  , 1).withStrings(() -> Collections.singletonList(ETConfig.GENERATOR_ELE.end   + " FE"));
    public static IngredientPart<ItemStack> SOIL  = IngredientPart.forItem(ElementtimesItems.soilElement , 1).withStrings(() -> Collections.singletonList(ETConfig.GENERATOR_ELE.soil  + " FE"));
    public static IngredientPart<ItemStack> WOOD  = IngredientPart.forItem(ElementtimesItems.woodElement , 1).withStrings(() -> Collections.singletonList(ETConfig.GENERATOR_ELE.wood  + " FE"));
    public static IngredientPart<ItemStack> WATER = IngredientPart.forItem(ElementtimesItems.waterElement, 1).withStrings(() -> Collections.singletonList(ETConfig.GENERATOR_ELE.water + " FE"));
    public static IngredientPart<ItemStack> FIRE  = IngredientPart.forItem(ElementtimesItems.fireElement , 1).withStrings(() -> Collections.singletonList(ETConfig.GENERATOR_ELE.fire  + " FE"));
    public static IngredientPart<ItemStack> SUN   = IngredientPart.forItem(ElementtimesItems.photoElement, 1).withStrings(() -> Collections.singletonList(ETConfig.GENERATOR_ELE.sun   + " FE"));
    public static IngredientPart<ItemStack> GOLD  = IngredientPart.forItem(ElementtimesItems.goldElement , 1).withStrings(() -> Collections.singletonList(ETConfig.GENERATOR_ELE.gold  + " FE"));

    public static void init() {
        if (RECIPE.getMachineRecipes().isEmpty()) {
            RECIPE.newRecipe().addCost(c -> -ETConfig.GENERATOR_ELE.five ).addItemInput(FIVE ).endAdd()
                  .newRecipe().addCost(c -> -ETConfig.GENERATOR_ELE.end  ).addItemInput(END  ).endAdd()
                  .newRecipe().addCost(c -> -ETConfig.GENERATOR_ELE.soil ).addItemInput(SOIL ).endAdd()
                  .newRecipe().addCost(c -> -ETConfig.GENERATOR_ELE.wood ).addItemInput(WOOD ).endAdd()
                  .newRecipe().addCost(c -> -ETConfig.GENERATOR_ELE.water).addItemInput(WATER).endAdd()
                  .newRecipe().addCost(c -> -ETConfig.GENERATOR_ELE.fire ).addItemInput(FIRE ).endAdd()
                  .newRecipe().addCost(c -> -ETConfig.GENERATOR_ELE.sun  ).addItemInput(SUN  ).endAdd()
                  .newRecipe().addCost(c -> -ETConfig.GENERATOR_ELE.gold ).addItemInput(GOLD ).endAdd();
        }
    }

    public TileGeneratorElement() {
        super(ETConfig.GENERATOR_ELE.capacity, 1, 0);
        addLifeCycle(new EnergyGeneratorLifecycle<>(this));
        getEnergyHandler().setTransferSupplier(() -> ETConfig.GENERATOR_ELE.output);
        getEnergyHandler().setCapacitySupplier(() -> ETConfig.GENERATOR_ELE.capacity);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public int getEnergyTick() {
        return ETConfig.GENERATOR_ELE.generate;
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        return new Slot[] {new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 80, 30)};
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
