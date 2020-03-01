package com.lupicus.ea;

import com.lupicus.ea.item.ModItems;
import com.lupicus.ea.item.crafting.EARecipe;
import com.lupicus.ea.network.Register;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MODID)
public class Main
{
    public static final String MODID = "ea"; 

    public Main()
    {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

	public void setup(final FMLCommonSetupEvent event)
	{
		Register.initPackets();
	}

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents
    {
    	@SubscribeEvent
    	public static void onItemsRegistry(final RegistryEvent.Register<Item> event)
    	{
    		ModItems.register(event.getRegistry());
    	}

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void onColorsRegistry(final ColorHandlerEvent.Item event)
        {
        	ModItems.register(event.getItemColors());
        }

        @SubscribeEvent
        public static void onRecipeRegistry(final RegistryEvent.Register<IRecipeSerializer<?>> event)
        {
        	event.getRegistry().register(EARecipe.CRAFTING_EA);
        }
    }
}
