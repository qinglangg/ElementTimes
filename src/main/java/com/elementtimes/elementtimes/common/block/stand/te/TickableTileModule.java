package com.elementtimes.elementtimes.common.block.stand.te;

import com.elementtimes.elementtimes.common.block.stand.module.ISupportStandModule;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

/**
 * 支持 Tickable 的使用 SSM 的 Te

 */
public class TickableTileModule<MODULE extends ISupportStandModule> extends BaseTileModule<MODULE> implements ITickableTileEntity {

    public TickableTileModule(TileEntityType<? extends TickableTileModule> type, MODULE module) {
        super(type, module);
    }

    @Override
    public void tick() {
        if (world != null && !world.isRemote) {
            if (module.onTick(world, pos)) {
                markDirty();
            }
        }
    }
}
