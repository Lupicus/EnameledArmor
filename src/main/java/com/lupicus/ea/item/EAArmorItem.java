package com.lupicus.ea.item;

import com.lupicus.ea.Main;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class EAArmorItem extends DyeableArmorItem implements IGuiRightClick
{
	public EAArmorItem(IArmorMaterial material, EquipmentSlotType slot, Properties prop, String name)
	{
		super(material, slot, prop);
		setRegistryName(Main.MODID, name);
	}

	@Override
	public int getColor(ItemStack stack)
	{
		CompoundNBT compoundnbt = stack.getChildTag("display");
		return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 0xCAC8C8;
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
