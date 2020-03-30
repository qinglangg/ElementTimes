package com.elementtimes.tutorial.other;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class EmptyMachineState implements IStateMapper {

    private static final ModelResourceLocation EMPTY = new ModelResourceLocation("elementtimes:emptymachine", "inventory");

    @Override
    public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn) {
        return Collections.singletonMap(blockIn.getDefaultState(), EMPTY);
    }
}
