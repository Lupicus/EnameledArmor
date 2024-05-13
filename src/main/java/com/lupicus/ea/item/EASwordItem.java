package com.lupicus.ea.item;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class EASwordItem extends SwordItem implements IGuiRightClick
{
	public EASwordItem(Tier tier, Properties prop)
	{
		super(tier, prop);
	}
}
