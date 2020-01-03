package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.FluidMachineLifecycle;
import net.minecraft.util.ResourceLocation;

/**
 * 流体储存机
 * @author luqin2007
 */
public class TileFluidTank extends BaseTileEntity {

    public TileFluidTank() {
        super(0, 1, 1, 1, 160000, 0, 0);
        addLifeCycle(new FluidMachineLifecycle(this, 1, 1));
    }

    @Override
    public ResourceLocation getBackground() {
        return null;
    }

    @Override
    public GuiSize getSize() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public int getGuiId() {
        return 0;
    }

    @Override
    public void update() {

    }
}
