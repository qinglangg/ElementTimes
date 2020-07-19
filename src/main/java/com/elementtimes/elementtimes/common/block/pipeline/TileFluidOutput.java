package com.elementtimes.elementtimes.common.block.pipeline;

import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "pipelineFluidOutput"))
public class TileFluidOutput extends TileFluidConnect {

    public TileFluidOutput(TileEntityType<TileFluidOutput> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public boolean canOutputElementTo(BlockPos target) {
        return hasHandler(target, CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
    }
}
