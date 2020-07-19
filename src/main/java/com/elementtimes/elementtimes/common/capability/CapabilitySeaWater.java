package com.elementtimes.elementtimes.common.capability;

import com.elementtimes.elementcore.api.annotation.ModCapability;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 记录实体使用盐数量

 */
public class CapabilitySeaWater {

    public static final ResourceLocation NAME = new ResourceLocation(ElementTimes.MODID, "cap_salt");

    @CapabilityInject(ISeaWater.class)
    public static Capability<ISeaWater> CAPABILITY_SEA_WATER;

    @ModCapability(factory = @Method(SeaWater.class), storage = @Getter(SeaStorage.class))
    public interface ISeaWater {

        int getSalt();

        void setSalt(int amount);

        void increaseSalt(int amount);

        void reduceSalt(int amount);

        int getCollidedTick();

        void collidedInSea();

        void resetCollidedTick();

        boolean isDirty();

        void markDirty();

        void clearDirty();

        void setLastSaltTick(long tick);

        long getLastSaltTick();

        @Nonnull
        CompoundNBT write();

        void read(@Nonnull CompoundNBT compound);
    }

    public static class SeaStorage implements Capability.IStorage<ISeaWater> {

        @Nullable
        @Override
        public INBT writeNBT(Capability<ISeaWater> capability, ISeaWater instance, Direction side) {
            return instance.write();
        }

        @Override
        public void readNBT(Capability<ISeaWater> capability, ISeaWater instance, Direction side, INBT nbt) {
            instance.read((CompoundNBT) nbt);
        }
    }

    public static class SeaWater implements ISeaWater {

        private int mSalt = 0;
        private int mCollided = 0;
        private long mSaltTick = 0;
        private boolean mDirty = false;

        @Override
        public int getSalt() {
            return mSalt;
        }

        @Override
        public void setSalt(int amount) {
            if (mSalt != amount) {
                mSalt = amount;
                markDirty();
            }
        }

        @Override
        public void increaseSalt(int amount) {
            mSalt += amount;
            markDirty();
        }

        @Override
        public void reduceSalt(int amount) {
            if (mSalt > 0) {
                mSalt -= amount;
                markDirty();
            }
        }

        @Override
        public int getCollidedTick() {
            return mCollided;
        }

        @Override
        public void collidedInSea() {
            mCollided++;
            markDirty();
        }

        @Override
        public void resetCollidedTick() {
            if (mCollided != 0) {
                mCollided = 0;
                markDirty();
            }
        }

        @Override
        public boolean isDirty() {
            return mDirty;
        }

        @Override
        public void markDirty() {
            mDirty = true;
        }

        @Override
        public void clearDirty() {
            mDirty = false;
        }

        @Override
        public long getLastSaltTick() {
            return mSaltTick;
        }

        @Override
        public void setLastSaltTick(long tick) {
            mSaltTick = tick;
        }

        @Override
        public void read(@Nonnull CompoundNBT compound) {
            mCollided = compound.getInt("collided");
            mSalt = compound.getInt("salt");
            mSaltTick = compound.getLong("tick");
        }

        @Nonnull
        @Override
        public CompoundNBT write() {
            CompoundNBT compound = new CompoundNBT();
            compound.putInt("collided", mCollided);
            compound.putInt("salt", mSalt);
            compound.putLong("tick", mSaltTick);
            return compound;
        }
    }

    public static class SaltAmountCapProvider implements ICapabilityProvider {

        private final ISeaWater mSeaWaterInfo;

        public SaltAmountCapProvider() {
            mSeaWaterInfo = new SeaWater();
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, final @Nullable Direction side) {
            if (cap == CAPABILITY_SEA_WATER) {
                return (LazyOptional<T>) LazyOptional.of(() -> mSeaWaterInfo);
            }
            return LazyOptional.empty();
        }
    }
}
