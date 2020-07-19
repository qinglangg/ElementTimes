package com.elementtimes.elementtimes.common.eletricity.src.block;

import net.minecraft.block.Block;

/**
 * 普通电线
 * TODO clean old codes: 请自行重写电线。因为自定义了 BlockItem 实现，不太想关注其中的细节
 * @author EmptyDremas
 * @version V1.0
 */
abstract public class TransferBlock extends Block {
	public TransferBlock(Properties properties) {
		super(properties);
	}
//
//	public static final AxisAlignedBB B_POINT =
//			new AxisAlignedBB(0.375F, 0.375F, 0.375F, 0.625F, 0.625F, 0.625F);
//	public static final AxisAlignedBB B_EAST =
//			new AxisAlignedBB(0.625F, 0.375F, 0.375F, 1, 0.625F, 0.625F);
//	public static final AxisAlignedBB B_WEST =
//			new AxisAlignedBB(0, 0.375F, 0.375F, 0.375F, 0.625F, 0.625F);
//	public static final AxisAlignedBB B_SOUTH =
//			new AxisAlignedBB(0.375F, 0.375F, 0.625F, 0.625F, 0.625F, 1);
//	public static final AxisAlignedBB B_NORTH =
//			new AxisAlignedBB(0.375F, 0.375F, 0, 0.625F, 0.625F, 0.375F);
//
//	/** 模型标记 */
//	public static final PropertyBool SOUTH = PropertyBool.create("south");
//	public static final PropertyBool NORTH = PropertyBool.create("north");
//	public static final PropertyBool WEST = PropertyBool.create("west");
//	public static final PropertyBool EAST = PropertyBool.create("east");
//	public static final PropertyBool UP = PropertyBool.create("up");
//	public static final PropertyBool DOWN = PropertyBool.create("down");
//
//	public final Item ITEM;
//
//	/**
//	 * 创建一个WireBlock同时自动创建物品对象
//	 *
//	 * @param name 电线名称，协定规定线缆名称以"wire_"开头但是该构造函数不会自动添加"wire_"
//	 */
//	public TransferBlock(String name) {
//		super(Material.CIRCUITS);
//		setSoundType(SoundType.SNOW);
//		setHardness(0.35F);
//		setCreativeTab(ETGroups.Industry);
//		setRegistryName("elementtimes", name);
//		setUnlocalizedName(name);
//		setDefaultState(getDefaultState().withProperty(SOUTH, false)
//				.withProperty(NORTH, false).withProperty(WEST, false).withProperty(EAST, false)
//				.withProperty(DOWN, false).withProperty(UP, false));
//		ITEM = new TransferItem(this, name);
//	}
//
//	@Override
//	public BlockState getActualState(BlockState state, IBlockAccess worldIn, BlockPos pos) {
//		EleSrcCable nbt = (EleSrcCable) worldIn.getTileEntity(pos);
//		state = state.withProperty(UP, nbt.getUp()).withProperty(DOWN, nbt.getDown())
//							.withProperty(EAST, nbt.getEast()).withProperty(WEST, nbt.getWest())
//							.withProperty(NORTH, nbt.getNorth()).withProperty(SOUTH, nbt.getSouth());
//		return state;
//	}
//
//	@Override
//	public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
//		return new AxisAlignedBB(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F);
//	}
//
//	@Override
//	public void addCollisionBoxToList(BlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
//	                                  List<AxisAlignedBB> list, Entity entityIn, boolean isActualState) {
//		if (!isActualState)
//			state = getActualState(state, worldIn, pos);
//
//		addCollisionBoxToList(pos, entityBox, list, B_POINT);
//		if (state.getValue(SOUTH)) addCollisionBoxToList(pos, entityBox, list, B_SOUTH);
//		if (state.getValue(NORTH)) addCollisionBoxToList(pos, entityBox, list, B_NORTH);
//		if (state.getValue(WEST)) addCollisionBoxToList(pos, entityBox, list, B_WEST);
//		if (state.getValue(EAST)) addCollisionBoxToList(pos, entityBox, list, B_EAST);
//	}
//
//	@Override
//	public ItemStack getItem(World worldIn, BlockPos pos, BlockState state) {
//		return new ItemStack(ITEM);
//	}
//
//	@Override
//	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
//		TileEntity fromEntity = worldIn.getTileEntity(fromPos);
//		Block block = fromEntity == null ? worldIn.getBlockState(fromPos).getBlock() : fromEntity.getBlockType();
//		if (block == Blocks.AIR) {
//			EleSrcCable tew = (EleSrcCable) worldIn.getTileEntity(pos);
//			assert tew != null;
//			tew.deleteLink(fromPos);
//		} else if (fromEntity != null) {
//			EleSrcCable tew = (EleSrcCable) worldIn.getTileEntity(pos);
//			assert tew != null;
//			tew.link(fromPos);
//		}
//	}
//
//	//==============================常规的覆盖MC代码==============================//
//
//	@Override
//	public boolean isOpaqueCube(BlockState state) {
//		return false;
//	}
//
//	@Override
//	public boolean isFullCube(BlockState state) {
//		return false;
//	}
//
//	@Override
//	public int getMetaFromState(BlockState state) {
//		return 0;
//	}
//
//	@Override
//	protected BlockStateContainer createBlockState() {
//		return new BlockStateContainer(this, EAST, NORTH, SOUTH, WEST, DOWN, UP);
//	}
//
//	@Override
//	public int quantityDropped(Random random) {
//		return 1;
//	}
//
//	/** 获取凋落物 */
//	@Nonnull
//	@Override
//	public Item getItemDropped(BlockState state, Random rand, int fortune) {
//		return ITEM;
//	}
//
//	/** 渲染方式 */
//	@Override
//	public EnumBlockRenderType getRenderType(BlockState state) {
//		return EnumBlockRenderType.MODEL;
//	}
//
}
