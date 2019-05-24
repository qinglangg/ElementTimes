//package com.elementtimes.tutorial.annotation;
//
//import com.elementtimes.tutorial.Elementtimes;
//import com.google.common.collect.BiMap;
//import com.google.common.collect.HashBiMap;
//import net.minecraft.block.Block;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.client.renderer.block.model.ModelResourceLocation;
//import net.minecraft.client.renderer.block.statemap.IStateMapper;
//import net.minecraft.client.renderer.block.statemap.StateMap;
//import net.minecraft.creativetab.CreativeTabs;
//import net.minecraft.init.Items;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemBlock;
//import net.minecraft.item.ItemMultiTexture;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.client.model.ModelLoader;
//import net.minecraftforge.client.model.b3d.B3DLoader;
//import net.minecraftforge.client.model.obj.OBJLoader;
//import net.minecraftforge.client.model.obj.OBJModel;
//import net.minecraftforge.event.RegistryEvent;
//import net.minecraftforge.registries.GameData;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class BlockUtil {
//
//    private static BiMap<Class, ItemMultiTexture> sTextureBiMap = HashBiMap.create();
//    public static BiMap<Class, BiMap<Integer, String>> sModelMap = HashBiMap.create();
//    public static BiMap<Class, BiMap<Integer, String>> sNameMap = HashBiMap.create();
//    public static BiMap<Class, String> sCreativeTabsMap = HashBiMap.create();
//    public static Set<Class> sB3D = new HashSet<>();
//    public static Set<Class> sOBJ = new HashSet<>();
//
//    // 获取对应的 Item, 即对应的 ItemMultiTexture 实例
//    public static ItemMultiTexture getMultiBlockItem(Block block) {
//        if (!sTextureBiMap.containsKey(block.getClass())) {
//            // getUnlocalizedName: {item.getUnlocalizedName()}.{nameFunction.apply()}
//            ItemMultiTexture item = new ItemMultiTexture(block /* super() */, block /* this.unused */,
//                    itemStack -> sNameMap.get(Block.getBlockFromItem(itemStack.getItem()).getClass()).get(itemStack.getMetadata()) /* nameFunction */);
//            item.setRegistryName(block.getRegistryName());
////            try {
////                item.setCreativeTab(Elements.getTab());
////            } catch (ClassNotFoundException e) {
////                e.printStackTrace();
////            }
//            sTextureBiMap.put(block.getClass(), item);
//        }
//        return sTextureBiMap.get(block.getClass());
//    }
//
//    // 注册对应 Item 的 Model
//    public static void registerModel(Block block) {
//
//        if (sOBJ.contains(block.getClass())) {
//            OBJLoader.INSTANCE.addDomain(Elementtimes.MODID);
//        }
//
//        if (sB3D.contains(block.getClass())) {
//            B3DLoader.INSTANCE.addDomain(Elementtimes.MODID);
//        }
//
//        if (sModelMapperMap.containsKey(block.getClass())) {
//            IStateMapper mapper = sModelMapperMap.get(block.getClass());
//            ModelLoader.setCustomStateMapper(block, mapper);
//        }
//
//        sModelMap.get(block.getClass()).forEach((meta, name) -> {
//            ModelResourceLocation model = new ModelResourceLocation(Elementtimes.MODID + ":" + name, "inventory");
//            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, model);
//        });
//    }
//
//    // 实例是
//    //  GameRegistry.registerBlock(Block, Class<? extends ItemBlock> null)
//    //  GameRegistry.registerItem(Item)
//    // 但是现在已经没有这个方法了
//    public static void registerBlock(RegistryEvent.Register<Block> blockEvent, Block block) {
//        blockEvent.getRegistry().register(block);
//    }
//
//    public static void registerBlockItem(RegistryEvent.Register<Item> itemEvent, Block block) {
//        ItemMultiTexture item = getMultiBlockItem(block);
//        itemEvent.getRegistry().register(item);
//        GameData.getBlockItemMap().put(block, item);
//    }
//}
