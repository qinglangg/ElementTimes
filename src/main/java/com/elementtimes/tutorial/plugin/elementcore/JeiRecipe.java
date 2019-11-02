package com.elementtimes.tutorial.plugin.elementcore;

import com.elementtimes.elementcore.api.common.ECModContainer;
import com.elementtimes.elementcore.api.common.ECUtils;
import com.elementtimes.elementcore.api.common.LoaderHelper;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.IGuiProvider;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import org.objectweb.asm.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

public class JeiRecipe {

    /**
     * 注册到 MachineRecipeHandler 对象上
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface MachineRecipe {
        /**
         * 方块 id
         * @return 方块 id
         */
        String block();
        String id() default "";
        Class<? extends IGuiProvider> gui();

        int u();
        int v();
        int w();
        int h();
    }

    public static void parser(ASMDataTable.ASMData data, ECModContainer container) {
        if (Loader.isModLoaded("jei")) {
            LoaderHelper.getOrLoadClass(container.elements, data.getClassName()).ifPresent(aClass -> {
                ECUtils.reflect.getField(aClass, data.getObjectName(), null, MachineRecipeHandler.class, container.logger).ifPresent(recipe -> {
                    Map<String, Object> info = data.getAnnotationInfo();
                    Block block = Block.getBlockFromName((String) info.get("block"));
                    if (block == null) {
                        throw new RuntimeException("Can't find block: " + info.get("block"));
                    }
                    String id = (String) info.getOrDefault("id", block.getRegistryName().toString());
                    int u = (int) info.get("u");
                    int v = (int) info.get("v");
                    int w = (int) info.get("w");
                    int h = (int) info.get("h");
                    Block finalBlock = block;
                    Type guiType = (Type) info.get("gui");
                    ECUtils.reflect.create(guiType.getClassName(), IGuiProvider.class, container.logger)
                            .ifPresent(gui -> com.elementtimes.tutorial.plugin.jei.JeiSupport.registerMachineRecipe(finalBlock, gui, recipe, id, u, v, w, h));
                });
            });
        }
    }
}
