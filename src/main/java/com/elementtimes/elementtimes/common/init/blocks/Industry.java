package com.elementtimes.elementtimes.common.init.blocks;

import com.elementtimes.elementcore.api.annotation.ModElement;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.ItemProps;
import com.elementtimes.elementcore.api.block.BlockClosable;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.block.Compressor;
import com.elementtimes.elementtimes.common.block.pipeline.Pipeline;
import com.elementtimes.elementtimes.common.init.Groups;
import com.elementtimes.elementtimes.common.block.machine.*;
import com.elementtimes.elementtimes.common.block.pipeline.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ToolType;

import java.util.function.Supplier;



@ModElement(itemProp = @ItemProps(group = @Getter(value = Groups.class, name = "Industry")))
public class Industry {

    public static BlockTileBase generatorElement = new BlockTileBase(machine(), te(TileElementGenerator.class));
    public static BlockClosable generatorFluid = new BlockClosable(machine(), te(TileFluidGenerator.class));
    public static BlockClosable generatorFuel = new BlockClosable(machine(), te(TileFuelGenerator.class));
    public static BlockClosable generatorSun = new BlockClosable(machine(), te(TileSunGenerator.class));

    public static BlockClosable centrifuge = new BlockClosable(machine(), te(TileCentrifuge.class));
    public static BlockClosable coagulator = new BlockClosable(machine(), te(TileCoagulator.class));
    public static Compressor compressor = new Compressor();
    public static BlockClosable condenser = new BlockClosable(machine(), te(TileCondenser.class));
    public static BlockClosable electrolyticCell = new BlockClosable(machine(), te(TileElectrolyticCell.class));
    public static BlockClosable extractor = new BlockClosable(machine(), te(TileExtractor.class));
    public static BlockClosable fermenter = new BlockClosable(machine(), te(TileFermenter.class));
    public static BlockClosable fluidHeater = new BlockClosable(machine(), te(TileFluidHeater.class));
    public static BlockClosable fluidReactor = new BlockClosable(machine(), te(TileFluidReactor.class));
    public static BlockClosable forming = new BlockClosable(machine(), te(TileForming.class));
    public static BlockClosable furnace = new BlockClosable(machine(), te(TileFurnace.class));
    public static BlockClosable itemReducer = new BlockClosable(machine(), te(TileItemReducer.class));
    public static BlockTileBase pulverizer = new BlockTileBase(machine(), te(TilePulverizer.class));
    public static BlockClosable pumpAir = new BlockClosable(machine(), te(TilePumpAir.class));
    public static BlockClosable pumpFluid = new BlockClosable(machine(), te(TilePumpFluid.class));
    public static BlockClosable rebuild = new BlockClosable(machine(), te(TileRebuild.class));
    public static BlockClosable solarDecomposer = new BlockClosable(machine(), te(TileSolarDecomposer.class));
    public static BlockClosable solidCentrifuge = new BlockClosable(machine(), te(TileSolidCentrifuge.class));
    public static BlockClosable solidFluidReactor = new BlockClosable(machine(), te(TileSolidFluidReactor.class));
    public static BlockClosable solidMelter = new BlockClosable(machine(), te(TileSolidMelter.class));
    public static BlockClosable solidReactor = new BlockClosable(machine(), te(TileSolidReactor.class));

    public static BlockTileBase energyBox = new BlockTileBase(machine(), te(TileEnergyBox.class));
    public static BlockTileBase fluidTank = new BlockTileBase(machine(), te(TileFluidTank.class));

    @ModElement.Skip
    public static Pipeline pipelineItemInput = new Pipeline(TileItemInput.class);
    @ModElement.Skip
    public static Pipeline pipelineItemOutput = new Pipeline(TileItemOutput.class);
    @ModElement.Skip
    public static Pipeline pipelineItemConnect = new Pipeline(TileItemConnect.class);
    @ModElement.Skip
    public static Pipeline pipelineFluidInput = new Pipeline(TileFluidInput.class);
    @ModElement.Skip
    public static Pipeline pipelineFluidOutput = new Pipeline(TileFluidOutput.class);
    @ModElement.Skip
    public static Pipeline pipelineFluidConnect = new Pipeline(TileFluidConnect.class);
    
    public static Block blockMultiSilverCopper = new Block(multi());
    public static Block blockMultiWoodStone = new Block(multi());
    public static Block blockMultiGoldPlatinum = new Block(multi());
    public static Block blockMultiCarbonSteel = new Block(multi());
    public static Block blockMultiTinLead = new Block(multi());
    public static Block blockMultiIronQuartz = new Block(multi());
    public static Block blockMultiObsidianDiamond = new Block(multi());

    public static BlockTileBase creativeEnergy = new BlockTileBase(machine(), te(TileCreativeEnergyBox.class));
    public static BlockTileBase creativeTank = new BlockTileBase(machine(), te(TileCreativeFluidTank.class));

    public static Block.Properties machine() {
        return Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(2);
    }
    public static Block.Properties multi() {
        return Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(2);
    }
    public static <T extends TileEntity> Supplier<T> te(Class<T> teClass) {
        return () -> {
            TileEntityType<?> type = ElementTimes.CONTAINER.elements().generatedTileEntityTypes.get(teClass);
            if (type == null) {
                return null;
            }
            return (T) type.create();
        };
    }
}
