package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.IGuiProvider;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileItemHandler;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 坩埚
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileCrucible extends BaseTileEntity {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        RECIPE = new MachineRecipeHandler(1, 2, 0, 2);
    }

    public NBTTagCompound nbt = new NBTTagCompound();

    public TileCrucible() {
        super(0, 1, 2, 0, 0, 2, 16000);
    }

    public TileCrucible(World world, BlockPos pos) {
        this();
        this.world = world;
        this.pos = pos;
    }

    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/crucible.png");
    }

    @Override
    public IGuiProvider.GuiSize getSize() {
        return new GuiSize().withSize(176, 215, 7, 132).withTitleY(120)
                .withProcess(41, 34).withProcess(46, 17, 0, 232, 14, 14);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.crucible.getLocalizedName();
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof ITileItemHandler) {
            ITileItemHandler handler = (ITileItemHandler) te;
            return new Slot[] {
                    new SlotItemHandler(handler.getItemHandler(SideHandlerType.INPUT), 0, 21, 33),
                    new SlotItemHandler(handler.getItemHandler(SideHandlerType.INPUT), 0, 79, 61),
                    new SlotItemHandler(handler.getItemHandler(SideHandlerType.INPUT), 0, 118, 61),
                    new SlotItemHandler(handler.getItemHandler(SideHandlerType.OUTPUT), 0, 67, 15),
                    new SlotItemHandler(handler.getItemHandler(SideHandlerType.OUTPUT), 0, 67, 33),
                    new SlotItemHandler(handler.getItemHandler(SideHandlerType.OUTPUT), 0, 97, 61),
                    new SlotItemHandler(handler.getItemHandler(SideHandlerType.OUTPUT), 0, 136, 61)
            };
        }
        return new Slot[0];
    }

    @Nonnull
    @Override
    public FluidSlotInfo[] getFluids() {
        return new FluidSlotInfo[] {
                FluidSlotInfo.createHorizontal(0, SideHandlerType.NONE, 65, 83),
                FluidSlotInfo.createOutput(0, 89, 11),
                FluidSlotInfo.createOutput(1, 128, 11)
        };
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.SupportStandC.id();
    }

    @Override
    public void update() {
        update(this);
    }
}
