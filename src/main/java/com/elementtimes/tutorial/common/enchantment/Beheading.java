package com.elementtimes.tutorial.common.enchantment;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 斩首
 * 25% 50% 75% 100%
 * @author luqin
 */
public class Beheading extends Enchantment {

    public static Beheading ENCHANTMENT = new Beheading();

    protected Beheading() {
        super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Mod.EventBusSubscriber
    public static class EventHandler {

        @SubscribeEvent
        public static void onKill(LivingDeathEvent event) {
            Entity entity = event.getSource().getTrueSource();
            Entity killed = event.getEntity();
            int headMeta;
            if (killed instanceof EntitySkeleton) {
                headMeta = 0;
            } else if (killed instanceof EntityWitherSkeleton) {
                headMeta = 1;
            } else if (killed instanceof EntityZombie) {
                headMeta = 2;
            } else if (killed instanceof EntityPlayer) {
                headMeta = 3;
            } else if (killed instanceof EntityCreeper) {
                headMeta = 4;
            } else {
                headMeta = -1;
            }
            if (headMeta >= 0 && entity instanceof EntityLivingBase) {
                EntityLivingBase living = (EntityLivingBase) entity;
                int mainLevel = EnchantmentHelper.getEnchantmentLevel(ENCHANTMENT, living.getHeldItem(EnumHand.MAIN_HAND));
                int offLevel = EnchantmentHelper.getEnchantmentLevel(ENCHANTMENT, living.getHeldItem(EnumHand.OFF_HAND));
                int level = Math.max(mainLevel, offLevel);
                if (living.world.rand.nextFloat() <= 0.25 * level) {
                    ItemStack headItem = new ItemStack(Blocks.SKULL, 1, headMeta);
                    if (headMeta == 3) {
                        NBTTagCompound compound = new NBTTagCompound();
                        compound.setString("SkullOwner", ((EntityPlayer) killed).getDisplayNameString());
                        headItem.setTagCompound(compound);
                        Items.SKULL.updateItemStackNBT(compound);
                    }
                    Block.spawnAsEntity(living.world, living.getPosition(), headItem);
                }
            }
        }
    }
}
