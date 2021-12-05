package com.lupicus.ea.item;

import com.lupicus.ea.Main;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class EAArmorMaterial implements ArmorMaterial
{
	private ArmorMaterial clone;
	private String materialName;
	
	public EAArmorMaterial(ArmorMaterial inClone, String materialPrefix) {
		clone = inClone;
		materialName = materialPrefix;
	}

	@Override
	public int getDurabilityForSlot(EquipmentSlot slotIn) {
		return clone.getDurabilityForSlot(slotIn);
	}

	@Override
	public int getDefenseForSlot(EquipmentSlot slotIn) {
		return clone.getDefenseForSlot(slotIn);
	}

	@Override
	public int getEnchantmentValue() {
		return clone.getEnchantmentValue();
	}

	@Override
	public SoundEvent getEquipSound() {
		return clone.getEquipSound();
	}

	@Override
	public Ingredient getRepairIngredient() {
		return clone.getRepairIngredient();
	}

	@Override
	public String getName()
	{
		return Main.MODID + ":" + materialName;
		//		return "ea:ea";
	}

	@Override
	public float getToughness() {
		return clone.getToughness();
	}
	
	@Override
	public float getKnockbackResistance() {
		return clone.getKnockbackResistance();
	}
}
