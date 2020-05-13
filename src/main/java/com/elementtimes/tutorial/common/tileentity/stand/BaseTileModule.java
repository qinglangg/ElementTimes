package com.elementtimes.tutorial.common.tileentity.stand;

import com.elementtimes.tutorial.common.tileentity.stand.module.ISupportStandModule;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import javax.annotation.Nullable;

import static com.elementtimes.tutorial.common.tileentity.stand.TileSupportStand.BIND_SSM;

/**
 * 简单的使用 SSM 的 Te
 * @author luqin2007
 */
public class BaseTileModule<MODULE extends ISupportStandModule> extends TileEntity implements ITickable {

    protected final MODULE module;

    public BaseTileModule(MODULE module) {
        this.module = module;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        module.deserializeNBT(compound.getCompoundTag(BIND_SSM));
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag(BIND_SSM, module.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            if (module.onTick(world, pos)) {
                markDirty();
            }
        }
    }

    public MODULE getModule() {
        return module;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound sync = module.getUpdateData(world, pos);
        if (sync == null) {
            return null;
        }
        return new SPacketUpdateTileEntity(pos, 1, sync);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        module.onTickClient(world, pos, pkt.getNbtCompound());
    }
}
