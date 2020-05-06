package com.elementtimes.tutorial.common.block.stand;

import com.elementtimes.elementcore.api.template.block.interfaces.IDismantleBlock;
import com.elementtimes.tutorial.common.block.BlockAABB;
import com.elementtimes.tutorial.common.block.stand.module.ISupportStandModule;
import com.elementtimes.tutorial.common.tileentity.stand.BaseTileModule;
import com.elementtimes.tutorial.common.tileentity.stand.TileSupportStand;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public abstract class BaseModuleBlock<MODULE extends ISupportStandModule, TE extends BaseTileModule<MODULE>>
        extends BlockAABB implements ITileEntityProvider, IDismantleBlock {

    protected final String key;

    public BaseModuleBlock(String key, AxisAlignedBB aabb) {
        super(aabb);
        this.key = key;
    }

    protected MODULE getModule(IBlockAccess world, BlockPos pos) {
        try {
            return ((TE) world.getTileEntity(pos)).getModule();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            MODULE module = getModule(worldIn, pos);
            if (module != null) {
                return module.onBlockActivated(playerIn, hand, facing, hitX, hitY, hitZ);
            }
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (!worldIn.isRemote) {
            MODULE module = getModule(worldIn, pos);
            if (module != null) {
                module.randomDisplayTick(stateIn, worldIn, pos, rand);
            }
        }
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        MODULE module = getModule(world, pos);
        int lightValue = super.getLightValue(state, world, pos);
        if (module != null) {
            return module.getLight(lightValue);
        }
        return lightValue;
    }

    boolean applyTo(World world, TileSupportStand te, IBlockState state,
                    EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                    float hitX, float hitY, float hitZ) {
        te.addModule(key);
        return true;
    }
}
