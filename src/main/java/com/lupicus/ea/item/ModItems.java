package com.lupicus.ea.item;

import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems
{
	// "ea" is not "ea_diamond" for backwards compatibility
	private static final IArmorMaterial DA_MATERIAL = new EAArmorMaterial(ArmorMaterial.DIAMOND,"ea");
	public static final Item EA_HELMET = new EAArmorItem(DA_MATERIAL, EquipmentSlotType.HEAD, new Properties().group(ItemGroup.COMBAT), "ea_helmet");
	public static final Item EA_CHESTPLATE = new EAArmorItem(DA_MATERIAL, EquipmentSlotType.CHEST, new Properties().group(ItemGroup.COMBAT), "ea_chestplate");
	public static final Item EA_LEGGINGS = new EAArmorItem(DA_MATERIAL, EquipmentSlotType.LEGS, new Properties().group(ItemGroup.COMBAT), "ea_leggings");
	public static final Item EA_BOOTS = new EAArmorItem(DA_MATERIAL, EquipmentSlotType.FEET, new Properties().group(ItemGroup.COMBAT), "ea_boots");
	// 	Swords have no color- just enchantment glint.
    public static final Item EA_DIAMOND_SWORD = new EASwordItem(ItemTier.DIAMOND, 3, -2.4F, (new Item.Properties()).group(ItemGroup.COMBAT),"ea_diamond_sword");
    public static final Item EA_BOW = new EABowItem((new Item.Properties()).maxDamage(384).group(ItemGroup.COMBAT),"ea_bow");
	private static final IArmorMaterial CH_MATERIAL = new EAArmorMaterial(ArmorMaterial.CHAIN,"ea_chainmail");
	public static final Item EA_CHAINMAIL_HELMET = new EAArmorItem(CH_MATERIAL, EquipmentSlotType.HEAD, new Properties().group(ItemGroup.COMBAT), "ea_chainmail_helmet");
	public static final Item EA_CHAINMAIL_CHESTPLATE = new EAArmorItem(CH_MATERIAL, EquipmentSlotType.CHEST, new Properties().group(ItemGroup.COMBAT), "ea_chainmail_chestplate");
	public static final Item EA_CHAINMAIL_LEGGINGS = new EAArmorItem(CH_MATERIAL, EquipmentSlotType.LEGS, new Properties().group(ItemGroup.COMBAT), "ea_chainmail_leggings");
	public static final Item EA_CHAINMAIL_BOOTS = new EAArmorItem(CH_MATERIAL, EquipmentSlotType.FEET, new Properties().group(ItemGroup.COMBAT), "ea_chainmail_boots");

	private static final IArmorMaterial IR_MATERIAL = new EAArmorMaterial(ArmorMaterial.IRON,"ea_iron");
	public static final Item EA_IRON_HELMET = new EAArmorItem(IR_MATERIAL, EquipmentSlotType.HEAD, new Properties().group(ItemGroup.COMBAT), "ea_iron_helmet");
	public static final Item EA_IRON_CHESTPLATE = new EAArmorItem(IR_MATERIAL, EquipmentSlotType.CHEST, new Properties().group(ItemGroup.COMBAT), "ea_iron_chestplate");
	public static final Item EA_IRON_LEGGINGS = new EAArmorItem(IR_MATERIAL, EquipmentSlotType.LEGS, new Properties().group(ItemGroup.COMBAT), "ea_iron_leggings");
	public static final Item EA_IRON_BOOTS = new EAArmorItem(IR_MATERIAL, EquipmentSlotType.FEET, new Properties().group(ItemGroup.COMBAT), "ea_iron_boots");

	
	public static void register(IForgeRegistry<Item> forgeRegistry)
	{
		Item t = Items.BOW;
		forgeRegistry.registerAll(EA_HELMET, EA_CHESTPLATE, EA_LEGGINGS, EA_BOOTS,
				EA_IRON_HELMET,EA_IRON_CHESTPLATE,EA_IRON_LEGGINGS, EA_IRON_BOOTS,
				EA_CHAINMAIL_HELMET,EA_CHAINMAIL_CHESTPLATE,EA_CHAINMAIL_LEGGINGS, EA_CHAINMAIL_BOOTS,
				EA_DIAMOND_SWORD,EA_BOW);
	}

	@OnlyIn(Dist.CLIENT)
	public static void register(ItemColors itemColors)
	{
		itemColors.register((itemstack, index) -> {
        	return index > 0 ? -1 : ((IDyeableArmorItem)itemstack.getItem()).getColor(itemstack);
        }, EA_HELMET, EA_CHESTPLATE, EA_LEGGINGS, EA_BOOTS,
				EA_IRON_HELMET,EA_IRON_CHESTPLATE,EA_IRON_LEGGINGS, EA_IRON_BOOTS,
				EA_CHAINMAIL_HELMET,EA_CHAINMAIL_CHESTPLATE,EA_CHAINMAIL_LEGGINGS, EA_CHAINMAIL_BOOTS);
	}
}
