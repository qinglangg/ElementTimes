package com.elementtimes.elementtimes.common.event;

import com.elementtimes.elementcore.api.utils.CommonUtils;
import com.elementtimes.elementtimes.common.capability.CapabilityLighting;
import com.elementtimes.elementtimes.common.capability.CapabilityLighting.LightWrapper;
import com.elementtimes.elementtimes.common.fluid.Buckets;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber
public class LightingEvent {

    private static final float PROBABILITY_HNO3 = 0.02f;
    private static final float PROBABILITY_O3 = 0.05f + PROBABILITY_HNO3;
    private static final float PROBABILITY_NO2 = 0.05f + PROBABILITY_O3;
    private static final float PROBABILITY_NO = 0.10f + PROBABILITY_NO2;

    /**
     * todo 准备使用 coremod 实现
     * @param entity 实体
     */
    public static void onLighting(LightningBoltEntity entity) {
        BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ).offset(Direction.UP);
        World world = entity.getEntityWorld();
        BucketItem fluid;
        float p = world.rand.nextFloat();
        if (p <= PROBABILITY_HNO3) {
            fluid = Buckets.hno3;
        } else if (p <= PROBABILITY_O3) {
            fluid = Buckets.o3;
        } else if (p <= PROBABILITY_NO2) {
            fluid = Buckets.no2;
        } else if (p <= PROBABILITY_NO) {
            fluid = Buckets.no;
        } else {
            fluid = null;
        }
        world.getCapability(CapabilityLighting.CAPABILITY_LIGHT).ifPresent(cap -> {
            if (fluid != null && fluid.tryPlaceContainedLiquid(null, world, pos, null)) {
                LightWrapper wrapper = new LightWrapper();
                wrapper.pos = pos;
                wrapper.fluid = fluid.getFluid();
                cap.add(wrapper);
            }
        });
    }

    @SubscribeEvent
    public static void onTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            for (ServerWorld world : CommonUtils.getServer().getWorlds()) {
                world.getCapability(CapabilityLighting.CAPABILITY_LIGHT).ifPresent(CapabilityLighting.ILightPos::tick);
            }
        }
    }

    @SubscribeEvent
    public static void onAttach(AttachCapabilitiesEvent<ServerWorld> event) {
        event.addCapability(CapabilityLighting.NAME, new CapabilityLighting.CapProvider(event.getObject()));
    }
}
