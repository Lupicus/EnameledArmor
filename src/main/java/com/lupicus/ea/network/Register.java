package com.lupicus.ea.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.StreamCodec;

public class Register
{
	public static void initPackets()
	{
		PayloadTypeRegistry.playC2S().register(EAPacket.TYPE, StreamCodec.of(EAPacket::writePacketData, EAPacket::readPacketData));

		ServerPlayNetworking.registerGlobalReceiver(EAPacket.TYPE, EAPacket::processPacket);
	}
}
