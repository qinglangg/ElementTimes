package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
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

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler(1, 1, 0, 0)
                    .add(ElementtimesConfig.PUL.pulPowderEnergy, "oreIron", 1, ElementtimesItems.ironPowder, ElementtimesConfig.PUL.pulPowderCount)
                    .add(ElementtimesConfig.PUL.pulPowderEnergy, "oreRedstone", 1, ElementtimesItems.redstonePowder, ElementtimesConfig.PUL.pulPowderCount)
                    .add(ElementtimesConfig.PUL.pulPowderEnergy, "oreGold", 1, ElementtimesItems.goldPowder, ElementtimesConfig.PUL.pulPowderCount)
                    .add(ElementtimesConfig.PUL.pulPowderEnergy, "oreDiamond", 1, ElementtimesItems.diamondPowder, ElementtimesConfig.PUL.pulPowderCount)
                    .add(ElementtimesConfig.PUL.pulPowderEnergy, "oreLapis", 1, ElementtimesItems.bluestonePowder, ElementtimesConfig.PUL.pulPowderCount)
                    .add(ElementtimesConfig.PUL.pulPowderEnergy, "oreEmerald", 1, ElementtimesItems.greenstonePowder, ElementtimesConfig.PUL.pulPowderCount)
                    .add(ElementtimesConfig.PUL.pulPowderEnergy, "oreCopper", 1, ElementtimesItems.copperPowder, ElementtimesConfig.PUL.pulPowderCount)
                    .add(ElementtimesConfig.PUL.pulPowderEnergy, "oreCoal", 1, ElementtimesItems.coalPowder, ElementtimesConfig.PUL.pulPowderCount)
                    .add(ElementtimesConfig.PUL.pulPowderEnergy, "orePlatinum", 1, ElementtimesItems.platinumOrePowder, ElementtimesConfig.PUL.pulPowderCount)
                    .add(ElementtimesConfig.PUL.pulPowderEnergy, "oreSilver", 1, ElementtimesItems.silverPowder, ElementtimesConfig.PUL.pulPowderCount)
                    .add(ElementtimesConfig.PUL.pulPowderEnergy, "oreQuartz", 1, ElementtimesItems.quartzPowder, ElementtimesConfig.PUL.pulPowderCount)
                    .add(ElementtimesConfig.PUL.pulPowderEnergy, "oreSulfur", 1, ElementtimesItems.sulfurOrePowder, ElementtimesConfig.PUL.pulPowderCount)
                    .add(ElementtimesConfig.PUL.pulPowderEnergy, "oreLead", 1, ElementtimesItems.leadPowder, ElementtimesConfig.PUL.pulPowderCount)
                    .add(ElementtimesConfig.PUL.pulPowderEnergy, "oreTin", 1, ElementtimesItems.tinPowder, ElementtimesConfig.PUL.pulPowderCount)
                    .add(ElementtimesConfig.PUL.pulPowderEnergy, Items.BLAZE_ROD, 1, Items.BLAZE_POWDER, 8);
        }
    }

    public TilePulverize() {
        super(ElementtimesConfig.PUL.pulMaxEnergy, 1, 1);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public void applyConfig() {
        setEnergyTransfer(ElementtimesConfig.PUL.pulMaxReceive);
    }

    @Override
    public int getEnergyTick() {
        return ElementtimesConfig.PUL.pulMaxExtract;
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

    @Override
    public void update() {
        update(this);
    }
}
