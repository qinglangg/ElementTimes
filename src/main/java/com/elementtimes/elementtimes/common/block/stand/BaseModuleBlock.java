package com.elementtimes.elementtimes.common.block.stand;

import com.elementtimes.elementcore.api.block.IDismantleBlock;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import com.elementtimes.elementtimes.common.block.stand.module.ISupportStandModule;
import com.elementtimes.elementtimes.common.block.stand.te.BaseTileModule;
import com.elementtimes.elementtimes.common.block.stand.te.TileSupportStand;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;



public class BaseModuleBlock<MODULE extends ISupportStandModule, TE extends BaseTileModule<MODULE>> extends Block implements IDismantleBlock {

    protected final String key;
    protected final VoxelShape mShape;
    protected final Supplier<TE> mTe;

    public BaseModuleBlock(String key, VoxelShape shape, Supplier<TE> te) {
        super(Properties.create(Material.GLASS).tickRandomly());
        this.key = key;
        mShape = shape;
        mTe = te;
    }

    public BaseModuleBlock(String key, Class<TE> teClass, double x1, double y1, double z1, double x2, double y2, double z2) {
        this(key, Block.makeCuboidShape(x1, y1, z1, x2, y2, z2), Industry.te(teClass));
    }

    public BaseModuleBlock(String key, Class<TE> teClass) {
        this(key, VoxelShapes.fullCube(), Industry.te(teClass));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return mTe.get();
    }

    @Nonnull
    protected MODULE getModule(IEnviromentBlockReader world, BlockPos pos) {
        return ((TE) world.getTileEntity(pos)).getModule();
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            return getModule(worldIn, pos).onBlockActivated(state, worldIn, pos, player, handIn, hit);
        }
        return false;
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random rand) {
        if (!worldIn.isRemote) {
            getModule(worldIn, pos).randomTick(state, worldIn, pos, rand);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        getModule(worldIn, pos).animateTick(stateIn, worldIn, pos, rand);
    }

    @Override
    public int getLightValue(BlockState state, IEnviromentBlockReader world, BlockPos pos) {
        int lightValue = super.getLightValue(state, world, pos);
        return getModule(world, pos).getLight(lightValue);
    }

    boolean addToSupportStand(World world, TileSupportStand te, BlockState state, PlayerEntity playerIn, Hand hand, BlockRayTraceResult hit) {
        te.addModule(key);
        return true;
    }
}
