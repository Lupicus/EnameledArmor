package com.lupicus.ea.data;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.blockstates.BlockStateGenerator;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.resources.ResourceLocation;

public class EABlockGenerators extends BlockModelGenerators
{
	public EABlockGenerators(Consumer<BlockStateGenerator> blocks, ItemModelOutput items,
			BiConsumer<ResourceLocation, ModelInstance> models) {
		super(blocks, items, models);
	}

	@Override
	public void run() {
	}
}
