package com.lupicus.ea.item;

import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems
{
	public static final IArmorMaterial DA_MATERIAL = new EAArmorMaterial();
	public static final Item EA_HELMET = new EAArmorItem(DA_MATERIAL, EquipmentSlotType.HEAD, new Properties().group(ItemGroup.COMBAT), "ea_helmet");
	public static final Item EA_CHESTPLATE = new EAArmorItem(DA_MATERIAL, EquipmentSlotType.CHEST, new Properties().group(ItemGroup.COMBAT), "ea_chestplate");
	public static final Item EA_LEGGINGS = new EAArmorItem(DA_MATERIAL, EquipmentSlotType.LEGS, new Properties().group(ItemGroup.COMBAT), "ea_leggings");
	public static final Item EA_BOOTS = new EAArmorItem(DA_MATERIAL, EquipmentSlotType.FEET, new Properties().group(ItemGroup.COMBAT), "ea_boots");

	public static void register(IForgeRegistry<Item> forgeRegistry)
	{
		forgeRegistry.registerAll(EA_HELMET, EA_CHESTPLATE, EA_LEGGINGS, EA_BOOTS);
	}

	@OnlyIn(Dist.CLIENT)
	public static void register(ItemColors itemColors)
	{
		itemColors.register((itemstack, index) -> {
        	return index > 0 ? -1 : ((IDyeableArmorItem)itemstack.getItem()).getColor(itemstack);
        }, EA_HELMET, EA_CHESTPLATE, EA_LEGGINGS, EA_BOOTS);
	}
}
