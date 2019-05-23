package com.elementtimes.tutorial.annotation;

import com.elementtimes.tutorial.Elementtimes;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.command.CommandBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.potion.Potion;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class Elements {

    private static BiMap<Class, ArrayList<Class>> sElementClasses = HashBiMap.create();
    private static BiMap<Class, ArrayList<Field>> sElementFields = HashBiMap.create();
    private static BiMap<Class, Object> sObjects = HashBiMap.create();

    private static ArrayList<Item> sItems = null;
    private static ArrayList<Block> sBlocks = null;
    private static ArrayList<Block> sMultiBlocks = null;
    private static ArrayList<BlockFluidClassic> sFluidBlocks = null;
    private static ArrayList<Enchantment> sEnchantments = null;
    private static ArrayList<Potion> sPotions = null;
    private static ArrayList<CommandBase> sCommands = null;
    private static ArrayList<ItemBucket> sBuckets = null;
    private static ArrayList<ModCapability> sCapabilities = null;
    private static BiMap<Integer, BiMap<Side, SimpleNetworkWrapper>> sNetworks = null;
    private static BiMap<Class, ModTileEntity> sTitleEntities = null;
    private static BiMap<Class, ModEntity> sEntities = null;

    /**
     * 添加 Mod 部分
     */
    public static void addElement(Class annotation, Class aClass) {
        if (!sElementClasses.containsKey(annotation))
            sElementClasses.put(annotation, new ArrayList<>());
        sElementClasses.get(annotation).add(aClass);
    }

    /**
     * 添加 Mod 部分(Field)
     */
    public static void addElement(Class annotation, Field field) {
        if (!sElementFields.containsKey(annotation))
            sElementFields.put(annotation, new ArrayList<>());
        sElementFields.get(annotation).add(field);
    }

    /**
     * 获取所有多 BlockState 方块
     */
    public static ArrayList<Block> getMultiBlocks() {
        if (sMultiBlocks == null) {
            sMultiBlocks = new ArrayList<>();
            sElementClasses.get(ModMultiBlock.class)
                    .forEach(aClass -> {
                        try {
                            ModMultiBlock info = (ModMultiBlock) aClass.getAnnotation(ModMultiBlock.class);
                            Block block = (Block) get(aClass);
                            block.setRegistryName(info.registerName());
                            block.setUnlocalizedName(info.unlocalizedName());
                            // creativeTab
                            block.setCreativeTab((CreativeTabs) get(Class.forName(info.creativeTab())));
                            BlockUtil.sCreativeTabsMap.put(aClass, info.creativeTab());
                            // model
                            BiMap<Integer, String> modelMap = HashBiMap.create();
                            ModStateMapper mapperInfo = (ModStateMapper) aClass.getAnnotation(ModStateMapper.class);
                            if (mapperInfo != null) {
                                Class propertyContainer = Class.forName(mapperInfo.propertyIn());
                                // suffix
                                StateMap.Builder builder = new StateMap.Builder().withSuffix(mapperInfo.suffix());
                                // withName
                                IProperty property = getField(propertyContainer, mapperInfo.propertyName(), block);
                                builder.withName(property);
                                // ignore
                                IProperty[] ignoreProperties = Arrays.stream(mapperInfo.propertyIgnore())
                                        .map(ignoreProperty -> getField(propertyContainer, ignoreProperty, block)).toArray(IProperty[]::new);
                                builder.ignore(ignoreProperties);
                                BlockUtil.sModelMapperMap.put(aClass, builder.build());

                                for (int i = 0; i < info.metadataToGetModel().length; i++) {
                                    IBlockState state = block.getStateFromMeta(info.metadataToGetModel()[i]);
                                    String name = property.getName(state.getValue(property));
                                    modelMap.put(info.metadataToGetModel()[i], name + mapperInfo.suffix());
                                }
                                BlockUtil.sModelMap.put(aClass, modelMap);
                            } else {
                                for (int i = 0; i < info.metadataToGetModel().length; i++)
                                    modelMap.put(info.metadataToGetModel()[i], info.modelByMetadata()[i]);
                                BlockUtil.sModelMap.put(aClass, modelMap);
                            }
                            if (info.useB3D()) BlockUtil.sB3D.add(aClass);
                            if (info.useOBJ()) BlockUtil.sOBJ.add(aClass);
                            // name
                            BiMap<Integer, String> nameMap = HashBiMap.create();
                            for (int i = 0; i < info.metadataToGetName().length; i++)
                                nameMap.put(info.metadataToGetName()[i], info.nameByMetadata()[i]);
                            BlockUtil.sNameMap.put(aClass, nameMap);
                            sMultiBlocks.add(block);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        }
        return sMultiBlocks;
    }

    /**
     * 获取所有普通方块
     */
    public static ArrayList<Block> getBlocks() {
        if (sBlocks == null) {
            sBlocks = new ArrayList<>();
            sElementClasses.get(ModBlock.class)
                    .forEach(aClass -> {
                        try {
                            ModBlock info = (ModBlock) aClass.getAnnotation(ModBlock.class);
                            Block block = (Block) get(aClass);
                            block.setRegistryName(info.registerName());
                            block.setUnlocalizedName(info.unlocalizedName());
                            block.setCreativeTab((CreativeTabs) get(Class.forName(info.creativeTabClass())));
                            sBlocks.add(block);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        }
        return sBlocks;
    }

    /**
     * 获取所有液体方块
     */
    public static ArrayList<BlockFluidClassic> getFluidBlocks() {
        if (sFluidBlocks == null) {
            sFluidBlocks = new ArrayList<>();
            sElementClasses.get(ModFluid.class)
                    .forEach(aClass -> {
                        try {
                            ModFluid info = (ModFluid) aClass.getAnnotation(ModFluid.class);
                            Fluid fluid = (Fluid) get(aClass);
                            fluid.setUnlocalizedName(info.unlocalizedName());
                            FluidUtil.register(fluid);
                            BlockFluidClassic block = (BlockFluidClassic) get(Class.forName(info.blockClass()));
                            block.setRegistryName(info.registerName());
                            block.setUnlocalizedName(info.unlocalizedName());
                            block.setFluidStack(new FluidStack(fluid, Fluid.BUCKET_VOLUME));
                            block.setCreativeTab((CreativeTabs) get(Class.forName(info.creativeTabClass())));
                            sFluidBlocks.add(block);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        }
        return sFluidBlocks;
    }

    /**
     * 获取所有物品
     */
    public static ArrayList<Item> getItems() {
        if (sItems == null) {
            sItems = new ArrayList<>();
            sElementClasses.get(ModItem.class)
                    .forEach(aClass -> {
                        try {
                            ModItem info = (ModItem) aClass.getAnnotation(ModItem.class);
                            Item item = (Item) get(aClass);
                            item.setRegistryName(info.registerName());
                            item.setUnlocalizedName(info.unlocalizedName());
                            item.setCreativeTab((CreativeTabs) get(Class.forName(info.creativeTabClass())));
                            sItems.add(item);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        }
        return sItems;
    }

    /**
     * 获取所有命令
     */
    public static ArrayList<CommandBase> getCommands() {
        if (sCommands == null) {
            sCommands = new ArrayList<>();
            sElementClasses.get(ModCommand.class)
                    .forEach(aClass -> {
                        try {
                            sCommands.add((CommandBase) get(aClass));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        }
        return sCommands;
    }

    /**
     * 获取所有附魔
     */
    public static ArrayList<Enchantment> getEnchantments() {
        if (sEnchantments == null) {
            sEnchantments = new ArrayList<>();
            sElementClasses.get(ModEnchantment.class)
                    .forEach(aClass -> {
                        try {
                            ModEnchantment info = (ModEnchantment) aClass.getAnnotation(ModEnchantment.class);
                            Enchantment enchantment = (Enchantment) get(aClass);
                            enchantment.setName(info.name());
                            enchantment.setRegistryName(info.registerName());
                            sEnchantments.add(enchantment);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        }
        return sEnchantments;
    }

    /**
     * 获取所有药水效果
     */
    public static ArrayList<Potion> getPotions() {
        if (sPotions == null) {
            sPotions = new ArrayList<>();
            sElementClasses.get(ModPotion.class)
                    .forEach(aClass -> {
                        try {
                            ModPotion info = (ModPotion) aClass.getAnnotation(ModPotion.class);
                            Potion potion = (Potion) get(aClass);
                            potion.setPotionName(info.name());
                            potion.setRegistryName(info.registerName());
                            sPotions.add(potion);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        }
        return sPotions;
    }

    /**
     * 获取所有桶
     */
    public static ArrayList<ItemBucket> getBuckets() {
        if (sBuckets == null) {
            sBuckets = new ArrayList<>();
            sElementClasses.get(ModBucket.class)
                    .forEach(aClass -> {
                        try {
                            ModBucket info = (ModBucket) aClass.getAnnotation(ModBucket.class);
                            ItemBucket item = (ItemBucket) get(aClass);
                            item.setRegistryName(info.registerName());
                            item.setUnlocalizedName(info.unlocalizedName());
                            item.setCreativeTab((CreativeTabs) get(Class.forName(info.creativeTabClass())));
                            sBuckets.add(item);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        }
        return sBuckets;
    }

    /**
     * 获取所有实体
     */
    public static BiMap<Class, ModEntity> getEntities() {
        if (sEntities == null) {
            sEntities = HashBiMap.create();
            sElementClasses.get(ModEntity.class)
                    .forEach(aClass -> {
                        try {
                            ModEntity entity = (ModEntity) aClass.getAnnotation(ModEntity.class);
                            sEntities.put(aClass, entity);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        }
        return sEntities;
    }

    /**
     * 获取所有 Capability
     */
    public static ArrayList<ModCapability> getCapabilities() {
        if (sCapabilities == null) {
            sCapabilities = new ArrayList<>();
            sElementClasses.get(ModCapability.class)
                    .forEach(aClass -> sCapabilities.add((ModCapability) aClass.getAnnotation(ModCapability.class)));
        }
        return sCapabilities;
    }

    /**
     * 获取通信
     * 注意: 本操作附带有注册过程 调用后无需注册
     */
    public static BiMap<Integer, BiMap<Side, SimpleNetworkWrapper>> getNetworks() {
        if (sNetworks == null) {
            sNetworks = HashBiMap.create();
            sElementClasses.get(ModNetwork.class)
                    .forEach(aClass -> {
                        try {
                            ModNetwork info = (ModNetwork) aClass.getAnnotation(ModNetwork.class);
                            IMessageHandler handler = getNew(Class.forName(info.handlerClass()));
                            for (Side side : info.side()) {
                                SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(Elementtimes.MODID);
                                instance.registerMessage(handler, aClass, info.id(), side);
                                if (!sNetworks.containsKey(info.id()))
                                    sNetworks.put(info.id(), HashBiMap.create());
                                sNetworks.get(info.id()).put(side, instance);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        }
        return sNetworks;
    }

    /**
     * 获取所有 TitleEntity
     */
    public static BiMap<Class, ModTileEntity> getTitleEntities() {
        if (sTitleEntities == null) {
            sTitleEntities = HashBiMap.create();
            sElementClasses.get(ModTileEntity.class)
                    .forEach(aClass -> {
                        ModTileEntity info = (ModTileEntity) aClass.getAnnotation(ModTileEntity.class);
                        sTitleEntities.put(aClass, info);
                    });
        }
        return sTitleEntities;
    }

    // 通过空白构造获取对应对象
    // 与 getNew 方法不同的是 此方法会维护一个缓存列表, 避免创建重复对象
    public static <T> T get(Class<? extends T> blockClass) {
        try {
            if (sObjects.containsKey(blockClass))
                return (T) sObjects.get(blockClass);
            T instance = getNew(blockClass);
            if (instance != null)
                sObjects.put(blockClass, instance);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 获取类的成员
    // 先从对象中获取, 返回 null 则去类中获取
    public static <T> T getField(Class clazz, String fieldName, Object object) {
        try {
            Field fieldHolder = clazz.getField(fieldName);
            fieldHolder.setAccessible(true);
            T field;
            if (object == null) {
                field = (T) fieldHolder.get(null);
                if (field == null) {
                    Constructor constructor = clazz.getConstructor();
                    constructor.setAccessible(true);
                    field = (T) fieldHolder.get(constructor.newInstance());
                }
            } else {
                field = (T) fieldHolder.get(object);
                if (field == null) {
                    field = (T) fieldHolder.get(null);
                }
            }
            return field;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getNew(Class clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
