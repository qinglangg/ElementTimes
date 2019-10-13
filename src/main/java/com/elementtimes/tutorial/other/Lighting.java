package com.elementtimes.tutorial.other;

import com.elementtimes.elementcore.api.common.ECUtils;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;

/**
 * 用于监视雷电
 * @author luqin2007
 */
@Mod.EventBusSubscriber(modid = ElementTimes.MODID)
public class Lighting {

    private static final float PROBABILITY_HNO3 = 0.02f;
    private static final float PROBABILITY_O3 = 0.05f + PROBABILITY_HNO3;
    private static final float PROBABILITY_NO2 = 0.05f + PROBABILITY_O3;
    private static final float PROBABILITY_NO = 0.10f + PROBABILITY_NO2;
    private static final int TICK_REMOVE = 200;

    private static final String BIND_FLUIDS = "_fluids_";
    private static final String BIND_FLUIDS_F = "_fluids_f_";
    private static final String BIND_FLUIDS_P = "_fluids_p_";
    private static final String BIND_FLUIDS_T = "_fluids_t_";

    private static final IntSet loadedWorld = new IntOpenHashSet();

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        World world = event.getWorld();
        if (!world.isRemote) {
            loadedWorld.add(world.provider.getDimension());
            try {
                Field weatherList = World.class.getField(MCPNames.WORLD_WEATHER_EFFECTS);
                LightingList list = new LightingList();
                list.addAll(world.weatherEffects);
                ECUtils.reflect.setFinalField(world, weatherList, list);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload event) {
        World world = event.getWorld();
        if (!world.isRemote) {
            loadedWorld.remove(world.provider.getDimension());
        }
    }

    @SubscribeEvent
    public static void onTick(TickEvent.WorldTickEvent event) {
        World world = event.world;
        if (!world.isRemote
                && loadedWorld.contains(world.provider.getDimension())
                && event.phase == TickEvent.Phase.START) {
            LightingFluidStorage data = LightingFluidStorage.get(world);
            Set<BlockPos> remove = new HashSet<>();
            data.fluids.forEach((pos, fluid) -> {
                int tick = data.ticks.getOrDefault(pos, TICK_REMOVE);
                if (tick >= TICK_REMOVE) {
                    if (world.isBlockLoaded(pos)) {
                        if (world.getBlockState(pos).getBlock() == fluid.getBlock()) {
                            world.setBlockToAir(pos);
                        }
                    }
                    remove.add(pos);
                } else {
                    if (world.isBlockLoaded(pos)) {
                        if (world.getBlockState(pos).getBlock() != fluid.getBlock()) {
                            remove.add(pos);
                        }
                    }
                    data.ticks.put(pos, tick + 1);
                }
            });
            for (BlockPos pos : remove) {
                data.fluids.remove(pos);
                data.ticks.remove(pos);
            }
            data.markDirty();
        }
    }

    private static void onAdd(Entity entity) {
//        System.out.println("add: " + entity.posX + ", " + entity.posY + ", " + entity.posZ);
    }

    private static void onRemove(Entity entity) {
        if (entity instanceof EntityLightningBolt) {
            BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ).offset(EnumFacing.UP);
            World world = entity.getEntityWorld();
            Fluid fluid;
            float p = world.rand.nextFloat();
            if (p <= PROBABILITY_HNO3) {
                fluid = ElementtimesFluids.hno3;
            } else if (p <= PROBABILITY_O3) {
                fluid = ElementtimesFluids.o3;
            } else if (p <= PROBABILITY_NO2) {
                fluid = ElementtimesFluids.no2;
            } else if (p <= PROBABILITY_NO) {
                fluid = ElementtimesFluids.no;
            } else {
                fluid = null;
            }
            if (fluid != null) {
                IBlockState destBlockState = world.getBlockState(pos);
                Material destMaterial = destBlockState.getMaterial();
                boolean isDestReplaceable = destBlockState.getBlock().isReplaceable(world, pos);
                if (world.isAirBlock(pos) || destMaterial.isSolid() || !isDestReplaceable) {
                    world.setBlockState(pos, fluid.getBlock().getDefaultState(), 11);
                    LightingFluidStorage.get(world).add(pos, fluid, 0).markDirty();
                }
            }
        }
    }

