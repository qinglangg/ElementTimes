package com.elementtimes.tutorial.common;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

public class UpdateHelper {

    public static void tankNbtFix(NBTTagCompound compound) {
        if (compound.hasKey("t", Constants.NBT.TAG_COMPOUND)) {
            NBTTagCompound nbt = compound.getCompoundTag("t");
            int level = nbt.getInteger("l");
            NBTTagCompound fluid = nbt.getCompoundTag("f");
            compound.setInteger("l", level);
            compound.setTag("f", fluid);
        }
    }
}
