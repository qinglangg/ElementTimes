package com.elementtimes.tutorial.plugin.elementcore;

import com.elementtimes.elementcore.api.common.ECModContainer;
import com.elementtimes.tutorial.common.tileentity.stand.module.ISupportStandModule;
import com.elementtimes.tutorial.common.tileentity.stand.TileSupportStand;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Supplier;

/**
 * 自动注册铁架台组件
 * @author luqin2007
 */
public class SSMRegister {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface SupportStandModule {
        String value();
    }

    public static void register(ASMDataTable.ASMData data, ECModContainer container) {
        String className = data.getClassName();
        String key = (String) data.getAnnotationInfo().get("value");
        Supplier<ISupportStandModule> creator = () -> {
            try {
                return (ISupportStandModule) Thread.currentThread().getContextClassLoader().loadClass(className).newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                return null;
            }
        };
        TileSupportStand.register(key, creator);
    }
}
