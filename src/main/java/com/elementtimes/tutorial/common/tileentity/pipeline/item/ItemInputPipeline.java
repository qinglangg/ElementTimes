package com.elementtimes.tutorial.common.tileentity.pipeline.item;

import com.elementtimes.tutorial.common.pipeline.ItemElement;
import com.elementtimes.tutorial.common.pipeline.test.PLTestNetwork;
import com.elementtimes.tutorial.interfaces.ITilePipelineInput;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

/**
 * 物品输入管道
 * 用于将物品从容器转移到管道网络
 * @author luqin
 */
public class ItemInputPipeline extends ItemIOPipeline implements ITilePipelineInput {

    private int coldDown = 0;
    private int maxColdDown = 20;

    @Override
    public void update() {
        super.update();
        markDirty();
        if (world == null || !isConnectedIO()) {
            return;
        }
        coldDown++;
        if (coldDown == maxColdDown) {
            coldDown = 0;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        coldDown = compound.getInteger("coldDown");
        super.readFromNBT(compound);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("coldDown", coldDown);
        return super.writeToNBT(compound);
    }

    @Override
    public void input() {
        if (world != null && !world.isRemote && coldDown == 0 && isConnectedIO()) {
            EnumFacing ioSide = EnumFacing.VALUES[readByteValue(12, 3)];
            BlockPos target = pos.offset(ioSide);
            TileEntity te = world.getTileEntity(target);
            if (te != null) {
                IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, ioSide.getOpposite());
                if (handler != null) {
                    for (int i = 0; i < handler.getSlots(); i++) {
                        ItemStack stack = handler.extractItem(i, Integer.MAX_VALUE, true);
                        if (!stack.isEmpty()) {
                            ItemElement element = (ItemElement) ItemElement.TYPE.newInstance();
                            element.element = stack;
                            PLTestNetwork.sendMessage(PLTestNetwork.TestElementType.input, element, pos);
                            if (element.send(world, this, target)) {
                                handler.extractItem(i, stack.getCount(), false);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
