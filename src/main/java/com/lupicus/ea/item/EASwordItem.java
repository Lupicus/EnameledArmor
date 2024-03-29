package com.lupicus.ea.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class EASwordItem extends SwordItem implements IGuiRightClick
{
	public EASwordItem(Tier tier, int damage, float speed, Properties prop)
	{
		super(tier, damage, speed, prop);
	}

	@Override
	public boolean isFoil(ItemStack stack)
	{
		CompoundTag compoundnbt = stack.getTagElement("display");
		boolean glint = compoundnbt != null && compoundnbt.contains("glint", 1) ? compoundnbt.getBoolean("glint") : false;
		return glint && super.isFoil(stack);
	}

	@Override
	public void menuRightClick(ItemStack stack)
	{
		CompoundTag compoundnbt = stack.getOrCreateTagElement("display");
		boolean glint = compoundnbt.contains("glint", 1) && compoundnbt.getBoolean("glint");
		if (glint)
			compoundnbt.remove("glint");
		else
			compoundnbt.putBoolean("glint", true);
	}
}
