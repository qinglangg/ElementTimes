package com.elementtimes.elementtimes.common.block;

import com.elementtimes.elementtimes.common.init.Items;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;



public class CornCrop extends BushBlock implements IGrowable {

	private final Set<BlockPos> updatePos = new HashSet<>();

	// 0-7: down; 8-11: up
	public static final IProperty<Integer> AGE = IntegerProperty.create("age", 0, 11);

	public CornCrop() {
		super(Properties.create(Material.PLANTS).tickRandomly());
		this.setDefaultState(getDefaultState().with(AGE, 0));
	}

	private static final VoxelShape[] CROPS_SHARPS = new VoxelShape[] {
			VoxelShapes.create(new AxisAlignedBB(0, 0, 0, 1, 0.28125, 1)),
			VoxelShapes.create(new AxisAlignedBB(0, 0, 0, 1,     0.5, 1)),
			VoxelShapes.create(new AxisAlignedBB(0, 0, 0, 1, 0.65625, 1)),
			VoxelShapes.create(new AxisAlignedBB(0, 0, 0, 1, 0.84375, 1)),
			VoxelShapes.create(new AxisAlignedBB(0, 0, 0, 1,       1, 1)),
			VoxelShapes.create(new AxisAlignedBB(0, 0, 0, 1,       1, 1)),
			VoxelShapes.create(new AxisAlignedBB(0, 0, 0, 1,       1, 1)),
			VoxelShapes.create(new AxisAlignedBB(0, 0, 0, 1,       1, 1)),
			VoxelShapes.create(new AxisAlignedBB(0, 0, 0, 0, 0.53125, 1)),
			VoxelShapes.create(new AxisAlignedBB(0, 0, 0, 1,   0.875, 1)),
			VoxelShapes.create(new AxisAlignedBB(0, 0, 0, 1,       1, 1)),
			VoxelShapes.create(new AxisAlignedBB(0, 0, 0, 1,       1, 1)) };

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return CROPS_SHARPS[state.get(AGE)];
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		super.onBlockHarvested(worldIn, pos, state, player);
		if (!worldIn.isRemote) {
			int age = state.get(AGE);
			if (age == 7) {
				BlockState newState = state.with(AGE, 4);
				spawnAsEntity(worldIn, pos, new ItemStack(Items.corn, 4));
				worldIn.setBlockState(pos, newState, 2);
			} else if (age == 11) {
				BlockState newState = state.with(AGE, 8);
				spawnAsEntity(worldIn, pos, new ItemStack(Items.corn, worldIn.rand.nextInt(2) + 1));
				worldIn.setBlockState(pos, newState, 2);
			}
		}
	}

	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
		return super.canSustainPlant(state, world, pos, facing, plantable) || state.getBlock() == this;
	}

	@Override
	public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
		super.tick(state, worldIn, pos, random);
		if (!worldIn.isRemote && canGrow(worldIn, pos, state, false) && ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt(10) == 0)) {
			grow(worldIn, random, pos, state);
			ForgeHooks.onCropsGrowPost(worldIn, pos, state);
		}
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
		if (fromPos.equals(pos.up()) && blockIn == this) {
			int age = state.get(AGE);
			if (age >= 4) {
				worldIn.setBlockState(pos, state.with(AGE, 3));
			}
		}
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(AGE);
	}

	@Override
	public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return false;
	}

	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		int age = state.get(AGE);
		if (age < 3) {
			return true;
		} else if (age == 3) {
			BlockPos up = pos.up();
			DirectionalPlaceContext context = new DirectionalPlaceContext((World) worldIn, pos, Direction.UP, ItemStack.EMPTY, Direction.DOWN);
			return worldIn.getBlockState(up).isReplaceable(context);
		} else if (age < 7) {
			return worldIn.getBlockState(pos.up()).getBlock() == this;
		}
		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return canGrow(worldIn, pos, state, worldIn.isRemote);
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, BlockState state) {
		if (!worldIn.isRemote) {
			int age = state.get(AGE);
			if (age < 3) {
				worldIn.setBlockState(pos, worldIn.getBlockState(pos).with(AGE, age + 1), 2);
			} else if (age == 3) {
				BlockPos up = pos.up();
				BlockState upBlockState = worldIn.getBlockState(up);
				Block upBlock = upBlockState.getBlock();
				DirectionalPlaceContext context = new DirectionalPlaceContext(worldIn, pos, Direction.UP, ItemStack.EMPTY, Direction.DOWN);
				if (upBlockState.isReplaceable(context)) {
					updatePos.add(pos);
					updatePos.add(up);
					worldIn.setBlockState(pos, worldIn.getBlockState(pos).with(AGE, 4), 2);
					worldIn.setBlockState(up, getDefaultState().with(AGE, 8), 2);
				}
			} else if (age < 7) {
				BlockPos up = pos.up();
				BlockState upBlock = worldIn.getBlockState(up);
				if (upBlock.getBlock() == this) {
					updatePos.add(pos);
					updatePos.add(up);
					worldIn.setBlockState(pos, worldIn.getBlockState(pos).with(AGE, age + 1), 2);
					worldIn.setBlockState(up, worldIn.getBlockState(up).with(AGE, age + 5), 2);
				} else {
					worldIn.setBlockState(pos, state.with(AGE, 3), 2);
				}
			}
			updatePos.clear();
		}
	}
}
