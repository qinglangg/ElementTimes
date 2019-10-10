package com.elementtimes.tutorial.common.block.fluid;

import com.elementtimes.tutorial.common.capability.CapabilitySeaWater;
import com.elementtimes.tutorial.common.init.ElementtimesMagic;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

/**
 * 海水
 * 主要增加浸入方块的玩家的药水效果
 * @author luqin2007
 */
public class SeaWaterBlock extends BlockFluidClassic {

    private static final int LEVEL_IV = 220;
    private static final int LEVEL_III = 150;
    private static final int LEVEL_II = 80;
    private static final int LEVEL_IV_TICK = 600;
    private static final int LEVEL_III_TICK = 300;
    private static final int LEVEL_II_TICK = 200;
    private static final int LEVEL_I_TICK = 100;

    public SeaWaterBlock(Fluid fluid) {
        super(fluid, Material.WATER);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (!worldIn.isRemote && entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityIn;
            PotionEffect effect = player.getActivePotionEffect(ElementtimesMagic.salt);
            CapabilitySeaWater.ISeaWater effectCap = player.getCapability(CapabilitySeaWater.CAPABILITY_SEA_WATER, null);
            assert effectCap != null;
            if (effect == null) {
                player.addPotionEffect(new PotionEffect(ElementtimesMagic.salt, LEVEL_I_TICK, 1));
                effectCap.resetCollidedTick();
            } else {
                int collidedTick = effectCap.getCollidedTick();
                int amplifier = effect.getAmplifier();
                if (amplifier == 1 && collidedTick >= LEVEL_II) {
                    player.addPotionEffect(new PotionEffect(ElementtimesMagic.salt, LEVEL_II_TICK, 2));
                    effectCap.resetCollidedTick();
                } else if (amplifier == 2 && collidedTick >= LEVEL_III) {
                    player.addPotionEffect(new PotionEffect(ElementtimesMagic.salt, LEVEL_III_TICK, 3));
                    effectCap.resetCollidedTick();
                } else if (amplifier == 3 && collidedTick >= LEVEL_IV) {
                    player.addPotionEffect(new PotionEffect(ElementtimesMagic.salt, LEVEL_IV_TICK, 4));
                    effectCap.resetCollidedTick();
                }
            }
            effectCap.collidedInSea();
        }
    }
}
