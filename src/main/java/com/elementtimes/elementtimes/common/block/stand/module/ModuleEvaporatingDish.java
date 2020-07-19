package com.elementtimes.elementtimes.common.block.stand.module;

import com.elementtimes.elementtimes.common.init.blocks.Chemical;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;



public class ModuleEvaporatingDish implements ISupportStandModule {

    public static final String KEY = "EvaporatingDish";

    @Nonnull
    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public ItemStack getModuleItem() {
        return new ItemStack(Chemical.evaporatingDish);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onRender() {
        com.mojang.blaze3d.platform.GlStateManager.translated(.5, .51, .5);
        com.mojang.blaze3d.platform.GlStateManager.scalef(3, 3, 3);
        net.minecraft.client.Minecraft.getInstance().getItemRenderer()
                .renderItem(getModuleItem(), net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType.GROUND);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }
}
