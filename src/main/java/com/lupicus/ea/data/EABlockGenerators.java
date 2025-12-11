package com.lupicus.ea.data;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.resources.Identifier;

public class EABlockGenerators extends BlockModelGenerators
{
	public EABlockGenerators(Consumer<BlockModelDefinitionGenerator> blocks, ItemModelOutput items,
			BiConsumer<Identifier, ModelInstance> models) {
		super(blocks, items, models);
	}

	@Override
	public void run() {
	}
}
