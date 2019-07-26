package com.elementtimes.tutorial.other.pipeline;

import com.elementtimes.tutorial.common.tileentity.TilePipeline;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;

/**
 * 代表一条路线
 * @author luqin2007
 */
@SuppressWarnings({"unused"})
public class PLPath implements INBTSerializable<NBTTagCompound> {

    private static final String BIND_NBT_PATH = "_nbt_pipeline_path_";
    private static final String BIND_NBT_PATH_FROM = "_nbt_pipeline_path_from_";
    private static final String BIND_NBT_PATH_TO = "_nbt_pipeline_path_to_";
    private static final String BIND_NBT_PATH_IN = "_nbt_pipeline_path_from_ori_";
    private static final String BIND_NBT_PATH_OUT = "_nbt_pipeline_path_to_ori_";
    private static final String BIND_NBT_PATH_POS = "_nbt_pipeline_path_pos_";
    private static final String BIND_NBT_PATH_TICK = "_nbt_pipeline_path_tick_";
    private static final String BIND_NBT_PATH_TICK_TOTAL = "_nbt_pipeline_path_tick_total_";
    private static final String BIND_NBT_PATH_POS_STACK = "_nbt_pipeline_path_pos_stack_";

    public BlockPos from, to;
    public PLInfo plIn, plOut, plPos;
    public long totalTick;
    public int tick;
    public LinkedList<PLInfo> path;
    private boolean tickAdd;

    public PLPath(BlockPos from, PLInfo in, BlockPos to, PLInfo out) {
        this.from = from;
        this.to = to;
        plIn = in;
        plPos = in;
        plOut = out;
        totalTick = 0;
        tick = 0;
        path = new LinkedList<>();
        tickAdd = false;
    }

    public PLPath(BlockPos from, BlockPos to, LinkedList<PLInfo> before, PLInfo next) {
        this.from = from;
        this.to = to;
        if (before.isEmpty()) {
            plIn = next;
            plOut = next;
            plPos = next;
        } else {
            plIn = before.getFirst();
            plOut = next;
            plPos = plIn;
        }
        totalTick = 0;
        tick = 0;
        path = new LinkedList<>();
        Collections.copy(path, before);
        path.add(next);
        tickAdd = false;
    }

    public void tickStart(World world, PLElement element) {
        if (world.isBlockLoaded(plPos.pos) && !tickAdd) {
            TileEntity te = world.getTileEntity(plPos.pos);
            if (te instanceof TilePipeline) {
                if (tick < plPos.keepTick) {
                    // 下一 tick
                    totalTick++;
                    tick++;
                } else {
                    if (plPos.equals(plOut) && plPos.listOut.contains(to)) {
                        tick = 0;
                        Object back = plPos.action.output(world, plPos.pos, element, false);
                        if (element.serializer.isObjectEmpty(back)) {
                            element.remove();
                        } else {
                            // 输出 返回剩余内容
                            backToStart(world, element, back);
                        }
                    } else {
                        int i = path.indexOf(plPos);
                        if (i < 0) {
                            // 无效路段 移除
                            element.remove();
                        } else {
                            // 传输到下一段
                            PLInfo next = path.get(i + 1);
                            Object send = plPos.action.send(world, next, element, false);
                            if (!element.serializer.isObjectEmpty(send)) {
                                PLElement back = element.copy(send);
                                back.path.backToStart(world, back, send);
                                back.send();
                            }
                        }
                    }
                }
            } else {
                // 当前路段无效 返回并查找新路径
                boolean next = backAndContinue(world, element);
                if (!next) {
                    backToStart(world, element, element.element);
                }
            }
            tickAdd = true;
        }
    }

