package com.elementtimes.elementtimes.common.block.lifecycle;

import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.tileentity.lifecycle.BaseTeBucketTransferLifecycle;

import static com.elementtimes.elementcore.api.utils.CollectionUtils.INT_EMPTY;
import static com.elementtimes.elementcore.api.utils.CollectionUtils.array;



public class SolidMelterBucketLifecycle extends BaseTeBucketTransferLifecycle {

    private final int[] outputs = array(0);

    public SolidMelterBucketLifecycle(BaseTileEntity tile) {
        super(tile);
    }

    @Override
    protected int getBucketInputSlot(int fluidSlot) {
        return 1;
    }

    @Override
    protected int getBucketOutputSlot(int fluidSlot) {
        return 2;
    }

    @Override
    protected int[] getFluidInputs() {
        return INT_EMPTY;
    }

    @Override
    protected int[] getFluidOutputs() {
        return outputs;
    }
}
