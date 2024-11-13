package com.lupicus.ea.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

public class EAArmorItem extends ArmorItem implements IGuiRightClick
{
	public static final int DEFCOLOR = 0xFFCAC8C8;

	public EAArmorItem(ArmorMaterial material, ArmorType type, Properties prop)
	{
		super(material, type, prop);
	}
}
