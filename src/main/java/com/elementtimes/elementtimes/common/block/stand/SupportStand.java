package com.elementtimes.elementtimes.common.block.stand;

import com.elementtimes.elementcore.api.block.IDismantleBlock;
import com.elementtimes.elementtimes.common.init.blocks.Chemical;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import com.elementtimes.elementtimes.common.block.stand.te.TileSupportStand;
import com.elementtimes.elementtimes.common.block.stand.module.ISupportStandModule;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;

/**
 * 铁架台

 */
public class SupportStand extends Block implements IDismantleBlock {

    public SupportStand() {
        super(Properties.create(Material.GLASS).tickRandomly());
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileSupportStand s = (TileSupportStand) worldIn.getTileEntity(pos);
            assert s != null;
            ItemStack stack = player.getHeldItem(handIn);
            Block block = Block.getBlockFromItem(stack.getItem());
            if (block instanceof BaseModuleBlock) {
                if (((BaseModuleBlock<?, ?>) block).addToSupportStand(worldIn, s, state, player, handIn, hit)) {
                    return true;
                }
            }
            for (ISupportStandModule module : s.getModules()) {
                if (module != null) {
                    if (module.onBlockActivated(state, worldIn, pos, player, handIn, hit)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean dismantleBlock(World world, BlockPos pos) {
        if (!world.isRemote) {
            TileSupportStand s = (TileSupportStand) world.getTileEntity(pos);
            assert s != null;
            for (String key : new ArrayList<>(s.getModuleKeys())) {
                ItemStack stack = s.removeToItem(key);
                Block.spawnAsEntity(world, pos, stack);
            }
            Block.spawnAsEntity(world, pos, new ItemStack(Chemical.supportStand));
        }
        return false;
    }

    @Override
    public int getLightValue(BlockState state, IEnviromentBlockReader world, BlockPos pos) {
        int light = super.getLightValue(state, world, pos);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileSupportStand) {
            for (ISupportStandModule module : ((TileSupportStand) te).getModules()) {
                light = module.getLight(light);
            }
        }
        return light;
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileSupportStand) {
            for (ISupportStandModule module : ((TileSupportStand) te).getModules()) {
                module.randomTick(state, worldIn, pos, random);
            }
        }
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileSupportStand) {
            for (ISupportStandModule module : ((TileSupportStand) te).getModules()) {
                module.animateTick(stateIn, worldIn, pos, rand);
            }
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return Industry.te(TileSupportStand.class).get();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.makeCuboidShape(0.1D, 0D, 0.1D, 0.9D, 0.88D, 0.9D);
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }
}
