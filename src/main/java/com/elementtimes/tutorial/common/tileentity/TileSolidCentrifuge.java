package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.recipe.IngredientPart;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.plugin.elementcore.JeiRecipe;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 固体离心机
 *
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileSolidCentrifuge extends BaseTileEntity {

    @JeiRecipe.MachineRecipe(block = "elementtimes:solidcentrifuge", gui = TileSolidCentrifuge.class, u = 52, v = 12, w = 76, h = 58)
    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler(1, 3, 0, 0);

    public static void init() {
        if (RECIPE.getMachineRecipes().isEmpty()) {
            RECIPE.newRecipe()
                    .addCost(1000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.slag, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.stonepowder, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.sandpowder, 1))
                    .endAdd();
        }
    }


    public TileSolidCentrifuge() {
        super(100000, 1, 3);
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
        return ElementtimesBlocks.solidCentrifuge.getLocalizedName();
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.SolidCentrifuge.id();
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        return new Slot[]{
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 55, 31),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 109, 15),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 109, 33),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 2, 109, 51)
        };
    }

    @Override
    public int getEnergyTick() {
        return 30;
    }

    @Override
    public void update() {
        update(this);
    }
}