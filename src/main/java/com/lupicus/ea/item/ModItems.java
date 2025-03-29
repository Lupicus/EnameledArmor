package com.lupicus.ea.item;

import java.util.function.Function;

import com.lupicus.ea.Main;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems
{
	// "ea" is not "ea_diamond" for backwards compatibility
	private static final ArmorMaterial DA_MATERIAL = create(ArmorMaterials.DIAMOND, "ea");
	public static final Item EA_HELMET = register("ea_helmet", EAArmorItem::new, baseProps().humanoidArmor(DA_MATERIAL, ArmorType.HELMET));
	public static final Item EA_CHESTPLATE = register("ea_chestplate", EAArmorItem::new, baseProps().humanoidArmor(DA_MATERIAL, ArmorType.CHESTPLATE));
	public static final Item EA_LEGGINGS = register("ea_leggings", EAArmorItem::new, baseProps().humanoidArmor(DA_MATERIAL, ArmorType.LEGGINGS));
	public static final Item EA_BOOTS = register("ea_boots", EAArmorItem::new, baseProps().humanoidArmor(DA_MATERIAL, ArmorType.BOOTS));
	// 	Swords have no color- just enchantment glint.
	public static final Item EA_DIAMOND_SWORD = register("ea_diamond_sword", EASwordItem::new, baseProps().sword(ToolMaterial.DIAMOND, 3.0F, -2.4F));
	public static final Item EA_BOW = register("ea_bow", EABowItem::new, baseProps().durability(384));

	private static final ArmorMaterial CH_MATERIAL = create(ArmorMaterials.CHAINMAIL, "ea_chainmail");
	public static final Item EA_CHAINMAIL_HELMET = register("ea_chainmail_helmet", EAArmorItem::new, baseProps().humanoidArmor(CH_MATERIAL, ArmorType.HELMET));
	public static final Item EA_CHAINMAIL_CHESTPLATE = register("ea_chainmail_chestplate", EAArmorItem::new, baseProps().humanoidArmor(CH_MATERIAL, ArmorType.CHESTPLATE));
	public static final Item EA_CHAINMAIL_LEGGINGS = register("ea_chainmail_leggings", EAArmorItem::new, baseProps().humanoidArmor(CH_MATERIAL, ArmorType.LEGGINGS));
	public static final Item EA_CHAINMAIL_BOOTS = register("ea_chainmail_boots", EAArmorItem::new, baseProps().humanoidArmor(CH_MATERIAL, ArmorType.BOOTS));

	private static final ArmorMaterial NT_MATERIAL = create(ArmorMaterials.NETHERITE, "ea_netherite");
	public static final Item EA_NETHERITE_HELMET = register("ea_netherite_helmet", EAArmorItem::new, baseNTProps().humanoidArmor(NT_MATERIAL, ArmorType.HELMET));
	public static final Item EA_NETHERITE_CHESTPLATE = register("ea_netherite_chestplate", EAArmorItem::new, baseNTProps().humanoidArmor(NT_MATERIAL, ArmorType.CHESTPLATE));
	public static final Item EA_NETHERITE_LEGGINGS = register("ea_netherite_leggings", EAArmorItem::new, baseNTProps().humanoidArmor(NT_MATERIAL, ArmorType.LEGGINGS));
	public static final Item EA_NETHERITE_BOOTS = register("ea_netherite_boots", EAArmorItem::new, baseNTProps().humanoidArmor(NT_MATERIAL, ArmorType.BOOTS));
	public static final Item EA_NETHERITE_SWORD = register("ea_netherite_sword", EASwordItem::new, baseNTProps().sword(ToolMaterial.NETHERITE, 3.0F, -2.4F));

	private static final ArmorMaterial IR_MATERIAL = create(ArmorMaterials.IRON, "ea_iron");
	public static final Item EA_IRON_HELMET = register("ea_iron_helmet", EAArmorItem::new, baseProps().humanoidArmor(IR_MATERIAL, ArmorType.HELMET));
	public static final Item EA_IRON_CHESTPLATE = register("ea_iron_chestplate", EAArmorItem::new, baseProps().humanoidArmor(IR_MATERIAL, ArmorType.CHESTPLATE));
	public static final Item EA_IRON_LEGGINGS = register("ea_iron_leggings", EAArmorItem::new, baseProps().humanoidArmor(IR_MATERIAL, ArmorType.LEGGINGS));
	public static final Item EA_IRON_BOOTS = register("ea_iron_boots", EAArmorItem::new, baseProps().humanoidArmor(IR_MATERIAL, ArmorType.BOOTS));
	public static final Item EA_IRON_SWORD = register("ea_iron_sword", EASwordItem::new, baseProps().sword(ToolMaterial.IRON, 3.0F, -2.4F));

	private static ArmorMaterial create(ArmorMaterial copy, String name)
	{
		ResourceLocation modelId = ResourceLocation.fromNamespaceAndPath(Main.MODID, name);
		ResourceKey<EquipmentAsset> assetId = ResourceKey.create(EquipmentAssets.ROOT_ID, modelId);
		return new ArmorMaterial(copy.durability(), copy.defense(), copy.enchantmentValue(), copy.equipSound(), copy.toughness(), copy.knockbackResistance(), copy.repairIngredient(), assetId);
	}

	private static Properties baseProps()
	{
		return new Properties().component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, false);
	}

	private static Properties baseNTProps()
	{
		return new Properties().fireResistant().component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, false);
	}

	public static void register(IForgeRegistry<Item> forgeRegistry)
	{
	}

	private static Item register(String name, Function<Properties, Item> func, Properties prop)
	{
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Main.MODID, name));
		return Items.registerItem(key, func, prop);
	}

	public static void setupTabs(BuildCreativeModeTabContentsEvent event)
	{
		if (event.getTabKey() == CreativeModeTabs.COMBAT)
		{
			event.accept(EA_CHAINMAIL_HELMET);
			event.accept(EA_CHAINMAIL_CHESTPLATE);
			event.accept(EA_CHAINMAIL_LEGGINGS);
			event.accept(EA_CHAINMAIL_BOOTS);
			event.accept(EA_IRON_HELMET);
			event.accept(EA_IRON_CHESTPLATE);
			event.accept(EA_IRON_LEGGINGS);
			event.accept(EA_IRON_BOOTS);
			event.accept(EA_HELMET);
			event.accept(EA_CHESTPLATE);
			event.accept(EA_LEGGINGS);
			event.accept(EA_BOOTS);
			event.accept(EA_NETHERITE_HELMET);
			event.accept(EA_NETHERITE_CHESTPLATE);
			event.accept(EA_NETHERITE_LEGGINGS);
			event.accept(EA_NETHERITE_BOOTS);
			event.accept(EA_IRON_SWORD);
			event.accept(EA_DIAMOND_SWORD);
			event.accept(EA_NETHERITE_SWORD);
			event.accept(EA_BOW);
		}
	}
}
