package com.lupicus.ea.network;

public class Register
{
	public static void initPackets()
	{
	    Network.registerMessage(EAPacket.class,
	    		EAPacket::writePacketData,
	    		EAPacket::readPacketData,
	    		EAPacket::processPacket);
	}
}