    /**
     * 当遇到异常路段时，返回到有效路段并重新规划路径
     * @param world 所在世界
     * @param element 传输内容
     * @return 可以正常规划到下一个路径
     */
    public boolean backAndContinue(World world, PLElement element) {
        int i = path.indexOf(plPos);
        if (i > 0) {
            while (i > 0) {
                i--;
                PLInfo back = path.get(i);
                TileEntity te = world.getTileEntity(back.pos);
                if (te instanceof TilePipeline) {
                    moveTo(world, back);
                    Map<BlockPos, PLPath> pathMap = back.allValidOutput(world, from, element);
                    PLPath path = pathMap.get(to);
                    if (path != null) {
                        for (PLInfo info : this.path.subList(i, this.path.size())) {
                            this.path.remove(info);
                        }
                        this.path.addAll(path.path);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 返回到起点
     * @param world 所在世界
     * @param element 传输内容
     * @param back 返回内容
     */
    public void backToStart(World world, PLElement element, Object back) {
        element.element = back;
        tick = 0;
        BlockPos swap = from;
        from = to;
        to = swap;
        Collections.reverse(path);
    }

    public void tickEnd() {
        tickAdd = false;
    }

    public PLPath copy() {
        PLPath p = new PLPath(from, plIn, to, plOut);
        p.plPos = plPos;
        p.totalTick = totalTick;
        p.tick = tick;
        p.path.clear();
        Collections.copy(p.path, path);
        return p;
    }

    public void append(PLInfo next, BlockPos out) {
        path.add(next);
        to = out;
        plOut = next;
    }

    public boolean moveTo(World world, PLInfo pos) {
        if (world.isBlockLoaded(pos.pos)) {
            this.plPos = pos;
            this.tick = 0;
            return true;
        }
        return false;
    }

    public void test(LinkedList<PLInfo> subPath, long subTick) {
        if (!subPath.isEmpty()) {
            int indexFirst = path.indexOf(subPath.getFirst());
            int indexLast = path.indexOf(subPath.getLast());
            if (indexFirst >= 0 && indexLast >= indexFirst) {
                List<PLInfo> center = path.subList(indexFirst, indexLast + 1);
                long t = center.stream().mapToLong(p -> p.keepTick).sum();
                if (t < subTick) {
                    List<PLInfo> first = path.subList(0, indexFirst);
                    List<PLInfo> last = path.subList(indexLast + 1, path.size());
                    LinkedList<PLInfo> paths = new LinkedList<>();
                    paths.addAll(first);
                    paths.addAll(subPath);
                    paths.addAll(last);
                    this.path = paths;
                }
            }
        }
    }

    public long calcTick() {
        return path.stream().mapToLong(p -> p.keepTick).sum();
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagCompound nbtPath = new NBTTagCompound();
        nbtPath.setTag(BIND_NBT_PATH_FROM, NBTUtil.createPosTag(from));
        nbtPath.setTag(BIND_NBT_PATH_TO, NBTUtil.createPosTag(to));
        nbtPath.setTag(BIND_NBT_PATH_IN, plIn.serializeNBT());
        nbtPath.setTag(BIND_NBT_PATH_OUT, plOut.serializeNBT());
        nbtPath.setTag(BIND_NBT_PATH_POS, plPos.serializeNBT());
        nbtPath.setInteger(BIND_NBT_PATH_TICK, tick);
        nbtPath.setLong(BIND_NBT_PATH_TICK_TOTAL, totalTick);

        NBTTagList pos = new NBTTagList();
        for (PLInfo p : path) {
            pos.appendTag(p.serializeNBT());
        }
        nbtPath.setTag(BIND_NBT_PATH_POS_STACK, pos);

        nbt.setTag(BIND_NBT_PATH, nbtPath);
        return nbt;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTTagCompound nbtPath = nbt.getCompoundTag(BIND_NBT_PATH);
        from = NBTUtil.getPosFromTag(nbtPath.getCompoundTag(BIND_NBT_PATH_FROM));
        to = NBTUtil.getPosFromTag(nbtPath.getCompoundTag(BIND_NBT_PATH_TO));
        plIn = PLInfo.fromNBT(nbtPath.getCompoundTag(BIND_NBT_PATH_IN));
        plOut = PLInfo.fromNBT(nbtPath.getCompoundTag(BIND_NBT_PATH_OUT));
        plPos = PLInfo.fromNBT(nbtPath.getCompoundTag(BIND_NBT_PATH_POS));
        totalTick = nbtPath.getLong(BIND_NBT_PATH_TICK_TOTAL);
        tick = nbtPath.getInteger(BIND_NBT_PATH_TICK);


        NBTTagList pos = (NBTTagList) nbtPath.getTag(BIND_NBT_PATH_POS_STACK);
        path = new LinkedList<>();
        for (int i = 0; i < pos.tagCount(); i++) {
            NBTTagCompound tag = pos.getCompoundTagAt(i);
            path.add(i, PLInfo.fromNBT(tag));
        }
    }

    public static PLPath fromNbt(NBTTagCompound nbt) {
        PLPath path = new PLPath(BlockPos.ORIGIN, null, BlockPos.ORIGIN, null);
        path.deserializeNBT(nbt);
        return path;
    }

}
