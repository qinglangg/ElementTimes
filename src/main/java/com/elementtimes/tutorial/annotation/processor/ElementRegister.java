package com.elementtimes.tutorial.annotation.processor;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.annotation.ModBlock;
import com.elementtimes.tutorial.annotation.ModItem;
import com.elementtimes.tutorial.annotation.ModRecipe;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.b3d.B3DLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.elementtimes.tutorial.annotation.util.MessageUtil.warn;

@Mod.EventBusSubscriber
public class ElementRegister {

    private static List<Block> sBlocks = new ArrayList<>();
    private static List<Item> sItems = new ArrayList<>();
    private static List<Supplier<IRecipe>> sRecipes = new ArrayList<>();
    private static boolean sInInit = false;

    // 初始化所有被注解元素，请在 register 事件前调用
    public static void init() {
        if (sInInit) return;
        HashMap<Class, ArrayList<AnnotatedElement>> elements = new HashMap<>();
        ModClassLoader.getClasses(elements, ModBlock.class, ModItem.class, ModRecipe.class);
        ModBlockLoader.getBlocks(elements, sBlocks);
        warn("[Elementtimes] 共计 {} Block", sBlocks.size());
        ModItemLoader.getItems(elements, sItems);
        warn("[Elementtimes] 共计 {} Item", sItems.size());
        ModRecipeLoader.getRecipes(elements, sRecipes);
        warn("[Elementtimes] 共计 {} Recipe", sRecipes.size());
        sInInit = true;
    }

    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        sBlocks.forEach(registry::register);
    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        sItems.forEach(item -> {
            registry.register(item);
            if (ModItemLoader.sItemOreDict.containsKey(item))
                OreDictionary.registerOre(ModItemLoader.sItemOreDict.get(item), item);
        });
        sBlocks.forEach(block -> {
            ItemBlock itemBlock = new ItemBlock(block);
            //noinspection ConstantConditions
            itemBlock.setRegistryName(block.getRegistryName());
            registry.register(itemBlock);
            if (ModBlockLoader.sBlockOreDict.containsKey(block))
                OreDictionary.registerOre(ModBlockLoader.sBlockOreDict.get(block), block);
            if (ModBlockLoader.sTileEntities.containsKey(block)) {
                GameRegistry.registerTileEntity(ModBlockLoader.sTileEntities.get(block).right, new ResourceLocation(Elementtimes.MODID, ModBlockLoader.sTileEntities.get(block).left));
            }
        });
    }

    @SubscribeEvent
    public static void registerModel(ModelRegistryEvent event) {
        // 三方渲染
        if (ModBlockLoader.useOBJ) OBJLoader.INSTANCE.addDomain(Elementtimes.MODID);
        if (ModBlockLoader.useB3D) B3DLoader.INSTANCE.addDomain(Elementtimes.MODID);
        // 注册渲染
        sItems.forEach(item -> {
            if (ModItemLoader.sSubItemModel.containsKey(item)) {
                Int2ObjectMap<ModelResourceLocation> map = ModItemLoader.sSubItemModel.get(item);
                map.forEach((meta, model) -> ModelLoader.setCustomModelResourceLocation(item, meta, model));
            } else {
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
            }
        });
        sBlocks.forEach(block -> {
            if (ModBlockLoader.sStateMaps.containsKey(block)) {
                // IStateMapper
                IStateMapper mapper = ModBlockLoader.sStateMaps.get(block);
                ModelLoader.setCustomStateMapper(block, mapper);
                // ResourceLocation
                ModBlock.StateMap stateMap = ModBlockLoader.sBlockStates.get(block);
                if (ModBlockLoader.sBlockStates.containsKey(block)) {
                    applyResourceByStateMap(block, stateMap, mapper);
                } else {
                    mapper.putStateModelLocations(block).forEach((iBlockState, modelResourceLocation) -> ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), block.getMetaFromState(iBlockState), modelResourceLocation));
                }
            } else {
                if (ModBlockLoader.sBlockStates.containsKey(block)) {
                    ModBlock.StateMap stateMap = ModBlockLoader.sBlockStates.get(block);
                    applyResourceByStateMap(block, stateMap, null);
                } else
                    //noinspection ConstantConditions
                    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
            }
        });
    }

    @SubscribeEvent
    public static void registerRecipe(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();
        sRecipes.forEach(getter -> registry.register(getter.get()));
    }

    private static void applyResourceByStateMap(Block block, ModBlock.StateMap map, IStateMapper mapper) {
        Item item = Item.getItemFromBlock(block);
        Map<IBlockState, ModelResourceLocation> locationMap = null;
        //noinspection ConstantConditions
        ModelResourceLocation defLocation = new ModelResourceLocation(block.getRegistryName(), "inventory");
        if (mapper != null) locationMap = mapper.putStateModelLocations(block);
        if (map.metadatas().length == 0) {
            // metadata from DefaultState
            int defMeta = block.getMetaFromState(block.getDefaultState());
            if (defMeta != 0b0000) {
                ModelLoader.setCustomModelResourceLocation(item, defMeta, getLocationFromState(locationMap, defLocation, block.getDefaultState()));
            }
            // metadata from 0b0000
            //noinspection deprecation
            IBlockState stateZero = block.getStateFromMeta(0b0000);
            ModelLoader.setCustomModelResourceLocation(item, defMeta, getLocationFromState(locationMap, defLocation, stateZero));
            return;
        }
        for (int i = 0; i < map.metadatas().length; i++) {
            int meta = map.metadatas()[i];
            if (meta > 0b1111 || meta < 0b0000) continue;
            String location = map.models()[i];
            String value = map.properties()[i];
            if (location == null || location.isEmpty() || value == null || value.isEmpty()) {
                //noinspection deprecation
                ModelLoader.setCustomModelResourceLocation(item, meta, getLocationFromState(locationMap, defLocation, block.getStateFromMeta(meta)));
            } else {
                ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(location, value));
            }
        }
    }

    private static ModelResourceLocation getLocationFromState(Map<IBlockState, ModelResourceLocation> locationMap, ModelResourceLocation defLocation, IBlockState state) {
        if (locationMap == null || !locationMap.containsKey(state)) return defLocation;
        return locationMap.get(state);
    }
}
