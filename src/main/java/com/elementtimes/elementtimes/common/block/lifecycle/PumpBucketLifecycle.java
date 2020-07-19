package com.elementtimes.elementtimes.common.block.lifecycle;

import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.tileentity.lifecycle.BaseTeBucketTransferLifecycle;

import static com.elementtimes.elementcore.api.utils.CollectionUtils.array;



public class PumpBucketLifecycle extends BaseTeBucketTransferLifecycle {

    private final int[] outputs = array(0);

    public PumpBucketLifecycle(BaseTileEntity tile) {
        super(tile);
    }

    @Override
    protected int getBucketInputSlot(int fluidSlot) {
        return 0;
    }

    @Override
    protected int getBucketOutputSlot(int fluidSlot) {
        return 1;
    }

    @Override
    protected int[] getFluidInputs() {
        return outputs;
    }

    @Override
    protected int[] getFluidOutputs() {
        return outputs;
    }
}
