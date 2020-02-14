package com.elementtimes.tutorial.common.block.pipeline;

import com.elementtimes.tutorial.common.pipeline.IPipelineOutput;
import com.elementtimes.tutorial.common.pipeline.ITilePipeline;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class PipelineIO extends Pipeline {

    public static PropertyEnum<SideType>[] CONNECTED_PROPERTIES = new PropertyEnum[] {
            PropertyEnum.create("connected_down", SideType.class),
            PropertyEnum.create("connected_up", SideType.class),
            PropertyEnum.create("connected_north", SideType.class),
            PropertyEnum.create("connected_south", SideType.class),
            PropertyEnum.create("connected_west", SideType.class),
            PropertyEnum.create("connected_east", SideType.class)
    };

    public PipelineIO(Supplier<? extends ITilePipeline> te) {
        super(te);
    }

    @Override
    public Comparable getDefaultPropertyValue(IProperty property) {
        return SideType.NONE;
    }

    @Override
    public IProperty[] getProperties() {
        return CONNECTED_PROPERTIES;
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        AxisAlignedBB aabb = super.getBoundingBox(state, source, pos);
        TileEntity te = source.getTileEntity(pos);
        if (te instanceof IPipelineOutput && ((IPipelineOutput) te).isConnectedIO()) {
            if (((IPipelineOutput) te).isConnectedIO(pos.offset(EnumFacing.UP), EnumFacing.UP)) {
                aabb = aabb.union(AABB_PIPELINE_UP);
            }
            if (((IPipelineOutput) te).isConnectedIO(pos.offset(EnumFacing.DOWN), EnumFacing.DOWN)) {
                aabb = aabb.union(AABB_PIPELINE_DOWN);
            }
            if (((IPipelineOutput) te).isConnectedIO(pos.offset(EnumFacing.EAST), EnumFacing.EAST)) {
                aabb = aabb.union(AABB_PIPELINE_EAST);
            }
            if (((IPipelineOutput) te).isConnectedIO(pos.offset(EnumFacing.WEST), EnumFacing.WEST)) {
                aabb = aabb.union(AABB_PIPELINE_WEST);
            }
            if (((IPipelineOutput) te).isConnectedIO(pos.offset(EnumFacing.NORTH), EnumFacing.NORTH)) {
                aabb = aabb.union(AABB_PIPELINE_NORTH);
            }
            if (((IPipelineOutput) te).isConnectedIO(pos.offset(EnumFacing.SOUTH), EnumFacing.SOUTH)) {
                aabb = aabb.union(AABB_PIPELINE_SOUTH);
            }
        }
        return aabb;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB entityBox, @Nonnull List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof IPipelineOutput && ((IPipelineOutput) te).isConnectedIO()) {
            if (((IPipelineOutput) te).isConnectedIO(pos.offset(EnumFacing.UP), EnumFacing.UP)) {
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_PIPELINE_UP);
            }
            if (((IPipelineOutput) te).isConnectedIO(pos.offset(EnumFacing.DOWN), EnumFacing.DOWN)) {
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_PIPELINE_DOWN);
            }
            if (((IPipelineOutput) te).isConnectedIO(pos.offset(EnumFacing.EAST), EnumFacing.EAST)) {
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_PIPELINE_EAST);
            }
            if (((IPipelineOutput) te).isConnectedIO(pos.offset(EnumFacing.WEST), EnumFacing.WEST)) {
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_PIPELINE_WEST);
            }
            if (((IPipelineOutput) te).isConnectedIO(pos.offset(EnumFacing.NORTH), EnumFacing.NORTH)) {
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_PIPELINE_NORTH);
            }
            if (((IPipelineOutput) te).isConnectedIO(pos.offset(EnumFacing.SOUTH), EnumFacing.SOUTH)) {
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_PIPELINE_SOUTH);
            }
        }
    }

    public enum SideType implements IStringSerializable {
        /**
         * 普通管道连接
         */
        NORMAL("true"),
        /**
         * 无连接
         */
        NONE("false"),
        /**
         * 输入输出端口
         */
        IO("io");

        private final String name;

        SideType(String name) {
            this.name = name;
        }

        @Nonnull
        @Override
        public String getName() {
            return name;
        }
    }
}
