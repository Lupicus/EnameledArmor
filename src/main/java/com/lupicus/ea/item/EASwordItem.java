package com.lupicus.ea.item;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ToolMaterial;

public class EASwordItem extends SwordItem implements IGuiRightClick
{
	public EASwordItem(ToolMaterial mat, float dmg, float spd, Properties prop)
	{
		super(mat, dmg, spd, prop);
	}
}
