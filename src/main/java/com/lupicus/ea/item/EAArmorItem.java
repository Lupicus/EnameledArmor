package com.lupicus.ea.item;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class EAArmorItem extends ArmorItem implements IGuiRightClick
{
	public static final int DEFCOLOR = 0xFFCAC8C8;

	public EAArmorItem(Holder<ArmorMaterial> material, ArmorItem.Type type, Properties prop)
	{
		super(material, type, prop);
	}
}
