package com.lupicus.ea.item.crafting;

import com.lupicus.ea.Main;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class EARecipe extends ShapelessRecipe
{
	protected final String operation;
	private final boolean copyDamage;
	public static final Serializer SERIALIZER = new Serializer();
	public static final ResourceLocation NAME = ResourceLocation.fromNamespaceAndPath(Main.MODID, "crafting_shapeless");

	public EARecipe(String groupIn, CraftingBookCategory catIn, ItemStack recipeOutputIn,
			NonNullList<Ingredient> recipeItemsIn, String operationIn)
	{
		super(groupIn, catIn, recipeOutputIn, recipeItemsIn);
		operation = operationIn;
		boolean copyDamage = false;
		if (recipeOutputIn.isDamageableItem())
		{
			for (Ingredient thing : recipeItemsIn)
			{
				for (ItemStack stack : thing.getItems())
				{
					if (stack.isDamageableItem() && stack.getMaxDamage() == recipeOutputIn.getMaxDamage())
					{
						copyDamage = true;
						break;
					}
				}
			}
		}
		this.copyDamage = copyDamage;
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return SERIALIZER;
	}

	@Override
    public ItemStack assemble(CraftingInput inv, HolderLookup.Provider reg)
    {
        ItemStack ret = this.getResultItem(reg).copy();
        if (copyDamage)
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

	public static class Serializer implements RecipeSerializer<EARecipe>
	{
		private static final MapCodec<EARecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
				Codec.STRING.optionalFieldOf("group", "").forGetter(rec -> rec.getGroup()),
				CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC)
						.forGetter(rec -> rec.category()),
				ItemStack.STRICT_CODEC.fieldOf("result").forGetter(rec -> rec.getResultItem(RegistryAccess.EMPTY)),
				Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap(list -> {
					Ingredient[] aingredient = list.stream().filter(ingrid -> !ingrid.isEmpty())
							.toArray(size -> new Ingredient[size]);
					if (aingredient.length == 0) {
						return DataResult.error(() -> {
							return "No ingredients for ea shapeless recipe";
						});
					} else {
						return aingredient.length > 3 * 3 ? DataResult.error(() -> {
							return "Too many ingredients for ea shapeless recipe";
						}) : DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
					}
				}, DataResult::success).forGetter(rec -> rec.getIngredients()),
				Codec.STRING.optionalFieldOf("operation", "").forGetter(rec -> rec.operation))
				.apply(inst, EARecipe::new));
		public static final StreamCodec<RegistryFriendlyByteBuf, EARecipe> STREAM_CODEC = StreamCodec
				.of(EARecipe.Serializer::toNetwork, EARecipe.Serializer::fromNetwork);

		@Override
		public MapCodec<EARecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, EARecipe> streamCodec() {
			return STREAM_CODEC;
		}

		private static EARecipe fromNetwork(RegistryFriendlyByteBuf buffer)
		{
			String group = buffer.readUtf();
			CraftingBookCategory cat = buffer.readEnum(CraftingBookCategory.class);
			int i = buffer.readVarInt();
			NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);
			nonnulllist.replaceAll(elem -> Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
			ItemStack itemstack = ItemStack.STREAM_CODEC.decode(buffer);
			String oper = buffer.readUtf();
			return new EARecipe(group, cat, itemstack, nonnulllist, oper);
		}

		private static void toNetwork(RegistryFriendlyByteBuf buffer, EARecipe recipe)
		{
			buffer.writeUtf(recipe.getGroup());
			buffer.writeEnum(recipe.category());
			NonNullList<Ingredient> nonnulllist = recipe.getIngredients();
			buffer.writeVarInt(nonnulllist.size());

			for (Ingredient ingredient : nonnulllist) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
			}

			ItemStack.STREAM_CODEC.encode(buffer, recipe.getResultItem(RegistryAccess.EMPTY));
			buffer.writeUtf(recipe.operation);
		}
	}
}
