package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileItemHandler;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 蒸发皿
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileEvaporatingDish extends BaseTileEntity {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        RECIPE = new MachineRecipeHandler(0, 2, 1, 2);
    }

    public TileEvaporatingDish() {
        super(0, 0, 2, 1, 16000, 2, 16000);
    }

    public TileEvaporatingDish(World world, BlockPos pos) {
        this();
        this.world = world;
        this.pos = pos;
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/evaporatingdish.png");
    }

    @Override
    public GuiSize getSize() {
        return new GuiSize().withSize(176, 215, 7, 132)
                .withProcess(50, 38)
                .withProcess(56, 21, 0, 232, 14, 14)
                .withTitleY(126);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.evaporatingDish.getLocalizedName();
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.SupportStandED.id();
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof ITileItemHandler) {
            ITileItemHandler handler = (ITileItemHandler) te;
            return new Slot[] {
                    new SlotItemHandler(handler.getItemHandler(SideHandlerType.INPUT), 0, 13, 65),
                    new SlotItemHandler(handler.getItemHandler(SideHandlerType.INPUT), 0, 89, 65),
                    new SlotItemHandler(handler.getItemHandler(SideHandlerType.INPUT), 0, 128, 65),
                    new SlotItemHandler(handler.getItemHandler(SideHandlerType.OUTPUT), 0, 77, 19),
                    new SlotItemHandler(handler.getItemHandler(SideHandlerType.OUTPUT), 0, 77, 37),
                    new SlotItemHandler(handler.getItemHandler(SideHandlerType.OUTPUT), 0, 31, 65),
                    new SlotItemHandler(handler.getItemHandler(SideHandlerType.OUTPUT), 0, 107, 65),
                    new SlotItemHandler(handler.getItemHandler(SideHandlerType.OUTPUT), 0, 146, 65),
            };
        }
        return new Slot[0];
    }

    @Nonnull
    @Override
    public FluidSlotInfo[] getFluids() {
        return new FluidSlotInfo[] {
                FluidSlotInfo.createHorizontal(0, SideHandlerType.NONE, 66, 87),
                FluidSlotInfo.createInput(0, 23, 15),
                FluidSlotInfo.createOutput(0, 99, 15),
                FluidSlotInfo.createOutput(0, 138, 15)
        };
    }

    @Override
    public void update() {
        update(this);
    }

    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }
}
