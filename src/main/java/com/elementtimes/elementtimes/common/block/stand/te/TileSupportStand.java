package com.elementtimes.elementtimes.common.block.stand.te;

import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter2;
import com.elementtimes.elementtimes.common.block.stand.capability.ModuleCap;
import com.elementtimes.elementtimes.common.block.stand.module.ISupportStandModule;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;



@SuppressWarnings("unused")
@ModTileEntity.Ter(@Getter2("com.elementtimes.elementtimes.client.block.SSMRender"))
public class TileSupportStand extends TileEntity implements ITickableTileEntity {

    public static final String BIND_KEY = "_k";
    public static final String BIND_SSM = "_s";
    private static final HashMap<String, Supplier<ISupportStandModule>> CREATORS = new HashMap<>();

    private final HashMap<String, ISupportStandModule> modules = new HashMap<>();
    private final Map<String, CompoundNBT> receivedData = new HashMap<>();
    private CompoundNBT sync = null;
    private ISupportStandModule selectedModule = null;

    public TileSupportStand(TileEntityType<TileSupportStand> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public static void register(String key, Supplier<ISupportStandModule> creator) {
        CREATORS.put(key, creator);
    }

    public void addModule(String key) {
        if (CREATORS.containsKey(key)) {
            ISupportStandModule module = CREATORS.get(key).get();
            modules.put(key, module);
            assert world != null;
            if (!world.isRemote) {
                CompoundNBT edit = getEditTag();
                ListNBT addList;
                if (edit.contains("Add")) {
                    addList = edit.getList("Add", Constants.NBT.TAG_STRING);
                } else {
                    addList = new ListNBT();
                    edit.put("Add", addList);
                }
                addList.add(new StringNBT(key));
            }
        }
    }

    public ISupportStandModule getModule(String key) {
        return modules.get(key);
    }

    public ISupportStandModule removeModule(String key) {
        ISupportStandModule module = modules.remove(key);
        if (module != null && world != null && !world.isRemote) {
            CompoundNBT edit = getEditTag();
            ListNBT delList;
            if (sync.contains("Del")) {
                delList = edit.getList("Del", Constants.NBT.TAG_STRING);
            } else {
                delList = new ListNBT();
                edit.put("Del", delList);
            }
            delList.add(new StringNBT(key));
        }
        return module;
    }

    private CompoundNBT getEditTag() {
        if (sync == null) {
            sync = new CompoundNBT();
        }
        if (sync.contains("Edit")) {
            return sync.getCompound("Edit");
        } else {
            CompoundNBT edit = new CompoundNBT();
            sync.put("Edit", edit);
            return edit;
        }
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
            CompoundNBT tag = item.getOrCreateChildTag("BlockEntityTag");
            CompoundNBT compound = module.serializeNBT();
            tag.put(BIND_SSM, compound);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void tick() {
        if (world != null && !world.isRemote) {
            CompoundNBT compound = new CompoundNBT();
            boolean update = false;
            for (ISupportStandModule module : modules.values()) {
                if (module.onTick(world, pos) && !update) {
                    update = true;
                }
                CompoundNBT data = module.getUpdateData(world, pos);
                if (data != null) {
                    compound.put(module.getKey(), data);
                }
            }
            if (update) {
                markDirty();
            }
            if (!compound.isEmpty() || sync != null) {
                if (sync == null) {
                    sync = compound;
                } else {
                    sync.merge(compound);
                }
                BlockState state = getBlockState();
                world.notifyBlockUpdate(pos, state, state, 1 | 2);
            }
        } else {
            for (ISupportStandModule module : modules.values()) {
                String key = module.getKey();
                CompoundNBT compound = receivedData.get(key);
                if (!module.onTickClient(world, pos, compound)) {
                    receivedData.remove(key);
                }
            }
        }
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        modules.clear();
        ListNBT listModule = compound.getList("_m", Constants.NBT.TAG_COMPOUND);
        for (INBT INBT : listModule) {
            CompoundNBT nbtModule = (CompoundNBT) INBT;
            Supplier<ISupportStandModule> supplier = CREATORS.get(nbtModule.getString(BIND_KEY));
            if (supplier != null) {
                ISupportStandModule module = supplier.get();
                module.deserializeNBT(nbtModule);
                modules.put(module.getKey(), module);
            }
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound = super.write(compound);
        ListNBT list = new ListNBT();
        for (ISupportStandModule module : modules.values()) {
            list.add(module.serializeNBT());
        }
        compound.put("_m", list);
        return compound;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return super.getCapability(cap, side);
    }

    public <T> List<ModuleCap<T>> getCapabilities(@Nonnull Capability<T> capability, Direction side) {
        List<ModuleCap<T>> list = new ArrayList<>();
        modules.values().forEach(module -> {
            LazyOptional<T> optional = module.getCapability(capability, side);
            if (optional.isPresent()) {
                list.add(new ModuleCap<>(module, optional));
            }
        });
        return list;
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        if (sync == null) {
            return null;
        }
        SUpdateTileEntityPacket packet = new SUpdateTileEntityPacket(pos, 1, sync);
        sync = null;
        return packet;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT compound = pkt.getNbtCompound();
        boolean maybeEdit = true;
        for (String key : compound.keySet()) {
            if (maybeEdit && "Edit".equals(key)) {
                CompoundNBT edit = compound.getCompound("Edit");
                ListNBT addKeys = edit.getList("Add", Constants.NBT.TAG_STRING);
                for (int i = 0; i < addKeys.size(); i++) {
                    String addKey = addKeys.getString(i);
                    addModule(addKey);
                }
                ListNBT delKeys = edit.getList("Del", Constants.NBT.TAG_STRING);
                for (int i = 0; i < delKeys.size(); i++) {
                    String delKey = delKeys.getString(i);
                    removeModule(delKey);
                }
                maybeEdit = false;
            } else {
                receivedData.put(key, compound.getCompound(key));
            }
        }
    }
}
