package com.lupicus.ea.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems
{
	// "ea" is not "ea_diamond" for backwards compatibility
	private static final ArmorMaterial DA_MATERIAL = new EAArmorMaterial(ArmorMaterials.DIAMOND, "ea");
	public static final Item EA_HELMET = new EAArmorItem(DA_MATERIAL, ArmorItem.Type.HELMET, new Properties());
	public static final Item EA_CHESTPLATE = new EAArmorItem(DA_MATERIAL, ArmorItem.Type.CHESTPLATE, new Properties());
	public static final Item EA_LEGGINGS = new EAArmorItem(DA_MATERIAL, ArmorItem.Type.LEGGINGS, new Properties());
	public static final Item EA_BOOTS = new EAArmorItem(DA_MATERIAL, ArmorItem.Type.BOOTS, new Properties());
	// 	Swords have no color- just enchantment glint.
	public static final Item EA_DIAMOND_SWORD = new EASwordItem(Tiers.DIAMOND, 3, -2.4F, new Properties());
	public static final Item EA_BOW = new EABowItem((new Properties()).durability(384));

	private static final ArmorMaterial CH_MATERIAL = new EAArmorMaterial(ArmorMaterials.CHAIN, "ea_chainmail");
	public static final Item EA_CHAINMAIL_HELMET = new EAArmorItem(CH_MATERIAL, ArmorItem.Type.HELMET, new Properties());
	public static final Item EA_CHAINMAIL_CHESTPLATE = new EAArmorItem(CH_MATERIAL, ArmorItem.Type.CHESTPLATE, new Properties());
	public static final Item EA_CHAINMAIL_LEGGINGS = new EAArmorItem(CH_MATERIAL, ArmorItem.Type.LEGGINGS, new Properties());
	public static final Item EA_CHAINMAIL_BOOTS = new EAArmorItem(CH_MATERIAL, ArmorItem.Type.BOOTS, new Properties());

	private static final ArmorMaterial NT_MATERIAL = new EAArmorMaterial(ArmorMaterials.NETHERITE, "ea_netherite");
	public static final Item EA_NETHERITE_HELMET = new EAArmorItem(NT_MATERIAL, ArmorItem.Type.HELMET, new Properties().fireResistant());
	public static final Item EA_NETHERITE_CHESTPLATE = new EAArmorItem(NT_MATERIAL, ArmorItem.Type.CHESTPLATE, new Properties().fireResistant());
	public static final Item EA_NETHERITE_LEGGINGS = new EAArmorItem(NT_MATERIAL, ArmorItem.Type.LEGGINGS, new Properties().fireResistant());
	public static final Item EA_NETHERITE_BOOTS = new EAArmorItem(NT_MATERIAL, ArmorItem.Type.BOOTS, new Properties().fireResistant());
	public static final Item EA_NETHERITE_SWORD = new EASwordItem(Tiers.NETHERITE, 3, -2.4F, new Properties().fireResistant());

	private static final ArmorMaterial IR_MATERIAL = new EAArmorMaterial(ArmorMaterials.IRON, "ea_iron");
	public static final Item EA_IRON_HELMET = new EAArmorItem(IR_MATERIAL, ArmorItem.Type.HELMET, new Properties());
	public static final Item EA_IRON_CHESTPLATE = new EAArmorItem(IR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Properties());
	public static final Item EA_IRON_LEGGINGS = new EAArmorItem(IR_MATERIAL, ArmorItem.Type.LEGGINGS, new Properties());
	public static final Item EA_IRON_BOOTS = new EAArmorItem(IR_MATERIAL, ArmorItem.Type.BOOTS, new Properties());
	public static final Item EA_IRON_SWORD = new EASwordItem(Tiers.IRON, 3, -2.4F, new Properties());

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
			return index > 0 ? -1 : ((DyeableArmorItem)itemstack.getItem()).getColor(itemstack);
		}, EA_HELMET, EA_CHESTPLATE, EA_LEGGINGS, EA_BOOTS,
		   EA_IRON_HELMET, EA_IRON_CHESTPLATE, EA_IRON_LEGGINGS, EA_IRON_BOOTS,
		   EA_NETHERITE_HELMET, EA_NETHERITE_CHESTPLATE, EA_NETHERITE_LEGGINGS, EA_NETHERITE_BOOTS,
		   EA_CHAINMAIL_HELMET, EA_CHAINMAIL_CHESTPLATE, EA_CHAINMAIL_LEGGINGS, EA_CHAINMAIL_BOOTS);
	}
}
