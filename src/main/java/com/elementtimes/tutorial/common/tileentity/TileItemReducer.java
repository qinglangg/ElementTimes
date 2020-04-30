package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.tools.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.recipe.IngredientPart;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ETConfig;
import com.elementtimes.tutorial.plugin.elementcore.JeiRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 物质还原机
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileItemReducer extends BaseTileEntity {

    @JeiRecipe.MachineRecipe(block = "elementtimes:itemreducer", gui = TileItemReducer.class, u = 44, v = 16, w = 90, h = 44)
    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler(1, 1, 0, 0);

    public static void init() {
        if (RECIPE.getMachineRecipes().isEmpty()) {
            RECIPE.newRecipe().addCost(c -> ETConfig.ITEM_REDUCER.energy).addItemInput(IngredientPart.forItem(ElementtimesItems.bambooCharcoal , 1)).addItemOutput(IngredientPart.forItem(ElementtimesBlocks.bamboo, 4)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.ITEM_REDUCER.energy).addItemInput(IngredientPart.forItem(ElementtimesBlocks.bambooBlock, 1)).addItemOutput(IngredientPart.forItem(ElementtimesBlocks.bamboo, 8)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.ITEM_REDUCER.energy).addItemInput(IngredientPart.forItem(ElementtimesItems.sucroseCharCoal, 1)).addItemOutput(IngredientPart.forItem(Items.REEDS, 4)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.ITEM_REDUCER.energy).addItemInput(IngredientPart.forItem("blockTin", 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.tin, 9)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.ITEM_REDUCER.energy).addItemInput(IngredientPart.forItem("blockLead", 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.lead, 9)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.ITEM_REDUCER.energy).addItemInput(IngredientPart.forItem("blockCopper", 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.copper, 9)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.ITEM_REDUCER.energy).addItemInput(IngredientPart.forItem("blockPlatinum", 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.platinumIngot, 9)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.ITEM_REDUCER.energy).addItemInput(IngredientPart.forItem("blockSilver", 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.silver, 9)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.ITEM_REDUCER.energy).addItemInput(IngredientPart.forItem("blockSteel", 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.steelIngot, 9)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.ITEM_REDUCER.energy).addItemInput(IngredientPart.forItem(ElementtimesBlocks.diamondBlock, 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.diamondIngot, 9)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.ITEM_REDUCER.energy).addItemInput(IngredientPart.forItem(Blocks.MELON_BLOCK, 1)).addItemOutput(IngredientPart.forItem(Items.MELON , 9)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.ITEM_REDUCER.energy).addItemInput(IngredientPart.forItem(Blocks.NETHER_WART_BLOCK, 1)).addItemOutput(IngredientPart.forItem(Items.NETHER_WART, 9)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.ITEM_REDUCER.energy).addItemInput(IngredientPart.forItem(Blocks.GLOWSTONE, 1)).addItemOutput(IngredientPart.forItem(Items.GLOWSTONE_DUST, 4)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.ITEM_REDUCER.energy).addItemInput(IngredientPart.forItem(new ItemStack(Blocks.WOOL, 1,0))).addItemOutput(IngredientPart.forItem(Items.STRING, 4)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.ITEM_REDUCER.energy).addItemInput(IngredientPart.forItem(Blocks.CLAY, 1)).addItemOutput(IngredientPart.forItem(Items.CLAY_BALL, 4)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.ITEM_REDUCER.energy).addItemInput(IngredientPart.forItem(Blocks.WEB, 1)).addItemOutput(IngredientPart.forItem(Items.STRING, 8)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.ITEM_REDUCER.energy).addItemInput(IngredientPart.forItem(new ItemStack(ElementtimesBlocks.bambooSlab, 2,0))).addItemOutput(IngredientPart.forItem(ElementtimesBlocks.bambooBlock, 1)).endAdd();

        }
    }

    public TileItemReducer() {
        super(ETConfig.ITEM_REDUCER.capacity, 1, 1);
        getEnergyHandler().setTransferSupplier(() -> ETConfig.ITEM_REDUCER.input);
        getEnergyHandler().setCapacitySupplier(() -> ETConfig.ITEM_REDUCER.capacity);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public int getEnergyTick() {
        return ETConfig.ITEM_REDUCER.energy;
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.ItemReducer.id();
    }

    @Override
    @Nonnull
    public Slot[] getSlots() {
        return new Slot[]{new SlotItemHandler(this.getItemHandler(SideHandlerType.INPUT), 0, 56, 30), new SlotItemHandler(this.getItemHandler(SideHandlerType.OUTPUT), 0, 110, 30)};
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/5.png");
    }

    @Override
    public GuiSize getSize() {
        return GUI_SIZE_176_156_74.copy().withTitleY(60)
                .withProcess(80, 30, 0, 156, 24, 17)
                .withEnergy(43, 55, 24, 156, 90, 4);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.itemReducer.getLocalizedName();
    }
}
