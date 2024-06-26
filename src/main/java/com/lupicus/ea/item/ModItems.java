package com.lupicus.ea.item;

import java.util.List;

import com.lupicus.ea.Main;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems
{
	// "ea" is not "ea_diamond" for backwards compatibility
	private static final Holder<ArmorMaterial> DA_MATERIAL = register(ArmorMaterials.DIAMOND, "ea");
	private static final int DA_MULT = 33;
	public static final Item EA_HELMET = armorItem(DA_MATERIAL, ArmorItem.Type.HELMET, DA_MULT);
	public static final Item EA_CHESTPLATE = armorItem(DA_MATERIAL, ArmorItem.Type.CHESTPLATE, DA_MULT);
	public static final Item EA_LEGGINGS = armorItem(DA_MATERIAL, ArmorItem.Type.LEGGINGS, DA_MULT);
	public static final Item EA_BOOTS = armorItem(DA_MATERIAL, ArmorItem.Type.BOOTS, DA_MULT);
	// 	Swords have no color- just enchantment glint.
	public static final Item EA_DIAMOND_SWORD = new EASwordItem(Tiers.DIAMOND, baseProps().attributes(SwordItem.createAttributes(Tiers.DIAMOND, 3, -2.4F)));
	public static final Item EA_BOW = new EABowItem(baseProps().durability(384));

	private static final Holder<ArmorMaterial> CH_MATERIAL = register(ArmorMaterials.CHAIN, "ea_chainmail");
	private static final int CH_MULT = 15;
	public static final Item EA_CHAINMAIL_HELMET = armorItem(CH_MATERIAL, ArmorItem.Type.HELMET, CH_MULT);
	public static final Item EA_CHAINMAIL_CHESTPLATE = armorItem(CH_MATERIAL, ArmorItem.Type.CHESTPLATE, CH_MULT);
	public static final Item EA_CHAINMAIL_LEGGINGS = armorItem(CH_MATERIAL, ArmorItem.Type.LEGGINGS, CH_MULT);
	public static final Item EA_CHAINMAIL_BOOTS = armorItem(CH_MATERIAL, ArmorItem.Type.BOOTS, CH_MULT);

	private static final Holder<ArmorMaterial> NT_MATERIAL = register(ArmorMaterials.NETHERITE, "ea_netherite");
	private static final int NT_MULT = 37;
	public static final Item EA_NETHERITE_HELMET = armorNTItem(NT_MATERIAL, ArmorItem.Type.HELMET, NT_MULT);
	public static final Item EA_NETHERITE_CHESTPLATE = armorNTItem(NT_MATERIAL, ArmorItem.Type.CHESTPLATE, NT_MULT);
	public static final Item EA_NETHERITE_LEGGINGS = armorNTItem(NT_MATERIAL, ArmorItem.Type.LEGGINGS, NT_MULT);
	public static final Item EA_NETHERITE_BOOTS = armorNTItem(NT_MATERIAL, ArmorItem.Type.BOOTS, NT_MULT);
	public static final Item EA_NETHERITE_SWORD = new EASwordItem(Tiers.NETHERITE, baseNTProps().attributes(SwordItem.createAttributes(Tiers.NETHERITE, 3, -2.4F)));

	private static final Holder<ArmorMaterial> IR_MATERIAL = register(ArmorMaterials.IRON, "ea_iron");
	private static final int IR_MULT = 15;
	public static final Item EA_IRON_HELMET = armorItem(IR_MATERIAL, ArmorItem.Type.HELMET, IR_MULT);
	public static final Item EA_IRON_CHESTPLATE = armorItem(IR_MATERIAL, ArmorItem.Type.CHESTPLATE, IR_MULT);
	public static final Item EA_IRON_LEGGINGS = armorItem(IR_MATERIAL, ArmorItem.Type.LEGGINGS, IR_MULT);
	public static final Item EA_IRON_BOOTS = armorItem(IR_MATERIAL, ArmorItem.Type.BOOTS, IR_MULT);
	public static final Item EA_IRON_SWORD = new EASwordItem(Tiers.IRON, baseProps().attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.4F)));

	private static EAArmorItem armorItem(Holder<ArmorMaterial> material, ArmorItem.Type type, int mult)
	{
		return new EAArmorItem(material, type, baseProps().durability(type.getDurability(mult)));
	}

	private static EAArmorItem armorNTItem(Holder<ArmorMaterial> material, ArmorItem.Type type, int mult)
	{
		return new EAArmorItem(material, type, baseNTProps().durability(type.getDurability(mult)));
	}

	private static Holder<ArmorMaterial> register(Holder<ArmorMaterial> hcopy, String name)
	{
		ArmorMaterial copy = hcopy.get();
		ResourceLocation res = ResourceLocation.fromNamespaceAndPath(Main.MODID, name);
		List<Layer> layers = List.of(new ArmorMaterial.Layer(res, "", true), new ArmorMaterial.Layer(res, "_overlay", false));
		return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, res,
				new ArmorMaterial(copy.defense(), copy.enchantmentValue(), copy.equipSound(), copy.repairIngredient(), layers, copy.toughness(), copy.knockbackResistance()));
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
		forgeRegistry.register("ea_helmet", EA_HELMET);
		forgeRegistry.register("ea_chestplate", EA_CHESTPLATE);
		forgeRegistry.register("ea_leggings", EA_LEGGINGS);
		forgeRegistry.register("ea_boots", EA_BOOTS);
		forgeRegistry.register("ea_diamond_sword", EA_DIAMOND_SWORD);
		forgeRegistry.register("ea_bow", EA_BOW);

		forgeRegistry.register("ea_iron_helmet", EA_IRON_HELMET);
		forgeRegistry.register("ea_iron_chestplate", EA_IRON_CHESTPLATE);
		forgeRegistry.register("ea_iron_leggings", EA_IRON_LEGGINGS);
		forgeRegistry.register("ea_iron_boots", EA_IRON_BOOTS);
		forgeRegistry.register("ea_iron_sword", EA_IRON_SWORD);

		forgeRegistry.register("ea_netherite_helmet", EA_NETHERITE_HELMET);
		forgeRegistry.register("ea_netherite_chestplate", EA_NETHERITE_CHESTPLATE);
		forgeRegistry.register("ea_netherite_leggings", EA_NETHERITE_LEGGINGS);
		forgeRegistry.register("ea_netherite_boots", EA_NETHERITE_BOOTS);
		forgeRegistry.register("ea_netherite_sword", EA_NETHERITE_SWORD);

		forgeRegistry.register("ea_chainmail_helmet", EA_CHAINMAIL_HELMET);
		forgeRegistry.register("ea_chainmail_chestplate", EA_CHAINMAIL_CHESTPLATE);
		forgeRegistry.register("ea_chainmail_leggings", EA_CHAINMAIL_LEGGINGS);
		forgeRegistry.register("ea_chainmail_boots", EA_CHAINMAIL_BOOTS);
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

	@OnlyIn(Dist.CLIENT)
	public static void register(RegisterColorHandlersEvent.Item event)
	{
		event.register((itemstack, index) -> {
			return index > 0 ? -1 : DyedItemColor.getOrDefault(itemstack, EAArmorItem.DEFCOLOR);
		}, EA_HELMET, EA_CHESTPLATE, EA_LEGGINGS, EA_BOOTS,
		   EA_IRON_HELMET, EA_IRON_CHESTPLATE, EA_IRON_LEGGINGS, EA_IRON_BOOTS,
		   EA_NETHERITE_HELMET, EA_NETHERITE_CHESTPLATE, EA_NETHERITE_LEGGINGS, EA_NETHERITE_BOOTS,
		   EA_CHAINMAIL_HELMET, EA_CHAINMAIL_CHESTPLATE, EA_CHAINMAIL_LEGGINGS, EA_CHAINMAIL_BOOTS);
	}
}
