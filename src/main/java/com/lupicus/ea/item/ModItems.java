package com.lupicus.ea.item;

import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems
{
	// "ea" is not "ea_diamond" for backwards compatibility
	private static final ArmorMaterial DA_MATERIAL = new EAArmorMaterial(ArmorMaterials.DIAMOND, "ea");
	public static final Item EA_HELMET = new EAArmorItem(DA_MATERIAL, EquipmentSlot.HEAD, new Properties().tab(CreativeModeTab.TAB_COMBAT), "ea_helmet");
	public static final Item EA_CHESTPLATE = new EAArmorItem(DA_MATERIAL, EquipmentSlot.CHEST, new Properties().tab(CreativeModeTab.TAB_COMBAT), "ea_chestplate");
	public static final Item EA_LEGGINGS = new EAArmorItem(DA_MATERIAL, EquipmentSlot.LEGS, new Properties().tab(CreativeModeTab.TAB_COMBAT), "ea_leggings");
	public static final Item EA_BOOTS = new EAArmorItem(DA_MATERIAL, EquipmentSlot.FEET, new Properties().tab(CreativeModeTab.TAB_COMBAT), "ea_boots");
	// 	Swords have no color- just enchantment glint.
    public static final Item EA_DIAMOND_SWORD = new EASwordItem(Tiers.DIAMOND, 3, -2.4F, (new Properties()).tab(CreativeModeTab.TAB_COMBAT), "ea_diamond_sword");
    public static final Item EA_BOW = new EABowItem((new Properties()).durability(384).tab(CreativeModeTab.TAB_COMBAT), "ea_bow");

    private static final ArmorMaterial CH_MATERIAL = new EAArmorMaterial(ArmorMaterials.CHAIN, "ea_chainmail");
	public static final Item EA_CHAINMAIL_HELMET = new EAArmorItem(CH_MATERIAL, EquipmentSlot.HEAD, new Properties().tab(CreativeModeTab.TAB_COMBAT), "ea_chainmail_helmet");
	public static final Item EA_CHAINMAIL_CHESTPLATE = new EAArmorItem(CH_MATERIAL, EquipmentSlot.CHEST, new Properties().tab(CreativeModeTab.TAB_COMBAT), "ea_chainmail_chestplate");
	public static final Item EA_CHAINMAIL_LEGGINGS = new EAArmorItem(CH_MATERIAL, EquipmentSlot.LEGS, new Properties().tab(CreativeModeTab.TAB_COMBAT), "ea_chainmail_leggings");
	public static final Item EA_CHAINMAIL_BOOTS = new EAArmorItem(CH_MATERIAL, EquipmentSlot.FEET, new Properties().tab(CreativeModeTab.TAB_COMBAT), "ea_chainmail_boots");

	private static final ArmorMaterial NT_MATERIAL = new EAArmorMaterial(ArmorMaterials.NETHERITE, "ea_netherite");
	public static final Item EA_NETHERITE_HELMET = new EAArmorItem(NT_MATERIAL, EquipmentSlot.HEAD, new Properties().tab(CreativeModeTab.TAB_COMBAT).fireResistant(), "ea_netherite_helmet");
	public static final Item EA_NETHERITE_CHESTPLATE = new EAArmorItem(NT_MATERIAL, EquipmentSlot.CHEST, new Properties().tab(CreativeModeTab.TAB_COMBAT).fireResistant(), "ea_netherite_chestplate");
	public static final Item EA_NETHERITE_LEGGINGS = new EAArmorItem(NT_MATERIAL, EquipmentSlot.LEGS, new Properties().tab(CreativeModeTab.TAB_COMBAT).fireResistant(), "ea_netherite_leggings");
	public static final Item EA_NETHERITE_BOOTS = new EAArmorItem(NT_MATERIAL, EquipmentSlot.FEET, new Properties().tab(CreativeModeTab.TAB_COMBAT).fireResistant(), "ea_netherite_boots");
    public static final Item EA_NETHERITE_SWORD = new EASwordItem(Tiers.NETHERITE, 3, -2.4F, (new Properties()).tab(CreativeModeTab.TAB_COMBAT).fireResistant(), "ea_netherite_sword");

	private static final ArmorMaterial IR_MATERIAL = new EAArmorMaterial(ArmorMaterials.IRON, "ea_iron");
	public static final Item EA_IRON_HELMET = new EAArmorItem(IR_MATERIAL, EquipmentSlot.HEAD, new Properties().tab(CreativeModeTab.TAB_COMBAT), "ea_iron_helmet");
	public static final Item EA_IRON_CHESTPLATE = new EAArmorItem(IR_MATERIAL, EquipmentSlot.CHEST, new Properties().tab(CreativeModeTab.TAB_COMBAT), "ea_iron_chestplate");
	public static final Item EA_IRON_LEGGINGS = new EAArmorItem(IR_MATERIAL, EquipmentSlot.LEGS, new Properties().tab(CreativeModeTab.TAB_COMBAT), "ea_iron_leggings");
	public static final Item EA_IRON_BOOTS = new EAArmorItem(IR_MATERIAL, EquipmentSlot.FEET, new Properties().tab(CreativeModeTab.TAB_COMBAT), "ea_iron_boots");
    public static final Item EA_IRON_SWORD = new EASwordItem(Tiers.IRON, 3, -2.4F, (new Properties()).tab(CreativeModeTab.TAB_COMBAT), "ea_iron_sword");

	public static void register(IForgeRegistry<Item> forgeRegistry)
	{
		forgeRegistry.registerAll(EA_HELMET, EA_CHESTPLATE, EA_LEGGINGS, EA_BOOTS, EA_DIAMOND_SWORD,
				EA_IRON_HELMET,EA_IRON_CHESTPLATE,EA_IRON_LEGGINGS, EA_IRON_BOOTS, EA_IRON_SWORD,
				EA_NETHERITE_HELMET,EA_NETHERITE_CHESTPLATE,EA_NETHERITE_LEGGINGS, EA_NETHERITE_BOOTS, EA_NETHERITE_SWORD,
				EA_CHAINMAIL_HELMET,EA_CHAINMAIL_CHESTPLATE,EA_CHAINMAIL_LEGGINGS, EA_CHAINMAIL_BOOTS,
				EA_BOW);
	}

	@OnlyIn(Dist.CLIENT)
	public static void register(ItemColors itemColors)
	{
		itemColors.register((itemstack, index) -> {
        	return index > 0 ? -1 : ((DyeableArmorItem)itemstack.getItem()).getColor(itemstack);
        }, EA_HELMET, EA_CHESTPLATE, EA_LEGGINGS, EA_BOOTS,
				EA_IRON_HELMET,EA_IRON_CHESTPLATE,EA_IRON_LEGGINGS, EA_IRON_BOOTS,
				EA_NETHERITE_HELMET,EA_NETHERITE_CHESTPLATE,EA_NETHERITE_LEGGINGS, EA_NETHERITE_BOOTS, 
				EA_CHAINMAIL_HELMET,EA_CHAINMAIL_CHESTPLATE,EA_CHAINMAIL_LEGGINGS, EA_CHAINMAIL_BOOTS);
	}
}
