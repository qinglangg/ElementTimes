package com.elementtimes.elementtimes.common.block.stand.te;

import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementtimes.common.block.stand.module.ModuleAlcoholLamp;
import com.elementtimes.elementtimes.common.init.blocks.Chemical;
import net.minecraft.tileentity.TileEntityType;

/**
 * 酒精灯

 */
@ModTileEntity(blocks = @Getter(value = Chemical.class, name = "alcoholLamp"))
public class TileAlcoholLamp extends TickableTileModule<ModuleAlcoholLamp> {

    public TileAlcoholLamp(TileEntityType<TileAlcoholLamp> type) {
        super(type, new ModuleAlcoholLamp());
    }
}
