package com.elementtimes.tutorial.common.tileentity.stand.module;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.plugin.elementcore.SSMRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SSMRegister.SupportStandModule(ModuleBeaker.KEY)
public class ModuleBeaker implements ISupportStandModule {

    public static final String KEY = "Beaker";

    @Nonnull
    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onRender() {
        net.minecraft.client.renderer.GlStateManager.translate(.5, 1.05, .5);
        net.minecraft.client.renderer.GlStateManager.scale(.75, .75, .75);
        net.minecraft.client.Minecraft.getMinecraft().getRenderItem()
                .renderItem(getModuleItem(), net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.GROUND);
    }

    @Override
    public ItemStack getModuleItem() {
        return new ItemStack(ElementtimesBlocks.beaker);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) { }
}
