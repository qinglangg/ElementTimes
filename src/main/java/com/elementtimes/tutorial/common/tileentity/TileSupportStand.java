package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.template.tileentity.interfaces.IGuiProvider;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.IMachineLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.IMachineTickable;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileTESR;
import com.elementtimes.tutorial.common.block.SupportStand;
import com.elementtimes.tutorial.interfaces.ISupportStandModule;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.interfaces.ISSMTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author KSGFK create in 2019/6/12
 */
@SuppressWarnings("unused")
public class TileSupportStand extends TileEntity implements ISSMTileEntity, ITickable, ITileTESR, IMachineTickable, IGuiProvider {

    private static final String NBT_RAIN_COLD_DOWN = "_nbt_bind_support_stand_cold_down_";
    private static final String NBT_ALCOHOL = "_nbt_bind_support_stand_alcohol_";
    private static final String NBT_ACTIVE = "_nbt_bind_support_stand_active_";

    private HashMap<String, RenderObject> tesr = new HashMap<>();
    private HashMap<String, Object> objects = new HashMap<>();
    private HashMap<String, ITickable> tickables = new HashMap<>();
    private HashMap<String, IMachineLifecycle> lifecycles = new HashMap<>();
    private NBTTagCompound properties;
    private String activeModule = null;

    // ISSMTileEntity
    @Override
    public Set<String> getModules() {
        return getRenderItems().keySet();
    }

    @Override
    public void putModule(ISupportStandModule module) {
        String key = module.getKey();
        if (!isRenderRegistered(key)) {
            registerRender(key, module.createRender());
        }
        setRender(key, true, pos);
        Object o = module.getActiveObject(world, pos).get();
        objects.put(key, o);
        if (o instanceof IMachineLifecycle) {
            lifecycles.put(key, (IMachineLifecycle) o);
        } else if (o instanceof ITickable) {
            tickables.put(key, (ITickable) o);
        }
        markRenderClient(pos);
    }

    @Override
    public void removeModule(String key) {
        getRenderItems().remove(key);
        objects.remove(key);
        if (key.equals(activeModule)) {
            setActiveModule(null);
        }
        if (tickables.remove(key) == null) {
            lifecycles.remove(key);
        }
        markRenderClient(pos);
    }

    @Override
    public Object getModuleObject(String key) {
        if (key == null) {
            return null;
        }
        Object o = objects.get(key);
        if (o == null) {
            ISupportStandModule module = SupportStand.MODULES.get(key);
            if (module != null) {
                o = module.getActiveObject(world, pos).get();
                objects.put(key, o);
            }
        }
        return o;
    }

    @Override
    public void setActiveModule(String key) {
        this.activeModule = key;
        if (key == null) {
            properties.removeTag(NBT_ACTIVE);
        } else {
            properties.setString(NBT_ACTIVE, key);
        }
        markRenderClient(pos);
    }

    @Override
    public Object getActiveModuleObject() {
        return getModuleObject(activeModule);
    }

    // ITickable
    @Override
    public void update() {
        update(this);
        for (Object value : objects.values()) {
            if (value instanceof ITickable && !(value instanceof IMachineLifecycle)) {
                ((ITickable) value).update();
            }
        }
    }

    @Override
    public void updateClient() {
        rReceiveRenderMessage();
    }

    // ITileTESR
    private NBTTagCompound renderNbt = null;

    @Override
    public HashMap<String, RenderObject> getRenderItems() {
        if (tesr == null) {
            tesr = new HashMap<>(SupportStand.MODULES.size());
        }
        return tesr;
    }

    @Override
    public void receiveRenderMessage(NBTTagCompound nbt) {
        if (world == null || !world.isRemote) {
            ITileTESR.super.receiveRenderMessage(nbt);
            resetProperties();
        } else {
            renderNbt = nbt;
        }
    }

    @SideOnly(Side.CLIENT)
    private void rReceiveRenderMessage() {
        synchronized (this) {
            if (renderNbt != null && renderNbt.hasKey(BIND_NBT_TESR_TE)) {
                ITileTESR.super.receiveRenderMessage(renderNbt);
                renderNbt = null;
                resetProperties();
            }
        }
    }

    private void resetProperties() {
        NBTTagCompound prop = getRenderProperties();
        activeModule = prop.hasKey(NBT_ACTIVE) ? prop.getString(NBT_ACTIVE) : null;
    }

    @Nonnull
    @Override
    public NBTTagCompound getRenderProperties() {
        if (properties == null) {
            properties = new NBTTagCompound();
        }
        return properties;
    }

    @Override
    public void setRenderProperties(@Nonnull NBTTagCompound properties) {
        this.properties = properties;
    }

    // IMachineTickable
    @Override
    public Set<IMachineLifecycle> getAllLifecycles() {
        Set<IMachineLifecycle> set = new HashSet<>(lifecycles.size());
        set.addAll(lifecycles.values());
        return set;
    }

