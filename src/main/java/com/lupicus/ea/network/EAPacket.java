package com.lupicus.ea.network;

import com.lupicus.ea.Main;
import com.lupicus.ea.item.IGuiRightClick;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.Context;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class EAPacket implements CustomPacketPayload
{
	public static final ResourceLocation PACKET_ID = ResourceLocation.fromNamespaceAndPath(Main.MODID, "right_click");
	public static final CustomPacketPayload.Type<EAPacket> TYPE = new CustomPacketPayload.Type<>(PACKET_ID);

	private int cmd;
	private int windowId;
	private int index;
	
	public EAPacket(int cmd, int windowId, int index)
	{
		this.cmd = cmd;
		this.windowId = windowId;
		this.index = index;
	}
	
	public void encode(FriendlyByteBuf buf)
	{
		buf.writeByte(cmd);
		buf.writeByte(windowId);
		buf.writeShort(index);
	}

	public static EAPacket readPacketData(FriendlyByteBuf buf)
	{
		int cmd = buf.readByte();
		int windowId = buf.readByte();
		int index = buf.readShort();
		return new EAPacket(cmd, windowId, index);
	}

	public static void writePacketData(FriendlyByteBuf buf, EAPacket msg)
	{
		msg.encode(buf);
	}

	public static void processPacket(EAPacket message, Context ctx)
	{
		ServerPlayer player = ctx.player();
		if (message.cmd == 1)
		{
			AbstractContainerMenu cont = player.containerMenu;
			if (message.windowId == cont.containerId && message.index >= 0 && cont.isValidSlotIndex(message.index))
			{
				Slot slot = cont.getSlot(message.index);
				if (slot.hasItem())
				{
					ItemStack stack = slot.getItem();
					if (stack.getItem() instanceof IGuiRightClick)
					{
						((IGuiRightClick) stack.getItem()).menuRightClick(stack);
						slot.setChanged();
					}
				}
			}
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
