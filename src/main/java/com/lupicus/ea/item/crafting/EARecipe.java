package com.lupicus.ea.item.crafting;

import com.lupicus.ea.Main;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class EARecipe extends ShapelessRecipe
{
	protected final String operation;
	private final boolean copyDamage;
	public static final Serializer SERIALIZER = new Serializer();
	public static final ResourceLocation NAME = new ResourceLocation(Main.MODID, "crafting_shapeless");

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
    public ItemStack assemble(CraftingContainer inv, RegistryAccess reg)
    {
        ItemStack ret = this.getResultItem(reg).copy();
        if (copyDamage)
        {
    	    for (int j = 0; j < inv.getContainerSize(); ++j)
    	    {
    	    	ItemStack itemstack = inv.getItem(j);
    	        if (!itemstack.isEmpty())
    	        {
    	        	if (itemstack.isDamageableItem() && itemstack.getMaxDamage() == ret.getMaxDamage())
    	        	{
    	        		ret.setDamageValue(itemstack.getDamageValue());
    	        		if (itemstack.hasTag())
    	        		{
    	        			ret.setTag(itemstack.getTag().copy());
    	        		}
    	        		break;
    	        	}
    	        }
    	    }
        }
        if (operation.equals("reset_color"))
        {
    		CompoundTag compoundnbt = ret.getTagElement("display");
    		if (compoundnbt != null && compoundnbt.contains("color", 99))
    			compoundnbt.remove("color");
        }
        else if (operation.equals("set_color"))
        {
	    	int color = -1;
    	    for (int j = 0; j < inv.getContainerSize(); ++j)
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
        		CompoundTag compoundnbt = ret.getOrCreateTagElement("display");
        		compoundnbt.putInt("color", color);
    	    }
    	    else
    	    	ret = ItemStack.EMPTY;
        }
        else if (operation.equals("remove"))
        {
    		CompoundTag compoundnbt = ret.getTagElement("display");
    		if (compoundnbt != null)
    		{
    			compoundnbt.remove("color");
    			compoundnbt.remove("glint");
    			if (compoundnbt.isEmpty())
    				ret.removeTagKey("display");
    		}
        }
        return ret;
    }

	public static class Serializer implements RecipeSerializer<EARecipe>
	{
		private static final Codec<EARecipe> CODEC = RecordCodecBuilder.create((inst) -> {
			return inst.group(ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter((rec) -> {
				return rec.getGroup();
			}), CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter((rec) -> {
				return rec.category();
			}), ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter((rec) -> {
				return rec.getResultItem(RegistryAccess.EMPTY);
			}), Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap((list) -> {
				Ingredient[] aingredient = list.stream().filter((ingrid) -> {
					return !ingrid.isEmpty();
				}).toArray((size) -> {
					return new Ingredient[size];
				});
				if (aingredient.length == 0) {
					return DataResult.error(() -> {
						return "No ingredients for ea shapeless recipe";
					});
				} else {
					return aingredient.length > 3 * 3 ? DataResult.error(() -> {
						return "Too many ingredients for ea shapeless recipe";
					}) : DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
				}
			}, DataResult::success).forGetter((rec) -> {
				return rec.getIngredients();
			}), ExtraCodecs.strictOptionalField(Codec.STRING, "operation", "").forGetter((rec) -> {
				return rec.operation;
			})).apply(inst, EARecipe::new);
		});

		@Override
		public Codec<EARecipe> codec() {
			return CODEC;
		}

		@Override
		public EARecipe fromNetwork(FriendlyByteBuf buffer)
		{
			String group = buffer.readUtf();
			CraftingBookCategory cat = buffer.readEnum(CraftingBookCategory.class);
			int i = buffer.readVarInt();
			NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

			for (int j = 0; j < nonnulllist.size(); ++j) {
				nonnulllist.set(j, Ingredient.fromNetwork(buffer));
			}

			ItemStack itemstack = buffer.readItem();
			String oper = buffer.readUtf();
			return new EARecipe(group, cat, itemstack, nonnulllist, oper);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, EARecipe recipe)
		{
			buffer.writeUtf(recipe.getGroup());
			buffer.writeEnum(recipe.category());
			NonNullList<Ingredient> nonnulllist = recipe.getIngredients();
			buffer.writeVarInt(nonnulllist.size());

			for (Ingredient ingredient : nonnulllist) {
				ingredient.toNetwork(buffer);
			}

			buffer.writeItem(recipe.getResultItem(RegistryAccess.EMPTY));
			buffer.writeUtf(recipe.operation);
		}
	}
}
