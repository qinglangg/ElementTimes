package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.IGuiProvider;
import com.elementtimes.elementcore.api.template.tileentity.recipe.IngredientPart;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeCapture;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.block.Crucible;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * 坩埚
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileCrucible extends TileEntity implements IGuiProvider {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        RECIPE = new MachineRecipeHandler(1, 1, 0, 0)
                .newRecipe()
                .addCost(20)
                .addItemInput(IngredientPart.forItem(ElementtimesItems.stoneIngot,1))
                .addItemOutput(IngredientPart.forItem(ElementtimesItems.calciumOxide,1 ))
                .endAdd();
    }

    public NBTTagCompound nbt = new NBTTagCompound();
    private List<EntityPlayerMP> player = new ArrayList<>(3);

    public TileCrucible() {}

    public TileCrucible(World world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey(Crucible.BIND_CRUCIBLE)) {
            nbt = compound.getCompoundTag(Crucible.BIND_CRUCIBLE);
        }
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound compoundNbt = super.writeToNBT(compound);
        compoundNbt.setTag(Crucible.BIND_CRUCIBLE, nbt);
        return compoundNbt;
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/supportstand.png");
    }

    @Override
    public GuiSize getSize() {
        return GUI_SIZE_176_201_119.copy().withTitleY(105).withProcess(80, 26, 0, 201, 14, 14);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.crucible.getLocalizedName();
    }

    @Override
    public List<EntityPlayerMP> getOpenedPlayers() {
        return player;
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.SupportStandC.id();
    }

    @Override
    public float getProcess() {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileSupportStand) {
            TileSupportStand tss = (TileSupportStand) te;
            MachineRecipeCapture workingRecipe = tss.getWorkingRecipe();
            if (workingRecipe != null) {
                return ((float) tss.getEnergyProcessed()) / ((float) workingRecipe.energy);
            }
        }
        return 0;
    }
}
