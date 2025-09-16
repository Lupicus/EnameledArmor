package com.lupicus.ea.event;

import org.lwjgl.glfw.GLFW;

import com.lupicus.ea.item.IGuiRightClick;
import com.lupicus.ea.mixin.ACScreenAccessor;
import com.lupicus.ea.network.EAPacket;
import com.lupicus.ea.network.Network;

import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen.ItemPickerMenu;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ClientEvents
{
	public static void beforeInit(Minecraft mc, Screen screen, int sw, int sh)
	{
		if (screen instanceof AbstractContainerScreen<?>)
			ScreenMouseEvents.allowMouseClick(screen).register(ClientEvents::allowMouseClick);
	}

	public static boolean allowMouseClick(Screen screen, MouseButtonEvent event)
	{
		if (event.button() != GLFW.GLFW_MOUSE_BUTTON_RIGHT)
			return true;
		AbstractContainerScreen<?> cg = (AbstractContainerScreen<?>) screen;
		Slot slot = ((ACScreenAccessor) cg).getHoveredSlot();
		if (slot != null && slot.hasItem())
		{
			ItemStack stack = slot.getItem();
			if (stack.getItem() instanceof IGuiRightClick)
			{
				int index = -1;
				AbstractContainerMenu cont = cg.getMenu();
				if (cont.containerId == 0 && cont instanceof ItemPickerMenu)
				{
					// handle SlotWrapper
					Slot slot3;
					if (slot instanceof CreativeModeInventoryScreen.SlotWrapper)
						slot3 = ((CreativeModeInventoryScreen.SlotWrapper) slot).target;
					else
						slot3 = slot;
					// need to remap to what the server side is using
					Minecraft mc = Minecraft.getInstance();
					for (Slot slot2 : mc.player.inventoryMenu.slots)
					{
						if (slot2.container == slot3.container && slot2.getContainerSlot() == slot3.getContainerSlot())
						{
							index = slot2.index;
							break;
						}
					}
				}
				else
					index = slot.index;
				if (cont instanceof RecipeBookMenu)
				{
					// skip if in the crafting section
					Container slotCont = slot.container;
					if (slotCont instanceof CraftingContainer || slotCont instanceof ResultContainer)
						return true;
					if (cont instanceof AbstractFurnaceMenu && slotCont instanceof SimpleContainer)
						return true;
				}
				if (index >= 0)
				{
					Network.sendToServer(new EAPacket(1, cont.containerId, index));
					return false;
				}
			}
		}
		return true;
	}
}
