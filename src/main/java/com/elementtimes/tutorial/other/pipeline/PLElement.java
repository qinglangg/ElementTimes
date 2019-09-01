package com.elementtimes.tutorial.other.pipeline;

import com.elementtimes.tutorial.common.tileentity.TilePipeline;
import com.elementtimes.tutorial.other.pipeline.interfaces.Serializer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static com.elementtimes.tutorial.other.pipeline.interfaces.Serializer.*;

/**
 * 管道传输内容信息
 * @author luqin2007
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class PLElement implements INBTSerializable<NBTTagCompound> {

    static {
        SItem.instance();
        SFluid.instance();
    }

    private static final String NBT_BIND_PIPELINE_ELEMENT = "_pipeline_element_";
    private static final String NBT_BIND_PIPELINE_ELEMENT_ELEMENT = "_pipeline_element_element_";
    private static final String NBT_BIND_PIPELINE_ELEMENT_PATH = "_pipeline_element_tick_path_";
    private static final String BIND_NBT_PIPELINE_ELEMENT_SERIALIZER = "_pipeline_element_serializer_";
    private static final String BIND_NBT_PIPELINE_ELEMENT_SERIALIZER_CLASS = "_pipeline_element_serializer_class_";

    public Object element;
    public PLPath path;
    public Serializer serializer = SItem.instance();
    public String serializerClass = "";
    public TilePipeline tp;

    public void onTick(World world) {
        path.onTick(world, this);
    }

    public void send(TilePipeline pipeline) {
        if (!(pipeline.elementsAdd.contains(this) || pipeline.elements.contains(this))) {
//            if (PLEvent.onElementSend(this)) {
                pipeline.elementsAdd.add(this);
//            }
        }
    }

    public void send() {
        send(tp);
    }

    public void remove() {
//        if (PLEvent.onElementRemove(this)) {
            if (tp != null) {
                tp.elementsRemove.add(this);
                tp = null;
            }
//        }
    }

    public void moveTo(World world, int position) {
        path.position = position;
        path.tick = 0;
        final TileEntity tileEntity = world.getTileEntity(path.path.get(position).pos);
        if (tileEntity instanceof TilePipeline && element != null) {
            setTp((TilePipeline) tileEntity);
        }
    }

    public void setTp(TilePipeline tp) {
        this.tp.elementsRemove.add(this);
        tp.elementsAdd.add(this);
    }

    public PLElement copy() {
        return copy(element);
    }

    public PLElement copy(Object element) {
        PLElement e = new PLElement();
        e.element = element;
        e.path = path.copy();
        e.serializer = serializer;
        e.serializerClass = serializerClass;
        e.setTp(tp);
        return e;
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
        serializer = SERIALIZERS.get(nameSerializer);
        if (serializer == null) {
            try {
                Class c = Class.forName(serializerClass);
                for (Constructor constructor : c.getDeclaredConstructors()) {
                    constructor.setAccessible(true);
                    if (constructor.getParameterCount() == 0) {
                        Object obj = constructor.newInstance();
                        if (obj instanceof Serializer) {
                            this.serializer = (Serializer) obj;
                            SERIALIZERS.put(this.serializer.name(), this.serializer);
                            break;
                        }
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        element = serializer.deserialize(elementNbt);
        path = PLPath.fromNbt(pathNbt);
    }

    @Override
    public String toString() {
        return element + " at " + path.path.get(path.position) + " (" + path.from + " -> " + path.to + "), time: " + path.tick + "/" + path.totalTick;
    }

    public static PLElement item(ItemStack itemStack) {
        PLElement element = new PLElement();
        element.serializerClass = "";
        element.element = itemStack;
        return element;
    }

    public static PLElement fluid(FluidStack fluidStack) {
        PLElement element = new PLElement();
        element.serializer = SFluid.instance();
        element.serializerClass = "";
        element.element = fluidStack;
        return element;
    }
}
