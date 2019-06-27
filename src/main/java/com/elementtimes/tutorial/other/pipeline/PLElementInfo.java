package com.elementtimes.tutorial.other.pipeline;

import com.google.common.collect.ImmutableList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * 管道传输内容信息
 * @author luqin2007
 */
public class PLElementInfo {
    public final BlockPos from;
    public final BlockPos to;
    public final long startTime;
    public final NBTTagCompound element;

    private PLNetwork mNetwork;
    private boolean mIsValid = true;
    private PLElementInfo mNextElement = this;
    private ImmutableList<BlockPos> mPath;

    public PLElementInfo(World world, PLNetwork network, BlockPos from, BlockPos to, NBTTagCompound element) {
        this.from = from;
        this.to = to;
        this.element = element;
        startTime = world.getWorldTime();

        mNetwork = network;
        mPath = mNetwork.queryPath(from, to);
    }

    public void interruptAt(BlockPos pos) {

    }
}
