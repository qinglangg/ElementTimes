package com.elementtimes.tutorial.capability.component;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;

import java.util.Set;


public class Component {
    private NBTTagCompound compound;
    private float temperature;
    private float weight;
    public Component(){
        this.compound = new NBTTagCompound();
    }
    public Component(NBTTagCompound compound){
        this.compound = compound;
    }
    public float getWeight() {
        if (weight > 0) return weight;
        return weight = getWeight(compound);
    }

    private static float getWeight(NBTTagCompound nbt) {
        float sum = 0;
        for (String i : nbt.getKeySet()) {
            NBTBase base = nbt.getTag(i);
            if (base instanceof NBTTagCompound) {
                sum += getWeight((NBTTagCompound) base);
            } else if (base instanceof NBTTagFloat) {
                sum += ((NBTTagFloat) base).getFloat();
            }
        }
        return sum;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public NBTTagCompound getAllComponent() {
        return compound.copy();
    }
    public Set<String> getComponents() {
        return compound.getKeySet();
    }

    public void changeComponent(String name,NBTTagCompound nbt){
        weight = 0;
        compound.setTag(name,nbt);
    }

    public void setComponent(NBTTagCompound compound){
        this.compound = compound;
    }
}
