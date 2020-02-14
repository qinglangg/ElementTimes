package com.elementtimes.tutorial.common.block;

import com.elementtimes.elementcore.api.common.ECUtils;
import com.elementtimes.elementcore.api.template.block.interfaces.IDismantleBlock;
import com.elementtimes.elementcore.api.template.capability.fluid.ITankHandler;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileTESR;
import com.elementtimes.tutorial.interfaces.ISupportStandModule;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.common.tileentity.TileAlcoholLamp;
import com.elementtimes.tutorial.common.tileentity.TileSupportStand;
import com.elementtimes.tutorial.interfaces.ISSMTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

import static com.elementtimes.elementcore.api.template.block.Properties.IS_BURNING;

/**
 * 酒精灯
 * @author KSGFK create in 2019/6/12
 */
public class AlcoholLamp extends BlockAABB implements ITileEntityProvider, ISupportStandModule, IDismantleBlock {

    public static final int ALCOHOL_AMOUNT = 32000;
    public static final int ALCOHOL_TICK = 5;
    public static final float ALCOHOL_RAIN_PROBABILITY = 0.1f;
    public static final String BIND_ALCOHOL = "_nbt_alcohol_";
    public static final String BIND_ALCOHOL_RAIN = "_nbt_alcohol_rain_";

    public AlcoholLamp() {
        super(new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.5D, 0.75D));
        this.setDefaultState(this.getDefaultState().withProperty(IS_BURNING, false));
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, IS_BURNING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(IS_BURNING) ? 0b0100 : 0b0000;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        boolean burning = (meta & 0b0100) == 0b0100;
        return super.getStateFromMeta(meta).withProperty(IS_BURNING, burning);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            onActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.getValue(IS_BURNING)) {
            worldIn.spawnParticle(EnumParticleTypes.FLAME,
                    pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                    0.0D, 0D, 0.0D);
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileAlcoholLamp();
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        if (world instanceof World) {
            return isFire((World) world, pos) ? 15 : 0;
        }
        return super.getLightValue(state, world, pos);
    }

    @Nonnull
    @Override
    public ItemStack getModelItem() {
        return new ItemStack(this);
    }

    @Nonnull
    @Override
    public Supplier<?> getActiveObject(World worldIn, BlockPos pos) {
        return () -> new TileAlcoholLamp(worldIn, pos);
    }

    @Override
    public boolean isActive(World worldIn, BlockPos pos) {
        return false;
    }

    @Nonnull
    @Override
    public String getKey() {
        return BIND_ALCOHOL;
    }

    @Nullable
    @Override
    public ITileTESR.RenderObject createRender() {
        return new ITileTESR.RenderObject(getModelItem()).translate(.5, -.13, .5).scale(3, 3, 3);
    }

    @Override
    public boolean onActivated(World worldIn, BlockPos pos, IBlockState state,
                               EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                               float hitX, float hitY, float hitZ) {
        ItemStack held = playerIn.getHeldItem(hand);
        if (held.getItem() == ElementtimesItems.spanner) {
            // 扳手
            ITankHandler ethanolTank = getEthanolTank(worldIn, pos);
            FluidStack fluid = ethanolTank == null ? null : ethanolTank.getFluidFirst();
            String msg = String.format(": %d mb", fluid == null ? 0 : fluid.amount);
            playerIn.sendMessage(
                    new TextComponentTranslation(ElementtimesFluids.ethanol.getUnlocalizedName()).appendText(msg));
            return true;
       } else if (held.getItem() == Items.FLINT_AND_STEEL) {
            // 打火石
            if (!isFire(worldIn, pos)) {
                setFire(worldIn, pos, true);
                held.damageItem(1, playerIn);
                return true;
            }
        } else if (held.getItem() == Items.WATER_BUCKET) {
            // 水桶
            if (isFire(worldIn, pos)) {
                setFire(worldIn, pos, false);
                if (!playerIn.isCreative()) {
                    playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
                }
                return true;
            }
        } else if (held.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            // 酒精
            FluidActionResult far = FluidUtil.tryEmptyContainer(held, getEthanolTank(worldIn, pos), ALCOHOL_AMOUNT, playerIn, true);
            if (far.isSuccess() && !playerIn.isCreative()) {
                playerIn.setHeldItem(hand, far.getResult());
            }
            return true;
        } else if (held.isEmpty()) {
            // 空手
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof TileSupportStand && ((TileSupportStand) te).getGuiId() >= 0) {
                return false;
            }
            if (isFire(worldIn, pos)) {
                setFire(worldIn, pos, false);
                if (worldIn.rand.nextInt(1) == 0) {
                    playerIn.setFire(3);
                }
                return true;
            }
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTickClient(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileSupportStand) {
            boolean isFire = isAdded(worldIn, pos)
                    && ((TileSupportStand) te).getRenderProperties().getBoolean(BIND_ALCOHOL);
            if (isFire) {
                double d0 = pos.getX();
                double d1 = pos.getY();
                double d2 = pos.getZ();
                worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.5D, d1 + 0.4D, d2 + 0.5D, 0.0D, 0D, 0.0D);
            }
        }
    }

    @Nullable
    private ITankHandler getEthanolTank(World world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        TileAlcoholLamp al = null;
        if (te instanceof ISSMTileEntity) {
            Object object = ((ISSMTileEntity) te).getModuleObject(getKey());
            if (object instanceof TileAlcoholLamp) {
                al = (TileAlcoholLamp) object;
            }
        } else if (te instanceof TileAlcoholLamp) {
            al = (TileAlcoholLamp) te;
        }
        if (al != null) {
            return al.getTanks(SideHandlerType.INPUT);
        }
        return null;
    }

    private void setFire(World world, BlockPos pos, boolean fire) {
        if (!world.isRemote) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof TileAlcoholLamp) {
                IBlockState newState = world.getBlockState(pos).withProperty(IS_BURNING, fire);
                ECUtils.block.setBlockState(world, pos, newState, te);
                world.markBlockRangeForRenderUpdate(pos, pos);
            } else if (te instanceof TileSupportStand) {
                NBTTagCompound renderProperties = ((TileSupportStand) te).getRenderProperties();
                renderProperties.setBoolean(BIND_ALCOHOL, fire);
                te.markDirty();
                ((TileSupportStand) te).markRenderClient(pos);
            }
        }
    }

    private boolean isFire(World world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        if (block == this) {
            return world.getBlockState(pos).getValue(IS_BURNING);
        } else if (block == ElementtimesBlocks.supportStand) {
            TileSupportStand tss = (TileSupportStand) world.getTileEntity(pos);
            assert tss != null;
            NBTTagCompound renderProperties = tss.getRenderProperties();
            if (renderProperties.hasKey(BIND_ALCOHOL)) {
                return renderProperties.getBoolean(BIND_ALCOHOL);
            }
        }
        return false;
    }
}