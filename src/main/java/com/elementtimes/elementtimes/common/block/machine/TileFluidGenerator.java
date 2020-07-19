package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.lifecycle.GeneratorLifecycle;
import com.elementtimes.elementcore.api.lifecycle.Strategies;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.FluidGeneratorData;
import com.elementtimes.elementtimes.common.block.lifecycle.FluidGeneratorBucketLifecycle;
import com.elementtimes.elementtimes.common.block.lifecycle.FluidGeneratorRecipeLifecycle;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import com.elementtimes.elementtimes.config.Config;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "generatorFluid"))
public class TileFluidGenerator extends BaseTileEntity implements INamedContainerProvider {

    public TileFluidGenerator(TileEntityType<TileFluidGenerator> type) {
        super(type, Config.genFluidCapacity.get(), 2, 1, Config.genFluidCapacity.get());
        getEnergyHandler().setCapacity(Config.genFluidCapacity::get);
        getEnergyHandler().setReceive(0);
        getEnergyHandler().setExtract(Config.genFluidOutput::get);
        getEngine().addLifeCycle(new GeneratorLifecycle<>(this, Strategies.AVERAGE));
        getEngine().addLifeCycle(new FluidGeneratorBucketLifecycle(this));
        getEngine().addLifeCycle(new FluidGeneratorRecipeLifecycle(this));
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return FluidGeneratorData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TileFluidGenerator.class, id, this, FluidGeneratorData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "generatorFluid"))
    public static Container generatorFluid(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.generatorFluid.readGuiBuffer(extraData);
        TileFluidGenerator te = (TileFluidGenerator) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TileFluidGenerator.class, id, te, FluidGeneratorData.INSTANCE, inventory.player);
    }
}
