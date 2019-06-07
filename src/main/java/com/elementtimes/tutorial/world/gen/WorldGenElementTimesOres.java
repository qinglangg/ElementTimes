package com.elementtimes.tutorial.world.gen;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * 世界生成
 * @author KSGFK
 */
public class WorldGenElementTimesOres implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ,
						 World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider){
	
		if (world.provider.getDimension() == 0) {
			generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		}
	}

	private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider){
		generateOre(ElementtimesBlocks.copperOre, world, random, chunkX, chunkZ);
		generateOre(ElementtimesBlocks.sulfurOre, world, random, chunkX, chunkZ);
		generateOre(ElementtimesBlocks.calciumFluoride, world, random, chunkX, chunkZ);
		generateOre(ElementtimesBlocks.platinumOre, world, random, chunkX, chunkZ);
	}

	private void generateOre(Block ore, World world, Random random, int chunkX, int chunkZ) {
		generateOre(ore.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 64, random.nextInt(7) + 4, 18);
	}

	private void generateOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances) {
		int deltaY = maxY - minY;

		for (int i = 0; i <chances; i++) {
			BlockPos pos = new BlockPos(x + random.nextInt(16),
									    minY + random.nextInt(deltaY),
									    z + random.nextInt(16));

			WorldGenMinable generator = new WorldGenMinable(ore, size);
			generator.generate(world, random, pos);
		}
	}
}
