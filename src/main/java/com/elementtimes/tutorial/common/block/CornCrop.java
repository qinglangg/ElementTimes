package com.elementtimes.tutorial.common.block;

import java.util.List;
import java.util.Random;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.sun.jna.platform.win32.WinDef.WORD;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CornCrop extends BlockBush implements IGrowable {
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
	private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[] {
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D),//1.0代表一个方块的高度,0.25代表4/1个方块的高度
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D),//前三个参数是第一个点,后三个参数是第二个点
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D),//前面心情慢慢调出最佳效果的碰撞箱,
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 2.0D, 1.0D) };//第8形态，我现在把碰撞箱设置到两个格子高度

	public CornCrop() {
		super(Material.ROCK);
		this.setRegistryName("corn_crop");
		this.setUnlocalizedName("cornCrop");
		this.setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(this.getAgeProperty(), Integer.valueOf(0)));
		this.disableStats();
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return CROPS_AABB[((Integer) state.getValue(this.getAgeProperty())).intValue()];
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {

		if (worldIn.getBlockState(pos.up()).getBlock() == ElementtimesBlocks.Corncropup && !worldIn.isRemote) {
			worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
			int count = 1;
			if (this.getAge(state) >= 7) {
				// 5-7
				count = worldIn.rand.nextInt(2) + 5;
			}
			worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(),
					new ItemStack(ElementtimesItems.Corn, count)));
		}

		super.onBlockHarvested(worldIn, pos, state, player);
	}

	/**
	 * Return true if the block can sustain a Bush
	 */
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == Blocks.FARMLAND;
	}

	protected PropertyInteger getAgeProperty() {
		return AGE;
	}

	public int getMaxAge() {
		return 7;
	}

	protected int getAge(IBlockState state) {
		return ((Integer) state.getValue(this.getAgeProperty())).intValue();
	}

	public IBlockState withAge(int age) {
		return this.getDefaultState().withProperty(this.getAgeProperty(), Integer.valueOf(age));
	}

	public boolean isMaxAge(IBlockState state) {
		return ((Integer) state.getValue(this.getAgeProperty())).intValue() >= this.getMaxAge();
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);

		if (!worldIn.isAreaLoaded(pos, 1))
			return; // Forge: prevent loading unloaded chunks when checking neighbor's light
		if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
			int i = this.getAge(state);

			if (i < this.getMaxAge()) {
				float f = getGrowthChance(this, worldIn, pos);

				if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state,
						rand.nextInt((int) (25.0F / f) + 1) == 0)) {
					growAge(worldIn, pos, i + 1, 2);
					net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state,
							worldIn.getBlockState(pos));
				}
			}
		}
	}

	public void growAge(World world, BlockPos pos, int age, int flags) {
		int upAge = -(4 - age);
		if (upAge >= 0) {
			CornCropUp up = (CornCropUp) ElementtimesBlocks.Corncropup;
			world.setBlockState(pos.up(), up.getAgeBlockState(upAge), 2);
		}
		world.setBlockState(pos, this.withAge(age), flags);
	}

	public void grow(World worldIn, BlockPos pos, IBlockState state) {
		int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
		int j = this.getMaxAge();

		if (i > j) {
			i = j;
		}
		growAge(worldIn, pos, i, 2);
	}

	// �����������
	protected int getBonemealAgeIncrease(World worldIn) {
		return MathHelper.getInt(worldIn.rand, 2, 5);
	}

	protected static float getGrowthChance(Block blockIn, World worldIn, BlockPos pos) {
		float f = 1.0F;
		BlockPos blockpos = pos.down();

		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				float f1 = 0.0F;
				IBlockState iblockstate = worldIn.getBlockState(blockpos.add(i, 0, j));

				if (iblockstate.getBlock().canSustainPlant(iblockstate, worldIn, blockpos.add(i, 0, j),
						net.minecraft.util.EnumFacing.UP, (net.minecraftforge.common.IPlantable) blockIn)) {
					f1 = 1.0F;

					if (iblockstate.getBlock().isFertile(worldIn, blockpos.add(i, 0, j))) {
						f1 = 3.0F;
					}
				}

				if (i != 0 || j != 0) {
					f1 /= 4.0F;
				}

				f += f1;
			}
		}

		BlockPos blockpos1 = pos.north();
		BlockPos blockpos2 = pos.south();
		BlockPos blockpos3 = pos.west();
		BlockPos blockpos4 = pos.east();
		boolean flag = blockIn == worldIn.getBlockState(blockpos3).getBlock()
				|| blockIn == worldIn.getBlockState(blockpos4).getBlock();
		boolean flag1 = blockIn == worldIn.getBlockState(blockpos1).getBlock()
				|| blockIn == worldIn.getBlockState(blockpos2).getBlock();

		if (flag && flag1) {
			f /= 2.0F;
		} else {
			boolean flag2 = blockIn == worldIn.getBlockState(blockpos3.north()).getBlock()
					|| blockIn == worldIn.getBlockState(blockpos4.north()).getBlock()
					|| blockIn == worldIn.getBlockState(blockpos4.south()).getBlock()
					|| blockIn == worldIn.getBlockState(blockpos3.south()).getBlock();

			if (flag2) {
				f /= 2.0F;
			}
		}

		return f;
	}
	/*
	 * @Override public void getDrops(net.minecraft.util.NonNullList<ItemStack>
	 * drops, net.minecraft.world.IBlockAccess world, BlockPos pos, IBlockState
	 * state, int fortune) { super.getDrops(drops, world, pos, state, 0); int age =
	 * getAge(state); Random rand = world instanceof World ? ((World)world).rand :
	 * new Random();
	 * 
	 * if (age >= getMaxAge()) { int k = 3 + fortune;
	 * 
	 * for (int i = 0; i < 3 + fortune; ++i) { if (rand.nextInt(2 * getMaxAge()) <=
	 * age) { drops.add(new ItemStack(this.getSeed(), 1, 0)); } } } }
	 */

	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return !this.isMaxAge(state);
	}

	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		this.grow(worldIn, pos, state);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		return this.withAge(meta);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
		return this.getAge(state);
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { AGE });
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}
}
