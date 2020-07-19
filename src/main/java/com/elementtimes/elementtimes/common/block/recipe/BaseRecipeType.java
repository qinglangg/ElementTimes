package com.elementtimes.elementtimes.common.block.recipe;

import com.elementtimes.elementcore.api.recipe.IExtendedRecipe;
import com.elementtimes.elementcore.api.recipe.IRecipeType;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 使用泛型类作为 toString 的 IRecipeType，创建时必须具有泛型类型

 */
public abstract class BaseRecipeType<T extends IExtendedRecipe> implements IRecipeType<T> {

    protected Class<T> mType;
    protected ResourceLocation mResourceLocation;

    public BaseRecipeType() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            mType = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
            mResourceLocation = new ResourceLocation(ElementTimes.MODID, mType.getSimpleName().toLowerCase());
        } else {
            throw new RuntimeException("该方法必须先初始化泛型类，否则使用接收一个 Class 的构造");
        }
    }

    @Override
    public Class<T> getType() {
        return mType;
    }

    @Override
    public ResourceLocation getId() {
        return mResourceLocation;
    }
}
