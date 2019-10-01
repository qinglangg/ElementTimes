package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.IGuiProvider;
import com.elementtimes.elementcore.api.template.tileentity.recipe.IngredientPart;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeCapture;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.block.EvaporatingDish;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
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
 * 蒸发皿
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileEvaporatingDish extends TileEntity implements IGuiProvider {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        RECIPE = new MachineRecipeHandler(0, 0, 1, 1)
                .newRecipe()
                .addCost(20)
                .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.NaClSolutionDilute,1000))
                .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.NaClSolutionConcentrated, 1000))
                .endAdd();
    }

    public NBTTagCompound nbt = new NBTTagCompound();
    private List<EntityPlayerMP> player = new ArrayList<>(3);

    public TileEvaporatingDish() {}
    public TileEvaporatingDish(World world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey(EvaporatingDish.BIND_EVAPORATING_DISH)) {
            nbt = compound.getCompoundTag(EvaporatingDish.BIND_EVAPORATING_DISH);
        }
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound compoundNbt = super.writeToNBT(compound);
        compoundNbt.setTag(EvaporatingDish.BIND_EVAPORATING_DISH, nbt);
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
        return ElementtimesBlocks.evaporatingDish.getLocalizedName();
    }

    @Override
    public List<EntityPlayerMP> getOpenedPlayers() {
        return player;
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.SupportStandED.id();
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
