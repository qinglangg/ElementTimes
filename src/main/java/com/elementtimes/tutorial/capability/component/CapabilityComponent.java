package com.elementtimes.tutorial.capability.component;

import com.elementtimes.tutorial.capability.CapabilityLoader;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

public class CapabilityComponent {
    public static class Storage implements Capability.IStorage<IComponentContain>
    {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IComponentContain> capability, IComponentContain instance, EnumFacing side) {
            return instance.getComponent().getAllComponent();
        }

        @Override
        public void readNBT(Capability<IComponentContain> capability, IComponentContain instance, EnumFacing side, NBTBase nbt) {
            if(nbt instanceof NBTTagCompound)
                instance.setComponent(new Component((NBTTagCompound) nbt));
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
        private Capability.IStorage<IComponentContain> storage = CapabilityLoader.positionHistory.getStorage();
        public ProviderPlayer(){

        }
        public ProviderPlayer(NBTTagCompound nbt){
            histories.setComponent(new Component(nbt));
        }
        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing)
        {
            return CapabilityLoader.positionHistory.equals(capability);
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing)
        {
            if (CapabilityLoader.positionHistory.equals(capability))
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
            compound.setTag("component", storage.writeNBT(CapabilityLoader.positionHistory, histories, null));
            return compound;
        }

        @Override
        public void deserializeNBT(NBTTagCompound compound)
        {
            NBTTagCompound list = (NBTTagCompound) compound.getTag("component");
            storage.readNBT(CapabilityLoader.positionHistory, histories, null, list);
        }
    }
}
