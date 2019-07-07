package com.elementtimes.tutorial.annotation.processor;

import com.elementtimes.tutorial.annotation.annotations.ModEnchantment;
import com.elementtimes.tutorial.annotation.other.ModInfo;
import com.elementtimes.tutorial.annotation.util.ReflectUtil;
import net.minecraft.enchantment.Enchantment;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.elementtimes.tutorial.annotation.util.MessageUtil.warn;

/**
 * 处理附魔
 * @author luqin2007
 */
public class ModEnchantmentLoader {

    public static void getEnchantments(Map<Class, ArrayList<AnnotatedElement>> elements, List<Enchantment> into) {
        elements.get(ModEnchantment.class).forEach(element -> buildEnchantment(element, into));
    }

    private static void buildEnchantment(AnnotatedElement element, List<Enchantment> into) {
        Optional<Enchantment> enchantmentOptional = ReflectUtil.getFromAnnotated(element, null)
                .filter(obj -> obj instanceof Enchantment)
                .map(obj -> (Enchantment) obj);
        String name = ReflectUtil.getName(element).orElse("???");
        ModEnchantment info = element.getAnnotation(ModEnchantment.class);
        if (enchantmentOptional.isPresent()) {
            Enchantment enchantment = enchantmentOptional.get();
            initEnchantment(enchantment, info, name);
            into.add(enchantment);
        } else {
            warn("Element {} is not an Enchantment", name, element);
        }
    }

    private static void initEnchantment(Enchantment enchantment, ModEnchantment info, String name) {
        if (enchantment.getName().equals("enchantment." + null)) {
            String iName = info.name();
            if (iName.isEmpty()) {
                enchantment.setName("elementtimes." + name.toLowerCase());
            } else {
                enchantment.setName(iName);
            }
        }

        if (enchantment.getRegistryName() == null) {
            String iName = info.registerName();
            if (iName.isEmpty()) {
                enchantment.setRegistryName(ModInfo.MODID, name.toLowerCase());
            } else {
                enchantment.setRegistryName(ModInfo.MODID, iName);
            }
        }
    }
}
