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
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 打粉机
 * @author KSGFK create in 2019/5/6
 */
@ModInvokeStatic("init")
public class TilePulverize extends BaseTileEntity {

    @JeiRecipe.MachineRecipe(block = "elementtimes:pulverizer", gui = TilePulverize.class, u = 44, v = 16, w = 90, h = 44)
    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler(1, 1, 0, 0);

    public static void init() {
        if (RECIPE.getMachineRecipes().isEmpty()) {
            RECIPE.newRecipe().addCost(c -> ETConfig.PULVERIZE.energy).addItemInput(IngredientPart.forItem("oreIron"      , 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.ironpowder       , () -> ETConfig.PULVERIZE.count)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.PULVERIZE.energy).addItemInput(IngredientPart.forItem("oreRedstone"  , 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.redstonePowder   , () -> ETConfig.PULVERIZE.count)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.PULVERIZE.energy).addItemInput(IngredientPart.forItem("oreGold"      , 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.goldPowder       , () -> ETConfig.PULVERIZE.count)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.PULVERIZE.energy).addItemInput(IngredientPart.forItem("oreDiamond"   , 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.diamondPowder    , () -> ETConfig.PULVERIZE.count)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.PULVERIZE.energy).addItemInput(IngredientPart.forItem("oreLapis"     , 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.bluestonePowder  , () -> ETConfig.PULVERIZE.count)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.PULVERIZE.energy).addItemInput(IngredientPart.forItem("oreEmerald"   , 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.greenstonePowder , () -> ETConfig.PULVERIZE.count)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.PULVERIZE.energy).addItemInput(IngredientPart.forItem("oreCopper"    , 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.copperPowder     , () -> ETConfig.PULVERIZE.count)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.PULVERIZE.energy).addItemInput(IngredientPart.forItem("oreCoal"      , 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.coalPowder       , () -> ETConfig.PULVERIZE.count)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.PULVERIZE.energy).addItemInput(IngredientPart.forItem("orePlatinum"  , 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.platinumOrePowder, () -> ETConfig.PULVERIZE.count)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.PULVERIZE.energy).addItemInput(IngredientPart.forItem("oreSilver"    , 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.silverPowder     , () -> ETConfig.PULVERIZE.count)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.PULVERIZE.energy).addItemInput(IngredientPart.forItem("oreQuartz"    , 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.quartzPowder     , () -> ETConfig.PULVERIZE.count)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.PULVERIZE.energy).addItemInput(IngredientPart.forItem("oreSulfur"    , 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.sulfurOrePowder  , () -> ETConfig.PULVERIZE.count)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.PULVERIZE.energy).addItemInput(IngredientPart.forItem("oreLead"      , 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.leadPowder       , () -> ETConfig.PULVERIZE.count)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.PULVERIZE.energy).addItemInput(IngredientPart.forItem("oreTin"       , 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.tinPowder        , () -> ETConfig.PULVERIZE.count)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.PULVERIZE.energy).addItemInput(IngredientPart.forItem("oreSalt"       , 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.salt            , () -> ETConfig.PULVERIZE.count)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.PULVERIZE.energy).addItemInput(IngredientPart.forItem(Items.BLAZE_ROD, 1)).addItemOutput(IngredientPart.forItem(Items.BLAZE_POWDER                 , () -> ETConfig.PULVERIZE.count)).endAdd();
            //其他模组矿粉
            RECIPE.newRecipe().addCost(c -> ETConfig.PULVERIZE.energy).addItemInput(IngredientPart.forItem("oreNickel",1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.nickelpowder          , () -> ETConfig.PULVERIZE.count)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.PULVERIZE.energy).addItemInput(IngredientPart.forItem("oreOsmium", 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.osmiumpowder         , () -> ETConfig.PULVERIZE.count)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.PULVERIZE.energy).addItemInput(IngredientPart.forItem("oreArdite", 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.arditepowder         , () -> ETConfig.PULVERIZE.count)).endAdd();
            RECIPE.newRecipe().addCost(c -> ETConfig.PULVERIZE.energy).addItemInput(IngredientPart.forItem("oreCobalt", 1)).addItemOutput(IngredientPart.forItem(ElementtimesItems.cobaltpowder         , () -> ETConfig.PULVERIZE.count)).endAdd();

        }
    }

    public TilePulverize() {
        super(ETConfig.PULVERIZE.capacity, 1, 1);
        getEnergyHandler().setTransferSupplier(() -> ETConfig.PULVERIZE.input);
        getEnergyHandler().setCapacitySupplier(() -> ETConfig.PULVERIZE.capacity);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public int getEnergyTick() {
        return ETConfig.PULVERIZE.energy;
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.Pulverize.id();
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
        return ElementtimesBlocks.pulverizer.getLocalizedName();

    }
}
