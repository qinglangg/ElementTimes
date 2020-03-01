package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.init.ElementtimesItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@SuppressWarnings("NullableProblems")
public class CornCrop extends BlockBush implements IGrowable {

	private Set<BlockPos> updatePos = new HashSet<>();

	// 0-7: down; 8-11: up
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 11);

	private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[] {
			new AxisAlignedBB(0, 0, 0, 1, 0.28125, 1),
			new AxisAlignedBB(0, 0, 0, 1,     0.5, 1),
			new AxisAlignedBB(0, 0, 0, 1, 0.65625, 1),
			new AxisAlignedBB(0, 0, 0, 1, 0.84375, 1),
			new AxisAlignedBB(0, 0, 0, 1,       1, 1),
			new AxisAlignedBB(0, 0, 0, 1,       1, 1),
			new AxisAlignedBB(0, 0, 0, 1,       1, 1),
			new AxisAlignedBB(0, 0, 0, 1,       1, 1),
			new AxisAlignedBB(0, 0, 0, 0, 0.53125, 0),
			new AxisAlignedBB(0, 0, 0, 1,   0.875, 1),
			new AxisAlignedBB(0, 0, 0, 1,       1, 1),
			new AxisAlignedBB(0, 0, 0, 1,       1, 1)
	};

	public CornCrop() {
		super(Material.PLANTS);
		this.setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
		this.disableStats();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return CROPS_AABB[state.getValue(AGE)];
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		super.onBlockHarvested(worldIn, pos, state, player);
		if (!worldIn.isRemote) {
			int age = state.getValue(AGE);
			if (age == 7) {
				IBlockState newState = state.withProperty(AGE, 4);
				worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(),
						new ItemStack(ElementtimesItems.corn, 4)));
				worldIn.setBlockState(pos, newState, 2);
			} else if (age == 11) {
				IBlockState newState = state.withProperty(AGE, 8);
				worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(),
						new ItemStack(ElementtimesItems.corn, worldIn.rand.nextInt(2) + 1)));
				worldIn.setBlockState(pos, newState, 2);
			}
		}
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == this;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		if (!worldIn.isRemote && canGrow(worldIn, pos, state, false) && ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(10) == 0)) {
			grow(worldIn, rand, pos, state);
			ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
		}
	}

	@Override
	protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.checkAndDropBlock(worldIn, pos, state);
		if (!worldIn.isRemote && worldIn.getBlockState(pos).getBlock() == this) {
			int age = state.getValue(AGE);
			Block down = worldIn.getBlockState(pos.down()).getBlock();
			if (age <= 7 && down != Blocks.FARMLAND) {
				dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
				if (age < 7) {
					spawnAsEntity(worldIn, pos, new ItemStack(ElementtimesItems.corn, 1));
				} else {
					BlockPos up = pos.up();
					IBlockState stateUp = worldIn.getBlockState(up);
					if (stateUp.getBlock() == this) {
						dropBlockAsItem(worldIn, up, stateUp, 0);
						worldIn.setBlockToAir(up);
					}
				}
			} else if (age > 7 && down != this) {
				dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
			}
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		if (!worldIn.isRemote) {
			if (updatePos.contains(pos)) {
				return;
			}
			int age = state.getValue(AGE);
			if (age >= 8) {
				BlockPos down = pos.down();
				IBlockState stateDown = worldIn.getBlockState(down);
				if (stateDown.getBlock() == this && stateDown.getValue(AGE) >= 4) {
					worldIn.setBlockState(down, stateDown.withProperty(AGE, 3));
				}
			} else if (age > 3) {
				BlockPos up = pos.up();
				IBlockState stateUp = worldIn.getBlockState(up);
				if (stateUp.getBlock() == this) {
					dropBlockAsItem(worldIn, up, stateUp, 0);
					worldIn.setBlockToAir(up);
				}
			}
		}
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		Random rand = world instanceof World ? ((World) world).rand : new Random(System.currentTimeMillis());
		int age = state.getValue(AGE);
		if (age == 7) {
			int add = fortune > 0 ? rand.nextInt(fortune) : 0;
			drops.add(new ItemStack(ElementtimesItems.corn, 4 + add));
		} else if (age == 11) {
			int add = fortune > 0 ? rand.nextInt(fortune) : 0;
			drops.add(new ItemStack(ElementtimesItems.corn, rand.nextInt(2) + 1 + add));
		}
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(AGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(AGE);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, AGE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		int age = state.getValue(AGE);
		if (age < 3) {
			return true;
		} else if (age == 3) {
			BlockPos up = pos.up();
			return worldIn.getBlockState(up).getBlock().isReplaceable(worldIn, up);
		} else if (age < 7) {
			return worldIn.getBlockState(pos.up()).getBlock() == this;
		}
		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return canGrow(worldIn, pos, state, worldIn.isRemote);
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			int age = state.getValue(AGE);
			if (age < 3) {
				worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(AGE, age + 1), 2);
			} else if (age == 3) {
				BlockPos up = pos.up();
				IBlockState upBlockState = worldIn.getBlockState(up);
				Block upBlock = upBlockState.getBlock();
				if (upBlock.isReplaceable(worldIn, pos)) {
					updatePos.add(pos);
					updatePos.add(up);
					worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(AGE, 4), 2);
					worldIn.setBlockState(up, getDefaultState().withProperty(AGE, 8), 2);
				}
			} else if (age < 7) {
				BlockPos up = pos.up();
				IBlockState upBlock = worldIn.getBlockState(up);
				if (upBlock.getBlock() == this) {
					updatePos.add(pos);
					updatePos.add(up);
					worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(AGE, age + 1), 2);
					worldIn.setBlockState(up, worldIn.getBlockState(up).withProperty(AGE, age + 5), 2);
				} else {
					worldIn.setBlockState(pos, state.withProperty(AGE, 3), 2);
				}
			}
			updatePos.clear();
		}
	}
}
