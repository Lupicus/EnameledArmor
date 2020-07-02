package com.lupicus.ea.item;

import com.lupicus.ea.Main;

import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

	public class EABowItem extends BowItem implements IGuiRightClick 
	{
		public EABowItem(Properties prop, String name) 
		{
			super(prop);
			setRegistryName(Main.MODID, name);
		}

		@Override
		public boolean hasEffect(ItemStack stack)
		{
			CompoundNBT compoundnbt = stack.getChildTag("display");
			boolean glint = compoundnbt != null && compoundnbt.contains("glint", 1) ? compoundnbt.getBoolean("glint") : false;
			return glint && super.hasEffect(stack);
		}

		@Override
		public void menuRightClick(ItemStack stack)
		{
			CompoundNBT compoundnbt = stack.getOrCreateChildTag("display");
			boolean glint = compoundnbt.contains("glint", 1) && compoundnbt.getBoolean("glint");
			if (glint)
				compoundnbt.remove("glint");
			else
				compoundnbt.putBoolean("glint", true);
		}
	}


