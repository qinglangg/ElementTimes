package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.IMachineLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileTESR;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.common.block.AlcoholLamp;
import com.elementtimes.tutorial.interfaces.ISupportStandModule;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.interfaces.ISSMTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

/**
 * 酒精灯
 * @author luqin2007
 */
public class TileAlcoholLamp extends BaseTileEntity {

    private int rainOutFireColdDown = 0;

    public TileAlcoholLamp() {
        super(0, 0, 0, 1, AlcoholLamp.ALCOHOL_AMOUNT, 0, 0);
        getAllLifecycles().clear();
    }

    // 作为 Module
    public TileAlcoholLamp(World world, BlockPos pos) {
        this();
        this.world = world;
        this.pos = pos;
        addLifeCycle(new AlcoholLampLifeCycle());
    }

    @Override
    public ResourceLocation getBackground() {
        return null;
    }

    @Override
    public GuiSize getSize() {
        return null;
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.alcoholLamp.getLocalizedName();
    }

    @Override
    public int getGuiId() {
        return -1;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        rainOutFireColdDown = compound.getInteger(AlcoholLamp.BIND_ALCOHOL_RAIN);
        super.readFromNBT(compound);
    }

    @Override
    public MachineRecipeHandler getRecipes() {
        return new MachineRecipeHandler(0, 0, 0, 0);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger(AlcoholLamp.BIND_ALCOHOL_RAIN, rainOutFireColdDown);
        return super.writeToNBT(compound);
    }

    @Override
    public boolean isFillValid(int slot, FluidStack fluidStack) {
        return fluidStack.getFluid() == ElementtimesFluids.ethanol;
    }

    private class AlcoholLampLifeCycle implements IMachineLifecycle {

        private ITileTESR getSSMRender() {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof ISSMTileEntity && te instanceof ITileTESR) {
                return (ITileTESR) te;
            }
            return null;
        }

        @Override
        public void onTickStart() {
            ITileTESR tesr = getSSMRender();
            if (tesr != null) {
                boolean isFire = ((ISupportStandModule) ElementtimesBlocks.alcoholLamp).isAdded(world, pos)
                        && tesr.getRenderProperties().getBoolean(AlcoholLamp.BIND_ALCOHOL);
                // 雨天无阻挡：几率灭火
                if (rainOutFireColdDown > 0) {
                    rainOutFireColdDown--;
                } else {
                    if (world.isRainingAt(pos) && isFire && world.rand.nextFloat() <= AlcoholLamp.ALCOHOL_RAIN_PROBABILITY) {
                        boolean needOutFire = true;
                        for (int h = pos.getY(); h < world.getActualHeight(); h++) {
                            final BlockPos pos = new BlockPos(TileAlcoholLamp.this.pos.getX(), h, TileAlcoholLamp.this.pos.getZ());
                            final IBlockState state = world.getBlockState(pos);
                            if (state.getBlock().isAir(state, world, pos)) {
                                needOutFire = false;
                            }
                        }
                        if (needOutFire) {
                            tesr.getRenderProperties().setBoolean(AlcoholLamp.BIND_ALCOHOL, false);
                            tesr.markRenderClient(pos);
                        } else {
                            // 10s 内不在检查
                            rainOutFireColdDown = 2000;
                        }
                    }
                }
                // 酒精消耗
                if (isFire) {
                    FluidStack drain = getTanks(SideHandlerType.INPUT)
                            .drainIgnoreCheck(0, AlcoholLamp.ALCOHOL_TICK, false);
                    if (drain == null || drain.amount < AlcoholLamp.ALCOHOL_TICK) {
                        tesr.getRenderProperties().setBoolean(AlcoholLamp.BIND_ALCOHOL, false);
                        tesr.markRenderClient(pos);
                        return;
                    } else {
                        getTanks(SideHandlerType.INPUT).drainIgnoreCheck(0, AlcoholLamp.ALCOHOL_TICK, true);
                    }
                }

                markDirty();
            }
        }

        @Override
        public boolean onCheckStart() {
            ITileTESR tesr = getSSMRender();
            if (tesr == null) {
                return false;
            }
            return tesr.getRenderProperties().getBoolean(AlcoholLamp.BIND_ALCOHOL);
        }

        @Override
        public boolean onLoop() {
            ITileTESR tesr = getSSMRender();
            if (tesr == null) {
                return false;
            }
            return tesr.getRenderProperties().getBoolean(AlcoholLamp.BIND_ALCOHOL);
        }

        @Override
        public boolean onCheckResume() {
            ITileTESR tesr = getSSMRender();
            if (tesr == null) {
                return false;
            }
            return tesr.getRenderProperties().getBoolean(AlcoholLamp.BIND_ALCOHOL);
        }
    }
}
