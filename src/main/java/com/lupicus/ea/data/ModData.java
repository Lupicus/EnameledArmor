package com.lupicus.ea.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator.Pack;

public class ModData implements DataGeneratorEntrypoint
{
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator dg)
	{
		Pack pack = dg.createPack();
		pack.addProvider(EAModelProvider::new);
		pack.addProvider(EARecipeProvider.Runner::new);
	}
}
