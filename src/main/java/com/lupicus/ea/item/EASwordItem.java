package com.lupicus.ea.item;

import com.lupicus.ea.Main;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;

	public class EASwordItem extends SwordItem implements IGuiRightClick 
	{
		public EASwordItem(IItemTier tier, int damage, float speed, Properties prop, String name) 
		{
			super(tier, damage, speed, prop);
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


