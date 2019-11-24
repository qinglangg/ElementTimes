package com.elementtimes.tutorial.common.block.pipeline;

import com.elementtimes.tutorial.common.tileentity.pipeline.BaseTilePipeline;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

/**
 * 代表一条路线
 * @author luqin2007
 */
public class PLPath {

    public static Map<BlockPos, PLPath> find(World world, BaseElement element, BlockPos beginPipeline, BlockPos blockFrom) {
        Set<BlockPos> blockPos = new HashSet<>();
        Map<BlockPos, PLPath> result = new LinkedHashMap<>();
        findWay(world, element, result, new LinkedList<>(), beginPipeline, blockFrom, blockPos);
        return result;
    }

    private static void findWay(World world, BaseElement element, Map<BlockPos, PLPath> paths,
                                LinkedList<BlockPos> before, BlockPos pos, BlockPos from,
                                Set<BlockPos> blockPos) {
        if (!blockPos.contains(pos)) {
            blockPos.add(pos);
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof BaseTilePipeline) {
                BaseTilePipeline pipeline = (BaseTilePipeline) te;
                for (EnumFacing direction : EnumFacing.values()) {
                    BlockPos offset = pos.offset(direction);
                    if (pipeline.canOutput(offset, element)) {
                        paths.put(offset, new PLPath(from, offset, before, pos));
                    } else if (pipeline.canSend(offset, element)) {
                        LinkedList<BlockPos> next = new LinkedList<>(before);
                        next.add(offset);
                        findWay(world, element, paths, next, offset, from, blockPos);
                    }
                }
            }
        } else {
            LinkedList<BlockPos> l = new LinkedList<>(before);
            long t = l.stream().map(world::getTileEntity).mapToInt(te -> ((BaseTilePipeline) te).getKeepTime(element)).sum();
            paths.values().forEach(p -> p.test(world, element, l, t));
        }
    }

    public BlockPos from, to;
    public LinkedList<BlockPos> path = new LinkedList<>();

    private PLPath(BlockPos from, BlockPos to, LinkedList<BlockPos> before, BlockPos next) {
        this.from = from;
        this.to = to;
        path.addAll(before);
        path.add(next);
    }

    private PLPath(BlockPos from, BlockPos to, LinkedList<BlockPos> path) {
        this.from = from;
        this.to = to;
        this.path.addAll(path);
    }

    public PLPath copy() {
        return new PLPath(from, to, path);
    }

    private void test(World world, BaseElement element, LinkedList<BlockPos> subPath, long subTick) {
        if (!subPath.isEmpty()) {
            int indexFirst = path.indexOf(subPath.getFirst());
            int indexLast = path.lastIndexOf(subPath.getLast());
            if (indexFirst >= 0 && indexLast >= indexFirst) {
                List<BlockPos> subPathCheck = path.subList(indexFirst, indexLast + 1);
                long t = subPathCheck.stream().map(world::getTileEntity).mapToLong(te -> ((BaseTilePipeline) te).getKeepTime(element)).sum();
                if (t > subTick || (t == subTick && subPathCheck.size() > subPath.size())) {
                    List<BlockPos> first = path.subList(0, indexFirst);
                    List<BlockPos> last = path.subList(indexLast + 1, path.size());
                    LinkedList<BlockPos> paths = new LinkedList<>();
                    paths.addAll(first);
                    paths.addAll(subPath);
                    paths.addAll(last);
                    this.path = paths;
                }
            }
        }
    }

    public boolean isEmpty() {
        return path.isEmpty();
    }
}
