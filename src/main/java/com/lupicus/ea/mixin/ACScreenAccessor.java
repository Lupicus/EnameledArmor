package com.lupicus.ea.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;

@Mixin(AbstractContainerScreen.class)
public interface ACScreenAccessor {
	@Accessor
	Slot getHoveredSlot();
}
