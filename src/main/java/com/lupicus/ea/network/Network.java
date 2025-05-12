package com.lupicus.ea.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public class Network
{
	@Environment(EnvType.CLIENT)
	public static void sendToServer(CustomPacketPayload msg)
	{
		ClientPlayNetworking.send(msg);
	}

	public static void sendToClient(CustomPacketPayload msg, ServerPlayer player)
	{
		ServerPlayNetworking.send(player, msg);
	}
}
