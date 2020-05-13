package com.elementtimes.tutorial.common.tileentity.stand.module;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.plugin.elementcore.SSMRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SSMRegister.SupportStandModule(ModuleCrucible.KEY)
public class ModuleCrucible implements ISupportStandModule {

    public static final String KEY = "Crucible";

    @Nonnull
    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getModuleItem() {
        return new ItemStack(ElementtimesBlocks.crucible);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onRender() {
        net.minecraft.client.renderer.GlStateManager.translate(.5, .55, .5);
        net.minecraft.client.renderer.GlStateManager.scale(3, 2, 3);
        net.minecraft.client.Minecraft.getMinecraft().getRenderItem()
                .renderItem(getModuleItem(), net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.GROUND);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

    }
}
