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
import com.elementtimes.elementtimes.common.block.gui.FuelGeneratorData;
import com.elementtimes.elementtimes.common.block.lifecycle.FuelGeneratorRecipeLifecycle;
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

/**
 * @author lq2007
 */
@ModTileEntity(blocks = @Getter(value = Industry.class, name = "generatorFuel"))
public class TileFuelGenerator extends BaseTileEntity implements INamedContainerProvider {

    public TileFuelGenerator(TileEntityType<TileFuelGenerator> type) {
        super(type, Config.genFuelCapacity.get(), 1);
        getEnergyHandler().setReceive(0);
        getEnergyHandler().setExtract(() -> Config.genFuelOutput.get());
        getEnergyHandler().setCapacity(() -> Config.genFuelCapacity.get());
        getEngine().addLifeCycle(new GeneratorLifecycle<>(this, Strategies.AVERAGE));
        getEngine().addLifeCycle(new FuelGeneratorRecipeLifecycle(this));
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return FuelGeneratorData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TileFuelGenerator.class, id, this, FuelGeneratorData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "generatorFuel"))
    public static Container generatorFuel(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.generatorFuel.readGuiBuffer(extraData);
        TileFuelGenerator te = (TileFuelGenerator) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TileFuelGenerator.class, id, te, FuelGeneratorData.INSTANCE, inventory.player);
    }
}
