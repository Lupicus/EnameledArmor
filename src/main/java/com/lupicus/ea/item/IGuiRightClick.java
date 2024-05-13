package com.lupicus.ea.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;

public interface IGuiRightClick
{
	default void menuRightClick(ItemStack stack)
	{
		Boolean comp = stack.get(DataComponents.ENCHANTMENT_GLINT_OVERRIDE);
		if (comp == null || comp.booleanValue())
			stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, false);
		else
			stack.remove(DataComponents.ENCHANTMENT_GLINT_OVERRIDE);
	}
}
