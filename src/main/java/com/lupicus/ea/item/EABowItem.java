package com.lupicus.ea.item;

import com.lupicus.ea.Main;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;

public class EABowItem extends BowItem implements IGuiRightClick 
{
	public EABowItem(Properties prop, String name) 
	{
		super(prop);
		setRegistryName(Main.MODID, name);
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
