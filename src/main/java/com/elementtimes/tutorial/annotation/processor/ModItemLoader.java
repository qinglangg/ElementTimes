package com.elementtimes.tutorial.annotation.processor;

import com.elementtimes.tutorial.annotation.annotations.ModItem;
import com.elementtimes.tutorial.annotation.annotations.ModOreDict;
import com.elementtimes.tutorial.annotation.other.ModInfo;
import com.elementtimes.tutorial.annotation.util.ReflectUtil;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.elementtimes.tutorial.annotation.util.MessageUtil.warn;

/**
 * 加载物品
 * 处理所有 ModItem 注解的成员
 *
 * @author luqin2007
 */
public class ModItemLoader {

    public static Map<Item, String> ORE_DICTIONARY = new HashMap<>();
    @SideOnly(Side.CLIENT)
    public static Map<Item, Int2ObjectMap<ModelResourceLocation>> SUB_ITEM_MODEL = new HashMap<>();
    @SideOnly(Side.CLIENT)
    public static Map<Item, IItemColor> ITEM_COLOR = new HashMap<>();

    public static void getItems(Map<Class, ArrayList<AnnotatedElement>> elements, List<Item> into) {
        elements.get(ModItem.class).forEach(element -> buildItem(element, into));
    }

    private static void buildItem(AnnotatedElement itemHolder, List<Item> into) {
        // object
        final ModItem info = itemHolder.getAnnotation(ModItem.class);
        if (info == null) {
            return;
        }
        Item item = ReflectUtil.getFromAnnotated(itemHolder, new Item()).orElse(new Item());

        String defaultName;
        if (itemHolder instanceof Class) {
            defaultName = ((Class) itemHolder).getSimpleName().toLowerCase();
        } else if (itemHolder instanceof Field) {
            defaultName = ((Field) itemHolder).getName().toLowerCase();
        } else {
            defaultName = null;
        }

        initItem(item, info, defaultName);
        // 矿辞
        initOreDict(item, itemHolder);
        // 子类型
        initSubItem(item, itemHolder);
        // 合成表保留
        initRetainedItem(item, itemHolder);
        // 耐久
        initDamageable(item, itemHolder);

        into.add(item);
    }

    private static void initItem(Item item, ModItem info, String defaultName) {
        String registryName = info.registerName();
        if (registryName.isEmpty()) {
            registryName = defaultName;
        }
        if (item.getRegistryName() == null) {
            if (registryName == null) {
                warn("Item {} don't have a RegisterName. It's a Bug!!!", item);
            } else {
                item.setRegistryName(registryName);
            }
        }
        String unlocalizedName = info.unlocalizedName();
        if (unlocalizedName.isEmpty()) {
            unlocalizedName = registryName;
        }
        item.setUnlocalizedName(ModInfo.MODID + "." + unlocalizedName);
        item.setCreativeTab(info.creativeTab().tab);
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            if (!info.itemColorClass().isEmpty()) {
                ReflectUtil.create(info.itemColorClass())
                        .filter(obj -> obj instanceof IItemColor)
                        .ifPresent(obj -> ITEM_COLOR.put(item, (IItemColor) obj));
            }
        }
    }

    private static void initOreDict(Item item, AnnotatedElement itemHolder) {
        ModOreDict oreDict = itemHolder.getAnnotation(ModOreDict.class);
        if (oreDict != null) {
            ORE_DICTIONARY.put(item, oreDict.value());
        }
    }

    private static void initSubItem(Item item, AnnotatedElement itemHolder) {
        ModItem.HasSubItem subItem = itemHolder.getAnnotation(ModItem.HasSubItem.class);
        if (subItem != null) {
            item.setHasSubtypes(true);
            item.setMaxDamage(0);
            item.setNoRepair();

            if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
                int[] metadata = subItem.metadatas();
                String[] models = subItem.models();
                Int2ObjectMap<ModelResourceLocation> map = new Int2ObjectArrayMap<>();
                for (int i = 0; i < metadata.length; i++) {
                    String model = models[i];
                    int domainIndex = model.indexOf(":");
                    int variantIndex = model.indexOf("#");
                    String domain;
                    String variant;
                    String resource;
                    if (domainIndex > 0) {
                        domain = model.substring(0, domainIndex);
                        if (variantIndex > 0) {
                            variant = model.substring(variantIndex + 1);
                            resource = model.substring(domainIndex + 1, variantIndex);
                        } else {
                            variant = "inventory";
                            resource = model.substring(domainIndex + 1);
                        }
                    } else {
                        domain = ModInfo.MODID;
                        if (variantIndex > 0) {
                            variant = model.substring(variantIndex + 1);
                            resource = model.substring(0, variantIndex);
                        } else {
                            variant = "inventory";
                            resource = model;
                        }
                    }
                    map.put(metadata[i], new ModelResourceLocation(new ResourceLocation(domain, resource), variant));
                }
                SUB_ITEM_MODEL.put(item, map);
            }
        }
    }

    private static void initRetainedItem(Item item, AnnotatedElement itemHolder) {
        ModItem.RetainInCrafting retain = itemHolder.getAnnotation(ModItem.RetainInCrafting.class);
        if (retain != null) {
            item.setContainerItem(item);
        }
    }

    private static void initDamageable(Item item, AnnotatedElement itemHolder) {
        ModItem.Damageable damageable = itemHolder.getAnnotation(ModItem.Damageable.class);
        if (damageable != null) {
            item.setMaxDamage(damageable.value());
            item.setMaxStackSize(1);
            if (damageable.noRepair()) {
                item.setNoRepair();
            }
        }
    }
}
