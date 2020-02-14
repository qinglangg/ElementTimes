package com.elementtimes.tutorial.common.capability;

import com.elementtimes.elementcore.api.annotation.ModCapability;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method;
import com.elementtimes.tutorial.ElementTimes;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 记录实体使用盐数量
 * @author luqin2007
 */
@ModCapability(type = CapabilitySeaWater.ISeaWater.class,
        typeFactory = @Method(CapabilitySeaWater.SeaWater.class),
        storage = @Getter(CapabilitySeaWater.SeaStorage.class))
public class CapabilitySeaWater {

    public static final ResourceLocation NAME = new ResourceLocation(ElementTimes.MODID, "cap_salt");

    @CapabilityInject(ISeaWater.class)
    public static Capability<ISeaWater> CAPABILITY_SEA_WATER;

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
        NBTTagCompound write();

        void read(@Nonnull NBTTagCompound compound);
    }

    public static class SeaStorage implements Capability.IStorage<ISeaWater> {

        @Nullable
        @Override
        public NBTBase writeNBT(Capability<ISeaWater> capability, ISeaWater instance, EnumFacing side) {
            return instance.write();
        }

        @Override
        public void readNBT(Capability<ISeaWater> capability, ISeaWater instance, EnumFacing side, NBTBase nbt) {
            instance.read((NBTTagCompound) nbt);
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
        public void read(@Nonnull NBTTagCompound compound) {
            mCollided = compound.getInteger("collided");
            mSalt = compound.getInteger("salt");
            mSaltTick = compound.getLong("tick");
        }

        @Nonnull
        @Override
        public NBTTagCompound write() {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("collided", mCollided);
            compound.setInteger("salt", mSalt);
            compound.setLong("tick", mSaltTick);
            return compound;
        }
    }

    public static class SaltAmountCapProvider implements ICapabilityProvider {

        private final ISeaWater mSeaWaterInfo;

        public SaltAmountCapProvider() {
            mSeaWaterInfo = new SeaWater();
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CAPABILITY_SEA_WATER;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            return capability == CAPABILITY_SEA_WATER ? (T) mSeaWaterInfo : null;
        }
    }
}