    @Override
    public void interrupt() {
        Object module = getActiveModuleObject();
        if (module instanceof IMachineTickable) {
            ((IMachineTickable) module).interrupt();
        }
    }

    @Override
    public boolean isWorking() {
        Object module = getActiveModuleObject();
        return module instanceof IMachineTickable && ((IMachineTickable) module).isWorking();
    }

    @Override
    public void setWorking(boolean b) {
        Object module = getActiveModuleObject();
        if (module instanceof IMachineTickable) {
            ((IMachineTickable) module).setWorking(b);
        }
    }

    @Override
    public boolean isPause() {
        Object module = getActiveModuleObject();
        return !(module instanceof IMachineTickable) || ((IMachineTickable) module).isPause();
    }

    @Override
    public void setPause(boolean b) {
        Object module = getActiveModuleObject();
        if (module instanceof IMachineTickable) {
            ((IMachineTickable) module).setPause(b);
        }
    }

    @Override
    public int getEnergyProcessed() {
        Object module = getActiveModuleObject();
        return !(module instanceof IMachineTickable) ? 0 : ((IMachineTickable) module).getEnergyProcessed();
    }

    @Override
    public void setEnergyProcessed(int i) {
        Object module = getActiveModuleObject();
        if (module instanceof IMachineTickable) {
            ((IMachineTickable) module).setEnergyProcessed(i);
        }
    }

    @Override
    public int getEnergyUnprocessed() {
        Object module = getActiveModuleObject();
        return !(module instanceof IMachineTickable) ? 0 : ((IMachineTickable) module).getEnergyUnprocessed();
    }

    @Override
    public void setEnergyUnprocessed(int i) {
        Object module = getActiveModuleObject();
        if (module instanceof IMachineTickable) {
            ((IMachineTickable) module).setEnergyUnprocessed(i);
        }
    }

    // IGuiProvider
    @Override
    public ResourceLocation getBackground() {
        Object module = getActiveModuleObject();
        if (module instanceof IGuiProvider) {
            return ((IGuiProvider) module).getBackground();
        }
        return null;
    }

    @Override
    public GuiSize getSize() {
        Object module = getActiveModuleObject();
        if (module instanceof IGuiProvider) {
            return ((IGuiProvider) module).getSize();
        }
        return null;
    }

    @Override
    public String getTitle() {
        Object module = getActiveModuleObject();
        if (module instanceof IGuiProvider) {
            return ((IGuiProvider) module).getTitle();
        }
        return ElementtimesBlocks.supportStand.getLocalizedName();
    }

    @Override
    public List<EntityPlayerMP> getOpenedPlayers() {
        Object module = getActiveModuleObject();
        if (module instanceof IGuiProvider) {
            return ((IGuiProvider) module).getOpenedPlayers();
        }
        return null;
    }

    @Override
    public int getGuiId() {
        Object module = getActiveModuleObject();
        if (module instanceof IGuiProvider) {
            return ((IGuiProvider) module).getGuiId();
        }
        return -1;
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        Object module = getActiveModuleObject();
        if (module instanceof IGuiProvider) {
            return ((IGuiProvider) module).getSlots();
        }
        return new Slot[0];
    }

    @Nonnull
    @Override
    public FluidSlotInfo[] getFluids() {
        Object module = getActiveModuleObject();
        if (module instanceof IGuiProvider) {
            return ((IGuiProvider) module).getFluids();
        }
        return new FluidSlotInfo[0];
    }

    @Override
    public float getProcess() {
        Object module = getActiveModuleObject();
        if (module instanceof IGuiProvider) {
            return ((IGuiProvider) module).getProcess();
        }
        return 0;
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
        Object module = getActiveModuleObject();
        if (module instanceof IGuiProvider) {
            ((IGuiProvider) module).onGuiClosed(player);
        }
    }

    // TileEntity
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        ITileTESR.super.readFromNBT(compound);
        objects.forEach((key, obj) -> {
            if (obj instanceof INBTSerializable) {
                ((INBTSerializable) obj).deserializeNBT(compound.getTag(key));
            }
        });
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        NBTTagCompound compound1 = ITileTESR.super.writeRenderNbt(compound);
        objects.forEach((key, obj) -> {
            if (obj instanceof INBTSerializable) {
                compound1.setTag(key, ((INBTSerializable) obj).serializeNBT());
            }
        });
        return compound1;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        Object object = getActiveModuleObject();
        if (object instanceof ICapabilityProvider) {
            return ((ICapabilityProvider) object).hasCapability(capability, facing);
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        Object object = getActiveModuleObject();
        if (object instanceof ICapabilityProvider) {
            return ((ICapabilityProvider) object).getCapability(capability, facing);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        markRenderClient(pos);
    }
}
