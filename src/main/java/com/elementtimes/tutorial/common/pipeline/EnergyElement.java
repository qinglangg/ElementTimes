package com.elementtimes.tutorial.common.pipeline;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnergyElement extends BaseElement {

    public static ElementType TYPE = new ElementType() {

        @Override
        public String type() {
            return "energy_ec";
        }

        @Override
        public EnergyElement newInstance() {
            return new EnergyElement();
        }
    };

    public EnergyElement() {
        super(TYPE.type());
    }

    @Override
    public void drop(World world, BlockPos pos) { }

    @Override
    public boolean isEmpty() {
        return (int) element == 0;
    }

    @Override
    public BaseElement copy() {
        EnergyElement e = (EnergyElement) TYPE.newInstance();
        e.path = path;
        e.type = type;
        e.element = element;
        e.posIndex = posIndex;
        e.totalTick = totalTick;
        return e;
    }

    @Override
    protected NBTTagCompound elementSerializer() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("energy", (Integer) element);
        return compound;
    }

    @Override
    protected void elementDeserializer(NBTTagCompound compound) {
        element = compound.getInteger("energy");
    }
}
