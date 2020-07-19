package com.elementtimes.elementtimes.common.block.pipeline;

import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "pipelineItemOutput"))
public class TileItemOutput extends TileItemConnect {

    public TileItemOutput(TileEntityType<TileItemOutput> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public boolean canOutputElementTo(BlockPos target) {
        return hasHandler(target, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
    }
}
