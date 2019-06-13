package com.elementtimes.tutorial.test_animation;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.creativetabs.ElementTimesTabs;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.eventbus.Subscribe;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.animation.ITimeValue;
import net.minecraftforge.common.animation.TimeValues;
import net.minecraftforge.common.model.animation.AnimationStateMachine;
import net.minecraftforge.common.model.animation.Clips;
import net.minecraftforge.common.model.animation.IClip;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class Register {

    public static Block b = new BlockAnimated();

    @SubscribeEvent
    public static void rBlock(RegistryEvent.Register<Block> event) {
        b.setRegistryName("anidemo");
        b.setCreativeTab(CreativeTabs.MISC);
        event.getRegistry().register(b);
        GameRegistry.registerTileEntity(TileBlockAnimated.class, new ResourceLocation(ElementTimes.MODID, "anidemo"));
    }

    @SubscribeEvent
    public static void rItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(b).setRegistryName(b.getRegistryName()));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void rModel(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), 0, new ModelResourceLocation(new ResourceLocation(ElementTimes.MODID, "cement"), "inventory"));
        ClientRegistry.bindTileEntitySpecialRenderer(TileBlockAnimated.class, new BlockAnimatedTESR());
    }
}
