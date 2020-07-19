package com.elementtimes.elementtimes.common.block.stand.te;

import com.elementtimes.elementtimes.common.block.stand.module.ModuleCrucible;
import com.elementtimes.elementtimes.common.init.Items;
import net.minecraft.tileentity.TileEntityType;

/**
 * todo cost 200
 *  uraniumPowder = UO2
 *  stoneIngot = calciumOxide

 */
public class TileCrucible extends BaseTileModule<ModuleCrucible> {

    public TileCrucible(TileEntityType<TileCrucible> type) {
        super(type, new ModuleCrucible());
    }
}
