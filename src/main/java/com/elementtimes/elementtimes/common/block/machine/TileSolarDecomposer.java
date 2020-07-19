package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.lifecycle.SolarDecomposerBucketLifecycle;
import com.elementtimes.elementtimes.common.block.lifecycle.SolarDecomposerRecipeLifecycle;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.tileentity.TileEntityType;

/**
 * todo gui 材质错误

 */
@ModTileEntity(blocks = @Getter(value = Industry.class, name = "solarDecomposer"))
public class TileSolarDecomposer extends BaseTileEntity {

    public TileSolarDecomposer(TileEntityType<TileSolarDecomposer> type) {
        super(type, 100000, 8, 4, 16000);
        getEngine().addLifeCycle(new SolarDecomposerBucketLifecycle(this));
        getEngine().addLifeCycle(new SolarDecomposerRecipeLifecycle(this));
    }
}
