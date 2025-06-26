package com.lupicus.ea.network;

import net.minecraft.network.codec.StreamCodec;

public class Register
{
	public static void initPackets()
	{
		Network.INSTANCE.play().serverbound()
			.add(EAPacket.class, StreamCodec.ofMember(EAPacket::encode, EAPacket::decode), EAPacket::processPacket)
			.build();
	}
}
