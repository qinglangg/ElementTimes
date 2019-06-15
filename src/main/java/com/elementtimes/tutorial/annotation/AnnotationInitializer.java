package com.elementtimes.tutorial.annotation;

import com.elementtimes.tutorial.annotation.annotations.*;
import com.elementtimes.tutorial.annotation.other.ModInfo;
import com.elementtimes.tutorial.annotation.processor.*;
import com.elementtimes.tutorial.annotation.register.OreBusRegister;
import com.elementtimes.tutorial.annotation.register.TerrainBusRegister;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

import static com.elementtimes.tutorial.annotation.util.MessageUtil.warn;

/**
 * 总入口
 * @author luqin2007
 */
public class AnnotationInitializer {

    private static boolean sInInit = false;



    public static List<Block> BLOCKS = new ArrayList<>();
    public static List<Item> ITEMS = new ArrayList<>();
    public static List<Supplier<IRecipe[]>> RECIPES = new ArrayList<>();
    public static List<Fluid> FLUIDS = new ArrayList<>();
    public static List<ModCapability> CAPABILITIES = new ArrayList<>();
    // ModNetwork, Class<IMessageHandler>, Class<IMessage>
    public static List<Object[]> NETWORKS = new ArrayList<>();

    public static void onPreInit(FMLPreInitializationEvent event, String modId, String packageName) {
        ModInfo.MODID = modId;
        ModInfo.PKG_NAME = packageName;
        init();
        registerCapabilities();
        registerFluids();
    }

    public static void onInit(FMLInitializationEvent event) {
    }

    public static void onPostInit(FMLPostInitializationEvent event) {
        invokeMethods();
        registerNetwork();
    }

    private static void init() {
        if (!sInInit) {
            warn("Annotation init start...");
            HashMap<Class, ArrayList<AnnotatedElement>> elements = new HashMap<>();
            ModClassLoader.getClasses(elements,
                    ModBlock.class, ModItem.class, ModRecipe.class, ModElement.class, ModFluid.class, ModCapability.class, ModNetwork.class);
            ModBlockLoader.getBlocks(elements, BLOCKS);
            warn("---> Find {} Block", BLOCKS.size());
            ModBlockLoader.WORLD_GENERATORS.forEach((genType, generators) -> {
                warn("\tGenerator[{}]: {}", genType.name(), generators.size());
            });
            warn("\tOreDictionary Name: {}", ModBlockLoader.ORE_DICTIONARY.size());
            warn("\tBlockState: {}", ModBlockLoader.BLOCK_STATES.size());
            warn("\tStateMap: {}", ModBlockLoader.STATE_MAPS.size());
            warn("\tTileEntity: {}", ModBlockLoader.TILE_ENTITIES.size());
            warn("\tB3D: {}, OBJ: {}", ModBlockLoader.B3D ? "on" : "off", ModBlockLoader.OBJ ? "on" : "off");
            ModItemLoader.getItems(elements, ITEMS);
            warn("---> Find {} Item", ITEMS.size());
            warn("\tOreDictionary Name: {}", ModItemLoader.ORE_DICTIONARY.size());
            warn("\tSubItem Model: {}", ModItemLoader.SUB_ITEM_MODEL.size());
            warn("\tIItemColor: {}", ModItemLoader.ITEM_COLOR.size());
            ModRecipeLoader.getRecipes(elements, RECIPES);
            warn("---> Find {} Recipe", RECIPES.size());
            ModFluidLoader.getFluids(elements, FLUIDS);
            warn("---> Find {} Fluids", FLUIDS.size());
            warn("\tBucket: {}", ModFluidLoader.HAS_BUCKET.size());
            warn("\tBlock: {}", ModFluidLoader.FLUID_BLOCK.size());
            warn("\tLoad fluid resource: {}", ModFluidLoader.FLUID_RESOURCES.size());
            warn("\tReset blockstate.json: {}", ModFluidLoader.FLUID_BLOCK_STATE.size());
            warn("\tReset CreativeTab: {}", ModFluidLoader.FLUID_TAB.size());
            ModCapabilityLoader.getCapabilities(elements, CAPABILITIES);
            warn("---> Find {} Capabilities", CAPABILITIES.size());
            ModElementLoader.getElements(elements);
            warn("---> Find {} Static Functions", ModElementLoader.STATIC_FUNCTIONS.size());
            ModNetworkLoader.getElements(elements, NETWORKS);
            warn("---> Find {} Network", NETWORKS.size());

            warn("Register Event Listener");
            MinecraftForge.ORE_GEN_BUS.register(OreBusRegister.class);
            MinecraftForge.EVENT_BUS.register(TerrainBusRegister.class);
            warn("Annotation init finished");
            sInInit = true;
        }
    }

    private static void invokeMethods() {
        ModElementLoader.STATIC_FUNCTIONS.forEach(method -> {
            try {
                method.invoke(null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                warn("Invoke Failure because {}, the method is {} in {} ", e.getMessage(), method.getName(), method.getDeclaringClass().getSimpleName());
            }
        });
    }
    
    private static void registerFluids() {
        FLUIDS.forEach(fluid -> {
            if (!FluidRegistry.registerFluid(fluid)) {
                warn("The name {} has been registered to another fluid!", fluid.getName());
            }
        });

        ModFluidLoader.HAS_BUCKET.forEach(FluidRegistry::addBucketForFluid);

        ModFluidLoader.FLUID_BLOCK.forEach((fluid, fluidBlockFunction) ->
                fluid.setBlock(fluidBlockFunction.apply(fluid)));
    }

    private static void registerCapabilities() {
        CAPABILITIES.forEach(modCapability -> {
            try {
                Class type = Class.forName(modCapability.typeInterfaceClass());
                Class impl = Class.forName(modCapability.typeImplementationClass());
                Class storage = Class.forName(modCapability.storageClass());
                Capability.IStorage storageObj = (Capability.IStorage) storage.getConstructor().newInstance();
                CapabilityManager.INSTANCE.register(type, storageObj, () -> impl.getConstructor().newInstance());
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
                warn("Can't register the capability, because " + e.getMessage());
            }
        });
    }

    private static void registerNetwork() {
        if (!NETWORKS.isEmpty()) {
            SimpleNetworkWrapper wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.MODID + "_network_annotation");
            AnnotationElements.CHANNEL = wrapper;

            NETWORKS.forEach(objects -> {
                ModNetwork info = (ModNetwork) objects[0];
                Class handler = (Class) objects[1];
                Class message = (Class) objects[2];
                int id = info.id();
                for (Side side : info.side()) {
                    wrapper.registerMessage(handler, message, id, side);
                }
            });
        }
    }
}
