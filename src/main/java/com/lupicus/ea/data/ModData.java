package com.lupicus.ea.data;

import com.lupicus.ea.Main;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Bus.MOD)
public class ModData
{
	@SubscribeEvent
	public static void onData(GatherDataEvent event)
	{
		DataGenerator dg = event.getGenerator();
		dg.addProvider(event.includeClient(), new EAModelProvider(dg.getPackOutput(), event.getExistingFileHelper()));
		dg.addProvider(event.includeServer(), new EARecipeProvider(dg.getPackOutput(), event.getLookupProvider()));
	}
}
