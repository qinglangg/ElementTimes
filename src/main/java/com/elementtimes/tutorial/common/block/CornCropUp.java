package com.elementtimes.tutorial.common.block;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CornCropUp 
extends Block{
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
	public CornCropUp() {
		super(Material.ROCK);
		this.setRegistryName("corn_crop_up");
		this.setUnlocalizedName("cornCropUp");
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
		this.setTickRandomly(true);
		this.disableStats();
	}
	
	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	// TODO Auto-generated method stub
    	return AABB;
    }
    public int getMaxAge()
    {
        return 3;
    }
    protected PropertyInteger getAgeProperty()
    {
        return AGE;
    }
    protected int getAge(IBlockState state)
    {
        return ((Integer)state.getValue(this.getAgeProperty())).intValue();
    }

    public IBlockState withAge(int age)
    {
        return this.getDefaultState().withProperty(this.getAgeProperty(), Integer.valueOf(age));
    }

    public boolean isMaxAge(IBlockState state)
    {
        return ((Integer)state.getValue(this.getAgeProperty())).intValue() >= this.getMaxAge();
    }
    
    
    
    
    
    public IBlockState getAgeBlockState(int age) {
    	return this.blockState.getBaseState().withProperty(AGE,age);
    }
    public IBlockState getStateFromMeta(int meta)
    {
        return this.withAge(meta);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return this.getAge(state);
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {AGE});
    }
}
