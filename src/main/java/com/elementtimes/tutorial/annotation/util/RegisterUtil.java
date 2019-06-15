package com.elementtimes.tutorial.annotation.util;

import com.elementtimes.tutorial.annotation.annotations.ModBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

import java.util.Map;

/**
 * 处理注册有关的方法
 *
 * @author luqin2007
 */
public class RegisterUtil {

    public static void applyResourceByStateMap(Block block, ModBlock.StateMap map, IStateMapper mapper) {
        Item item = Item.getItemFromBlock(block);
        Map<IBlockState, ModelResourceLocation> locationMap = null;
        //noinspection ConstantConditions
        ModelResourceLocation defLocation = new ModelResourceLocation(block.getRegistryName(), "inventory");
        if (mapper != null) {
            locationMap = mapper.putStateModelLocations(block);
        }
        if (map.metadatas().length == 0) {
            // metadata from DefaultState
            int defMeta = block.getMetaFromState(block.getDefaultState());
            if (defMeta != 0b0000) {
                ModelLoader.setCustomModelResourceLocation(item, defMeta, getLocationFromState(locationMap, defLocation, block.getDefaultState()));
            }
            // metadata from 0b0000
            //noinspection deprecation
            IBlockState stateZero = block.getStateFromMeta(0b0000);
            ModelLoader.setCustomModelResourceLocation(item, defMeta, getLocationFromState(locationMap, defLocation, stateZero));
            return;
        }
        for (int i = 0; i < map.metadatas().length; i++) {
            int meta = map.metadatas()[i];
            if (meta > 0b1111 || meta < 0b0000) {
                continue;
            }
            String location = map.models()[i];
            String value = map.properties()[i];
            if (location == null || location.isEmpty() || value == null || value.isEmpty()) {
                //noinspection deprecation
                ModelLoader.setCustomModelResourceLocation(item, meta, getLocationFromState(locationMap, defLocation, block.getStateFromMeta(meta)));
            } else {
                ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(location, value));
            }
        }
    }

    private static ModelResourceLocation getLocationFromState(Map<IBlockState, ModelResourceLocation> locationMap, ModelResourceLocation defLocation, IBlockState state) {
        if (locationMap == null || !locationMap.containsKey(state)) {
            return defLocation;
        }
        return locationMap.get(state);
    }
}
