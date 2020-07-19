package com.elementtimes.elementtimes.client.block;

import com.elementtimes.elementcore.api.gui.BaseContainer;
import com.elementtimes.elementcore.api.gui.BaseScreen;
import com.elementtimes.elementtimes.common.block.machine.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;



@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unchecked")
public class Screens {

    public static Screen generatorElement(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileElementGenerator>) container);
    }
    
    public static Screen generatorFluid(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileFluidGenerator>) container);
    }
    
    public static Screen generatorFuel(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileFuelGenerator>) container);
    }
    
    public static Screen generatorSun(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileSunGenerator>) container);
    }

    public static Screen centrifuge(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileCentrifuge>) container);
    }
    
    public static Screen coagulator(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileCoagulator>) container);
    }
    
    public static Screen compressor(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileCompressor>) container);
    }
    
    public static Screen condenser(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileCondenser>) container);
    }
    
    public static Screen electrolyticCell(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileElectrolyticCell>) container);
    }
    
    public static Screen extractor(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileExtractor>) container);
    }
    
    public static Screen fermenter(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileFermenter>) container);
    }
    
    public static Screen fluidHeater(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileFluidHeater>) container);
    }
    
    public static Screen fluidReactor(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileFluidReactor>) container);
    }
    
    public static Screen forming(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileForming>) container);
    }
    
    public static Screen furnace(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileForming>) container);
    }
    
    public static Screen itemReducer(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileItemReducer>) container);
    }
    
    public static Screen pulverizer(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TilePulverizer>) container);
    }
    
    public static Screen pumpAir(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TilePumpAir>) container);
    }
    
    public static Screen pumpFluid(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TilePumpFluid>) container);
    }
    
    public static Screen rebuild(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileRebuild>) container);
    }
    
    public static Screen solidCentrifuge(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileSolidCentrifuge>) container);
    }
    
    public static Screen solidFluidReactor(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileSolidFluidReactor>) container);
    }
    
    public static Screen solidMelter(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileSolidMelter>) container);
    }
    
    public static Screen solidReactor(Container container, PlayerInventory inventory, ITextComponent name) {
        return new BaseScreen<>((BaseContainer<TileSolidReactor>) container);
    }
}
