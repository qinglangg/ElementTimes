package com.elementtimes.tutorial.common.capability.test;

import com.elementtimes.tutorial.annotation.ModCapability;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@ModCapability(storageClass= "com.elementtimes.tutorial.common.capability.test.CapabilityTest$Storage",
               typeInterfaceClass = "com.elementtimes.tutorial.common.capability.test.IMessage",
               typeImplementationClass  = "com.elementtimes.tutorial.common.capability.test.IMessage$Impl")
public class CapabilityTest {

    private static Capability<IMessage> sMessageCapability;

    @CapabilityInject(IMessage.class)
    public static void setMessageCapability(Capability<IMessage> capability) {
        sMessageCapability = capability;
    }

    public static Capability<IMessage> getMessageCapability() {
        return sMessageCapability;
    }

    public static class Storage implements Capability.IStorage<IMessage> {

        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IMessage> capability, IMessage instance, EnumFacing side) {
            return new NBTTagString(instance.getMessage());
        }

        @Override
        public void readNBT(Capability<IMessage> capability, IMessage instance, EnumFacing side, NBTBase nbt) {
            instance.setMessage(((NBTTagString) nbt).getString());
        }
    }

    public static class Provider implements ICapabilityProvider {

        private IMessage mMessage = new IMessage.Impl();
        private Capability.IStorage<IMessage> mStorage = getMessageCapability().getStorage();

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return getMessageCapability().equals(capability);
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            return (T) mMessage;
        }
    }
}
