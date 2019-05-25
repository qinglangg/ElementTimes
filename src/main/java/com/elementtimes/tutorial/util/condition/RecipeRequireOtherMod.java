package com.elementtimes.tutorial.util.condition;

import com.google.gson.JsonObject;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.Loader;

import java.util.function.BooleanSupplier;

public class RecipeRequireOtherMod {
    public static class TR implements IConditionFactory {
        @Override
        public BooleanSupplier parse(JsonContext context, JsonObject json) {
            return () -> Loader.instance().getModList().stream().anyMatch(modContainer -> modContainer.getModId().equals("techreborn"));
        }
    }

    public static class IC2 implements IConditionFactory {
        @Override
        public BooleanSupplier parse(JsonContext context, JsonObject json) {
            return () -> Loader.instance().getModList().stream().anyMatch(modContainer -> modContainer.getModId().equals("ic2"));
        }
    }

    public static class GT implements IConditionFactory {
        @Override
        public BooleanSupplier parse(JsonContext context, JsonObject json) {
            return () -> Loader.instance().getModList().stream().anyMatch(modContainer -> modContainer.getModId().equals("gregtech"));
        }
    }
}
