package com.lupicus.ea.item.crafting;

import java.util.List;

import javax.annotation.Nullable;

import com.lupicus.ea.Main;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

public class EARecipe implements CraftingRecipe
{
	final String group;
	final CraftingBookCategory category;
	final ItemStack result;
	final List<Ingredient> ingredients;
	@Nullable
	private PlacementInfo placementInfo;
	private final boolean isSimple;
	protected final String operation;
	public static final RecipeSerializer<EARecipe> SERIALIZER = new Serializer();
	public static final ResourceLocation NAME = ResourceLocation.fromNamespaceAndPath(Main.MODID, "crafting_shapeless");

	public EARecipe(String groupIn, CraftingBookCategory catIn, ItemStack recipeOutputIn,
			List<Ingredient> recipeItemsIn, String operationIn)
	{
		group = groupIn;
		category = catIn;
		result = recipeOutputIn;
		ingredients = recipeItemsIn;
		operation = operationIn;
		isSimple = ingredients.stream().allMatch(Ingredient::isSimple);
	}

	@Override
	public RecipeSerializer<EARecipe> getSerializer()
	{
		return SERIALIZER;
	}

	@Override
	public String group() {
		return group;
	}

	@Override
	public CraftingBookCategory category() {
		return category;
	}

	@Override
	public PlacementInfo placementInfo() {
		if (placementInfo == null) {
			placementInfo = PlacementInfo.create(ingredients);
		}

		return placementInfo;
	}

	@Override
	public boolean matches(CraftingInput inv, Level level) {
		if (inv.ingredientCount() != ingredients.size()) {
			return false;
		} else if (inv.size() == 1 && ingredients.size() == 1) {
			return ingredients.getFirst().test(inv.getItem(0));
		} else if (!isSimple) {
			return net.minecraftforge.common.util.RecipeMatcher.findMatches(inv.items(), ingredients) != null;
		} else {
			return inv.stackedContents().canCraft(this, null);
		}
	}

	@Override
    public ItemStack assemble(CraftingInput inv, HolderLookup.Provider reg)
    {
        ItemStack ret = result.copy();
        if (ret.isDamageableItem())
        {
    	    for (int j = 0; j < inv.size(); ++j)
    	    {
    	    	ItemStack itemstack = inv.getItem(j);
    	        if (!itemstack.isEmpty())
    	        {
    	        	if (itemstack.isDamageableItem() && itemstack.getMaxDamage() == ret.getMaxDamage())
    	        	{
    	        		ret.applyComponents(itemstack.getComponentsPatch());
    	        		break;
    	        	}
    	        }
    	    }
        }
        if (operation.equals("reset_color"))
        {
        	ret.remove(DataComponents.DYED_COLOR);
        }
        else if (operation.equals("set_color"))
        {
	    	int color = -1;
    	    for (int j = 0; j < inv.size(); ++j)
    	    {
    	    	ItemStack itemstack = inv.getItem(j);
    	        if (!itemstack.isEmpty())
    	        {
    	        	Item item = itemstack.getItem();
    	        	if (item instanceof DyeItem)
    	        	{
    	        		if (item.equals(Items.WHITE_DYE))
    	        		{
    	        			color = 0xFFFFFF;
    	        		}
    	        		else if (item.equals(Items.RED_DYE))
    	        		{
    	        			color = 0xFF0000;
    	        		}
    	        		else if (item.equals(Items.GREEN_DYE))
    	        		{
    	        			color = 0x00FF00;
    	        		}
    	        		else if (item.equals(Items.BLUE_DYE))
    	        		{
    	        			color = 0x0000FF;
    	        		}
    	        		else if (item.equals(Items.BLACK_DYE))
    	        		{
    	        			color = 0x000000;
    	        		}
    	        		break;
    	        	}
    	        }
    	    }
    	    if (color >= 0)
    	    {
                ret.set(DataComponents.DYED_COLOR, new DyedItemColor(color, true));
    	    }
    	    else
    	    	ret = ItemStack.EMPTY;
        }
        else if (operation.equals("remove"))
        {
        	ret.remove(DataComponents.DYED_COLOR);
        	ret.remove(DataComponents.ENCHANTMENT_GLINT_OVERRIDE);
        }
        return ret;
    }

	@Override
	public List<RecipeDisplay> display() {
		return List.of(new ShapelessCraftingRecipeDisplay(ingredients.stream().map(Ingredient::display).toList(),
				new SlotDisplay.ItemStackSlotDisplay(result),
				new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)));
	}

	public static class Serializer implements RecipeSerializer<EARecipe>
	{
		private static final MapCodec<EARecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
				Codec.STRING.optionalFieldOf("group", "").forGetter(rec -> rec.group()),
				CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(rec -> rec.category()),
				ItemStack.STRICT_CODEC.fieldOf("result").forGetter(rec -> rec.result),
				Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(rec -> rec.ingredients),
				Codec.STRING.optionalFieldOf("operation", "").forGetter(rec -> rec.operation))
				.apply(inst, EARecipe::new));
		public static final StreamCodec<RegistryFriendlyByteBuf, EARecipe> STREAM_CODEC = StreamCodec.composite(
				ByteBufCodecs.STRING_UTF8, rec -> rec.group,
				CraftingBookCategory.STREAM_CODEC, rec -> rec.category,
				ItemStack.STREAM_CODEC, rec -> rec.result,
				Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), rec -> rec.ingredients,
				ByteBufCodecs.STRING_UTF8, rec -> rec.operation,
				EARecipe::new);

		@Override
		public MapCodec<EARecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, EARecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
