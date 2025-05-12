package com.lupicus.ea;

import com.lupicus.ea.item.ModItems;
import com.lupicus.ea.item.crafting.EARecipe;
import com.lupicus.ea.network.Register;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class Main implements ModInitializer
{
    public static final String MODID = "ea";

	@Override
	public void onInitialize()
	{
		Register.initPackets();
		ModItems.register();
		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, EARecipe.NAME, EARecipe.SERIALIZER);
		ModItems.setupTabs();
	}
}
