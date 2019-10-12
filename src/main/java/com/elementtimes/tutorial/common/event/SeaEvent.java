package com.elementtimes.tutorial.common.event;

import com.elementtimes.elementcore.api.common.ECUtils;
import com.elementtimes.tutorial.common.capability.CapabilitySeaWater;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.common.init.ElementtimesMagic;
import com.elementtimes.tutorial.common.item.Salt;
import com.elementtimes.tutorial.common.potion.SaltedFish;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.UUID;

/**
 * 与海水相关的事件
 * @author luqin2007
 */
@Mod.EventBusSubscriber
public class SeaEvent {

    private static UUID UUID_SPEED = UUID.fromString("c343e6bf-7660-4c69-8c67-d231b66de537");

    /*
    [Cap] 为玩家绑定 CapabilitySaltEffect 能力
     */
    @SubscribeEvent
    public static void onEntityCapabilityAttach(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (!entity.world.isRemote && entity instanceof EntityPlayer) {
            CapabilitySeaWater.SaltAmountCapProvider cap = new CapabilitySeaWater.SaltAmountCapProvider();
            event.addCapability(CapabilitySeaWater.NAME, cap);
        }
    }

    /*
    [Cap] 玩家穿越维度时复制 Capability
     */
    @SubscribeEvent
    public static void onEntityCopy(PlayerEvent.Clone event) {
        Capability<CapabilitySeaWater.ISeaWater> capability = CapabilitySeaWater.CAPABILITY_SEA_WATER;
        EntityPlayer playerOri = event.getOriginal();
        EntityPlayer playerNew = event.getEntityPlayer();
        if (playerOri.hasCapability(capability, null) && playerNew.hasCapability(capability, null)) {
            NBTBase nbt = capability.writeNBT(playerOri.getCapability(capability, null), null);
            capability.readNBT(playerNew.getCapability(capability, null), null, nbt);
        }
    }

    /*
    [Salt] 若持有盐，处理盐的物品栈，使其可食用
     */
    @SubscribeEvent
    public static void onItemUse(PlayerInteractEvent.RightClickItem event) {
        if (!event.getWorld().isRemote) {
            EntityPlayer player = event.getEntityPlayer();
            PotionEffect saltEffect = player.getActivePotionEffect(ElementtimesMagic.salt);
            if (saltEffect != null) {
                ItemStack heldItem = player.getHeldItem(event.getHand());
                int[] oreIDs = OreDictionary.getOreIDs(player.getHeldItem(player.getActiveHand()));
                if (ArrayUtils.contains(oreIDs, OreDictionary.getOreID("salt"))) {
                    if (heldItem.getItem() == ElementtimesItems.salt) {
                        Salt.bindTag(heldItem, null, saltEffect.getAmplifier());
                    } else {
                        ItemStack salt = new ItemStack(ElementtimesItems.salt, heldItem.getCount());
                        Salt.bindTag(salt, heldItem, saltEffect.getAmplifier());
                    }
                }
            }
        }
    }

    /*
    [Cap] 回复玩家的 ISeaWater
     */
    @SubscribeEvent
    public static void onEntityJoin(EntityJoinWorldEvent event) {
        World world = event.getWorld();
        Entity entity = event.getEntity();
        if (entity instanceof EntityPlayer && !world.isRemote) {
            try {
                File saveDir = ECUtils.storage.saveDir(world, "elementtimes");
                String[] list = saveDir.isDirectory() ? saveDir.list() : new String[0];
                EntityPlayer player = (EntityPlayer) entity;
                String uuid = EntityPlayer.getUUID(player.getGameProfile()).toString();
                if (ArrayUtils.contains(list, uuid)) {
                    CapabilitySeaWater.ISeaWater cap = player.getCapability(CapabilitySeaWater.CAPABILITY_SEA_WATER, null);
                    if (cap != null) {
                        File file = new File(saveDir, uuid);
                        try(BufferedInputStream is = new BufferedInputStream(new FileInputStream(file))) {
                            NBTTagCompound compound = CompressedStreamTools.readCompressed(is);
                            cap.read(compound);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    [Cap] 保存玩家的 ISeaWater
     */
    @SubscribeEvent
    public static void onWorldSaveEvent(WorldEvent.Save event) {
        World world = event.getWorld();
        if (!world.isRemote) {
            try {
                File saveDir = ECUtils.storage.saveDir(world, "elementtimes");
                //noinspection ResultOfMethodCallIgnored
                saveDir.mkdirs();
                for (EntityPlayer player : world.playerEntities) {
                    CapabilitySeaWater.ISeaWater cap = player.getCapability(CapabilitySeaWater.CAPABILITY_SEA_WATER, null);
                    if (cap != null && cap.isDirty()) {
                        File file = new File(saveDir, EntityPlayer.getUUID(player.getGameProfile()).toString());
                        try(BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file))) {
                            NBTTagCompound compound = cap.write();
                            CompressedStreamTools.writeCompressed(compound, os);
                            os.flush();
                            cap.clearDirty();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    [Salt] 恢复盐的物品栈
     */
    @SubscribeEvent
    public static void onItemUseFinished(LivingEntityUseItemEvent event) {
        if (event instanceof LivingEntityUseItemEvent.Stop || event instanceof LivingEntityUseItemEvent.Finish) {
            EntityLivingBase entityLiving = event.getEntityLiving();
            if (entityLiving instanceof EntityPlayer && !entityLiving.world.isRemote) {
                EntityPlayer player = (EntityPlayer) entityLiving;
                ItemStack heldItem = player.getHeldItem(player.getActiveHand());
                player.setHeldItem(player.getActiveHand(), Salt.removeTag(heldItem));
            }
        }
    }

    /*
    [SaltedFish] 无法跳跃
     */
    @SubscribeEvent
    public static void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        PotionEffect effect = entity.getActivePotionEffect(ElementtimesMagic.saltedFish);
        if (effect != null) {
            event.getEntityLiving().motionY = 0;
        }
    }

    /*
    [Potion] 移除 Salt 效果时赋予 SaltedFish 效果
    [SaltedFish] 速度减少 90%
     */
    @SubscribeEvent
    public static void onPlayerVelocity(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityPlayer && !entity.world.isRemote) {
            CapabilitySeaWater.ISeaWater cap = entity.getCapability(CapabilitySeaWater.CAPABILITY_SEA_WATER, null);
            assert cap != null;
            // salt
            PotionEffect effectSalt = entity.getActivePotionEffect(ElementtimesMagic.salt);
            if (effectSalt != null) {
                cap.setLastSaltTick(entity.world.getWorldTime());
            } else {
                long currentTime = entity.world.getWorldTime();
                long savedTime = cap.getLastSaltTick();
                if (currentTime == savedTime + 1) {
                    SaltedFish.effectOn((EntityPlayer) entity);
                }
            }
            // speed
            PotionEffect effectSaltedFish = entity.getActivePotionEffect(ElementtimesMagic.saltedFish);
            IAttributeInstance speedAttr = event.getEntityLiving().getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED);
            speedAttr.removeModifier(UUID_SPEED);
            if (effectSaltedFish != null) {
                speedAttr.applyModifier(new AttributeModifier(UUID_SPEED, "speed-", -speedAttr.getAttributeValue() * 0.9, 0));
                event.getEntityLiving().moveVertical *= 0.9;
            }
        }
    }
}