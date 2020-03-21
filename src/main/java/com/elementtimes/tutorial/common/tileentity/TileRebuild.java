package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.tools.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
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
 * 物质重构机
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileRebuild extends BaseTileEntity {

    @JeiRecipe.MachineRecipe(block = "elementtimes:rebuild", gui = TileRebuild.class, u = 44, v = 16, w = 90, h = 44)
    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler(1, 1, 0, 0);
    public int confVersion = 0;

    public static void init() {
        if (RECIPE.getMachineRecipes().isEmpty()) {
            RECIPE.add(10000000,Items.EGG, 1,new ItemStack(Blocks.DRAGON_EGG, 1))
                    .add(4000, ElementtimesItems.starchPowder, 1, new ItemStack(ElementtimesItems.sucroseCharCoal, 1))
                    .add(10000,ElementtimesItems.woodElement, 1, Items.TOTEM_OF_UNDYING, 1)
                    .add(4000, new ItemStack(Items.COAL, 1, 1), new ItemStack(Items.COAL, 1, 0))
                    .add(50000, new ItemStack(Items.COAL, 1, 0), Items.DIAMOND, 1)
                    .add(50000, Blocks.DIAMOND_BLOCK, 1, ElementtimesItems.diamondIngot, 3)
                    .add(10000, Blocks.DIRT, 1, Blocks.FARMLAND, 1)
                    .add(10000, Blocks.GRASS, 1, Blocks.GRASS_PATH, 1)
                    .add(20000, Blocks.SAND, 1, Blocks.SOUL_SAND, 1)
                    .add(20000, Blocks.GLASS, 1, Items.QUARTZ, 1)
                    .add(2000, ElementtimesItems.sucroseCharCoal,1, ElementtimesItems.bambooCharcoal, 1)
                    .add(2000, ElementtimesItems.bambooCharcoal,1, ElementtimesItems.sucroseCharCoal, 1);
        }
    }

    public TileRebuild() {
        super(ETConfig.REBUILD.capacity, 1, 1);
        getEnergyHandler().setTransferSupplier(() -> ETConfig.REBUILD.input);
        getEnergyHandler().setCapacitySupplier(() -> ETConfig.REBUILD.capacity);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public void applyConfig() {
        setEnergyTransfer(ETConfig.REBUILD.input);
    }

    @Override
    public int getEnergyTick() {
        return ETConfig.REBUILD.extract;
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.Rebuild.id();
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
        return ElementtimesBlocks.rebuild.getLocalizedName();

    }
}
