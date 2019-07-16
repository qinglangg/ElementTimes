package com.elementtimes.tutorial.other.pipeline;

import com.elementtimes.tutorial.common.storage.PLNetworkStorage;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class PLNetwork implements INBTSerializable<NBTTagCompound> {

    private static String BIND_NBT_NETWORK = "_nbt_network_";
    private static String BIND_NBT_NETWORK_NODE = "_nbt_network_node_";
    private static String BIND_NBT_NETWORK_ELEMENT = "_nbt_network_element_";

    public static List<PLNetwork> NETWORKS = new ArrayList<>();

    public static void read(NBTTagList nbt) {
        NETWORKS.clear();
        for (NBTBase nbtBase : nbt) {
            NBTTagCompound network = (NBTTagCompound) nbtBase;
            PLNetwork n = new PLNetwork();
            n.deserializeNBT(network);
            NETWORKS.add(n);
        }
    }

    public static NBTTagList write() {
        NBTTagList nbt = new NBTTagList();
        for (PLNetwork network : NETWORKS) {
            nbt.appendTag(network.serializeNBT());
        }
        return nbt;
    }

    public static void markDirty(World world) {
        PLNetworkStorage.load(world).markDirty();
    }

    public static void nextTick(World world) {
        boolean change = false;
        for (PLNetwork network : NETWORKS) {
            for (PLElement element : network.elements) {
                element.nextTick(world, network);
                change = true;
            }
        }
        if (change) {
            markDirty(world);
        }
    }

    public List<PLInfo> nodes = new LinkedList<>();
    public List<PLElement> elements = new LinkedList<>();

    public void add(PLInfo node) {
        Stream<PLInfo> stream = nodes.stream().filter(pnode -> node.pos.equals(pnode.pos));
        if (!stream.findFirst().isPresent()) {
            nodes.add(node);
        }
    }

    public void remove(BlockPos pos) {
        nodes.removeIf(n -> n.pos.equals(pos));
        if (nodes.isEmpty()) {
            NETWORKS.remove(this);
        }
    }

    public void remove(PLInfo node) {
        remove(node.pos);
    }

    public NBTTagCompound writeToNbt(NBTTagCompound nbt) {
        NBTTagCompound nbtNetwork = new NBTTagCompound();
        NBTTagList nodeList = new NBTTagList();
        for (PLInfo node : nodes) {
            nodeList.appendTag(node.serializeNBT());
        }
        nbtNetwork.setTag(BIND_NBT_NETWORK_NODE, nodeList);
        NBTTagList elementList = new NBTTagList();
        for (PLElement element : elements) {
            elementList.appendTag(element.serializeNBT());
        }
        nbtNetwork.setTag(BIND_NBT_NETWORK_ELEMENT, elementList);
        nbt.setTag(BIND_NBT_NETWORK, nbtNetwork);
        return nbt;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return writeToNbt(new NBTTagCompound());
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        nodes.clear();
        elements.clear();
        NBTTagCompound nbtNetwork = nbt.getCompoundTag(BIND_NBT_NETWORK);
        NBTTagList nodes = (NBTTagList) nbtNetwork.getTag(BIND_NBT_NETWORK_NODE);
        for (NBTBase nbtBase : nodes) {
            NBTTagCompound node = (NBTTagCompound) nbtBase;
            this.nodes.add(PLInfo.fromNBT(node));
        }
        NBTTagList elements = (NBTTagList) nbtNetwork.getTag(BIND_NBT_NETWORK_ELEMENT);
        for (NBTBase nbtBase : elements) {
            NBTTagCompound element = (NBTTagCompound) nbtBase;
            PLElement plElement = new PLElement();
            plElement.deserializeNBT(element);
            this.elements.add(plElement);
        }
    }
}
