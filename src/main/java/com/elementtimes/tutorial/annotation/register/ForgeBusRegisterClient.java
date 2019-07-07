package com.elementtimes.tutorial.annotation.register;

import com.elementtimes.tutorial.annotation.AnnotationInitializer;
import com.elementtimes.tutorial.annotation.annotations.ModBlock;
import com.elementtimes.tutorial.annotation.other.ModInfo;
import com.elementtimes.tutorial.annotation.processor.ModBlockLoader;
import com.elementtimes.tutorial.annotation.processor.ModFluidLoader;
import com.elementtimes.tutorial.annotation.processor.ModItemLoader;
import com.elementtimes.tutorial.annotation.util.RegisterUtil;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.b3d.B3DLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * 注解注册
 *
 * @author luqin2007
 */
@Mod.EventBusSubscriber
@SideOnly(Side.CLIENT)
public class ForgeBusRegisterClient {

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
        // 注册动画
        ModBlockLoader.ANIMATION_HANDLER.forEach(ClientRegistry::bindTileEntitySpecialRenderer);
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
        });
    }

    @SubscribeEvent
    public static void registerItemColor(ColorHandlerEvent.Item event) {
        ModItemLoader.ITEM_COLOR.forEach((item, iItemColor) ->
                event.getItemColors().registerItemColorHandler(iItemColor, item));
    }
}
