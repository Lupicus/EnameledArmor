package com.lupicus.ea.data;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;

public class EAModelProvider extends FabricModelProvider
{
	public EAModelProvider(FabricDataOutput output)
	{
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerator) {
		EAItemGenerators inst = new EAItemGenerators(itemModelGenerator);
		inst.run();
	}
}
