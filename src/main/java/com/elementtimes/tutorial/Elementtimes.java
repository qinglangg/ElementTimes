package com.elementtimes.tutorial;

import com.elementtimes.tutorial.common.CommonProxy;
import com.elementtimes.tutorial.common.init.ModRecipes;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;

@Mod(modid = "elementtimes")
public class Elementtimes
{
	@SidedProxy(serverSide = "com.elementtimes.tutorial.common.CommonProxy", clientSide = "com.elementtimes.tutorial.client.ClientProxy")
    public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(proxy);
	}
	
	@EventHandler
	public static void Init(FMLInitializationEvent event)
	{
		ModRecipes.init();
	}
	
}
