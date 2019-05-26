package com.elementtimes.tutorial.annotation;

import com.elementtimes.tutorial.Elementtimes;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Elements {

    // block
    public static Map<Block, ImmutablePair<String, Class<? extends TileEntity>>> sTileEntities = new HashMap<>();
    public static Map<Block, IStateMapper> sStateMaps = new HashMap<>();
    public static Map<Block, ModBlock.StateMap> sBlockStates = new HashMap<>();
    public static Map<Block, String> sBlockOreDict = new HashMap<>();
    public static boolean useB3D = false;
    public static boolean useOBJ = false;
    // item
    public static Map<Item, String> sItemOreDict = new HashMap<>();

    private static HashMap<Class, ArrayList<Class>> sElementClasses = new HashMap<>();
    private static HashMap<Class, ArrayList<Field>> sElementFields = new HashMap<>();

    public static void init() {
        Class[] support = new Class[] {
                ModBlock.class, ModItem.class
        };

        for (Class aClass : support) {
            if (!sElementFields.containsKey(aClass))
                sElementFields.put(aClass, new ArrayList<>());
            if (!sElementClasses.containsKey(aClass))
                sElementClasses.put(aClass, new ArrayList<>());
        }

        try {
            Set<Class<?>> classes = getClasses("com.elementtimes.tutorial");
            warn("共计 {} Class", classes.size());
            for (Class<?> aClass : classes) {
                for (Class annotation : support) {
                    if (aClass.getAnnotation(annotation) != null){
                        sElementClasses.get(annotation).add(aClass);
                        break;
                    }
                }
                for (Field field : aClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    for (Class annotation : support) {
                        if (field.getAnnotation(annotation) != null){
                            sElementFields.get(annotation).add(field);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            warn("Error when load class {}", e.getMessage());
        }
    }

    /**
     * 从包package中获取所有的Class
     * 代码来自 http://guoliangqi.iteye.com/blog/644876
     */
    private static Set<Class<?>> getClasses(String pack) throws Exception {
        Set<Class<?>> classes = new LinkedHashSet<>();
        boolean recursive = true;
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;
        dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
        while (dirs.hasMoreElements()) {
            URL url = dirs.nextElement();
            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                System.err.println("file类型的扫描");
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
            } else if ("jar".equals(protocol)) {
                System.err.println("jar类型的扫描");
                JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    if (name.charAt(0) == '/') name = name.substring(1);
                    if (name.startsWith(packageDirName)) {
                        int idx = name.lastIndexOf('/');
                        if (idx != -1) packageName = name.substring(0, idx).replace('/', '.');
                        if ((idx != -1) || recursive) {
                            if (name.endsWith(".class") && !entry.isDirectory()) {
                                String className = name.substring(packageName.length() + 1, name.length() - 6);
                                classes.add(Class.forName(packageName + '.' + className));
                            }
                        }
                    }
                }
            }
        }

        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     */
    private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, Set<Class<?>> classes) throws Exception {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] dirfiles = dir.listFiles(file -> (recursive && file.isDirectory())
                || (file.getName().endsWith(".class")));
        for (File file : dirfiles) {
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
            }
        }
    }

    /**
     * 获取所有方块
     */
    public static List<Block> getBlocks() {
        List<Block> blocks = new ArrayList<>();
        sElementFields.get(ModBlock.class).forEach(field -> initBlock(field, blocks));
        sElementClasses.get(ModBlock.class).forEach(clazz -> initBlock(clazz, blocks));
        return blocks;
    }
    private static void initBlock(Object blockHolder, List<Block> into) {
        Block block = null;
        ModBlock info = null;
        Map<Class<? extends Annotation>, Annotation> annotationMap = null;
        // object
        if (blockHolder instanceof Field) {
            Field field = (Field) blockHolder;
            info = field.getAnnotation(ModBlock.class);
            block = get(field, null);
            annotationMap = annotationsFilter(field.getAnnotations(), ModOreDict.class, ModBlock.TileEntity.class,
                    ModBlock.StateMap.class, ModBlock.StateMapper.class, ModBlock.StateMapperCustom.class);
        } else if (blockHolder instanceof Class) {
            Class clazz = (Class) blockHolder;
            info = (ModBlock) clazz.getAnnotation(ModBlock.class);
            block = (Block) get(clazz);
            annotationMap = annotationsFilter(clazz.getAnnotations(), ModOreDict.class, ModBlock.TileEntity.class,
                    ModBlock.StateMap.class, ModBlock.StateMapper.class, ModBlock.StateMapperCustom.class);
        }
        if (block == null) block = new Block(Material.ROCK);
        // init
        if (block.getRegistryName() == null)
            block.setRegistryName(info.registerName());
        block.setUnlocalizedName(Elementtimes.MODID + "." + info.unlocalizedName());
        block.setCreativeTab(info.creativeTab().tab);
        // OreDict
        if (annotationMap.containsKey(ModOreDict.class))
            sBlockOreDict.put(block, ((ModOreDict)annotationMap.get(ModOreDict.class)).value());
        // TileEntity
        ModBlock.TileEntity teInfo = (ModBlock.TileEntity) annotationMap.get(ModBlock.TileEntity.class);
        if (teInfo != null) {
            try {
                sTileEntities.put(block, ImmutablePair.of(teInfo.name(), (Class<? extends TileEntity>) Class.forName(teInfo.clazz())));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        // StateMapper
        ModBlock.StateMapper smInfo = (ModBlock.StateMapper) annotationMap.get(ModBlock.StateMapper.class);
        try {
            if (smInfo != null) {
                Block finalBlock = block;
                Class propertyContainer = Class.forName(smInfo.propertyIn());
                net.minecraft.client.renderer.block.statemap.StateMap.Builder builder = new net.minecraft.client.renderer.block.statemap.StateMap.Builder().withSuffix(smInfo.suffix());
                IProperty property = getField(propertyContainer, smInfo.propertyName(), block);
                builder.withName(property);
                IProperty[] ignoreProperties = Arrays.stream(smInfo.propertyIgnore())
                        .map(ignoreProperty -> getField(propertyContainer, ignoreProperty, finalBlock)).toArray(IProperty[]::new);
                builder.ignore(ignoreProperties);
                sStateMaps.put(finalBlock, builder.build());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // CustomState
        ModBlock.StateMapperCustom mscInfo = (ModBlock.StateMapperCustom) annotationMap.get(ModBlock.StateMapperCustom.class);
        if (mscInfo != null) {
            try {
                String cn = mscInfo.value();
                IStateMapper mapper = null;
                if (!cn.isEmpty())
                    mapper = (IStateMapper) get(Class.forName(mscInfo.value()));
                if (mapper == null)
                    mapper = new DefaultStateMapper();
                sStateMaps.put(block, mapper);
            } catch (ClassNotFoundException e) {
                warn("Cannot load mapper: " + mscInfo.value());
                e.printStackTrace();
            }
        }
        // IBlockState
        ModBlock.StateMap bsInfo = (ModBlock.StateMap) annotationMap.get(ModBlock.StateMap.class);
        if (bsInfo != null) {
            if (!useB3D) useB3D = bsInfo.useB3D();
            if (!useOBJ) useOBJ = bsInfo.useOBJ();

            if (bsInfo.metadatas().length > 0) {
                sBlockStates.put(block, bsInfo);
            }
        }
        into.add(block);
    }

    /**
     * 获取所有液体方块
     * @deprecated 未实现
     */
    @Deprecated
    public static List<Fluid> getFluidBlocks() {
        List<Fluid> fluids = new ArrayList<>();
        sElementFields.get(ModFluid.class).forEach(field -> initFluid(field, fluids));
        sElementClasses.get(ModFluid.class).forEach(clazz -> initFluid(clazz, fluids));
        return fluids;
    }
    private static void initFluid(Object fluidHolder, List<Fluid> into) {
        Fluid fluid = null;
        ModFluid info = null;
//        into.add(fluid);
    }

    /**
     * 获取所有物品
     */
    public static List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        sElementFields.get(ModItem.class).forEach(field -> initItem(field, items));
        sElementClasses.get(ModItem.class).forEach(clazz -> initItem(clazz, items));
        return items;
    }
    private static void initItem(Object itemHolder, List<Item> into) {
        Item item = null;
        ModItem info = null;
        Map<Class<? extends Annotation>, Annotation> annotationMap = null;
        // object
        if (itemHolder instanceof Field) {
            Field field = (Field) itemHolder;
            info = field.getAnnotation(ModItem.class);
            if (info == null) return;
            item = get(field, null);
            annotationMap = annotationsFilter(field.getAnnotations(), ModOreDict.class);
        } else if (itemHolder instanceof Class) {
            Class clazz = (Class) itemHolder;
            info = (ModItem) clazz.getAnnotation(ModItem.class);
            if (info == null) return;
            item = (Item) get(clazz);
            annotationMap = annotationsFilter(clazz.getAnnotations(), ModOreDict.class);
        }
        if (item == null) item = new Item();

        // OreDict
        if (annotationMap.containsKey(ModOreDict.class))
            sItemOreDict.put(item, ((ModOreDict)annotationMap.get(ModOreDict.class)).value());
        if (item.getRegistryName() == null) {
            item.setRegistryName(info.registerName());
        } else  {
            warn("Item has registryName: {}", item.getRegistryName());
        }
        item.setUnlocalizedName(Elementtimes.MODID + "." + info.unlocalizedName());
        item.setCreativeTab(info.creativeTab().tab);
        into.add(item);
    }
//
//    /**
//     * 获取所有命令
//     */
//    public static ArrayList<CommandBase> getCommands() {
//        if (sCommands == null) {
//            sCommands = new ArrayList<>();
//            sElementClasses.get(ModCommand.class)
//                    .forEach(aClass -> {
//                        try {
//                            sCommands.add((CommandBase) get(aClass));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    });
//        }
//        return sCommands;
//    }
//
//    /**
//     * 获取所有附魔
//     */
//    public static ArrayList<Enchantment> getEnchantments() {
//        if (sEnchantments == null) {
//            sEnchantments = new ArrayList<>();
//            sElementClasses.get(ModEnchantment.class)
//                    .forEach(aClass -> {
//                        try {
//                            ModEnchantment info = (ModEnchantment) aClass.getAnnotation(ModEnchantment.class);
//                            Enchantment enchantment = (Enchantment) get(aClass);
//                            enchantment.setName(info.name());
//                            enchantment.setRegistryName(info.registerName());
//                            sEnchantments.add(enchantment);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    });
//        }
//        return sEnchantments;
//    }
//
//    /**
//     * 获取所有药水效果
//     */
//    public static ArrayList<Potion> getPotions() {
//        if (sPotions == null) {
//            sPotions = new ArrayList<>();
//            sElementClasses.get(ModPotion.class)
//                    .forEach(aClass -> {
//                        try {
//                            ModPotion info = (ModPotion) aClass.getAnnotation(ModPotion.class);
//                            Potion potion = (Potion) get(aClass);
//                            potion.setPotionName(info.name());
//                            potion.setRegistryName(info.registerName());
//                            sPotions.add(potion);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    });
//        }
//        return sPotions;
//    }
//
//    /**
//     * 获取所有桶
//     * @deprecated 有万能桶了。该注解可能会被删除，也可能会保留（自定义桶，比如某些液体一桶的容量）
//     */
//    @Deprecated
//    public static ArrayList<ItemBucket> getBuckets() {
//        if (sBuckets == null) {
//            sBuckets = new ArrayList<>();
//            sElementClasses.get(ModFluid.ModBucket.class)
//                    .forEach(aClass -> {
//                        try {
//                            ModFluid.ModBucket info = (ModFluid.ModBucket) aClass.getAnnotation(ModFluid.ModBucket.class);
//                            ItemBucket item = (ItemBucket) get(aClass);
//                            item.setRegistryName(info.registerName());
//                            item.setUnlocalizedName(info.unlocalizedName());
//                            item.setCreativeTab((CreativeTabs) get(Class.forName(info.creativeTabClass())));
//                            sBuckets.add(item);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    });
//        }
//        return sBuckets;
//    }
//
//    /**
//     * 获取所有实体
//     */
//    public static BiMap<Class, ModEntity> getEntities() {
//        if (sEntities == null) {
//            sEntities = HashBiMap.create();
//            sElementClasses.get(ModEntity.class)
//                    .forEach(aClass -> {
//                        try {
//                            ModEntity entity = (ModEntity) aClass.getAnnotation(ModEntity.class);
//                            sEntities.put(aClass, entity);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    });
//        }
//        return sEntities;
//    }
//
//    /**
//     * 获取所有 Capability
//     */
//    public static ArrayList<ModCapability> getCapabilities() {
//        if (sCapabilities == null) {
//            sCapabilities = new ArrayList<>();
//            sElementClasses.get(ModCapability.class)
//                    .forEach(aClass -> sCapabilities.add((ModCapability) aClass.getAnnotation(ModCapability.class)));
//        }
//        return sCapabilities;
//    }
//
//    /**
//     * 获取通信
//     * 注意: 本操作附带有注册过程 调用后无需注册
//     */
//    public static BiMap<Integer, BiMap<Side, SimpleNetworkWrapper>> getNetworks() {
//        if (sNetworks == null) {
//            sNetworks = HashBiMap.create();
//            sElementClasses.get(ModNetwork.class)
//                    .forEach(aClass -> {
//                        try {
//                            ModNetwork info = (ModNetwork) aClass.getAnnotation(ModNetwork.class);
//                            IMessageHandler handler = (IMessageHandler) get(Class.forName(info.handlerClass()));
//                            for (Side side : info.side()) {
//                                SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(Elementtimes.MODID);
//                                instance.registerMessage(handler, aClass, info.id(), side);
//                                if (!sNetworks.containsKey(info.id()))
//                                    sNetworks.put(info.id(), HashBiMap.create());
//                                sNetworks.get(info.id()).put(side, instance);
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    });
//        }
//        return sNetworks;
//    }

    // 通过空白构造获取对应对象
    public static <T> T get(Class<? extends T> aClass) {
        try {
            if (aClass == null) return null;
            return aClass.newInstance();
        } catch (Exception e) {
            warn("Cannot create instance: {}", aClass.getSimpleName());
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T get(Field field, Class<? extends T> defaultClass) {
        if (field == null) return null;
        field.setAccessible(true);
        T obj = null;
        try {
            obj = (T) field.get(null);
        } catch (Exception e) {
            warn("Field {} has no value", field.getName());
            e.printStackTrace();
        }
        if (obj != null) return obj;
        obj = (T) get(field.getType());
        if (obj == null && defaultClass != null) obj = get(defaultClass);
        try {
            field.set(null, obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            warn("Field {} cannot set value \n\t{}", field.getName(), obj);
        }
        return obj;
    }

    private static void warn(String message, Object... params) {
        Elementtimes.getLogger().warn("[Elementtimes] " + message, params);
    }

    private static Map<Class<? extends Annotation>, Annotation> annotationsFilter(Annotation[] annotations, Class<? extends Annotation>... find) {
        Map<Class<? extends Annotation>, Annotation> annotationMap = new HashMap<>();
        for (Annotation annotation : annotations) {
            for (Class<? extends Annotation> aClass : find) {
                if (aClass.isAssignableFrom(annotation.getClass())) {
                    annotationMap.put(aClass, annotation);
                    break;
                }
            }
            if (annotationMap.size() == find.length) break;
        }
        return annotationMap;
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
            warn("Cannot get field {} from {}", fieldName, clazz.getSimpleName());
            e.printStackTrace();
            return null;
        }
    }
}
