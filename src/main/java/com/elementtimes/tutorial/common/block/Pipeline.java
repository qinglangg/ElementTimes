package com.elementtimes.tutorial.common.block;

import com.elementtimes.elementcore.api.ECUtils;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.tileentity.TilePipeline;
import com.elementtimes.tutorial.other.pipeline.PLElement;
import com.elementtimes.tutorial.other.pipeline.PLInfo;
import com.google.common.base.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyHelper;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 管道类
 * @author luqin2007
 */
@SuppressWarnings("deprecation")
public class Pipeline extends Block implements ITileEntityProvider {

    public static List<String> PIPELINE_TYPES = new LinkedList<>();

    public static String TYPE_ITEM = "pipeline_item";
    public static String TYPE_ITEM_IN = "pipeline_item_in";
    public static String TYPE_ITEM_OUT = "pipeline_item_out";
    public static String TYPE_FLUID = "pipeline_fluid";
    public static String TYPE_FLUID_IN = "pipeline_fluid_in";
    public static String TYPE_FLUID_OUT = "pipeline_fluid_out";

    static {
        PIPELINE_TYPES.add(TYPE_ITEM);
        PIPELINE_TYPES.add(TYPE_ITEM_IN);
        PIPELINE_TYPES.add(TYPE_ITEM_OUT);
        PIPELINE_TYPES.add(TYPE_FLUID);
        PIPELINE_TYPES.add(TYPE_FLUID_IN);
        PIPELINE_TYPES.add(TYPE_FLUID_OUT);
    }

    /**
     * 管道连接方向
     */
    public static PropertyBool PL_CONNECTED_UP = PropertyBool.create("connected_up");
    public static PropertyBool PL_CONNECTED_DOWN = PropertyBool.create("connected_down");
    public static PropertyBool PL_CONNECTED_EAST = PropertyBool.create("connected_east");
    public static PropertyBool PL_CONNECTED_WEST = PropertyBool.create("connected_west");
    public static PropertyBool PL_CONNECTED_NORTH = PropertyBool.create("connected_north");
    public static PropertyBool PL_CONNECTED_SOUTH = PropertyBool.create("connected_south");

    @SuppressWarnings("WeakerAccess")
    public static IProperty<String> PL_TYPE = new PropertyHelper<String>("type", String.class) {
        @Nonnull
        @Override
        public Collection<String> getAllowedValues() {
            return PIPELINE_TYPES;
        }

        @Nonnull
        @Override
        @SuppressWarnings({"Guava", "ConstantConditions"})
        public Optional<String> parseValue(@Nonnull String value) {
            return Optional.fromJavaUtil(PIPELINE_TYPES.stream().filter(s -> s.equals(value)).findFirst());
        }

        @Nonnull
        @Override
        public String getName(@Nonnull String value) {
            return value;
        }
    };

    private static String BIND_NBT_PIPELINE = "_pipeline_";

    public Pipeline() {
        super(Material.CIRCUITS);
        setDefaultState(getDefaultState()
                .withProperty(PL_TYPE, TYPE_ITEM)
                .withProperty(PL_CONNECTED_UP, false)
                .withProperty(PL_CONNECTED_DOWN, false)
                .withProperty(PL_CONNECTED_EAST, false)
                .withProperty(PL_CONNECTED_WEST, false)
                .withProperty(PL_CONNECTED_NORTH, false)
                .withProperty(PL_CONNECTED_SOUTH, false));
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, PL_TYPE, PL_CONNECTED_UP, PL_CONNECTED_DOWN,
                PL_CONNECTED_EAST, PL_CONNECTED_WEST, PL_CONNECTED_NORTH, PL_CONNECTED_SOUTH);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TilePipeline();
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        super.getSubBlocks(itemIn, items);
        boolean removed = items.removeIf(is -> Block.getBlockFromItem(is.getItem()) == this);
        if (removed) {
            items.add(create(PLInfo.ITEM, "item.elementtimes.pipeline.item.link.normal"));
            items.add(create(PLInfo.ITEM_IN, "item.elementtimes.pipeline.item.input.normal"));
            items.add(create(PLInfo.ITEM_OUT, "item.elementtimes.pipeline.item.output.normal"));
            items.add(create(PLInfo.FLUID, "item.elementtimes.pipeline.fluid.link.normal"));
            items.add(create(PLInfo.FLUID_IN, "item.elementtimes.pipeline.fluid.input.normal"));
            items.add(create(PLInfo.FLUID_OUT, "item.elementtimes.pipeline.fluid.output.normal"));
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (!worldIn.isRemote) {
            PLInfo info = null;
            NBTTagCompound tagCompound = stack.getTagCompound();
            if (tagCompound != null) {
                if (tagCompound.hasKey(BIND_NBT_PIPELINE)) {
                    NBTTagCompound nbt = tagCompound.getCompoundTag(BIND_NBT_PIPELINE);
                    info = PLInfo.fromNBT(nbt);
                }
            }
            if (info == null) {
                info = PLInfo.ITEM;
            }

            TilePipeline tp = (TilePipeline) worldIn.getTileEntity(pos);
            assert tp != null;
            tp.setInfo(info);

            // 切换 te
            tp.setTickable();
        }
    }

    @Nonnull
    @Override
    public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        IBlockState actualState = super.getActualState(state, worldIn, pos);
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TilePipeline) {
            return ((TilePipeline) te).bindActualState(actualState);
        }
        return actualState;
    }

    public static ItemStack create(PLInfo type, String translateKey) {
        ItemStack itemStack = new ItemStack(ElementtimesBlocks.pipeline);
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            itemStack.setStackDisplayName(I18n.format(translateKey));
        } else {
            itemStack.setStackDisplayName(translateKey);
        }
        itemStack.setTagInfo(BIND_NBT_PIPELINE, type.serializeNBT());
        return itemStack;
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(world, pos, neighbor);
        if (world instanceof World && !((World) world).isRemote) {
            TileEntity teNeighbor = world.getTileEntity(neighbor);
            if (world.getBlockState(pos).getBlock() != Blocks.AIR && !(teNeighbor instanceof TilePipeline)) {
                TileEntity te = world.getTileEntity(pos);
                EnumFacing facing = ECUtils.block.getPosFacing(pos, neighbor);
                if (te instanceof TilePipeline) {
                    ((TilePipeline) te).tryConnect(facing);
                }
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            final TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof TilePipeline) {
                ((TilePipeline) te).updateElement();
                final List<PLElement> elements = ((TilePipeline) te).elements;
                for (PLElement element : elements) {
                    element.serializer.drop(worldIn, element, pos);
                }
            }
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
}
