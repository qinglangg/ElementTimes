package com.elementtimes.tutorial.other.pipeline;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 管道传输内容信息
 * @author luqin2007
 */
@SuppressWarnings("unused")
public class PLElement implements INBTSerializable<NBTTagCompound> {

    private static final Map<String, Serializer> SERIALIZER = new HashMap<>();

    private static final String NBT_BIND_PIPELINE_ELEMENT = "_pipeline_element_";
    private static final String NBT_BIND_PIPELINE_ELEMENT_ELEMENT = "_pipeline_element_element_";
    private static final String NBT_BIND_PIPELINE_ELEMENT_PATH = "_pipeline_element_tick_path_";
    private static final String BIND_NBT_PIPELINE_ELEMENT_SERIALIZER = "_pipeline_element_serializer_";
    private static final String BIND_NBT_PIPELINE_ELEMENT_SERIALIZER_CLASS = "_pipeline_element_serializer_class_";

    private Object element;
    private PLPath path;
    private Serializer serializer = emptySerializer();
    private String serializerClass = "";

    public void nextTick(World world, PLNetwork network) {
        path.tickStart(world, network);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagCompound nbtPipeline = new NBTTagCompound();

        nbtPipeline.setTag(NBT_BIND_PIPELINE_ELEMENT_ELEMENT, serializer.serialize(element));
        nbtPipeline.setTag(NBT_BIND_PIPELINE_ELEMENT_PATH, path.serializeNBT());
        nbtPipeline.setString(BIND_NBT_PIPELINE_ELEMENT_SERIALIZER, serializer.name());
        nbtPipeline.setString(BIND_NBT_PIPELINE_ELEMENT_SERIALIZER_CLASS, serializerClass);

        nbt.setTag(NBT_BIND_PIPELINE_ELEMENT, nbtPipeline);
        return nbt;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTTagCompound nbtPipeline = nbt.getCompoundTag(NBT_BIND_PIPELINE_ELEMENT);

        NBTTagCompound pathNbt = nbtPipeline.getCompoundTag(NBT_BIND_PIPELINE_ELEMENT_PATH);
        NBTBase elementNbt = nbtPipeline.getTag(NBT_BIND_PIPELINE_ELEMENT_ELEMENT);
        serializerClass = nbtPipeline.getString(BIND_NBT_PIPELINE_ELEMENT_SERIALIZER_CLASS);
        String nameSerializer = nbtPipeline.getString(BIND_NBT_PIPELINE_ELEMENT_SERIALIZER);
        serializer = SERIALIZER.get(nameSerializer);
        if (serializer == null) {
            try {
                Class c = Class.forName(serializerClass);
                for (Constructor constructor : c.getDeclaredConstructors()) {
                    constructor.setAccessible(true);
                    if (constructor.getParameterCount() == 0) {
                        Object obj = constructor.newInstance();
                        if (obj instanceof Serializer) {
                            this.serializer = (Serializer) obj;
                            SERIALIZER.put(this.serializer.name(), this.serializer);
                            break;
                        }
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                serializer = emptySerializer();
            }
        }
        element = serializer.deserialize(elementNbt);
        path = PLPath.fromNbt(pathNbt);
    }

    public interface Serializer {
        String name();

        @Nonnull
        NBTBase serialize(@Nonnull Object object);
        @Nonnull
        Object deserialize(@Nonnull NBTBase nbt);
    }

    public static Serializer emptySerializer() {
        Serializer serializer = SERIALIZER.get("_EMPTY_");
        if (serializer == null) {
            serializer = new Serializer() {
                @Override
                public String name() {
                    return "_EMPTY_";
                }

                @Nonnull
                @Override
                public NBTBase serialize(@Nonnull Object object) {
                    return new NBTTagCompound();
                }

                @Nonnull
                @Override
                public Object deserialize(@Nonnull NBTBase nbt) {
                    return nbt;
                }
            };
            SERIALIZER.put(serializer.name(), serializer);
        }
        return serializer;
    }
}
