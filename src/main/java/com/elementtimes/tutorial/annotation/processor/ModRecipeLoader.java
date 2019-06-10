package com.elementtimes.tutorial.annotation.processor;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.annotation.ModRecipe;
import com.elementtimes.tutorial.annotation.other.DamageIngredient;
import com.elementtimes.tutorial.annotation.util.RecipeUtil;
import com.elementtimes.tutorial.annotation.util.ReflectUtil;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;
import java.lang.reflect.AnnotatedElement;
import java.util.*;
import java.util.function.Supplier;

import static com.elementtimes.tutorial.annotation.util.MessageUtil.warn;

/**
 * 加载合成表
 * 处理所有 ModRecipe 注解的成员
 * 目前只有一个可用 ModRecipe.Ore
 *
 * @author luqin2007
 */
public class ModRecipeLoader {

    /**
     * 获取所有合成表
     */
    public static void getRecipes(Map<Class, ArrayList<AnnotatedElement>> elements, List<Supplier<IRecipe>> into) {
        elements.get(ModRecipe.class).forEach(element -> {
            Object obj = ReflectUtil.getFromAnnotated(element, null).orElse(null);
            if (obj == null) {
                return;
            }

            addOreRecipe(obj, element, into);
            String name = ReflectUtil.getName(element).orElse(null);
            addCraftingRecipe(element, name, into);
        });
    }

    private static void addCraftingRecipe(AnnotatedElement element, String name, List<Supplier<IRecipe>> into) {
        ModRecipe.Crafting info = element.getAnnotation(ModRecipe.Crafting.class);
        if (info != null) {
            into.add(() -> {
                String rName = info.value().isEmpty() ? name : info.value();
                Object obj = ReflectUtil.getFromAnnotated(element, null).orElse(null);
                if (obj instanceof IRecipe) {
                    return (IRecipe) obj;
                } else if (obj instanceof Object[]) {
                    Object[] objects = (Object[]) obj;
                    Object result = objects[0];
                    ItemStack r;
                    if (result instanceof Item) {
                        r = new ItemStack((Item) result);
                    } else if (result instanceof Block) {
                        r = new ItemStack((Block) result);
                    } else if (result instanceof ItemStack) {
                        r = (ItemStack) result;
                    } else if (result instanceof Ingredient) {
                        r = ((Ingredient) result).getMatchingStacks()[0];
                    } else {
                        r = CraftingHelper.getIngredient(result).getMatchingStacks()[0];
                    }
                    IRecipe recipe;
                    CraftingHelper.ShapedPrimer primer = new CraftingHelper.ShapedPrimer();
                    int size = info.width() * info.height();
                    primer.input = NonNullList.withSize(size, Ingredient.EMPTY);
                    primer.width = info.width();
                    primer.height = info.height();
                    if (size < objects.length - 1) {
                        warn("You want to register a recipe({}) with {} items, but you put {} items in it. Some items will ignore.",
                                rName, size, objects.length - 1);
                    }
                    for (int i = 1; i < objects.length; i++) {
                        Object o = objects[i];
                        if (i - 1 >= size) {
                            warn("Ignore item[()]: {}.", i - 1, o);
                            break;
                        }
                        primer.input.set(i - 1, CraftingHelper.getIngredient(o == null ? ItemStack.EMPTY : o));
                    }
                    if (info.shaped()) {
                        if (info.ore()) {
                            recipe = new ShapedOreRecipe(new ResourceLocation(Elementtimes.MODID, "recipe"), r, primer);
                        } else {
                            recipe = new ShapedRecipes("recipe", primer.width, primer.height, primer.input, r);
                        }
                    } else {
                        if (info.ore()) {
                            recipe = new ShapelessOreRecipe(new ResourceLocation(Elementtimes.MODID, "recipe"), primer.input, r);
                        } else {
                            recipe = new ShapelessRecipes("recipe", r, primer.input);
                        }
                    }
                    recipe.setRegistryName(new ResourceLocation(Elementtimes.MODID, rName));
                    return recipe;
                }
                return null;
            });
        }
    }

    private static void addOreRecipe(Object ore, AnnotatedElement element, List<Supplier<IRecipe>> into) {
        // 矿物锤合成
        ModRecipe.Ore info = element.getAnnotation(ModRecipe.Ore.class);
        final String logOreName = "logWood";
        if (info != null) {
            if (logOreName.equals(ore)) {
                // 对原木进行特殊处理
                Map<ItemStack, ItemStack> itemStacks = new LinkedHashMap<>();
                RecipeUtil.collectOneBlockCraftingResult("logWood", itemStacks);
                for (Map.Entry<ItemStack, ItemStack> entry: itemStacks.entrySet()) {
                    into.add(() -> {
                        NonNullList<Ingredient> input = NonNullList.create();
                        ItemStack output = entry.getValue().copy();
                        input.add(Ingredient.fromStacks(entry.getKey()));
                        return getRecipeHammer(input, output, info.damage(), info.value()
                                + "_" + Objects.requireNonNull(output.getItem().getRegistryName()).getResourceDomain()
                                + "_" + output.getItem().getRegistryName().getResourcePath()
                                + "_" + output.getItemDamage());
                    });
                }
            } else {
                into.add(() -> {
                    NonNullList<Ingredient> input = NonNullList.create();
                    ItemStack[] matchingStacks = RecipeUtil.getIngredient(info.output()).getMatchingStacks();
                    if (matchingStacks.length == 0) {
                        return null;
                    }
                    ItemStack output = matchingStacks[0].copy();
                    input.add(RecipeUtil.getIngredient(ore));
                    output.setCount(info.dustCount());
                    return getRecipeHammer(input, output, info.damage(), info.value());
                });
            }
        }
    }

    private static ShapelessOreRecipe getRecipeHammer(NonNullList<Ingredient> input, ItemStack output, int damage, String name) {
        input.add(new DamageIngredient(new Item[] {ElementtimesItems.smallHammer, ElementtimesItems.bigHammer}, damage));
        ShapelessOreRecipe recipe = new ShapelessOreRecipe(new ResourceLocation(Elementtimes.MODID, "recipe"), input, output){
            @Nonnull
            @Override
            public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
                NonNullList<ItemStack> ret = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
                for (int i = 0; i < ret.size(); i++) {
                    ItemStack stack = inv.getStackInSlot(i);
                    if (stack.getItem() == ElementtimesItems.smallHammer || stack.getItem() == ElementtimesItems.bigHammer) {
                        stack.getOrCreateSubCompound(Elementtimes.MODID + "_bind").setInteger("damage", damage);
                    }
                    ret.set(i, ForgeHooks.getContainerItem(stack));
                }
                return ret;
            }
        };
        recipe.setRegistryName(Elementtimes.MODID, name);
        return recipe;
    }




}
