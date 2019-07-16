package com.elementtimes.tutorial.other.pipeline;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 代表一条路线
 * @author luqin2007
 */
@SuppressWarnings({"unused"})
public class PLPath implements INBTSerializable<NBTTagCompound> {

    private static final String BIND_NBT_PATH = "_nbt_pipeline_path_";
    private static final String BIND_NBT_PATH_FACE_FROM = "_nbt_pipeline_path_face_from";
    private static final String BIND_NBT_PATH_FACE_TO = "_nbt_pipeline_path_face_to";
    private static final String BIND_NBT_PATH_FROM = "_nbt_pipeline_path_from_";
    private static final String BIND_NBT_PATH_TO = "_nbt_pipeline_path_to_";
    private static final String BIND_NBT_PATH_POS = "_nbt_pipeline_path_to_";
    private static final String BIND_NBT_PATH_IN = "_nbt_pipeline_path_from_ori_";
    private static final String BIND_NBT_PATH_OUT = "_nbt_pipeline_path_to_ori_";
    private static final String BIND_NBT_PATH_PL_POS = "_nbt_pipeline_path_pos_";
    private static final String BIND_NBT_PATH_TICK = "_nbt_pipeline_path_tick_";
    private static final String BIND_NBT_PATH_TICK_TOTAL = "_nbt_pipeline_path_tick_total_";
    private static final String BIND_NBT_PATH_SEARCH = "_nbt_pipeline_path_search_";
    private static final String BIND_NBT_PATH_POS_STACK = "_nbt_pipeline_path_pos_stack_";
    private static final String BIND_NBT_PATH_SELECTED = "_nbt_pipeline_path_selected_";

    public EnumFacing faceFrom, faceTo;
    public BlockPos from, to, pos;
    public PLInfo plIn, plOut, plPos;
    public long totalTick;
    public int tick;
    public List<EnumFacing> search;
    public LinkedList<PLInfo> path;
    public LinkedList<PLInfo> selected;
    private boolean tickAdd = false;

    public PLPath(BlockPos from, PLInfo in, BlockPos to, PLInfo out, EnumFacing face) {
        this.from = from;
        pos = from;
        this.to = to;
        plIn = in;
        plPos = in;
        plOut = out;
        totalTick = 0;
        tick = 0;
        search = new ArrayList<>(6);
        path = new LinkedList<>();
        path.push(plIn);
        faceFrom = face;
    }

    public void tickStart(World world, PLNetwork network) {
        if (!tickAdd) {
            totalTick++;
            if (tick < plPos.keepTick) {
                tick++;
            } else {
                // TODO: 此段走完
            }
            tickAdd = true;
        }
    }

    public void tickEnd() {
        tickAdd = false;
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
        nbtPath.setString(BIND_NBT_PATH_FACE_FROM, faceFrom.getName());
        nbtPath.setString(BIND_NBT_PATH_FACE_TO, faceTo.getName());

        NBTTagList list = new NBTTagList();
        for (EnumFacing search : search) {
            list.appendTag(new NBTTagString(search.getName()));
        }
        nbtPath.setTag(BIND_NBT_PATH_SEARCH, list);

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
        faceFrom = EnumFacing.byName(nbtPath.getString(BIND_NBT_PATH_FACE_FROM));
        faceTo = EnumFacing.byName(nbtPath.getString(BIND_NBT_PATH_FACE_TO));

        NBTTagList list = (NBTTagList) nbtPath.getTag(BIND_NBT_PATH_SEARCH);
        search = new ArrayList<>(6);
        for (int i = 0; i < list.tagCount(); i++) {
            search.add(i, EnumFacing.byName(list.getStringTagAt(i)));
        }

        NBTTagList pos = (NBTTagList) nbtPath.getTag(BIND_NBT_PATH_POS_STACK);
        path = new LinkedList<>();
        for (int i = 0; i < pos.tagCount(); i++) {
            NBTTagCompound tag = pos.getCompoundTagAt(i);
            path.add(i, PLInfo.fromNBT(tag));
        }

        NBTTagList selected = (NBTTagList) nbtPath.getTag(BIND_NBT_PATH_SELECTED);
        this.selected = new LinkedList<>();
        for (int i = 0; i < selected.tagCount(); i++) {
            NBTTagCompound tag = selected.getCompoundTagAt(i);
            this.selected.add(i, PLInfo.fromNBT(tag));
        }
    }

    public static PLPath fromNbt(NBTTagCompound nbt) {
        PLPath path = new PLPath(BlockPos.ORIGIN, null, BlockPos.ORIGIN, null, null);
        path.deserializeNBT(nbt);
        return path;
    }
}
