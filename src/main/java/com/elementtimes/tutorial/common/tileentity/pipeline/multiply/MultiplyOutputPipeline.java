package com.elementtimes.tutorial.common.tileentity.pipeline.multiply;

import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileFluidHandler;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileItemHandler;
import com.elementtimes.tutorial.common.pipeline.BaseElement;
import com.elementtimes.tutorial.common.pipeline.IPipelineOutput;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/**
 * 物品输出管道
 * 用于将 ItemStack 从管道中输出
 * @author luqin2007
 */
public class MultiplyOutputPipeline extends MultiplyIOPipeline implements IPipelineOutput {

    @Override
    public boolean canConnectIO(BlockPos pos, EnumFacing direction) {
        if (world != null) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof ITileItemHandler) {
                return ((ITileItemHandler) te).getItemHandler(SideHandlerType.INPUT).getSlots() > 0;
            } else if (te instanceof ITileFluidHandler) {
                return ((ITileFluidHandler) te).getTanks(SideHandlerType.INPUT).size() > 0;
            }
        }
        return super.canConnectIO(pos, direction);
    }

    @Override
    protected boolean isElementAllowOutput(BaseElement element) {
        return true;
    }
}
