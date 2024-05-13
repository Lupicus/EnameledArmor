package com.lupicus.ea.data;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.lupicus.ea.Main;
import com.lupicus.ea.item.EAArmorItem;
import com.lupicus.ea.item.ModItems;

import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelBuilder.OverrideBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class EAModelProvider extends ItemModelProvider
{
	private static final List<TrimModelData> GENERATED_TRIM_MODELS = List.of(
			new TrimModelData("quartz", 0.1F, Map.of()),
			new TrimModelData("iron", 0.2F, Map.of(ArmorMaterials.IRON, "iron_darker")),
			new TrimModelData("netherite", 0.3F, Map.of(ArmorMaterials.NETHERITE, "netherite_darker")),
			new TrimModelData("redstone", 0.4F, Map.of()),
			new TrimModelData("copper", 0.5F, Map.of()),
			new TrimModelData("gold", 0.6F, Map.of(ArmorMaterials.GOLD, "gold_darker")),
			new TrimModelData("emerald", 0.7F, Map.of()),
			new TrimModelData("diamond", 0.8F, Map.of(ArmorMaterials.DIAMOND, "diamond_darker")),
			new TrimModelData("lapis", 0.9F, Map.of()),
			new TrimModelData("amethyst", 1.0F, Map.of()));
	private Field fTextures = null;

	public EAModelProvider(PackOutput output, ExistingFileHelper existingFileHelper)
	{
		super(output, Main.MODID, existingFileHelper);
		try {
			fTextures = ModelBuilder.class.getDeclaredField("textures");
			fTextures.setAccessible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void registerModels()
	{
		add(ModItems.EA_HELMET);
		add(ModItems.EA_CHESTPLATE);
		add(ModItems.EA_LEGGINGS);
		add(ModItems.EA_BOOTS);
		add(ModItems.EA_IRON_HELMET);
		add(ModItems.EA_IRON_CHESTPLATE);
		add(ModItems.EA_IRON_LEGGINGS);
		add(ModItems.EA_IRON_BOOTS);
		add(ModItems.EA_CHAINMAIL_HELMET);
		add(ModItems.EA_CHAINMAIL_CHESTPLATE);
		add(ModItems.EA_CHAINMAIL_LEGGINGS);
		add(ModItems.EA_CHAINMAIL_BOOTS);
		add(ModItems.EA_NETHERITE_HELMET);
		add(ModItems.EA_NETHERITE_CHESTPLATE);
		add(ModItems.EA_NETHERITE_LEGGINGS);
		add(ModItems.EA_NETHERITE_BOOTS);
	}

	private void add(Item item)
	{
		if (item instanceof EAArmorItem)
		{
			EAArmorItem armor = (EAArmorItem) item;
			ResourceLocation key = ForgeRegistries.ITEMS.getKey(armor);
			makeTrims(armor, key);
			ItemModelBuilder b = getBuilder(key.getPath());
			basic(b, key);
			// overrides
			for (TrimModelData trim : GENERATED_TRIM_MODELS)
			{
				OverrideBuilder ob = b.override();
				ob.model(getTrimBuilder(armor, key, trim));
				ob.predicate(ItemModelGenerators.TRIM_TYPE_PREDICATE_ID, trim.itemModelIndex());
			}
		}
	}

	private ItemModelBuilder getTrimBuilder(EAArmorItem armor, ResourceLocation key, TrimModelData trim)
	{
		return getBuilder(key.getPath() + "_" + trim.name(armor.getMaterial()) + "_trim");
	}

	private void makeTrims(EAArmorItem armor, ResourceLocation key)
	{
		for (TrimModelData trim : GENERATED_TRIM_MODELS)
		{
			ItemModelBuilder b = getTrimBuilder(armor, key, trim);
			basic(b, key);
			Map<String, String> textures = getTextures(b);
			textures.put("layer2", "minecraft:trims/items/" + armor.getType().getName() + "_trim_" + trim.name(armor.getMaterial()));
		}
	}

	private void basic(ItemModelBuilder b, ResourceLocation key)
	{
		b.parent(new ModelFile.UncheckedModelFile("item/generated"));
		String path = ITEM_FOLDER + "/" + key.getPath();
		b.texture("layer0", path);
		b.texture("layer1", path+ "_overlay");
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> getTextures(ItemModelBuilder b)
	{
		Map<String, String> ret = null;
		try {
			ret = (Map<String, String>) fTextures.get(b);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	static record TrimModelData(String name, float itemModelIndex, Map<Holder<ArmorMaterial>, String> overrideArmorMaterials) {
		public String name(Holder<ArmorMaterial> mat) {
			return this.overrideArmorMaterials.getOrDefault(mat, this.name);
		}
	}
}
