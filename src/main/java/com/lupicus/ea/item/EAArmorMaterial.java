package com.lupicus.ea.item;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

public class EAArmorMaterial implements IArmorMaterial
{
	private final static IArmorMaterial clone = ArmorMaterial.DIAMOND;

	@Override
	public int getDurability(EquipmentSlotType slotIn) {
		return clone.getDurability(slotIn);
	}

	@Override
	public int getDamageReductionAmount(EquipmentSlotType slotIn) {
		return clone.getDamageReductionAmount(slotIn);
	}

	@Override
	public int getEnchantability() {
		return clone.getEnchantability();
	}

	@Override
	public SoundEvent getSoundEvent() {
		return clone.getSoundEvent();
	}

	@Override
	public Ingredient getRepairMaterial() {
		return clone.getRepairMaterial();
	}

	@Override
	public String getName()
	{
		return "ea:ea";
	}

	@Override
	public float getToughness() {
		return clone.getToughness();
	}
}
