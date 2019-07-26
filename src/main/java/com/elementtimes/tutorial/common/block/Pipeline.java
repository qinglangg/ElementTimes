package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.tileentity.TilePipeline;
import com.elementtimes.tutorial.other.pipeline.PLInfo;
import com.elementtimes.tutorial.util.BlockUtil;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
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

    public static String TYPE_ITEM = "pipeline_item";
    public static String TYPE_ITEM_IN = "pipeline_item_in";
    public static String TYPE_ITEM_OUT = "pipeline_item_out";
    public static String TYPE_FLUID = "pipeline_fluid";
    public static String TYPE_FLUID_IN = "pipeline_fluid_in";
    public static String TYPE_FLUID_OUT = "pipeline_fluid_out";
    public static String TYPE_ENERGY = "pipeline_energy";

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
            World w = (World) world;
            String format = String.format("neighbor changed: this=%s, neighbor=%s", pos.toString(), neighbor.toString());
            for (EntityPlayer player : w.playerEntities) {
                player.sendMessage(new TextComponentString(format));
            }

            TileEntity te = world.getTileEntity(pos);
            EnumFacing facing = BlockUtil.getPosFacing(pos, neighbor);
            if (te instanceof TilePipeline && facing != null) {
                ((TilePipeline) te).tryConnect(facing);
            }
        }
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
