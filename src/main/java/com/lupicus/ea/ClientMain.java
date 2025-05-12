package com.lupicus.ea;

import com.lupicus.ea.event.ClientEvents;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;

public class ClientMain implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		ScreenEvents.BEFORE_INIT.register(ClientEvents::beforeInit);
	}
}
