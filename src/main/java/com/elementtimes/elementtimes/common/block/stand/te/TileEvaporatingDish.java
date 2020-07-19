package com.elementtimes.elementtimes.common.block.stand.te;

import com.elementtimes.elementtimes.common.block.stand.module.ModuleEvaporatingDish;
import net.minecraft.tileentity.TileEntityType;

/**
 * todo
 *  NaClSolutionDilute = NaClSolutionConcentrated (cost 200)

 */
public class TileEvaporatingDish extends BaseTileModule<ModuleEvaporatingDish> {

    public TileEvaporatingDish(TileEntityType<TileEvaporatingDish> type) {
        super(type, new ModuleEvaporatingDish());
    }
}
