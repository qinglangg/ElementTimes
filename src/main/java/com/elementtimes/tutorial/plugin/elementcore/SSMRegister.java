package com.elementtimes.tutorial.plugin.elementcore;

import com.elementtimes.elementcore.api.common.ECModContainer;
import com.elementtimes.tutorial.common.block.SupportStand;
import com.elementtimes.tutorial.common.block.interfaces.ISupportStandModule;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * 自动注册铁架台组件
 * @author luqin2007
 */
public class SSMRegister {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface SupportStandModule {}

    public static void register(ASMDataTable.ASMData data, ECModContainer container) {
        try {
            String className = data.getClassName();
            String objectName = data.getObjectName();
            Class<?> aClass = Class.forName(className);
            Field field = aClass.getDeclaredField(objectName);
            field.setAccessible(true);
            Object o = field.get(null);
            if (o instanceof ISupportStandModule) {
                SupportStand.registerModule((ISupportStandModule) o);
            }
        } catch (IllegalAccessException | ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
