package com.elementtimes.tutorial.other.pipeline.interfaces;

import com.elementtimes.elementcore.api.utils.FluidUtils;
import com.elementtimes.tutorial.other.pipeline.PLElement;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public interface Serializer {
    Map<String, Serializer> SERIALIZERS = new HashMap<>();

    /**
     * 转化器名称
     * @return 名称
     */
    String name();

    /**
     * 将传输内容转化为 NBT 数据
     * @param object 传输数据
     * @return NBT 数据
     */
    @Nonnull
    NBTBase serialize(@Nonnull Object object);

    /**
     * 将 NBT 数据转化为传输内容
     * @param nbt NBT 数据
     * @return 传输数据
     */
    @Nonnull
    Object deserialize(@Nonnull NBTBase nbt);

    /**
     * 检查传输内容是否为空
     * @param object 传输内容
     * @return 是否为空
     */
    boolean isObjectEmpty(Object object);

    /**
     * 检查两个元素是否相同
     * @param object1 元素1
     * @param object2 元素2
     * @return 是否相同
     */
    default boolean isObjectEqual(Object object1, Object object2) {
        if (object1 instanceof ItemStack && object2 instanceof ItemStack) {
            return ItemStack.areItemStacksEqual((ItemStack) object1, (ItemStack) object2);
        } else {
            return object1 == object2;
        }
    }


    void drop(World world, PLElement element, BlockPos pos);

    class SItem implements Serializer {
        public static final String NAME = "item";

        public static Serializer instance() {
            Serializer instance = SERIALIZERS.get(NAME);
            if (instance == null) {
                instance = new SItem();
                SERIALIZERS.put(NAME, instance);
            }
            return instance;
        }

        @Override
        public String name() {
            return NAME;
        }

        @Nonnull
        @Override
        public NBTBase serialize(@Nonnull Object object) {
            if (object instanceof ItemStack) {
                return ((ItemStack) object).serializeNBT();
            } else {
                return ItemStack.EMPTY.serializeNBT();
            }
        }

        @Nonnull
        @Override
        public Object deserialize(@Nonnull NBTBase nbt) {
            return new ItemStack((NBTTagCompound) nbt);
        }

        @Override
        public boolean isObjectEmpty(Object object) {
            boolean isEmpty = object == null;
            isEmpty = isEmpty || (object instanceof ItemStack && ((ItemStack) object).isEmpty());
            isEmpty = isEmpty || object == Items.AIR || object == Blocks.AIR;
            return isEmpty;
        }

        @Override
        public void drop(World world, PLElement element, BlockPos pos) {
            ItemStack is;
            if (element.element instanceof ItemStack) {
                is = (ItemStack) element.element;
            } else if (element.element instanceof Item) {
                is = new ItemStack((Item) element.element);
            } else if (element.element instanceof Block) {
                is = new ItemStack((Block) element.element);
            } else {
                return;
            }

            if (world.isBlockLoaded(pos)) {
                Block.spawnAsEntity(world, pos, is);
            }
        }
    }

    class SFluid implements Serializer {
        public static final String NAME = "fluid";

        public static Serializer instance() {
            Serializer instance = SERIALIZERS.get(NAME);
            if (instance == null) {
                instance = new SFluid();
                SERIALIZERS.put(NAME, instance);
            }
            return instance;
        }

        @Override
        public String name() {
            return NAME;
        }

        @Nonnull
        @Override
        public NBTBase serialize(@Nonnull Object object) {
            NBTTagCompound nbt = new NBTTagCompound();
            if (object instanceof FluidStack) {
                return ((FluidStack) object).writeToNBT(nbt);
            } else {
                return FluidUtils.EMPTY.writeToNBT(nbt);
            }
        }

        @Nonnull
        @Override
        public Object deserialize(@Nonnull NBTBase nbt) {
            FluidStack stack = FluidStack.loadFluidStackFromNBT((NBTTagCompound) nbt);
            if (stack == null) {
                stack = FluidUtils.EMPTY;
            }
            return stack;
        }

        @Override
        public boolean isObjectEmpty(Object object) {
            return object == null
                    || object == FluidUtils.EMPTY
                    || ((object instanceof FluidStack) && ((FluidStack) object).amount == 0);
        }

        @Override
        public void drop(World world, PLElement element, BlockPos pos) {
            Block block;
            int count;
            if (element.element instanceof FluidStack) {
                FluidStack fluidStack = (FluidStack) element.element;
                block = fluidStack.getFluid().getBlock();
                count = fluidStack.amount / Fluid.BUCKET_VOLUME;
            } else if (element.element instanceof Fluid) {
                block = ((Fluid) element.element).getBlock();
                count = 1;
            } else {
                return;
            }

            int r = 0;
            int i = 0;
            while (count > 0) {
                BlockPos p;
                if (i <= r * 2) {
                    p = new BlockPos(pos.getX() + r, pos.getY(), pos.getZ() + i - r);
                    i++;
                } else if (i <= r * 4) {
                    p = new BlockPos(pos.getX() + 3 * r - i, pos.getY(), pos.getZ() - r);
                    i++;
                } else if (i <= r * 6) {
                    p = new BlockPos(pos.getX() - r, pos.getY(), pos.getZ() + i - 5 * r);
                    i++;
                } else if (i < r * 8) {
                    p = new BlockPos(pos.getX() + i - 7 * r, pos.getY(), pos.getZ() + r);
                    i++;
                } else {
                    r++;
                    i = 0;
                    continue;
                }

                boolean set = false;
                for (int j = 0; j <= 4; j++) {
                    final BlockPos posSet = p.offset(EnumFacing.UP, j);
                    final IBlockState state = world.getBlockState(posSet);
                    if (state.getBlock() == Blocks.AIR || state.getMaterial().isReplaceable()) {
                        world.setBlockState(posSet, block.getDefaultState());
                        set = true;
                        break;
                    }
                }
                if (!set) {
                    for (int j = 1; j <= 4; j++) {
                        final BlockPos posSet = p.offset(EnumFacing.DOWN, j);
                        final IBlockState state = world.getBlockState(posSet);
                        if (state.getBlock() == Blocks.AIR || state.getMaterial().isReplaceable()) {
                            world.setBlockState(posSet, block.getDefaultState());
                            set = true;
                            break;
                        }
                    }
                }
                if (set) {
                    count--;
                }
            }
        }
    }
}
