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
import com.elementtimes.tutorial.plugin.elementcore.JeiRecipe;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.animation.TimeValues;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.model.animation.AnimationStateMachine;
import net.minecraftforge.common.model.animation.CapabilityAnimation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.elementtimes.elementcore.api.template.block.Properties.IS_RUNNING;

/**
 * 压缩机
 * @author lq2007 create in 2019/5/19
 */
@ModInvokeStatic("init")
public class TileCompressor extends BaseTileEntity {
    private final static String ANIMATION_STATE_PLAY = "play";
    private final static String ANIMATION_STATE_STOP = "stop";

    public TileCompressor() {
        super(ElementtimesConfig.COMPRESSOR.maxEnergy, 1, 1);
    }

    @JeiRecipe.MachineRecipe(block = "elementtimes:compressor", gui = TileCompressor.class, u = 44, v = 16, w = 90, h = 44)
    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler(1, 1, 0, 0);

    public static void init() {
        if (RECIPE.getMachineRecipes().isEmpty()) {
            RECIPE.add(ElementtimesConfig.COMPRESSOR.powderEnergy, "plankWood", 1, ElementtimesItems.platewood, ElementtimesConfig.COMPRESSOR.powderCount)
                    .add(ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotCopper", 1, ElementtimesItems.plateCopper, ElementtimesConfig.COMPRESSOR.powderCount)
                    .add(ElementtimesConfig.COMPRESSOR.powderEnergy, "gemDiamond", 1, ElementtimesItems.plateDiamond, ElementtimesConfig.COMPRESSOR.powderCount)
                    .add(ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotGold", 1, ElementtimesItems.plateGold, ElementtimesConfig.COMPRESSOR.powderCount)
                    .add(ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotIron", 1, ElementtimesItems.plateIron, ElementtimesConfig.COMPRESSOR.powderCount)
                    .add(ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotPlatinum", 1, ElementtimesItems.platePlatinum, ElementtimesConfig.COMPRESSOR.powderCount)
                    .add(ElementtimesConfig.COMPRESSOR.powderEnergy, "gemQuartz", 1, ElementtimesItems.plateQuartz, ElementtimesConfig.COMPRESSOR.powderCount)
                    .add(ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotSteel", 1, ElementtimesItems.plateSteel, ElementtimesConfig.COMPRESSOR.powderCount)
                    .add(ElementtimesConfig.COMPRESSOR.powderEnergy, "stone", 1, ElementtimesItems.plateStone, ElementtimesConfig.COMPRESSOR.powderCount)
                    .add(ElementtimesConfig.COMPRESSOR.powderEnergy,ElementtimesItems.stonepowder, 1, ElementtimesItems.plateStone, ElementtimesConfig.COMPRESSOR.powderCount)
                    .add(ElementtimesConfig.COMPRESSOR.powderEnergy, Items.COAL, 1, ElementtimesItems.plateCarbon, ElementtimesConfig.COMPRESSOR.powderCount)
                    .add(ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotLead", 1, ElementtimesItems.plateLead, ElementtimesConfig.COMPRESSOR.powderCount)
                    .add(ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotTin", 1, ElementtimesItems.plateTin, ElementtimesConfig.COMPRESSOR.powderCount)
                    .add(ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotSilver", 1, ElementtimesItems.plateSilver, ElementtimesConfig.COMPRESSOR.powderCount)
                    .add(ElementtimesConfig.COMPRESSOR.powderEnergy, ElementtimesItems.diamondIngot, 1, ElementtimesItems.plateAdamas, ElementtimesConfig.COMPRESSOR.powderCount)
                    .add(ElementtimesConfig.COMPRESSOR.powderEnergy, Blocks.OBSIDIAN, 1, ElementtimesItems.plateObsidian, ElementtimesConfig.COMPRESSOR.powderCount)
            		.add(ElementtimesConfig.COMPRESSOR.powderEnergy, ElementtimesItems.Silicon, 1, ElementtimesItems.siliconPlate, ElementtimesConfig.COMPRESSOR.powderCount)
                    .add(ElementtimesConfig.COMPRESSOR.powderEnergy, ElementtimesItems.Al, 1, ElementtimesItems.aluminiumPlate, ElementtimesConfig.COMPRESSOR.powderCount)
            		
            		;
        }
    }

    private AnimationStateMachine mAnimationStateMachine;

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public void applyConfig() {
        setEnergyTransfer(ElementtimesConfig.COMPRESSOR.maxReceive);
    }

    @Override
    public int getEnergyTick() {
        return ElementtimesConfig.COMPRESSOR.maxExtract;
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.Compressor.id();
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return super.hasCapability(capability, facing) || capability == CapabilityAnimation.ANIMATION_CAPABILITY;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityAnimation.ANIMATION_CAPABILITY && FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            return capability.cast((T) getASM());
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    private AnimationStateMachine getASM() {
        if (mAnimationStateMachine == null) {
            mAnimationStateMachine = (AnimationStateMachine) net.minecraftforge.client.model.ModelLoaderRegistry.loadASM(
                    new ResourceLocation(ElementTimes.MODID, "asms/block/compressor.json"),
                    ImmutableMap.of("cycle_length", new TimeValues.ConstValue(5)));
        }
        return mAnimationStateMachine;
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
        return ElementtimesBlocks.compressor.getLocalizedName();

    }

    @Override
    public IBlockState updateState(IBlockState old) {
        boolean working = isWorking();
        if (old.getValue(IS_RUNNING) != working) {
            return old.withProperty(IS_RUNNING, working);
        }
        return old;
    }

    @Override
    public void updateClient() {
        Boolean running = world.getBlockState(pos).getValue(IS_RUNNING);
        String state = getASM().currentState();
        if (running && ANIMATION_STATE_STOP.equals(state)) {
            getASM().transition(ANIMATION_STATE_PLAY);
        } else if (!running && ANIMATION_STATE_PLAY.equals(state)) {
            getASM().transition(ANIMATION_STATE_STOP);
        }
    }
}
