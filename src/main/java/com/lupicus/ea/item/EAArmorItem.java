package com.lupicus.ea.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;

public class EAArmorItem extends DyeableArmorItem implements IGuiRightClick
{
	public EAArmorItem(ArmorMaterial material, EquipmentSlot slot, Properties prop)
	{
		super(material, slot, prop);
	}

	@Override
	public int getColor(ItemStack stack)
	{
		CompoundTag compoundnbt = stack.getTagElement("display");
		return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 0xCAC8C8;
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
