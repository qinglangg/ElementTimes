package com.elementtimes.elementtimes.common.block.stand.te;

import com.elementtimes.elementtimes.common.block.stand.module.ISupportStandModule;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.elementtimes.elementtimes.common.block.stand.te.TileSupportStand.BIND_SSM;

/**
 * 简单的使用 SSM 的 Te

 */
public class BaseTileModule<MODULE extends ISupportStandModule> extends TileEntity {

    protected final MODULE module;

    public BaseTileModule(TileEntityType<? extends BaseTileModule> type, MODULE module) {
        super(type);
        this.module = module;
    }

    @Override
    public void read(CompoundNBT compound) {
        module.deserializeNBT(compound.getCompound(BIND_SSM));
        super.read(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put(BIND_SSM, module.serializeNBT());
        return super.write(compound);
    }

    @Nonnull
    public MODULE getModule() {
        return module;
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT sync = module.getUpdateData(world, pos);
        if (sync == null) {
            return null;
        }
        return new SUpdateTileEntityPacket(pos, 1, sync);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        module.onTickClient(world, pos, pkt.getNbtCompound());
    }
}
