package com.elementtimes.tutorial.annotation.processor;

import com.elementtimes.tutorial.annotation.annotations.ModBlock;
import com.elementtimes.tutorial.annotation.annotations.ModOreDict;
import com.elementtimes.tutorial.annotation.enums.GenType;
import com.elementtimes.tutorial.annotation.other.DefaultOreGenerator;
import com.elementtimes.tutorial.annotation.other.ModInfo;
import com.elementtimes.tutorial.annotation.util.ReflectUtil;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.*;

import static com.elementtimes.tutorial.annotation.util.MessageUtil.warn;
import static com.elementtimes.tutorial.annotation.util.ReflectUtil.getField;

/**
 * 用于处理 Block 注解
 * 所有被 ModBlock 注解的成员都会在此处理
 *
 * @see ModBlock
 * @author luqin2007
 */
public class ModBlockLoader {

    public static Map<Block, ImmutablePair<String, Class<? extends TileEntity>>> TILE_ENTITIES = new HashMap<>();
    public static Map<Block, IStateMapper> STATE_MAPS = new HashMap<>();
    public static Map<Block, ModBlock.StateMap> BLOCK_STATES = new HashMap<>();
    public static Map<Block, String> ORE_DICTIONARY = new HashMap<>();
    public static Map<GenType, List<WorldGenerator>> WORLD_GENERATORS = new HashMap<>();
    public static Object2IntMap<Block> BURNING_TIMES = new Object2IntArrayMap<>();
    public static boolean B3D = false, OBJ = false;

    /**
     * 获取所有方块
     */
    public static void getBlocks(Map<Class, ArrayList<AnnotatedElement>> elements, List<Block> into) {
        elements.get(ModBlock.class).forEach(element -> buildBlock(element, into));
    }
    private static void buildBlock(AnnotatedElement blockHolder, List<Block> into) {
        ModBlock info = blockHolder.getAnnotation(ModBlock.class);
        Block block = ReflectUtil.getFromAnnotated(blockHolder, new Block(Material.ROCK)).orElse(new Block(Material.ROCK));

        String defaultName;
        if (blockHolder instanceof Class) {
            defaultName = ((Class) blockHolder).getSimpleName().toLowerCase();
        } else if (blockHolder instanceof Field) {
            defaultName = ((Field) blockHolder).getName().toLowerCase();
        } else {
            defaultName = null;
        }

        initBlock(block, info, defaultName);
        // 无法被链式调用的方法
        initBlock2(block, blockHolder);
        // 矿辞
        initOreDict(block, blockHolder);
        // TileEntity
        initTileEntity(block, blockHolder);
        // IStateMapper
        initStateMapper(block, blockHolder);
        // BlockState 对应材质
        initBlockState(block, blockHolder);
        // 世界生成
        initWorldGenerator(block, blockHolder);
        into.add(block);
    }

    private static void initBlock(Block block, ModBlock info, String defaultName) {
        String registryName = info.registerName();
        if (registryName.isEmpty()) {
            registryName = defaultName;
        }
        if (block.getRegistryName() == null) {
            if (registryName == null || registryName.isEmpty()) {
                warn("Block {} don't have a RegisterName. It's a Bug!!!", block);
            } else {
                block.setRegistryName(ModInfo.MODID, registryName);
            }
        }
        String unlocalizedName = info.unlocalizedName();
        if (unlocalizedName.isEmpty()) {
            unlocalizedName = registryName;
        }
        block.setUnlocalizedName(ModInfo.MODID + "." + unlocalizedName);
        block.setCreativeTab(info.creativeTab().tab);
        int burningTime = info.burningTime();
        if (burningTime > 0) {
            BURNING_TIMES.put(block, burningTime);
        }
    }

    private static void initBlock2(Block block, AnnotatedElement blockHolder) {
        ModBlock.HarvestLevel harvestLevel = blockHolder.getAnnotation(ModBlock.HarvestLevel.class);
        if (harvestLevel != null) {
            block.setHarvestLevel(harvestLevel.toolClass(), harvestLevel.level());
        }
    }

