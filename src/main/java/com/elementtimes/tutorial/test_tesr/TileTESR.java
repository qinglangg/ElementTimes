package com.elementtimes.tutorial.test_tesr;

import net.minecraft.tileentity.TileEntity;

public class TileTESR extends TileEntity {

    @Override
    public boolean hasFastRenderer() {
        return true;
    }
}
