package com.elementtimes.elementtimes.common.block.lifecycle;

import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.tileentity.lifecycle.BaseTeBucketTransferLifecycle;

import static com.elementtimes.elementcore.api.utils.CollectionUtils.array;
import static com.elementtimes.elementcore.api.utils.CollectionUtils.range;



public class SolidFluidReactorBucketLifecycle extends BaseTeBucketTransferLifecycle {

    private final int[] inputs = array(0);
    private final int[] outputs = array(1, 2);
    private final int[][] slots = new int[][] {
            array(1, 6), array(2, 7), array(3, 8)
    };

    public SolidFluidReactorBucketLifecycle(BaseTileEntity tile) {
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
