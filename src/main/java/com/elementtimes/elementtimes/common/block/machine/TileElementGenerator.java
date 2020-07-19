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
import com.elementtimes.elementtimes.common.block.gui.ElementGeneratorData;
import com.elementtimes.elementtimes.common.block.lifecycle.ElementGeneratorRecipeLifecycle;
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



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "generatorElement"))
public class TileElementGenerator extends BaseTileEntity implements INamedContainerProvider {

    public TileElementGenerator(TileEntityType<TileElementGenerator> type) {
        super(type, Config.genElemCapacity.get(), 1);
        getEnergyHandler().setReceive(0);
        getEnergyHandler().setExtract(Config.genElemOutput.get());
        getEnergyHandler().setCapacity(Config.genElemCapacity.get());
        getEngine().addLifeCycle(new GeneratorLifecycle<>(this, Strategies.AVERAGE));
        getEngine().addLifeCycle(new ElementGeneratorRecipeLifecycle(this));
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return ElementGeneratorData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TileElementGenerator.class, id, this, ElementGeneratorData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "generatorElement"))
    public static Container generatorElement(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.generatorElement.readGuiBuffer(extraData);
        TileElementGenerator te = (TileElementGenerator) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TileElementGenerator.class, id, te, ElementGeneratorData.INSTANCE, inventory.player);
    }
}
