package com.lupicus.ea.data.recipes;

import java.util.LinkedHashMap;
import java.util.Map;

import com.lupicus.ea.Main;
import com.lupicus.ea.item.crafting.EARecipe;

import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

public class EAShapelessRecipeBuilder implements RecipeBuilder
{
	private final HolderGetter<Item> items;
	private final RecipeCategory category;
	private final Item result;
	private final int count;
	private final NonNullList<Ingredient> ingredients = NonNullList.create();
	private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
	private String group;
	private String operation;
	private String namespace;
	private String suffix;

	public EAShapelessRecipeBuilder(HolderGetter<Item> items, RecipeCategory category, ItemLike item, int count) {
		this.items = items;
		this.category = category;
		this.result = item.asItem();
		this.count = count;
		group = "";
		operation = "";
	}

	public static EAShapelessRecipeBuilder shapeless(HolderGetter<Item> items, RecipeCategory category, ItemLike item) {
		return new EAShapelessRecipeBuilder(items, category, item, 1);
	}

	public EAShapelessRecipeBuilder requires(TagKey<Item> tag) {
		return requires(Ingredient.of(items.getOrThrow(tag)));
	}

	public EAShapelessRecipeBuilder requires(ItemLike item) {
		return requires(item, 1);
	}

	public EAShapelessRecipeBuilder requires(ItemLike item, int num) {
		for (int i = 0; i < num; ++i) {
			requires(Ingredient.of(item));
		}

		return this;
	}

	public EAShapelessRecipeBuilder requires(Ingredient ingredient) {
		return requires(ingredient, 1);
	}

	public EAShapelessRecipeBuilder requires(Ingredient ingredient, int num) {
		for (int i = 0; i < num; ++i) {
			ingredients.add(ingredient);
		}

		return this;
	}

	@Override
	public EAShapelessRecipeBuilder unlockedBy(String key, Criterion<?> criterionIn) {
		criteria.put(key, criterionIn);
		return this;
	}

	@Override
	public EAShapelessRecipeBuilder group(String group) {
		this.group = group;
		return this;
	}

	public EAShapelessRecipeBuilder setOperation(String oper)
	{
		this.operation = oper;
		return this;
	}

	public EAShapelessRecipeBuilder setNamespace()
	{
		this.namespace = Main.MODID;
		return this;
	}

	public EAShapelessRecipeBuilder setNamespace(String name)
	{
		this.namespace = name;
		return this;
	}

	public EAShapelessRecipeBuilder addSuffix(String name)
	{
		this.suffix = name;
		return this;
	}

	@Override
	public Item getResult() {
		return result;
	}

	@Override
	public void save(RecipeOutput consumer) {
		ResourceLocation resLoc = BuiltInRegistries.ITEM.getKey(result);
		if (namespace != null || suffix != null)
			save(consumer, resLoc.toString());
		else
			save(consumer, ResourceKey.create(Registries.RECIPE, resLoc));
	}

	@Override
	public void save(RecipeOutput consumer, String resName) {
		if (namespace != null || suffix != null)
		{
			ResourceLocation work = ResourceLocation.parse(resName);
			if (namespace != null)
			{
				work = ResourceLocation.fromNamespaceAndPath(namespace, work.getPath());
			}
			if (suffix != null)
			{
				work = ResourceLocation.fromNamespaceAndPath(work.getNamespace(), work.getPath() + suffix);
			}
			resName = work.toString();
		}
		ResourceLocation resLoc = ResourceLocation.parse(resName);
		ResourceLocation iLoc = BuiltInRegistries.ITEM.getKey(result);
		if (resLoc.equals(iLoc)) {
			throw new IllegalStateException("Shapeless Recipe " + resName + " should remove its 'save' argument");
		} else {
			save(consumer, ResourceKey.create(Registries.RECIPE, resLoc));
		}
	}

	@Override
	public void save(RecipeOutput consumer, ResourceKey<Recipe<?>> resKey) {
//		ensureValid(resLoc);
		AdvancementHolder advHolder = null;
		if (!criteria.isEmpty())
		{
			Builder adv = consumer.advancement()
					.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resKey))
					.rewards(AdvancementRewards.Builder.recipe(resKey))
					.requirements(AdvancementRequirements.Strategy.OR);
			criteria.forEach(adv::addCriterion);
			advHolder = adv.build(resKey.location().withPrefix("recipes/" + category.getFolderName() + "/"));
		}
		EARecipe recipe = new EARecipe(group, RecipeBuilder.determineBookCategory(category), new ItemStack(result, count), ingredients, operation);
		consumer.accept(resKey, recipe, advHolder);
	}

//	private void ensureValid(ResourceLocation resLoc) {
//		if (advancementBuilder.getCriteria().isEmpty()) {
//			throw new IllegalStateException("No way of obtaining recipe " + resLoc);
//		}
//	}
}
