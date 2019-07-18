package com.elementtimes.tutorial.other.pipeline;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
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
    private static final String BIND_NBT_PATH_POS = "_nbt_pipeline_path_to_";
    private static final String BIND_NBT_PATH_IN = "_nbt_pipeline_path_from_ori_";
    private static final String BIND_NBT_PATH_OUT = "_nbt_pipeline_path_to_ori_";
    private static final String BIND_NBT_PATH_PL_POS = "_nbt_pipeline_path_pos_";
    private static final String BIND_NBT_PATH_TICK = "_nbt_pipeline_path_tick_";
    private static final String BIND_NBT_PATH_TICK_TOTAL = "_nbt_pipeline_path_tick_total_";
    private static final String BIND_NBT_PATH_POS_STACK = "_nbt_pipeline_path_pos_stack_";

    public BlockPos from, to, pos;
    public PLInfo plIn, plOut, plPos;
    public long totalTick;
    public int tick;
    public LinkedList<PLInfo> path;
    private boolean tickAdd;

    public PLPath(BlockPos from, PLInfo in, BlockPos to, PLInfo out) {
        this.from = from;
        pos = from;
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
        this.pos = from;
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
        if (!tickAdd) {
            totalTick++;
            if (tick < plPos.keepTick) {
                tick++;
            } else {
                tick = 0;
                PLElement retElement = null;
                if (plPos.equals(plOut)) {
                    // 输出 返回剩余内容
                    retElement = plPos.action.output(world, pos, element);
                    BlockPos swap = from;
                    from = to;
                    to = swap;
                } else {
                    PLInfo back = null;
                    int i = path.indexOf(plPos);
                    if (i > 0) {
                        if (plPos.action.isValid(world, plPos, element)) {
                            back = plPos;
                            int nextIndex = i + 1;
                            if (nextIndex < path.size()) {
                                PLInfo next = path.get(i + 1);
                                if (plPos.action.canSend(world, next, element)) {
                                    moveTo(next);
                                    retElement = null;
                                } else {
                                    // 不可向前传输 返回
                                    retElement = element;
                                }
                            } else {
                                // 前方无路 返回
                                retElement = element;
                            }
                        } else {
                            // 当前方块被破坏 返回
                            while (i > 0) {
                                i--;
                                back = path.get(i);
                                if (back.action.isValid(world, back, element)) {
                                    break;
                                }
                                back = null;
                            }
                            retElement = element;
                        }
                    } else {
                        back = null;
                    }
                    if (back == null) {
                        element.remove();
                    } else {
                        moveTo(back);
                    }
                }

                if (retElement != null) {
                    Map<BlockPos, PLPath> pathMap = plPos.allValidOutput(world, from, element);
                    PLPath p = pathMap.get(to);
                    if (p == null) {
                        p = pathMap.get(from);
                        BlockPos swap = from;
                        from = to;
                        to = swap;
                        tick = 0;
                        if (p == null) {
                            Optional<PLPath> first = pathMap.values().stream().findFirst();
                            p = first.orElseGet(() -> new PLPath(from, plPos, to, plPos));
                        }
                    }
                    element.remove();
                    PLElement newElement = new PLElement();
                    newElement.path = p;
                    newElement.element = element.element;
                    newElement.serializer = element.serializer;
                    newElement.serializerClass = element.serializerClass;
                    newElement.send();
                }
            }
            tickAdd = true;
        }
    }

    public void tickEnd() {
        tickAdd = false;
    }

    public PLPath copy() {
        PLPath p = new PLPath(from, plIn, to, plOut);
        p.pos = new BlockPos(pos);
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

    public void moveTo(PLInfo pos) {
        this.plPos = pos;
        this.pos = pos.pos;
        this.tick = 0;
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
        nbtPath.setTag(BIND_NBT_PATH_POS, NBTUtil.createPosTag(pos));
        nbtPath.setTag(BIND_NBT_PATH_IN, plIn.serializeNBT());
        nbtPath.setTag(BIND_NBT_PATH_OUT, plOut.serializeNBT());
        nbtPath.setTag(BIND_NBT_PATH_PL_POS, plPos.serializeNBT());
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
        pos = NBTUtil.getPosFromTag(nbtPath.getCompoundTag(BIND_NBT_PATH_POS));
        plIn = PLInfo.fromNBT(nbtPath.getCompoundTag(BIND_NBT_PATH_IN));
        plOut = PLInfo.fromNBT(nbtPath.getCompoundTag(BIND_NBT_PATH_OUT));
        plPos = PLInfo.fromNBT(nbtPath.getCompoundTag(BIND_NBT_PATH_PL_POS));
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
