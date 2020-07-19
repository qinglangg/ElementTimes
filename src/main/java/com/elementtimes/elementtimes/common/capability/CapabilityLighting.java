package com.elementtimes.elementtimes.common.capability;

import com.elementtimes.elementcore.api.annotation.ModCapability;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class CapabilityLighting {

    public static final ResourceLocation NAME = new ResourceLocation(ElementTimes.MODID, "cap_light");

    @CapabilityInject(CapabilityLighting.ILightPos.class)
    public static Capability<CapabilityLighting.ILightPos> CAPABILITY_LIGHT;

    @ModCapability(factory = @Method(CapabilityLighting.LightPos.class), storage = @Getter(CapabilityLighting.LightStorage.class))
    public interface ILightPos {

        void add(LightWrapper wrapper);

        void tick();

        @Nonnull
        ListNBT write();

        void read(@Nonnull ListNBT compound);
    }

    public static class LightStorage implements Capability.IStorage<CapabilityLighting.ILightPos> {

        @Nullable
        @Override
        public INBT writeNBT(Capability<CapabilityLighting.ILightPos> capability, CapabilityLighting.ILightPos instance, Direction side) {
            return instance.write();
        }

        @Override
        public void readNBT(Capability<CapabilityLighting.ILightPos> capability, CapabilityLighting.ILightPos instance, Direction side, INBT nbt) {
            instance.read((ListNBT) nbt);
        }
    }

    public static class LightPos implements CapabilityLighting.ILightPos {

        List<LightWrapper> mLights = new ArrayList<>();

        World mWorld;

        public LightPos(World world) {
            mWorld = world;
        }

        public LightPos() { }

        @Override
        public void add(LightWrapper wrapper) {
            mLights.add(wrapper);
        }

        @Override
        public void tick() {
            if (mWorld == null || mWorld.isRemote) {
                return;
            }
            Iterator<LightWrapper> iterator = mLights.iterator();
            while (iterator.hasNext()) {
                LightWrapper wrapper = iterator.next();
                if (wrapper.exist > 0) {
                    wrapper.exist--;
                } else if (mWorld.isAreaLoaded(wrapper.pos, 1)) {
                    Block block = mWorld.getBlockState(wrapper.pos).getBlock();
                    if (block instanceof FlowingFluidBlock && ((FlowingFluidBlock) block).getFluid() == wrapper.fluid) {
                        mWorld.removeBlock(wrapper.pos, false);
                        iterator.remove();
                    }
                }
            }
        }

        @Override
        public void read(@Nonnull ListNBT list) {
            mLights.clear();
            for (int i = 0; i < list.size(); i++) {
                CompoundNBT nbt = list.getCompound(i);
                LightWrapper wrapper = new LightWrapper();
                wrapper.fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(nbt.getString("fluid")));
                wrapper.exist = nbt.getInt("exist");
                wrapper.pos = BlockPos.fromLong(nbt.getLong("pos"));
                mLights.add(wrapper);
            }
        }

        @Nonnull
        @Override
        public ListNBT write() {
            ListNBT list = new ListNBT();
            for (LightWrapper light : mLights) {
                CompoundNBT nbt = new CompoundNBT();
                nbt.putInt("exist", light.exist);
                nbt.putString("fluid", light.fluid.getRegistryName().toString());
                nbt.putLong("pos", light.pos.toLong());
                list.add(nbt);
            }
            return list;
        }
    }

    public static class CapProvider implements ICapabilityProvider {

        private final CapabilityLighting.ILightPos mCapability;

        public CapProvider(World world) {
            mCapability = new LightPos(world);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, final @Nullable Direction side) {
            if (cap == CAPABILITY_LIGHT) {
                return (LazyOptional<T>) LazyOptional.of(() -> mCapability);
            }
            return LazyOptional.empty();
        }
    }

    public static class LightWrapper {
        public int exist = 200;
        public BlockPos pos;
        public Fluid fluid;
    }
}
