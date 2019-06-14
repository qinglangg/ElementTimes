package com.elementtimes.tutorial.annotation.register;

import com.elementtimes.tutorial.annotation.AnnotationInitializer;
import com.elementtimes.tutorial.annotation.ModBlock;
import com.elementtimes.tutorial.annotation.other.ModInfo;
import com.elementtimes.tutorial.annotation.processor.ModBlockLoader;
import com.elementtimes.tutorial.annotation.processor.ModFluidLoader;
import com.elementtimes.tutorial.annotation.processor.ModItemLoader;
import com.elementtimes.tutorial.annotation.util.RegisterUtil;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.common.item.ItemBottleFuel;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.b3d.B3DLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * 注解注册
 *
 * @author luqin2007
 */
@Mod.EventBusSubscriber
public class ForgeBusRegister {

    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        AnnotationInitializer.BLOCKS.forEach(registry::register);
        ModFluidLoader.FLUID_BLOCK.keySet().forEach(fluid -> {
            registry.register(fluid.getBlock());
        });
    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        AnnotationInitializer.ITEMS.forEach(item -> {
            registry.register(item);
            if (ModItemLoader.ORE_DICTIONARY.containsKey(item)) {
                OreDictionary.registerOre(ModItemLoader.ORE_DICTIONARY.get(item), item);
            }
        });
        AnnotationInitializer.BLOCKS.forEach(block -> {
            ItemBlock itemBlock = new ItemBlock(block) {
                @Override
                public int getItemBurnTime(ItemStack itemStack) {
                    return ModBlockLoader.BURNING_TIMES.getOrDefault(block, 0);
                }
            };
            //noinspection ConstantConditions
            itemBlock.setRegistryName(block.getRegistryName());
            registry.register(itemBlock);
            if (ModBlockLoader.ORE_DICTIONARY.containsKey(block)) {
                OreDictionary.registerOre(ModBlockLoader.ORE_DICTIONARY.get(block), block);
            }
            if (ModBlockLoader.TILE_ENTITIES.containsKey(block)) {
                GameRegistry.registerTileEntity(ModBlockLoader.TILE_ENTITIES.get(block).right, new ResourceLocation(ModInfo.MODID, ModBlockLoader.TILE_ENTITIES.get(block).left));
            }
        });
    }

    @SubscribeEvent
    public static void registerModel(ModelRegistryEvent event) {
        // 三方渲染
        if (ModBlockLoader.OBJ) {
            OBJLoader.INSTANCE.addDomain(ModInfo.MODID);
        }
        if (ModBlockLoader.B3D) {
            B3DLoader.INSTANCE.addDomain(ModInfo.MODID);
        }
        // 注册渲染
        AnnotationInitializer.ITEMS.forEach(item -> {
            if (ModItemLoader.SUB_ITEM_MODEL.containsKey(item)) {
                Int2ObjectMap<ModelResourceLocation> map = ModItemLoader.SUB_ITEM_MODEL.get(item);
                map.forEach((meta, model) -> ModelLoader.setCustomModelResourceLocation(item, meta, model));
            } else {
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
            }
        });
        AnnotationInitializer.BLOCKS.forEach(block -> {
            if (ModBlockLoader.STATE_MAPS.containsKey(block)) {
                // IStateMapper
                IStateMapper mapper = ModBlockLoader.STATE_MAPS.get(block);
                ModelLoader.setCustomStateMapper(block, mapper);
                // ResourceLocation
                ModBlock.StateMap stateMap = ModBlockLoader.BLOCK_STATES.get(block);
                if (ModBlockLoader.BLOCK_STATES.containsKey(block)) {
                    RegisterUtil.applyResourceByStateMap(block, stateMap, mapper);
                } else {
                    mapper.putStateModelLocations(block).forEach((iBlockState, modelResourceLocation) -> ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), block.getMetaFromState(iBlockState), modelResourceLocation));
                }
            } else {
                if (ModBlockLoader.BLOCK_STATES.containsKey(block)) {
                    ModBlock.StateMap stateMap = ModBlockLoader.BLOCK_STATES.get(block);
                    RegisterUtil.applyResourceByStateMap(block, stateMap, null);
                } else {
                    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
                }
            }
        });
        // 注册流体
        ModFluidLoader.FLUID_BLOCK.forEach((fluid, block) -> ModelLoader.setCustomStateMapper(fluid.getBlock(), new StateMapperBase() {
            @Nonnull
            @Override
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                String bs = ModFluidLoader.FLUID_BLOCK_STATE.get(fluid);
                ResourceLocation location;
                if (bs != null && !bs.isEmpty()) {
                    location = new ResourceLocation(ModInfo.MODID, bs);
                } else {
                    location = fluid.getBlock().getRegistryName();
                }
                assert location != null;
                return new ModelResourceLocation(location, fluid.getName());
            }
        }));
    }

    @SubscribeEvent
    public static void registerRecipe(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();
        AnnotationInitializer.RECIPES.forEach(getter -> {
            Arrays.stream(getter.get()).filter(Objects::nonNull).forEach(registry::register);
        });
    }

    @SubscribeEvent
    public static void regFluidSpirit(TextureStitchEvent.Pre event) {
        TextureMap textureMap = event.getMap();
        ModFluidLoader.FLUID_RESOURCES.forEach(fluid -> {
            if (fluid.getStill() != null) {
                textureMap.registerSprite(fluid.getStill());
            }

            if (fluid.getFlowing() != null) {
                textureMap.registerSprite(fluid.getFlowing());
            }

            if (fluid.getOverlay() != null) {
                textureMap.registerSprite(fluid.getOverlay());
            }
        });
    }

    @SubscribeEvent
    public static void registerItemColor(ColorHandlerEvent.Item event) {
        ModItemLoader.ITEM_COLOR.forEach((item, iItemColor) ->
                event.getItemColors().registerItemColorHandler(iItemColor, item));
    }

    @SubscribeEvent
    public static void onBurningTime(FurnaceFuelBurnTimeEvent event) {
        ItemStack itemStack = event.getItemStack();
        String name = null;
        if (itemStack.getItem() == ElementtimesItems.bottle) {
            Optional<String> fluidName = ItemBottleFuel.getFluidNBT(itemStack).getKeySet().stream().findFirst();
            if (fluidName.isPresent()) {
                name = fluidName.get();
            }
        } else if (FluidRegistry.isUniversalBucketEnabled() && itemStack.getItem() == ForgeModContainer.getInstance().universalBucket) {
            Optional<IFluidTankProperties> fluidBucket = Arrays.stream(FluidUtil.getFluidHandler(itemStack).getTankProperties()).findFirst();
            if (fluidBucket.isPresent()) {
                Fluid fluid = fluidBucket.get().getContents().getFluid();
                if (fluid != null) {
                    name = fluid.getName();
                }
            }
        }
        int time = ModFluidLoader.FLUID_BURNING_TIME.getOrDefault(name, -1);
        if (time > 0) {
            event.setBurnTime(time);
        }
    }
}
