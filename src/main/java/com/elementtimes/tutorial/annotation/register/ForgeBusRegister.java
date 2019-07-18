package com.elementtimes.tutorial.annotation.register;

import com.elementtimes.tutorial.annotation.AnnotationInitializer;
import com.elementtimes.tutorial.annotation.other.ModInfo;
import com.elementtimes.tutorial.annotation.processor.ModBlockLoader;
import com.elementtimes.tutorial.annotation.processor.ModFluidLoader;
import com.elementtimes.tutorial.annotation.processor.ModItemLoader;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * 注解注册
 *
 * @author luqin2007
 */
@Mod.EventBusSubscriber
public class ForgeBusRegister {

    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        AnnotationInitializer.BLOCKS.forEach(registry::register);
        ModFluidLoader.FLUID_BLOCK.keySet().forEach(fluid -> registry.register(fluid.getBlock()));
    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        AnnotationInitializer.ITEMS.forEach(item -> {
            registry.register(item);
            if (ModItemLoader.ORE_DICTIONARY.containsKey(item)) {
                OreDictionary.registerOre(ModItemLoader.ORE_DICTIONARY.get(item), item);
            }
        });
        AnnotationInitializer.BLOCKS.forEach(block -> {
            ItemBlock itemBlock = new ItemBlock(block) {
                @Override
                public int getItemBurnTime(ItemStack itemStack) {
                    return ModBlockLoader.BURNING_TIMES.getOrDefault(block, 0);
                }
            };
            //noinspection ConstantConditions
            itemBlock.setRegistryName(block.getRegistryName());
            registry.register(itemBlock);
            if (ModBlockLoader.ORE_DICTIONARY.containsKey(block)) {
                OreDictionary.registerOre(ModBlockLoader.ORE_DICTIONARY.get(block), block);
            }
            if (ModBlockLoader.TILE_ENTITIES.containsKey(block)) {
                GameRegistry.registerTileEntity(ModBlockLoader.TILE_ENTITIES.get(block).right, new ResourceLocation(ModInfo.MODID, ModBlockLoader.TILE_ENTITIES.get(block).left));
            }
        });
    }

    @SubscribeEvent
    public static void registerRecipe(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();
        AnnotationInitializer.RECIPES.forEach(getter ->
                Arrays.stream(getter.get()).filter(Objects::nonNull).forEach(registry::register));
    }

    @SubscribeEvent
    public static void onBurningTime(FurnaceFuelBurnTimeEvent event) {
        ItemStack itemStack = event.getItemStack();
        String name = null;
        if (itemStack.getItem() == ElementtimesItems.bottle) {
            name = com.elementtimes.tutorial.util.FluidUtil.getFluid(itemStack).getFluid().getName();
        } else if (FluidRegistry.isUniversalBucketEnabled() && itemStack.getItem() == ForgeModContainer.getInstance().universalBucket) {
            Optional<IFluidTankProperties> fluidBucket = Arrays.stream(FluidUtil.getFluidHandler(itemStack).getTankProperties()).findFirst();
            if (fluidBucket.isPresent()) {
                Fluid fluid = fluidBucket.get().getContents().getFluid();
                if (fluid != null) {
                    name = fluid.getName();
                }
            }
        }
        int time = ModFluidLoader.FLUID_BURNING_TIME.getOrDefault(name, -1);
        if (time > 0) {
            event.setBurnTime(time);
        }
    }

    @SubscribeEvent
    public static void onRegisterEnchantment(RegistryEvent.Register<Enchantment> event) {
        IForgeRegistry<Enchantment> registry = event.getRegistry();
        for (Enchantment enchantment : AnnotationInitializer.ENCHANTMENTS) {
            registry.register(enchantment);
        }
    }
}
