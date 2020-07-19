package com.elementtimes.elementtimes.common.block.stand.te;

import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementtimes.common.block.stand.module.ModuleBeaker;
import com.elementtimes.elementtimes.common.init.blocks.Chemical;
import net.minecraft.tileentity.TileEntityType;

/**
 * todo
 *  salt + waterDistilled = NaClSolutionDilute
 *  NaClSolutionDilute + waterDistilled = waterDistilled

 */
@ModTileEntity(blocks = @Getter(value = Chemical.class, name = "alcoholLamp"))
public class TileBeaker extends BaseTileModule<ModuleBeaker> {

    public TileBeaker(TileEntityType<TileBeaker> type) {
        super(type, new ModuleBeaker());
    }
}
