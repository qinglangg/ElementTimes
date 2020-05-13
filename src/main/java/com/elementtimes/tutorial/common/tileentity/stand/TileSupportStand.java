package com.elementtimes.tutorial.common.tileentity.stand;

import com.elementtimes.tutorial.common.tileentity.stand.module.ISupportStandModule;
import com.elementtimes.tutorial.common.tileentity.stand.capability.ModuleCap;
import com.elementtimes.tutorial.common.tileentity.stand.capability.ModuleFluids;
import com.elementtimes.tutorial.common.tileentity.stand.capability.ModuleItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author KSGFK create in 2019/6/12
 */
@SuppressWarnings("unused")
public class TileSupportStand extends TileEntity implements ITickable {

    public static final String BIND_KEY = "_k";
    public static final String BIND_SSM = "_s";
    private static final HashMap<String, Supplier<ISupportStandModule>> CREATORS = new HashMap<>();

    private final HashMap<String, ISupportStandModule> modules = new HashMap<>();
    private final Map<String, NBTTagCompound> receivedData = new HashMap<>();
    private NBTTagCompound sync = null;
    private ISupportStandModule selectedModule = null;

    // ISSMManager
    public static void register(String key, Supplier<ISupportStandModule> creator) {
        CREATORS.put(key, creator);
    }

    public ISupportStandModule addModule(String key) {
        if (CREATORS.containsKey(key)) {
            ISupportStandModule module = CREATORS.get(key).get();
            modules.put(key, module);
            if (!world.isRemote) {
                if (sync == null) {
                    sync = new NBTTagCompound();
                }
                NBTTagCompound edit;
                if (sync.hasKey("Edit")) {
                    edit = sync.getCompoundTag("Edit");
                } else {
                    edit = new NBTTagCompound();
                    sync.setTag("Edit", edit);
                }
                NBTTagList addList;
                if (edit.hasKey("Add")) {
                    addList = edit.getTagList("Add", Constants.NBT.TAG_STRING);
                } else {
                    addList = new NBTTagList();
                    edit.setTag("Add", addList);
                }
                addList.appendTag(new NBTTagString(key));
            }
            return module;
        }
        return null;
    }

    public ISupportStandModule getModule(String key) {
        return modules.get(key);
    }

    public ISupportStandModule removeModule(String key) {
        ISupportStandModule module = modules.remove(key);
        if (module != null && !world.isRemote) {
            if (sync == null) {
                sync = new NBTTagCompound();
            }
            NBTTagCompound edit;
            if (sync.hasKey("Edit")) {
                edit = sync.getCompoundTag("Edit");
            } else {
                edit = new NBTTagCompound();
                sync.setTag("Edit", edit);
            }
            NBTTagList delList;
            if (sync.hasKey("Del")) {
                delList = edit.getTagList("Del", Constants.NBT.TAG_STRING);
            } else {
                delList = new NBTTagList();
                edit.setTag("Del", delList);
            }
            delList.appendTag(new NBTTagString(key));
        }
        return module;
    }

    public Set<String> getModuleKeys() {
        return modules.keySet();
    }

    public Collection<ISupportStandModule> getModules() {
        return modules.values();
    }

    public ItemStack removeToItem(String key) {
        ISupportStandModule module = getModule(key);
        if (module != null) {
            ItemStack item = module.getModuleItem();
            NBTTagCompound tag = item.getOrCreateSubCompound("BlockEntityTag");
            NBTTagCompound compound = module.serializeNBT();
            tag.setTag(BIND_SSM, compound);
        }
        return ItemStack.EMPTY;
    }

    // ITickable
    @Override
    public void update() {
        if (!world.isRemote) {
            NBTTagCompound compound = new NBTTagCompound();
            boolean update = false;
            for (ISupportStandModule module : modules.values()) {
                if (module.onTick(world, pos) && !update) {
                    update = true;
                }
                NBTTagCompound data = module.getUpdateData(world, pos);
                if (data != null) {
                    compound.setTag(module.getKey(), data);
                }
            }
            if (update) {
                markDirty();
            }
            if (!compound.hasNoTags() || sync != null) {
                if (sync == null) {
                    sync = compound;
                } else {
                    sync.merge(compound);
                }
                IBlockState state = world.getBlockState(pos);
                world.notifyBlockUpdate(pos, state, state, 1 | 2);
            }
        } else {
            for (ISupportStandModule module : modules.values()) {
                String key = module.getKey();
                NBTTagCompound compound = receivedData.get(key);
                if (!module.onTickClient(world, pos, compound)) {
                    receivedData.remove(key);
                }
            }
        }
    }

    // nbt
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        modules.clear();
        NBTTagList listModule = compound.getTagList("_m", Constants.NBT.TAG_COMPOUND);
        for (NBTBase nbtBase : listModule) {
            NBTTagCompound nbtModule = (NBTTagCompound) nbtBase;
            Supplier<ISupportStandModule> supplier = CREATORS.get(nbtModule.getString(BIND_KEY));
            if (supplier != null) {
                ISupportStandModule module = supplier.get();
                module.deserializeNBT(nbtModule);
                modules.put(module.getKey(), module);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        NBTTagList list = new NBTTagList();
        for (ISupportStandModule module : modules.values()) {
            list.appendTag(module.serializeNBT());
        }
        compound.setTag("_m", list);
        return compound;
    }

    // Capability
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        for (ISupportStandModule module : modules.values()) {
            if (module.hasCapability(capability, facing)) {
                selectedModule = module;
                return true;
            }
        }
        return false;
    }

    public boolean hasCapability(String module, Capability<?> capability, @Nullable EnumFacing facing) {
        return getModule(module).hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) new ModuleItems(this, facing);
        } else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T) new ModuleFluids(this, facing);
        } else {
            for (ISupportStandModule module : modules.values()) {
                if (module.hasCapability(capability, facing)) {
                    return module.getCapability(capability, facing);
                }
            }
        }
        return null;
    }

    public <T> T getCapability(String module, Capability<T> capability, @Nullable EnumFacing facing) {
        return getModule(module).getCapability(capability, facing);
    }

    @Nonnull
    public <T> List<ModuleCap<T>> getCapabilities(Capability<T> capability, @Nullable EnumFacing facing) {
        List<ModuleCap<T>> list = new ArrayList<>();
        for (ISupportStandModule module : modules.values()) {
            if (module.hasCapability(capability, facing)) {
                T cap = module.getCapability(capability, facing);
                list.add(new ModuleCap<>(module, cap));
            }
        }
        return list;
    }

    // Tesr
    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    // sync
    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        if (sync == null) {
            return null;
        }
        SPacketUpdateTileEntity packet = new SPacketUpdateTileEntity(pos, 1, sync);
        sync = null;
        return packet;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        NBTTagCompound compound = pkt.getNbtCompound();
        boolean maybeEdit = true;
        for (String key : compound.getKeySet()) {
            if (maybeEdit && "Edit".equals(key)) {
                NBTTagCompound edit = compound.getCompoundTag("Edit");
                NBTTagList addKeys = edit.getTagList("Add", Constants.NBT.TAG_STRING);
                for (int i = 0; i < addKeys.tagCount(); i++) {
                    String addKey = addKeys.getStringTagAt(i);
                    addModule(addKey);
                }
                NBTTagList delKeys = edit.getTagList("Del", Constants.NBT.TAG_STRING);
                for (int i = 0; i < delKeys.tagCount(); i++) {
                    String delKey = delKeys.getStringTagAt(i);
                    removeModule(delKey);
                }
                maybeEdit = false;
            } else {
                receivedData.put(key, compound.getCompoundTag(key));
            }
        }
    }
}
