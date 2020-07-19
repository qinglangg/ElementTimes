package com.elementtimes.elementtimes.common.enchantment;

import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 斩首
 * 25% 50% 75% 100%
 * @author luqin
 */
public class Beheading extends Enchantment {

    public static Beheading ENCHANTMENT = new Beheading();

    protected Beheading() {
        super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
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
            ItemStack head;
            if (killed instanceof SkeletonEntity) {
                head = new ItemStack(Blocks.SKELETON_SKULL);
            } else if (killed instanceof WitherSkeletonEntity) {
                head = new ItemStack(Blocks.WITHER_SKELETON_SKULL);
            } else if (killed instanceof ZombieEntity) {
                head = new ItemStack(Blocks.ZOMBIE_HEAD);
            } else if (killed instanceof PlayerEntity) {
                head = new ItemStack(Blocks.PLAYER_HEAD);
                CompoundNBT profile = NBTUtil.writeGameProfile(new CompoundNBT(), ((PlayerEntity) killed).getGameProfile());
                head.getOrCreateTag().put("SkullOwner", profile);
            } else if (killed instanceof CreeperEntity) {
                head = new ItemStack(Blocks.CREEPER_HEAD);
            } else {
                head = ItemStack.EMPTY;
            }
            if (!head.isEmpty() && entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;
                int mainLevel = EnchantmentHelper.getEnchantmentLevel(ENCHANTMENT, living.getHeldItem(Hand.MAIN_HAND));
                int offLevel = EnchantmentHelper.getEnchantmentLevel(ENCHANTMENT, living.getHeldItem(Hand.OFF_HAND));
                int level = Math.max(mainLevel, offLevel);
                if (living.world.rand.nextFloat() <= 0.25 * level) {
                    living.entityDropItem(head);
                }
            }
        }
    }
}
