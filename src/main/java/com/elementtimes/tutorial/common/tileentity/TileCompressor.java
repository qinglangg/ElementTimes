package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.ModInvokeStatic;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.block.base.BaseClosableMachine;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.interfaces.tileentity.IMachineLifecycle;
import com.elementtimes.tutorial.other.machineRecipe.MachineRecipeHandler;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.animation.TimeValues;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.model.animation.AnimationStateMachine;
import net.minecraftforge.common.model.animation.CapabilityAnimation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 压缩机
 * @author lq2007 create in 2019/5/19
 */
@ModInvokeStatic("init")
public class TileCompressor extends BaseOneToOne {
    private final static String ANIMATION_STATE_PLAY = "play";
    private final static String ANIMATION_STATE_STOP = "stop";

    public TileCompressor() {
        super(ElementtimesConfig.COMPRESSOR.maxEnergy);
        addLifeCycle(new IMachineLifecycle() {
            @Override
            public void onTickFinish() {

            }
        });
    }

    public static MachineRecipeHandler sRecipeHandler;

    public static void init() {
        sRecipeHandler = new MachineRecipeHandler()
                .add("0", ElementtimesConfig.COMPRESSOR.powderEnergy, "plankWood", 1, ElementtimesItems.platewood, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("1", ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotCopper", 1, ElementtimesItems.plateCopper, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("2", ElementtimesConfig.COMPRESSOR.powderEnergy, "gemDiamond", 1, ElementtimesItems.plateDiamond, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("3", ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotGold", 1, ElementtimesItems.plateGold, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("4", ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotIron", 1, ElementtimesItems.plateIron, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("5", ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotPlatinum", 1, ElementtimesItems.platePlatinum, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("6", ElementtimesConfig.COMPRESSOR.powderEnergy, "gemQuartz", 1, ElementtimesItems.plateQuartz, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("7", ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotSteel", 1, ElementtimesItems.plateSteel, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("8", ElementtimesConfig.COMPRESSOR.powderEnergy, "stone", 1, ElementtimesItems.plateStone, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("81", ElementtimesConfig.COMPRESSOR.powderEnergy,ElementtimesItems.stonepowder, 1, ElementtimesItems.plateStone, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("9", ElementtimesConfig.COMPRESSOR.powderEnergy, Items.COAL, 1, ElementtimesItems.plateCarbon, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("10", ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotLead", 1, ElementtimesItems.plateLead, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("11", ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotTin", 1, ElementtimesItems.plateTin, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("12", ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotSilver", 1, ElementtimesItems.plateSilver, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("13", ElementtimesConfig.COMPRESSOR.powderEnergy, ElementtimesItems.diamondIngot, 1, ElementtimesItems.gearAdamas, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("13", ElementtimesConfig.COMPRESSOR.powderEnergy, Blocks.OBSIDIAN, 1, ElementtimesItems.plateObsidian, ElementtimesConfig.COMPRESSOR.powderCount);
    }

    private AnimationStateMachine mAnimationStateMachine;

    @Nonnull
    @Override
    public MachineRecipeHandler createRecipe() {
        return sRecipeHandler;
    }

    @Override
    public void applyConfig() {
        setMaxTransfer(ElementtimesConfig.COMPRESSOR.maxReceive);
    }

    @Override
    public int getMaxEnergyChange() {
        return ElementtimesConfig.COMPRESSOR.maxExtract;
    }

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.Compressor;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return super.hasCapability(capability, facing) || capability == CapabilityAnimation.ANIMATION_CAPABILITY;
    }

    @Nullable
    @Override
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
    public AnimationStateMachine getASM() {
        if (mAnimationStateMachine == null) {
            mAnimationStateMachine = (AnimationStateMachine) net.minecraftforge.client.model.ModelLoaderRegistry.loadASM(
                    new ResourceLocation(ElementTimes.MODID, "asms/block/compressor.json"),
                    ImmutableMap.of("cycle_length", new TimeValues.ConstValue(5)));
        }
        return mAnimationStateMachine;
    }

    @Override
    public IBlockState updateState(IBlockState old) {
        boolean working = isWorking();
        if (old.getValue(BaseClosableMachine.IS_RUNNING) != working) {
            return old.withProperty(BaseClosableMachine.IS_RUNNING, working);
        }
        return old;
    }

    @Override
    public void updateClient() {
        Boolean running = world.getBlockState(pos).getValue(BaseClosableMachine.IS_RUNNING);
        String state = getASM().currentState();
        if (running && ANIMATION_STATE_STOP.equals(state)) {
            getASM().transition(ANIMATION_STATE_PLAY);
        } else if (!running && ANIMATION_STATE_PLAY.equals(state)) {
            getASM().transition(ANIMATION_STATE_STOP);
        }
    }
}
