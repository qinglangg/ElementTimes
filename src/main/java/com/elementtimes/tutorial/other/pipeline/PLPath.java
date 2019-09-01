package com.elementtimes.tutorial.other.pipeline;

import com.elementtimes.tutorial.common.tileentity.TilePipeline;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 代表一条路线
 * @author luqin2007
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class PLPath implements INBTSerializable<NBTTagCompound> {

    private static final String BIND_NBT_PATH = "_nbt_pipeline_path_";
    private static final String BIND_NBT_PATH_FROM = "_nbt_pipeline_path_from_";
    private static final String BIND_NBT_PATH_TO = "_nbt_pipeline_path_to_";
    private static final String BIND_NBT_PATH_POS = "_nbt_pipeline_path_pos_";
    private static final String BIND_NBT_PATH_TICK = "_nbt_pipeline_path_tick_";
    private static final String BIND_NBT_PATH_TICK_TOTAL = "_nbt_pipeline_path_tick_total_";
    private static final String BIND_NBT_PATH_TICK_PAUSE = "_nbt_pipeline_path_tick_pause_";
    private static final String BIND_NBT_PATH_POS_STACK = "_nbt_pipeline_path_pos_stack_";

    /**
     * 输入/输出方块
     */
    public BlockPos from, to;
    /**
     * 当前位置在 path 的索引
     */
    public int position = 0;
    /**
     * 传输总时间
     */
    public long totalTick = 0;
    /**
     * 在某一节管道中持续的时间
     */
    public int tick = 0;
    /**
     * 暂停时间
     */
    public int pause = 0;
    /**
     * 总路径
     */
    public LinkedList<PLInfo> path = new LinkedList<>();

    public PLPath(BlockPos from, PLInfo in, BlockPos to, PLInfo out) {
        this.from = from;
        this.to = to;
        path.addFirst(in);
        path.addLast(out);
    }

    public PLPath(BlockPos from, BlockPos to, LinkedList<PLInfo> before, PLInfo next) {
        this.from = from;
        this.to = to;
        path.addAll(before);
        path.add(next);
    }

    public PLPath(BlockPos from, BlockPos to, LinkedList<PLInfo> path) {
        this.from = from;
        this.to = to;
        this.path.addAll(path);
    }

    public void onTick(World world, PLElement element) {
        position = Math.max(0, Math.min(position, path.size() - 1));
        final PLInfo plPos = path.get(position);
        if (world.isBlockLoaded(plPos.pos)) {
//            if (!ticked && PLEvent.onPathTickPre(this, world)) {
               tick(world, element, plPos);
//            }
        }
    }

    private void tick(World world, PLElement element, PLInfo plPos) {
        if (pause > 0) {
            pauseNext(world);
        } else {
            if (tick < plPos.keepTick) {
                tickNext(world);
            } else {
                if (to == null) {
                    if (!outputNoTarget(world, plPos, element)) {
                        sendNoTarget(world, element);
                    }
                } else if (atEnd()) {
                    if (plPos.listOut.contains(to)) {
                        output(world, plPos, element);
                    } else {
                        if (!setNewPath(world, element, plPos)) {
                            setNoTarget(world);
                            element.moveTo(world, 0);
                        }
                    }
                } else {
                    send(world, plPos, element);
                }
            }
        }
    }

    private void pauseNext(World world) {
        pause--;
//        PLEvent.onPathTickPause(this, world);
    }

    private void tickNext(World world) {
        // 下一 tick
        totalTick++;
        tick++;
//        PLEvent.onPathTickIncrease(this, world);
    }

    private void output(World world, PLInfo plPos, PLElement element) {
        tick = 0;
        // 输出 返回剩余内容
//        if (PLEvent.onPathTickOutput(this, world, plPos, element)) {
            Object back = plPos.action.output(world, plPos.pos, to, element, false);
            if (element.serializer.isObjectEmpty(back)) {
                element.remove();
            } else {
                backToStart(world, element, back);
            }
//        }
    }

    private boolean outputNoTarget(World world, PLInfo plPos, PLElement element) {
        tick = 0;
        for (BlockPos to : plPos.listOut) {
            // 输出 返回剩余内容
//            if (PLEvent.onPathTickOutput(this, world, plPos, element)) {
                Object backObj = plPos.action.output(world, plPos.pos, to, element, false);
                if (element.serializer.isObjectEmpty(backObj)) {
                    element.remove();
                    return true;
                }
                element.element = backObj;
//            }
        }
        return false;
    }

    private void send(World world, PLInfo plPos, PLElement element) {
        // 传输到下一段
        int nextPosition = position + 1;
//        PLInfo next = path.get(nextPosition);
//        final PLEvent.PLPathTickEvent.Transfer.Send sendEvent = PLEvent.onPathTickSend(this, world, plPos, element, next);
//        if (sendEvent.getResult() != Event.Result.DENY) {
//            if (next != sendEvent.next && path.contains(sendEvent.next)) {
//                nextPosition = path.indexOf(sendEvent.next);
//            }
            Object send = plPos.action.send(world, nextPosition, element, false);
            if (!element.serializer.isObjectEmpty(send)) {
                if (element.serializer.isObjectEqual(element.element, send)) {
                    // 所有的内容都被返回，则回退
                    PLElement back = element.copy(send);
                    back.path.backToStart(world, back, send);
                    back.send(element.tp);
                } else {
                    // 只返回了部分，将剩余部分继续发送，
                    PLElement sub = element.copy(send);
                    sub.send(element.tp);
                    sub.path.setPause(sub);
                }
            }
//        }
    }

    private void sendNoTarget(World world, PLElement element) {
        final PLInfo plPos = path.get(position);
        final PLInfo plPosBefore = path.get(position == 0 ? position : position - 1);
        final PLInfo[] pls = plPos.action.select(world, plPos, element);
        final PLInfo[] plsNoBack = ArrayUtils.removeElement(pls, plPosBefore);
        final PLInfo plPosNext;
        if (plsNoBack.length != 0) {
            plPosNext = plsNoBack[world.rand.nextInt(plsNoBack.length)];
        } else {
            plPosNext = plPos;
        }
        path.clear();
        Collections.addAll(path, plPosBefore, plPos, plPosNext);
        element.moveTo(world, path.size() - 1);
    }

    /**
     * 当遇到异常路段时，重新规划路径
     * @param world 所在世界
     * @param element 传输内容
     * @return 可以正常规划到下一个路径
     */
    public boolean setNewPath(World world, PLElement element, PLInfo info) {
        final Object receive = info.action.receive(world, element, position, false);
        if (!element.serializer.isObjectEqual(element.element, receive)) {
            if (!element.serializer.isObjectEmpty(receive)) {
                // 其余部分
                PLElement sub = element.copy(receive);
                sub.send();
                setNewPath(world, sub, info);
            }
            return addContinuePath(info, world, element);
        }
        return false;
    }

    private boolean addContinuePath(PLInfo back, World world, PLElement element) {
        Map<BlockPos, PLPath> pathMap = back.allValidOutput(world, element, from);
        PLPath path = pathMap.get(to);
        if (path != null) {
//            if (PLEvent.onBackAndContinue(this, world, element, pathMap, path)) {
                while (this.path.size() > position + 1) {
                    this.path.removeLast();
                }
                this.path.addAll(path.path);
                return true;
//            }
        }
        return false;
    }

    public boolean backToValid(World world, PLElement element) {
        position--;
        while (position > 0) {
            PLInfo back = path.get(position);
            this.path.removeLast();
            TileEntity te = world.getTileEntity(back.pos);
            if (te instanceof TilePipeline && world.isBlockLoaded(back.pos)) {
                final PLInfo info = ((TilePipeline) te).getInfo();
                final Object receive = info.action.receive(world, element, position, true);
                if (!element.serializer.isObjectEqual(element.element, receive)) {
                    element.moveTo(world, position);
                    return true;
                }
            } else {
                position--;
            }
        }
        return false;
    }

    public void setNoTarget(World world) {
        to = null;
        final PLInfo plInfo = path.get(position);
        path.clear();
        path.add(plInfo);
    }

    /**
     * 返回到起点
     * @param world 所在世界
     * @param element 传输内容
     * @param back 返回内容
     */
    public void backToStart(World world, PLElement element, Object back) {
        Object before = element.element;
        element.element = back;
        tick = 0;
        BlockPos swap = from;
        from = to;
        to = swap;
        Collections.reverse(path);
        element.moveTo(world, 0);
//        PLEvent.onBackToStart(this, world, element, before);
    }

    public PLPath copy() {
        PLPath p = new PLPath(from, to, path);
        p.position = position;
        p.totalTick = totalTick;
        p.tick = tick;
        p.pause = pause;
        return p;
    }

    public void append(PLInfo next, BlockPos out) {
//        if (PLEvent.onPathAppend(this, next, out)) {
            path.add(next);
            to = out;
//        }
    }

    public void test(LinkedList<PLInfo> subPath, long subTick) {
        if (!subPath.isEmpty()) {
            int indexFirst = path.indexOf(subPath.getFirst());
            int indexLast = path.lastIndexOf(subPath.getLast());
            if (indexFirst >= 0 && indexLast >= indexFirst) {
                List<PLInfo> subPathCheck = path.subList(indexFirst, indexLast + 1);
                long t = subPathCheck.stream().mapToLong(p -> p.keepTick).sum();
                if (t > subTick || (t == subTick && subPathCheck.size() > subPath.size())) {
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

    public void setPause(PLElement element) {
        setPause(path.get(position).action.coldDown(element));
    }

    public void setPause(int pause) {
        this.pause = pause;
    }

    public boolean atFirst() {
        return position == 0;
    }

    public boolean atEnd() {
        return position >= path.size() - 1;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagCompound nbtPath = new NBTTagCompound();
        nbtPath.setTag(BIND_NBT_PATH_FROM, NBTUtil.createPosTag(from));
        if (to != null) {
            nbtPath.setTag(BIND_NBT_PATH_TO, NBTUtil.createPosTag(to));
        }
        nbtPath.setInteger(BIND_NBT_PATH_POS, position);
        nbtPath.setInteger(BIND_NBT_PATH_TICK, tick);
        nbtPath.setLong(BIND_NBT_PATH_TICK_TOTAL, totalTick);
        nbtPath.setInteger(BIND_NBT_PATH_TICK_PAUSE, pause);

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
        if (nbtPath.hasKey(BIND_NBT_PATH_TO)) {
            to = NBTUtil.getPosFromTag(nbtPath.getCompoundTag(BIND_NBT_PATH_TO));
        }
        position = nbtPath.getInteger(BIND_NBT_PATH_POS);
        totalTick = nbtPath.getLong(BIND_NBT_PATH_TICK_TOTAL);
        tick = nbtPath.getInteger(BIND_NBT_PATH_TICK);
        pause = nbtPath.getInteger(BIND_NBT_PATH_TICK_PAUSE);

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
