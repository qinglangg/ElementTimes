package com.elementtimes.tutorial.common.capability.component;

import com.elementtimes.tutorial.annotation.ModCapability;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

@ModCapability(storageClass = "com.elementtimes.tutorial.common.capability.component.CapabilityComponent$Storage",
        typeInterfaceClass = "com.elementtimes.tutorial.common.capability.component.IComponentContain",
        typeImplementationClass = "com.elementtimes.tutorial.common.capability.component.CapabilityComponent$Implementation")
public class CapabilityComponent {

    @CapabilityInject(IComponentContain.class)
    public static Capability<IComponentContain> positionHistory;

    public static class Storage implements Capability.IStorage<IComponentContain>
    {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IComponentContain> capability, IComponentContain instance, EnumFacing side) {
            return instance.getComponent().getAllComponent();
        }

        @Override
        public void readNBT(Capability<IComponentContain> capability, IComponentContain instance, EnumFacing side, NBTBase nbt) {
            if(nbt instanceof NBTTagCompound) {
                instance.setComponent(new Component((NBTTagCompound) nbt));
            }
        }
    }

    public static class Implementation implements IComponentContain {
        private Component component = new Component();
        @Override
        public Component getComponent() {
            return component;
        }

        @Override
        public void setComponent(Component component) {
            this.component = component;
        }
    }

    public static class ProviderPlayer implements ICapabilitySerializable<NBTTagCompound>
    {
        private IComponentContain histories = new Implementation();
        private Capability.IStorage<IComponentContain> storage = positionHistory.getStorage();
        public ProviderPlayer(){

        }
        public ProviderPlayer(NBTTagCompound nbt){
            histories.setComponent(new Component(nbt));
        }
        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing)
        {
            return positionHistory.equals(capability);
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing)
        {
            if (positionHistory.equals(capability))
            {
                @SuppressWarnings("unchecked")
                T result = (T) histories;
                return result;
            }
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setTag("component", storage.writeNBT(positionHistory, histories, null));
            return compound;
        }

        @Override
        public void deserializeNBT(NBTTagCompound compound)
        {
            NBTTagCompound list = (NBTTagCompound) compound.getTag("component");
            storage.readNBT(positionHistory, histories, null, list);
        }
    }
}