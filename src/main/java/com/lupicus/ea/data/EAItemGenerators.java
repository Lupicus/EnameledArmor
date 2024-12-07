package com.lupicus.ea.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.lupicus.ea.item.EAArmorItem;
import com.lupicus.ea.item.ModItems;

import net.minecraft.client.color.item.Dye;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider.ItemInfoCollector;
import net.minecraft.client.data.models.ModelProvider.SimpleModelCollector;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.numeric.UseDuration;
import net.minecraft.client.renderer.item.properties.select.TrimMaterialProperty;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;

public class EAItemGenerators extends ItemModelGenerators
{
	private static final List<TrimMaterialData> GENERATED_TRIM_MODELS = List.of(
			new TrimMaterialData("quartz", TrimMaterials.QUARTZ, Map.of()),
			new TrimMaterialData("iron", TrimMaterials.IRON, Map.of(EquipmentAssets.IRON, "iron_darker")),
			new TrimMaterialData("netherite", TrimMaterials.NETHERITE, Map.of(EquipmentAssets.NETHERITE, "netherite_darker")),
			new TrimMaterialData("redstone", TrimMaterials.REDSTONE, Map.of()),
			new TrimMaterialData("copper", TrimMaterials.COPPER, Map.of()),
			new TrimMaterialData("gold", TrimMaterials.GOLD, Map.of(EquipmentAssets.GOLD, "gold_darker")),
			new TrimMaterialData("emerald", TrimMaterials.EMERALD, Map.of()),
			new TrimMaterialData("diamond", TrimMaterials.DIAMOND, Map.of(EquipmentAssets.DIAMOND, "diamond_darker")),
			new TrimMaterialData("lapis", TrimMaterials.LAPIS, Map.of()),
			new TrimMaterialData("amethyst", TrimMaterials.AMETHYST, Map.of()),
			new TrimMaterialData("resin", TrimMaterials.RESIN, Map.of()));

	public EAItemGenerators(ItemInfoCollector items, SimpleModelCollector models)
	{
		super(items, models);
	}

	@Override
	public void run()
	{
		generateTrimmableItem(ModItems.EA_HELMET);
		generateTrimmableItem(ModItems.EA_CHESTPLATE);
		generateTrimmableItem(ModItems.EA_LEGGINGS);
		generateTrimmableItem(ModItems.EA_BOOTS);
		generateTrimmableItem(ModItems.EA_IRON_HELMET);
		generateTrimmableItem(ModItems.EA_IRON_CHESTPLATE);
		generateTrimmableItem(ModItems.EA_IRON_LEGGINGS);
		generateTrimmableItem(ModItems.EA_IRON_BOOTS);
		generateTrimmableItem(ModItems.EA_CHAINMAIL_HELMET);
		generateTrimmableItem(ModItems.EA_CHAINMAIL_CHESTPLATE);
		generateTrimmableItem(ModItems.EA_CHAINMAIL_LEGGINGS);
		generateTrimmableItem(ModItems.EA_CHAINMAIL_BOOTS);
		generateTrimmableItem(ModItems.EA_NETHERITE_HELMET);
		generateTrimmableItem(ModItems.EA_NETHERITE_CHESTPLATE);
		generateTrimmableItem(ModItems.EA_NETHERITE_LEGGINGS);
		generateTrimmableItem(ModItems.EA_NETHERITE_BOOTS);
		generateFlatItem(ModItems.EA_DIAMOND_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
		generateFlatItem(ModItems.EA_IRON_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
		generateFlatItem(ModItems.EA_NETHERITE_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
		generateBow2(ModItems.EA_BOW);
	}

	protected void generateTrimmableItem(Item item)
	{
		ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(item);
		ResourceLocation resourcelocation1 = TextureMapping.getItemTexture(item);
		ResourceLocation resourcelocation2 = TextureMapping.getItemTexture(item, "_overlay");
		List<SelectItemModel.SwitchCase<ResourceKey<TrimMaterial>>> list = new ArrayList<>(GENERATED_TRIM_MODELS.size());

		Equippable equippable = item.components().get(DataComponents.EQUIPPABLE);
		if (equippable != null && equippable.slot().getType() == EquipmentSlot.Type.HUMANOID_ARMOR
				&& equippable.assetId().isPresent()) {
			ResourceKey<EquipmentAsset> assetId = equippable.assetId().get();
			String name = switch (equippable.slot()) {
			case HEAD -> "helmet";
			case CHEST -> "chestplate";
			case LEGS -> "leggings";
			case FEET -> "boots";
			default -> throw new UnsupportedOperationException();
			};

			for (TrimMaterialData itemmodelgenerators$trimmaterialdata : GENERATED_TRIM_MODELS) {
				ResourceLocation resourcelocation3 = resourcelocation
						.withSuffix("_" + itemmodelgenerators$trimmaterialdata.name() + "_trim");
				ResourceLocation resourcelocation4 = ResourceLocation.withDefaultNamespace(
						"trims/items/" + name + "_trim_" + itemmodelgenerators$trimmaterialdata.textureName(assetId));
				this.generateLayeredItem(resourcelocation3, resourcelocation1, resourcelocation2, resourcelocation4);
				ItemModel.Unbaked itemmodel$unbaked = ItemModelUtils.tintedModel(resourcelocation3, new Dye(EAArmorItem.DEFCOLOR));
				list.add(ItemModelUtils.when(itemmodelgenerators$trimmaterialdata.materialKey, itemmodel$unbaked));
			}

			ModelTemplates.TWO_LAYERED_ITEM.create(resourcelocation,
					TextureMapping.layered(resourcelocation1, resourcelocation2), this.modelOutput);
			ItemModel.Unbaked itemmodel$unbaked1 = ItemModelUtils.tintedModel(resourcelocation, new Dye(EAArmorItem.DEFCOLOR));

			this.itemModelOutput.accept(item,
					ItemModelUtils.select(new TrimMaterialProperty(), itemmodel$unbaked1, list));
		}
	}

	protected void generateBow2(Item item)
	{
		ResourceLocation model = ModelLocationUtils.getModelLocation(item);
		ModelTemplate tmpl = new ModelTemplate(Optional.of(model), Optional.empty(), TextureSlot.LAYER0);
		ItemModel.Unbaked itemmodel$unbaked = ItemModelUtils.plainModel(model);
		ItemModel.Unbaked itemmodel$unbaked1 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_pulling_0", tmpl));
		ItemModel.Unbaked itemmodel$unbaked2 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_pulling_1", tmpl));
		ItemModel.Unbaked itemmodel$unbaked3 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_pulling_2", tmpl));
		this.itemModelOutput.accept(item,
				ItemModelUtils.conditional(ItemModelUtils.isUsingItem(),
						ItemModelUtils.rangeSelect(new UseDuration(false), 0.05F, itemmodel$unbaked1,
								ItemModelUtils.override(itemmodel$unbaked2, 0.65F),
								ItemModelUtils.override(itemmodel$unbaked3, 0.9F)),
						itemmodel$unbaked));
	}

	static record TrimMaterialData(String name, ResourceKey<TrimMaterial> materialKey, Map<ResourceKey<EquipmentAsset>, String> overrideArmorMaterials) {
		public String textureName(ResourceKey<EquipmentAsset> mat) {
			return this.overrideArmorMaterials.getOrDefault(mat, this.name);
		}
	}
}
