package com.elementtimes.tutorial.annotation.processor;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.annotation.ModBlock;
import com.elementtimes.tutorial.annotation.ModOreDict;
import com.elementtimes.tutorial.annotation.util.ReflectUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.tileentity.TileEntity;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.lang.reflect.AnnotatedElement;
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

    static Map<Block, ImmutablePair<String, Class<? extends TileEntity>>> sTileEntities = new HashMap<>();
    static Map<Block, IStateMapper> sStateMaps = new HashMap<>();
    static Map<Block, ModBlock.StateMap> sBlockStates = new HashMap<>();
    static Map<Block, String> sBlockOreDict = new HashMap<>();
    static boolean useB3D = false;
    static boolean useOBJ = false;

    /**
     * 获取所有方块
     */
    public static void getBlocks(Map<Class, ArrayList<AnnotatedElement>> elements, List<Block> into) {
        elements.get(ModBlock.class).forEach(element -> buildBlock(element, into));
    }
    private static void buildBlock(AnnotatedElement blockHolder, List<Block> into) {
        ModBlock info = blockHolder.getAnnotation(ModBlock.class);
        Block block = ReflectUtil.getFromAnnotated(blockHolder, new Block(Material.ROCK)).orElse(new Block(Material.ROCK));

        initBlock(block, info);
        // 矿辞
        initOreDict(block, blockHolder);
        // TileEntity
        initTileEntity(block, blockHolder);
        // IStateMapper
        initStateMapper(block, blockHolder);
        // BlockState 对应材质
        initBlockState(block, blockHolder);
        into.add(block);
    }

    private static void initBlock(Block block, ModBlock info) {
        if (block.getRegistryName() == null) {
            block.setRegistryName(info.registerName());
        }
        block.setUnlocalizedName(Elementtimes.MODID + "." + info.unlocalizedName());
        block.setCreativeTab(info.creativeTab().tab);
    }

    private static void initOreDict(Block block, AnnotatedElement blockHolder) {
        ModOreDict oreDict = blockHolder.getAnnotation(ModOreDict.class);
        if (oreDict != null) {
            sBlockOreDict.put(block, oreDict.value());
        }

    }

    private static void initTileEntity(Block block, AnnotatedElement blockHolder) {
        ModBlock.TileEntity tileEntity = blockHolder.getAnnotation(ModBlock.TileEntity.class);
        if (tileEntity != null) {
            try {
                sTileEntities.put(block, ImmutablePair.of(tileEntity.name(), (Class<? extends TileEntity>) Class.forName(tileEntity.clazz())));
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
                sStateMaps.put(block, builder.build());
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
            sStateMaps.put(block, mapper);
        }
    }

    private static void initBlockState(Block block, AnnotatedElement blockHolder) {
        ModBlock.StateMap bsInfo = blockHolder.getAnnotation(ModBlock.StateMap.class);
        if (bsInfo != null) {
            if (!useB3D) {
                useB3D = bsInfo.useB3D();
            }
            if (!useOBJ) {
                useOBJ = bsInfo.useOBJ();
            }

            if (bsInfo.metadatas().length > 0) {
                sBlockStates.put(block, bsInfo);
            }
        }
    }
}
