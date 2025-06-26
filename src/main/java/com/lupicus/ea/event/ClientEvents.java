package com.lupicus.ea.event;

import org.lwjgl.glfw.GLFW;

import com.lupicus.ea.Main;
import com.lupicus.ea.item.IGuiRightClick;
import com.lupicus.ea.network.EAPacket;
import com.lupicus.ea.network.Network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen.ItemPickerMenu;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent.MouseButtonPressed;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)
public class ClientEvents
{
	@SubscribeEvent
	public static boolean onMouseScreenEvent(MouseButtonPressed.Pre event)
	{
		if (event.getButton() != GLFW.GLFW_MOUSE_BUTTON_RIGHT)
			return false;
		Screen gui = event.getScreen();
		if (gui == null || !(gui instanceof AbstractContainerScreen<?>))
			return false;
		AbstractContainerScreen<?> cg = (AbstractContainerScreen<?>) gui;
		Slot slot = cg.getSlotUnderMouse();
		if (slot != null && slot.hasItem())
		{
			ItemStack stack = slot.getItem();
			if (stack.getItem() instanceof IGuiRightClick)
			{
				AbstractContainerMenu cont = cg.getMenu();
				int index = -1;
				if (cont.containerId == 0 && cont instanceof ItemPickerMenu)
				{
					// need to remap to what the server side is using
					Minecraft mc = gui.getMinecraft();
					for (Slot slot2 : mc.player.inventoryMenu.slots)
					{
						if (slot2.isSameInventory(slot) && slot2.getSlotIndex() == slot.getSlotIndex())
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
						return false;
					if (cont instanceof AbstractFurnaceMenu && slotCont instanceof SimpleContainer)
						return false;
				}
				if (index >= 0)
				{
					Network.sendToServer(new EAPacket(1, cont.containerId, index));
					return true;
				}
			}
		}
		return false;
	}
}