    private static void initOreDict(Block block, AnnotatedElement blockHolder) {
        ModOreDict oreDict = blockHolder.getAnnotation(ModOreDict.class);
        if (oreDict != null) {
            ORE_DICTIONARY.put(block, oreDict.value());
        }

    }

    private static void initTileEntity(Block block, AnnotatedElement blockHolder) {
        ModBlock.TileEntity tileEntity = blockHolder.getAnnotation(ModBlock.TileEntity.class);
        if (tileEntity != null) {
            try {
                TILE_ENTITIES.put(block, ImmutablePair.of(tileEntity.name(), (Class<? extends TileEntity>) Class.forName(tileEntity.clazz())));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void initStateMapper(final Block block, AnnotatedElement blockHolder) {
        ModBlock.StateMapper smInfo = blockHolder.getAnnotation(ModBlock.StateMapper.class);
        try {
            if (smInfo != null) {
                Class propertyContainer = Class.forName(smInfo.propertyIn());
                net.minecraft.client.renderer.block.statemap.StateMap.Builder builder = new net.minecraft.client.renderer.block.statemap.StateMap.Builder().withSuffix(smInfo.suffix());
                IProperty property = (IProperty) ReflectUtil.getField(propertyContainer, smInfo.propertyName(), block).orElse(null);
                builder.withName(property);
                IProperty[] ignoreProperties = Arrays.stream(smInfo.propertyIgnore())
                        .map(ignoreProperty -> getField(propertyContainer, ignoreProperty, block)).toArray(IProperty[]::new);
                builder.ignore(ignoreProperties);
                STATE_MAPS.put(block, builder.build());
            }
        } catch (ClassNotFoundException e) {
            warn("Cannot load mapper: " + smInfo.propertyName());
            e.printStackTrace();
        }

        ModBlock.StateMapperCustom mscInfo = blockHolder.getAnnotation(ModBlock.StateMapperCustom.class);
        if (mscInfo != null) {
            IStateMapper mapper = mscInfo.value().isEmpty()
                    ? new DefaultStateMapper()
                    : (IStateMapper) ReflectUtil.create(mscInfo.value()).orElse(new DefaultStateMapper());
            STATE_MAPS.put(block, mapper);
        }
    }

    private static void initBlockState(Block block, AnnotatedElement blockHolder) {
        ModBlock.StateMap bsInfo = blockHolder.getAnnotation(ModBlock.StateMap.class);
        if (bsInfo != null) {
            if (!B3D) {
                B3D = bsInfo.useB3D();
            }
            if (!OBJ) {
                OBJ = bsInfo.useOBJ();
            }

            if (bsInfo.metadatas().length > 0) {
                BLOCK_STATES.put(block, bsInfo);
            }
        }
    }

    private static void initWorldGenerator(Block block, AnnotatedElement blockHolder) {
        ModBlock.WorldGenClass wgcInfo = blockHolder.getAnnotation(ModBlock.WorldGenClass.class);
        WorldGenerator wgcObject = null;
        if (wgcInfo != null) {
            wgcObject = ReflectUtil.<WorldGenerator>create(wgcInfo.value(), new Object[] {block}).orElse(null);
        }
        if (wgcObject != null) {
            List<WorldGenerator> worldGenerators = WORLD_GENERATORS.computeIfAbsent(wgcInfo.type(), k -> new ArrayList<>());
            worldGenerators.add(wgcObject);
        } else {
            ModBlock.WorldGen wgInfo = blockHolder.getAnnotation(ModBlock.WorldGen.class);
            if (wgInfo != null) {
                List<WorldGenerator> worldGenerators = WORLD_GENERATORS.computeIfAbsent(wgInfo.type(), k -> new ArrayList<>());
                worldGenerators.add(new DefaultOreGenerator(wgInfo, block.getDefaultState()));
            }
        }
    }
}
