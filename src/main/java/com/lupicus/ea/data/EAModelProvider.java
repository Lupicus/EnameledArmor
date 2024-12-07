package com.lupicus.ea.data;

import java.util.ArrayList;
import java.util.stream.Stream;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

public class EAModelProvider extends ModelProvider
{
	public EAModelProvider(PackOutput output, ExistingFileHelper existingFileHelper)
	{
		super(output);
	}

	@Override
	protected Stream<Block> getKnownBlocks()
	{
		return new ArrayList<Block>().stream();
	}

	@Override
	@SuppressWarnings("deprecation")
	protected Stream<Item> getKnownItems()
	{
		return BuiltInRegistries.ITEM.stream()
				.filter(item -> "ea".equals(item.builtInRegistryHolder().key().location().getNamespace()));
	}

	@Override
	protected BlockModelGenerators getBlockModelGenerators(BlockStateGeneratorCollector blocks, ItemInfoCollector items,
			SimpleModelCollector models)
	{
		return new EABlockGenerators(blocks, items, models);
	}

	@Override
	protected ItemModelGenerators getItemModelGenerators(ItemInfoCollector items, SimpleModelCollector models)
	{
		return new EAItemGenerators(items, models);
	}
}
