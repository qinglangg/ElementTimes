package com.elementtimes.tutorial.test_animation;

import com.elementtimes.tutorial.ElementTimes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.animation.TimeValues;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.model.animation.CapabilityAnimation;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class TileBlockAnimated extends TileEntity {

    public TileBlockAnimated() {
        mASM = ModelLoaderRegistry.loadASM(new ResourceLocation(ElementTimes.MODID, "asms/block/anidemo.json"), ImmutableMap.of(
                "cycle_length", new TimeValues.ConstValue(10f)
        ));
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityAnimation.ANIMATION_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityAnimation.ANIMATION_CAPABILITY) {
            return capability.cast((T) getASM());
        }
        return super.getCapability(capability, facing);
    }

    @SideOnly(Side.CLIENT)
    private IAnimationStateMachine mASM = null;

    @SideOnly(Side.CLIENT)
    private IAnimationStateMachine getASM() {
        return mASM;
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }
}
