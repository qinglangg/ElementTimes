package com.elementtimes.elementtimes.common.event;

import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.capability.CapabilitySeaWater;
import com.elementtimes.elementtimes.common.feature.SeaWaterFeature;
import com.elementtimes.elementtimes.common.init.Magic;
import com.elementtimes.elementtimes.common.potion.SaltedFish;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 与海水相关的事件

 */
@Mod.EventBusSubscriber(modid = ElementTimes.MODID)
public class SeaEvent {

    private static final UUID UUID_SPEED = UUID.fromString("c343e6bf-7660-4c69-8c67-d231b66de537");

    /**
    [Cap] 为玩家绑定 CapabilitySaltEffect 能力
     */
    @SubscribeEvent
    public static void onEntityCapabilityAttach(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity != null && entity.world != null) {
            if (!entity.world.isRemote && entity instanceof PlayerEntity) {
                CapabilitySeaWater.SaltAmountCapProvider cap = new CapabilitySeaWater.SaltAmountCapProvider();
                event.addCapability(CapabilitySeaWater.NAME, cap);
            }
        }
    }

    /**
    [Cap] 玩家穿越维度时复制 ModuleCap
     */
    @SubscribeEvent
    public static void onEntityCopy(PlayerEvent.Clone event) {
        if (!event.getEntity().world.isRemote) {
            Capability<CapabilitySeaWater.ISeaWater> capability = CapabilitySeaWater.CAPABILITY_SEA_WATER;
            PlayerEntity playerOri = event.getOriginal();
            PlayerEntity playerNew = event.getPlayer();
            LazyOptional<CapabilitySeaWater.ISeaWater> oriCapability = playerOri.getCapability(capability);
            LazyOptional<CapabilitySeaWater.ISeaWater> newCapability = playerNew.getCapability(capability);
            if (oriCapability.isPresent() && newCapability.isPresent()) {
                CapabilitySeaWater.ISeaWater oriSea = oriCapability.orElseThrow(RuntimeException::new);
                CapabilitySeaWater.ISeaWater newSea = newCapability.orElseThrow(RuntimeException::new);
                INBT oldData = capability.writeNBT(oriSea, null);
                capability.readNBT(newSea, null, oldData);
            }
        }
    }

    /**
    [Cap] 保存玩家的 ISeaWater
     */
    @SubscribeEvent
    public static void onPlayerSave(PlayerEvent.SaveToFile event) {
        event.getPlayer().getCapability(CapabilitySeaWater.CAPABILITY_SEA_WATER).ifPresent(seaData -> {
            CompoundNBT data = seaData.write();
            try {
                File file = event.getPlayerFile("et.sea.dat");
                CompressedStreamTools.safeWrite(data, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
    [Cap] 恢复玩家的 ISeaWater
     */
    @SubscribeEvent
    public static void onPlayerLoad(PlayerEvent.LoadFromFile event) {
        event.getPlayer().getCapability(CapabilitySeaWater.CAPABILITY_SEA_WATER).ifPresent(seaData -> {
            try {
                File file = event.getPlayerFile("et.sea.dat");
                CompoundNBT data = CompressedStreamTools.read(file);
                if (data != null) {
                    seaData.read(CompressedStreamTools.read(file));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
    [SaltedFish] 无法跳跃
     */
    @SubscribeEvent
    public static void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.getActivePotionEffect(Magic.saltedFishEffect) != null) {
            Vec3d motion = event.getEntityLiving().getMotion();
            event.getEntityLiving().setMotion(new Vec3d(motion.x, 0, motion.z));
        }
    }

    /**
    [Potion] 移除 Salt 效果时赋予 SaltedFish 效果
    [SaltedFish] 速度减少 90%
     */
    @SubscribeEvent
    public static void onPlayerVelocity(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity instanceof PlayerEntity && !entity.world.isRemote) {
            entity.getCapability(CapabilitySeaWater.CAPABILITY_SEA_WATER, null).ifPresent(cap -> {
                // salt
                if (entity.getActivePotionEffect(Magic.saltEffect) != null) {
                    cap.setLastSaltTick(entity.world.getWorldInfo().getGameTime());
                } else {
                    long currentTime = entity.world.getWorldInfo().getGameTime();
                    long savedTime = cap.getLastSaltTick();
                    if (currentTime == savedTime + 1) {
                        SaltedFish.effectOn((PlayerEntity) entity);
                    }
                }
                // speed
                IAttributeInstance speedAttr = event.getEntityLiving().getAttributes().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED);
                if (speedAttr != null) {
                    speedAttr.removeModifier(UUID_SPEED);
                    if (entity.getActivePotionEffect(Magic.saltedFishEffect) != null) {
                        speedAttr.applyModifier(new AttributeModifier(UUID_SPEED, "speed-", -speedAttr.getValue() * 0.9, AttributeModifier.Operation.ADDITION));
                        event.getEntityLiving().moveVertical *= 0.9;
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onSpawn(RegistryEvent.Register<Feature<?>> event) {
        SeaWaterFeature feature = new SeaWaterFeature();
        feature.setRegistryName(new ResourceLocation(ElementTimes.MODID, "seawater"));
        event.getRegistry().register(feature);
        ConfiguredFeature<?> decoratedFeature = Biome.createDecoratedFeature(feature, NoFeatureConfig.NO_FEATURE_CONFIG, Placement.TOP_SOLID_HEIGHTMAP, IPlacementConfig.NO_PLACEMENT_CONFIG);
        Biome.BIOMES.stream()
                .filter(b -> b.getTempCategory() == Biome.TempCategory.OCEAN)
                .forEach(b -> b.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, decoratedFeature));
    }
}
