package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.old.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.recipe.IngredientPart;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.plugin.elementcore.JeiRecipe;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 提取机
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileExtractor extends BaseTileEntity {

    @JeiRecipe.MachineRecipe(block = "elementtimes:extractor", gui = TileExtractor.class, u = 44, v = 16, w = 90, h = 44)
    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler(1, 3, 0, 0);

    public static void init() {
        if (RECIPE.getMachineRecipes().isEmpty()) {
            RECIPE.newRecipe().addCost(1000)
                    .addItemInput(IngredientPart.forItem(ElementtimesBlocks.rubberLeaf, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.rubberRaw, 1))
                    .addItemOutput(IngredientPart.EMPTY_ITEM)
                    .addItemOutput(IngredientPart.EMPTY_ITEM)
                    .endAdd()
                    .newRecipe().addCost(4000)
                    .addItemInput(IngredientPart.forItem(ElementtimesBlocks.rubberLog, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.rubberRaw, 4))
                    .addItemOutput(IngredientPart.EMPTY_ITEM)
                    .addItemOutput(IngredientPart.EMPTY_ITEM)
                    .endAdd()
                    .newRecipe().addCost(2000)
                    .addItemInput(IngredientPart.forItem(ElementtimesBlocks.rubberSapling, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.rubberRaw, 2))
                    .addItemOutput(IngredientPart.EMPTY_ITEM)
                    .addItemOutput(IngredientPart.EMPTY_ITEM)
                    .endAdd()
                    .newRecipe().addCost(2000)
                    .addItemInput(IngredientPart.forItem(Items.GUNPOWDER, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.sulfurPowder, 1))
                    .addItemOutput(IngredientPart.EMPTY_ITEM)
                    .addItemOutput(IngredientPart.EMPTY_ITEM)
                    .endAdd();
        }
    }

    public TileExtractor() {
        super(ElementtimesConfig.EXTRACTOR.maxEnergy, 1, 3);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public int getEnergyTick() {
        return ElementtimesConfig.EXTRACTOR.maxExtract;
    }

    @Override
    public void applyConfig() {
        setEnergyTransfer(ElementtimesConfig.EXTRACTOR.maxReceive);
    }

    @Override
    @Nonnull
    public Slot[] getSlots() {
        return new Slot[]{
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 55, 31),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 109, 15),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 109, 33),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 2, 109, 51)
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/solidcentrifuge.png");
    }

    @Override
    public GuiSize getSize() {
        return GUI_SIZE_176_166_84.copy().withTitleY(72)
                .withProcess(80, 31, 0, 183, 16, 16)
                .withEnergy(43, 73, 24, 166, 90, 4);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.extractor.getLocalizedName();

    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.Extractor.id();
    }
}

