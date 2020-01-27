package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.old.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.plugin.elementcore.JeiRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 成型机
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileForming extends BaseTileEntity {

    public TileForming() {
        super(ElementtimesConfig.FORMING.maxEnergy, 1, 1);
    }

    @JeiRecipe.MachineRecipe(block = "elementtimes:forming", gui = TileForming.class, u = 44, v = 16, w = 90, h = 44)
    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler(1, 1, 0, 0);

    public static void init() {
        if (RECIPE.getMachineRecipes().isEmpty()) {
            RECIPE.add(10000, "plankWood", 9, ElementtimesItems.gearWood, 3)
                    .add(10000, "ingotSilver", 9, ElementtimesItems.gearSilver, 3)
                    .add(10000, "stone", 9, ElementtimesItems.gearStone, 3)
                    .add(10000, "coal", 9, ElementtimesItems.gearCarbon, 3)
                    .add(10000, "ingotGold", 9, ElementtimesItems.gearGold, 3)
                    .add(10000, "ingotSteel", 9, ElementtimesItems.gearSteel, 3)
                    .add(10000, "gemDiamond", 9, ElementtimesItems.gearDiamond, 3)
                    .add(10000, "ingotIron", 9, ElementtimesItems.gearIron, 3)
                    .add(10000, "ingotPlatinum", 9, ElementtimesItems.gearPlatinum, 3)
                    .add(10000, "ingotCopper", 9, ElementtimesItems.gearCopper, 3)
                    .add(10000, "ingotLead", 9, ElementtimesItems.gearLead, 3)
                    .add(10000, ElementtimesItems.diamondIngot, 9, ElementtimesItems.gearAdamas, 3)
                    .add(10000,Blocks.OBSIDIAN, 9, ElementtimesItems.gearObsidian, 3)
                    .add(10000, "gemQuartz", 9, ElementtimesItems.gearQuartz, 3)
                    .add(10000, "ingotTin", 9, ElementtimesItems.gearTin, 3);
        }
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public void applyConfig() {
        setEnergyTransfer(ElementtimesConfig.FORMING.maxReceive);
    }

    @Override
    public int getEnergyTick() {
        return ElementtimesConfig.FORMING.maxExtract;
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
        return ElementtimesBlocks.forming.getLocalizedName();

    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.Forming.id();
    }
}
