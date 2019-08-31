package com.elementtimes.tutorial.common.block;

import com.elementtimes.elementcore.api.ECUtils;
import com.elementtimes.tutorial.common.capability.impl.TankHandler;
import com.elementtimes.tutorial.common.tileentity.TileAlcoholLamp;
import com.elementtimes.tutorial.interfaces.block.IDismantleBlock;
import com.elementtimes.tutorial.other.SideHandlerType;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * 酒精灯
 * TODO:像熔炉一样的光照
 *
 * @author KSGFK create in 2019/6/12
 */
public class AlcoholLamp extends BlockAABB implements ITileEntityProvider, IDismantleBlock {
    public static final IProperty<Boolean> IS_BURNING = PropertyBool.create("burning");

    public AlcoholLamp() {
        super(new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.5D, 0.75D));
        this.setDefaultState(this.getDefaultState().withProperty(IS_BURNING, Boolean.FALSE));
        this.setLightLevel(0);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, IS_BURNING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(IS_BURNING) ? 0b0100 : 0b0000;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        boolean burning = (meta & 0b0100) == 0b0100;
        return super.getStateFromMeta(meta).withProperty(IS_BURNING, burning);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        TileEntity te = worldIn.getTileEntity(pos);
        NBTTagCompound fireNbt = stack.getOrCreateSubCompound("fire");
        boolean fire = false;
        if (fireNbt.hasKey("fire")) {
            fire = fireNbt.getBoolean("fire");
        }
        if (fire) {
            setLightLevel(1);
            ECUtils.block.setBlockState(worldIn, pos, state.withProperty(IS_BURNING, true), te);
        } else {
            setLightLevel(0);
            ECUtils.block.setBlockState(worldIn, pos, state.withProperty(IS_BURNING, false), te);
        }
        NBTTagCompound fluidNbt = stack.getOrCreateSubCompound("fluid");
        if (!fluidNbt.hasNoTags()) {
            FluidStack fluid = FluidStack.loadFluidStackFromNBT(fluidNbt);
            if (te instanceof TileAlcoholLamp) {
                TankHandler tanks = ((TileAlcoholLamp) te).getTanks(SideHandlerType.IN_OUT);
                tanks.fill(0, fluid, true);
            }
        }
    }

    @Override
    public ItemStack dismantleBlock(World world, BlockPos pos) {
        ItemStack itemStack = new ItemStack(this);
        itemStack.getOrCreateSubCompound("fire").setBoolean("fire", world.getBlockState(pos).getValue(IS_BURNING));
        TileAlcoholLamp te = (TileAlcoholLamp) world.getTileEntity(pos);
        TankHandler tanks = te.getTanks(SideHandlerType.IN_OUT);
        FluidStack fluidStack = tanks.drainIgnoreCheck(0, Integer.MAX_VALUE, false);
        NBTTagCompound fluidNbt = itemStack.getOrCreateSubCompound("fluid");
        fluidStack.writeToNBT(fluidNbt);
        return itemStack;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            ItemStack held = playerIn.getHeldItem(hand);
            Item heldItem = held.getItem();

            TileAlcoholLamp tileEntity = (TileAlcoholLamp) worldIn.getTileEntity(pos);
            if (heldItem == Items.FLINT_AND_STEEL) {
                setLightLevel(1);
                ECUtils.block.setBlockState(worldIn, pos, state.withProperty(IS_BURNING, Boolean.TRUE), tileEntity);
            } else {
                if (playerIn.isSneaking()) {
                    playerIn.sendMessage(new TextComponentString(tileEntity.getTanks(SideHandlerType.IN_OUT).drainIgnoreCheck(0, Integer.MAX_VALUE, false).amount + ""));
                } else {
                    setLightLevel(0);
                    ECUtils.block.setBlockState(worldIn, pos, state.withProperty(IS_BURNING, Boolean.FALSE), tileEntity);
                }
            }
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (getMetaFromState(stateIn) != 0b0100) {
            return;
        }

        double d0 = pos.getX();
        double d1 = pos.getY();
        double d2 = pos.getZ();
        worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.5D, d1 + 0.5D, d2 + 0.5D, 0.0D, 0D, 0.0D);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileAlcoholLamp();
    }
}
