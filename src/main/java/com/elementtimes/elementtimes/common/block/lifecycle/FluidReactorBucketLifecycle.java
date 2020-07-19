package com.elementtimes.elementtimes.common.block.lifecycle;

import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.tileentity.lifecycle.BaseTeBucketTransferLifecycle;

import static com.elementtimes.elementcore.api.utils.CollectionUtils.array;
import static com.elementtimes.elementcore.api.utils.CollectionUtils.range;



public class FluidReactorBucketLifecycle extends BaseTeBucketTransferLifecycle {

    private final int[] inputs = array(0, 1);
    private final int[] outputs = range(2, 5);
    private final int[][] slots = new int[][] {
            array(0, 6), array(1, 7), array(2, 8), array(3, 9), array(4, 10)
    };

    public FluidReactorBucketLifecycle(BaseTileEntity tile) {
        super(tile);
    }

    @Override
    protected int getBucketInputSlot(int fluidSlot) {
        return slots[fluidSlot][0];
    }

    @Override
    protected int getBucketOutputSlot(int fluidSlot) {
        return slots[fluidSlot][1];
    }

    @Override
    protected int[] getFluidInputs() {
        return inputs;
    }

    @Override
    protected int[] getFluidOutputs() {
        return outputs;
    }
}