    private static class LightingList extends ArrayList<Entity> {
        @Override
        public boolean addAll(Collection<? extends Entity> c) {
            c.forEach(Lighting::onAdd);
            return super.addAll(c);
        }

        @Override
        public boolean add(Entity entity) {
            onAdd(entity);
            return super.add(entity);
        }

        @Override
        public void add(int index, Entity element) {
            onAdd(element);
            super.add(index, element);
        }

        @Override
        public boolean addAll(int index, Collection<? extends Entity> c) {
            c.forEach(Lighting::onAdd);
            return super.addAll(index, c);
        }

        @Override
        public boolean remove(Object o) {
            onRemove((Entity) o);
            return super.remove(o);
        }

        @Override
        public Entity remove(int index) {
            onRemove(get(index));
            return super.remove(index);
        }

        @Override
        protected void removeRange(int fromIndex, int toIndex) {
            for (int i = fromIndex; i < toIndex; i++) {
                onRemove(get(i));
            }
            super.removeRange(fromIndex, toIndex);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            c.forEach(o -> onRemove((Entity) o));
            return super.removeAll(c);
        }

        @Override
        public boolean removeIf(Predicate<? super Entity> filter) {
            for (Entity entity : this) {
                if (filter.test(entity)) {
                    onRemove(entity);
                }
            }
            return super.removeIf(filter);
        }
    }

    public static class LightingFluidStorage extends WorldSavedData {

        private static final Map<String, LightingFluidStorage> DATA = new HashMap<>();

        public static LightingFluidStorage get(World world) {
            return DATA.computeIfAbsent("etlf" + world.provider.getDimension(), name -> {
                LightingFluidStorage data = (LightingFluidStorage) world.getPerWorldStorage().getOrLoadData(LightingFluidStorage.class, name);
                if (data == null) {
                    data = new LightingFluidStorage(name);
                    world.getPerWorldStorage().setData(name, data);
                }
                return data;
            });
        }

        public final Map<BlockPos, Fluid> fluids = Collections.synchronizedMap(new HashMap<>());
        public final Map<BlockPos, Integer> ticks = Collections.synchronizedMap(new HashMap<>());

        public LightingFluidStorage(String name) {
            super(name);
        }

        public LightingFluidStorage add(BlockPos pos, Fluid fluid, int tick) {
            fluids.put(pos, fluid);
            ticks.put(pos, tick);
            return this;
        }

        @Override
        public void readFromNBT(NBTTagCompound nbt) {
            if (nbt.hasKey(BIND_FLUIDS)) {
                NBTTagList list = (NBTTagList) nbt.getTag(BIND_FLUIDS);
                for (NBTBase nbtBase : list) {
                    NBTTagCompound fluidCompound = (NBTTagCompound) nbtBase;
                    BlockPos pos = NBTUtil.getPosFromTag(fluidCompound.getCompoundTag(BIND_FLUIDS_P));
                    Fluid fluid = FluidRegistry.getFluid(fluidCompound.getString(BIND_FLUIDS_F));
                    int tick = fluidCompound.getInteger(BIND_FLUIDS_T);
                    if (fluid != null) {
                        add(pos, fluid, tick);
                    }
                }
            }
        }

        @Nonnull
        @Override
        public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
            NBTTagList list = new NBTTagList();
            fluids.forEach((pos, fluid) -> {
                NBTTagCompound fluidCompound = new NBTTagCompound();
                fluidCompound.setTag(BIND_FLUIDS_P, NBTUtil.createPosTag(pos));
                fluidCompound.setString(BIND_FLUIDS_F, fluid.getName());
                fluidCompound.setInteger(BIND_FLUIDS_T, ticks.get(pos));
                list.appendTag(fluidCompound);
            });
            compound.setTag(BIND_FLUIDS, list);
            return compound;
        }
    }
}
