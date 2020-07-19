package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.ExtractorData;
import com.elementtimes.elementtimes.common.block.lifecycle.ExtractorRecipeLifecycle;
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



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "extractor"))
public class TileExtractor extends BaseTileEntity implements INamedContainerProvider {

    public TileExtractor(TileEntityType<TileExtractor> type) {
        super(type, Config.extractorCapacity.get(), 4);
        getEnergyHandler().setCapacity(Config.extractorCapacity::get);
        getEnergyHandler().setReceive(Config.extractorInput::get);
        getEngine().addLifeCycle(new ExtractorRecipeLifecycle(this));
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return ExtractorData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TileExtractor.class, id, this, ExtractorData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "extractor"))
    public static Container extractor(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.extractor.readGuiBuffer(extraData);
        TileExtractor te = (TileExtractor) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TileExtractor.class, id, te, ExtractorData.INSTANCE, inventory.player);
    }
}

