package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.utils.CommonUtils;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.CompressorData;
import com.elementtimes.elementtimes.common.block.lifecycle.CompressorRecipeLifecycle;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import com.elementtimes.elementtimes.config.Config;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.animation.TimeValues;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.model.animation.AnimationStateMachine;
import net.minecraftforge.common.model.animation.CapabilityAnimation;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;



@ModTileEntity.Ter
@ModTileEntity(blocks = @Getter(value = Industry.class, name = "compressor"))
public class TileCompressor extends BaseTileEntity implements INamedContainerProvider {

    private final static String ANIMATION_STATE_PLAY = "play";
    private final static String ANIMATION_STATE_STOP = "stop";

    private AnimationStateMachine mAnimationStateMachine;

    public TileCompressor(TileEntityType<TileCompressor> type) {
        super(type, Config.compressorCapacity.get(), 2);
        getEnergyHandler().setCapacity(Config.compressorCapacity::get);
        getEnergyHandler().setReceive(Config.compressorInput::get);
        getEngine().addLifeCycle(new CompressorRecipeLifecycle(this));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        if (capability == CapabilityAnimation.ANIMATION_CAPABILITY && CommonUtils.isClient()) {
            return LazyOptional.of(() -> (T) getASM());
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    private AnimationStateMachine getASM() {
        if (mAnimationStateMachine == null) {
            mAnimationStateMachine = (AnimationStateMachine) net.minecraftforge.client.model.ModelLoaderRegistry.loadASM(
                    new ResourceLocation(ElementTimes.MODID, "asms/block/compressor.json"),
                    ImmutableMap.of("cycle_length", new TimeValues.ConstValue(5)));
        }
        return mAnimationStateMachine;
    }

    @Override
    public void updateClient() {
        super.updateClient();
        boolean running = getEngine().isWorking();
        String state = getASM().currentState();
        if (running && ANIMATION_STATE_STOP.equals(state)) {
            getASM().transition(ANIMATION_STATE_PLAY);
        } else if (!running && ANIMATION_STATE_PLAY.equals(state)) {
            getASM().transition(ANIMATION_STATE_STOP);
        }
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return CompressorData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TileCompressor.class, id, this, CompressorData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "compressor"))
    public static Container compressor(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.compressor.readGuiBuffer(extraData);
        TileCompressor te = (TileCompressor) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TileCompressor.class, id, te, CompressorData.INSTANCE, inventory.player);
    }
}

