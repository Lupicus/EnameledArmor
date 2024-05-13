package com.lupicus.ea.data;

import java.util.concurrent.CompletableFuture;

import com.lupicus.ea.Main;
import com.lupicus.ea.data.recipes.EAShapelessRecipeBuilder;
import com.lupicus.ea.item.ModItems;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;

public class EARecipeProvider extends RecipeProvider
{
	TagKey<Item> TRUE_DYES = ItemTags.create(new ResourceLocation(Main.MODID, "true_dyes"));

	public EARecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup) {
		super(packOutput, lookup);
	}

	@Override
	protected void buildRecipes(RecipeOutput consumer)
	{
		weaponSet(consumer, ModItems.EA_BOW, Items.BOW);
		weaponSet(consumer, ModItems.EA_IRON_SWORD, Items.IRON_SWORD);
		weaponSet(consumer, ModItems.EA_DIAMOND_SWORD, Items.DIAMOND_SWORD);
		weaponSet(consumer, ModItems.EA_NETHERITE_SWORD, Items.NETHERITE_SWORD);
		armorSet(consumer, ModItems.EA_CHESTPLATE, Items.DIAMOND_CHESTPLATE);
		armorSet(consumer, ModItems.EA_LEGGINGS, Items.DIAMOND_LEGGINGS);
		armorSet(consumer, ModItems.EA_BOOTS, Items.DIAMOND_BOOTS);
		armorSet(consumer, ModItems.EA_HELMET, Items.DIAMOND_HELMET);
		armorSet(consumer, ModItems.EA_NETHERITE_CHESTPLATE, Items.NETHERITE_CHESTPLATE);
		armorSet(consumer, ModItems.EA_NETHERITE_LEGGINGS, Items.NETHERITE_LEGGINGS);
		armorSet(consumer, ModItems.EA_NETHERITE_BOOTS, Items.NETHERITE_BOOTS);
		armorSet(consumer, ModItems.EA_NETHERITE_HELMET, Items.NETHERITE_HELMET);
		armorSet(consumer, ModItems.EA_IRON_CHESTPLATE, Items.IRON_CHESTPLATE);
		armorSet(consumer, ModItems.EA_IRON_LEGGINGS, Items.IRON_LEGGINGS);
		armorSet(consumer, ModItems.EA_IRON_BOOTS, Items.IRON_BOOTS);
		armorSet(consumer, ModItems.EA_IRON_HELMET, Items.IRON_HELMET);
		armorSet(consumer, ModItems.EA_CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE);
		armorSet(consumer, ModItems.EA_CHAINMAIL_LEGGINGS, Items.CHAINMAIL_LEGGINGS);
		armorSet(consumer, ModItems.EA_CHAINMAIL_BOOTS, Items.CHAINMAIL_BOOTS);
		armorSet(consumer, ModItems.EA_CHAINMAIL_HELMET, Items.CHAINMAIL_HELMET);

		String prefix = Main.MODID + ":craft_chainmail_";
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.CHAINMAIL_CHESTPLATE).define('N', Items.IRON_NUGGET)
				.define('X', Items.IRON_INGOT).pattern("X X").pattern("NXN").pattern("XNX")
				.unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(consumer, prefix + "chestplate");
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.CHAINMAIL_LEGGINGS).define('N', Items.IRON_NUGGET)
				.define('X', Items.IRON_INGOT).pattern("XNX").pattern("N N").pattern("X X")
				.unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(consumer, prefix + "leggings");
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.CHAINMAIL_BOOTS).define('N', Items.IRON_NUGGET)
				.define('X', Items.IRON_INGOT).pattern("N N").pattern("X X")
				.unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(consumer, prefix + "boots");
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.CHAINMAIL_HELMET).define('N', Items.IRON_NUGGET)
				.define('X', Items.IRON_INGOT).pattern("NXN").pattern("X X")
				.unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(consumer, prefix + "helmet");
	}

	protected void armorSet(RecipeOutput consumer, ItemLike result, ItemLike input)
	{
		EAShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, result).requires(input).requires(Items.CLAY_BALL).unlockedBy("has_clay_ball", has(Items.CLAY_BALL)).save(consumer);
		EAShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, result).setOperation("set_color").requires(result).requires(Tags.Items.MUSHROOMS).requires(TRUE_DYES).addSuffix("_set").save(consumer);
		EAShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, result).setOperation("reset_color").requires(result).addSuffix("_reset").save(consumer);
		EAShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, input).setNamespace().setOperation("remove").requires(result).requires(Items.GRAVEL).save(consumer);
	}

	protected void weaponSet(RecipeOutput consumer, ItemLike result, ItemLike input)
	{
		EAShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, result).requires(input).requires(Items.CLAY_BALL).unlockedBy("has_clay_ball", has(Items.CLAY_BALL)).save(consumer);
		EAShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, input).setNamespace().setOperation("remove").requires(result).requires(Items.GRAVEL).save(consumer);
	}
}
