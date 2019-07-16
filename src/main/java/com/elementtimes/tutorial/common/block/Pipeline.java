package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.tileentity.TilePipeline;
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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

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

    public static List<String> ALL_TYPES = new LinkedList<>();

    /**
     * 管道连接方向
     */
    public static PropertyBool PL_CONNECTED_UP = PropertyBool.create("connected_up");
    public static PropertyBool PL_CONNECTED_DOWN = PropertyBool.create("connected_down");
    public static PropertyBool PL_CONNECTED_EAST = PropertyBool.create("connected_east");
    public static PropertyBool PL_CONNECTED_WEST = PropertyBool.create("connected_west");
    public static PropertyBool PL_CONNECTED_NORTH = PropertyBool.create("connected_north");
    public static PropertyBool PL_CONNECTED_SOUTH = PropertyBool.create("connected_south");

    public static IProperty<String> PL_TYPE = new PropertyHelper<String>("type", String.class) {
        @Override
        public Collection<String> getAllowedValues() {
            return ALL_TYPES;
        }

        @Override
        public Optional<String> parseValue(String value) {
            return Optional.fromJavaUtil(ALL_TYPES.stream().filter(s -> s.equals(value)).findFirst());
        }

        @Override
        public String getName(String value) {
            return value;
        }
    };

    public static String BIND_NBT_PIPELINE = "_pipeline_";
    public static String BIND_NBT_PIPELINE_TYPE = "_pipeline_type_";
    public static String BIND_NBT_PIPELINE_CONNECTED = "_pipeline_connected_";
    public static String BIND_NBT_PIPELINE_DISCONNECTED = "_pipeline_disconnected_";

    public Pipeline() {
        super(Material.CIRCUITS);
        ALL_TYPES.add("item");
        ALL_TYPES.add("item_in");
        ALL_TYPES.add("item_out");
        ALL_TYPES.add("fluid");
        ALL_TYPES.add("fluid_in");
        ALL_TYPES.add("fluid_out");
        ALL_TYPES.add("energy");
        setDefaultState(getDefaultState()
                .withProperty(PL_TYPE, "item")
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

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        super.getSubBlocks(itemIn, items);
        boolean removed = items.removeIf(is -> Block.getBlockFromItem(is.getItem()) == this);
        // TODO: 增加管道
//        if (removed) {
//            items.add(create(PLInfo.Item, 20, "item.elementtimes.pipeline.item.link.normal"));
//            items.add(create(PLInfo.ItemIn, 20, "item.elementtimes.pipeline.item.input.normal"));
//            items.add(create(PLInfo.ItemOut, 20, "item.elementtimes.pipeline.item.output.normal"));
//            items.add(create(PLInfo.Fluid, 20, "item.elementtimes.pipeline.fluid.link.normal"));
//            items.add(create(PLInfo.FluidIn, 20, "item.elementtimes.pipeline.fluid.input.normal"));
//            items.add(create(PLInfo.FluidOut, 20, "item.elementtimes.pipeline.fluid.output.normal"));
//
//        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (!worldIn.isRemote) {
            TilePipeline tp = (TilePipeline) worldIn.getTileEntity(pos);
            assert tp != null;
            // nbt
            // TODO: 配置管道
//            NBTTagCompound tagCompound = stack.getTagCompound();
//            int tick;
//            if (tagCompound != null && tagCompound.hasKey(NBT_BIND_PIPELINE_TICK)) {
//                tick = tagCompound.getInteger(NBT_BIND_PIPELINE_TICK);
//            } else {
//                tick = 20;
//            }
//            tp.init(tick);
//            PLInfo info = tp.getInfo();
//            for (EnumFacing facing : EnumFacing.values()) {
//                tp.tryConnect(facing);
//            }
//
//            PLNetworkManager.addPipeline(placer, worldIn, info);
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        // TODO: 移除管道
        TilePipeline tp = (TilePipeline) worldIn.getTileEntity(pos);
        super.breakBlock(worldIn, pos, state);
        assert tp != null;
        PLInfo info = tp.getInfo();
//        info.getNetwork().remove(worldIn, info);
    }

    @Nonnull
    @Override
    public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity te = worldIn.getTileEntity(pos);
        IBlockState actualState = super.getActualState(state, worldIn, pos);
        if (te instanceof TilePipeline) {
            return ((TilePipeline) te).bindActualState(actualState);
        }
        return actualState;
    }

    // TODO: 新建管道
//    public static ItemStack create(PLInfo type, int keepTick, String translateKey) {
//        ItemStack itemStack = new ItemStack(ElementtimesBlocks.pipeline, 1, type.toInt());
//        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
//            itemStack.setStackDisplayName(I18n.format(translateKey));
//        } else {
//            itemStack.setStackDisplayName(translateKey);
//        }
//        itemStack.setTagInfo("_pipeline_tick_", new NBTTagInt(keepTick));
//        return itemStack;
//    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
}
