package com.elementtimes.tutorial.annotation.processor;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.annotation.ModItem;
import com.elementtimes.tutorial.annotation.ModOreDict;
import com.elementtimes.tutorial.annotation.util.ReflectUtil;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ModItemLoader {

    static Map<Item, String> sItemOreDict = new HashMap<>();
    static Map<Item, Int2ObjectMap<ModelResourceLocation>> sSubItemModel = new HashMap<>();

    static void getItems(Map<Class, ArrayList<AnnotatedElement>> elements, List<Item> into) {
        elements.get(ModItem.class).forEach(element -> buildItem(element, into));
    }

    private static void buildItem(AnnotatedElement itemHolder, List<Item> into) {
        // object
        final ModItem info = itemHolder.getAnnotation(ModItem.class);
        if (info == null) return;
        Item item = ReflectUtil.getFromAnnotated(itemHolder, new Item()).orElse(new Item());

        initOreDict(item, itemHolder);
        initItem(item, info);
        initSubItem(item, itemHolder);
        initRetainedItem(item, itemHolder);
        initDamageable(item, itemHolder);

        into.add(item);
    }

    private static void initItem(Item item, ModItem info) {
        if (item.getRegistryName() == null)
            item.setRegistryName(info.registerName());
        item.setUnlocalizedName(Elementtimes.MODID + "." + info.unlocalizedName());
        item.setCreativeTab(info.creativeTab().tab);
    }

    private static void initOreDict(Item item, AnnotatedElement itemHolder) {
        ModOreDict oreDict = itemHolder.getAnnotation(ModOreDict.class);
        if (oreDict != null) sItemOreDict.put(item, oreDict.value());
    }

    private static void initSubItem(Item item, AnnotatedElement itemHolder) {
        ModItem.HasSubItem subItem = itemHolder.getAnnotation(ModItem.HasSubItem.class);
        if (subItem != null) {
            item.setHasSubtypes(true);
            item.setMaxDamage(0);
            item.setNoRepair();

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
                    domain = Elementtimes.MODID;
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
            sSubItemModel.put(item, map);
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
            if (damageable.noRepair())
                item.setNoRepair();
        }
    }
}
