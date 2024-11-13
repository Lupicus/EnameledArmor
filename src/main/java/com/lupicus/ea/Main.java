package com.lupicus.ea;

import org.jetbrains.annotations.NotNull;

import com.lupicus.ea.item.ModItems;
import com.lupicus.ea.item.crafting.EARecipe;
import com.lupicus.ea.network.Register;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MODID)
public class Main
{
    public static final String MODID = "ea"; 

    public Main(FMLJavaModLoadingContext context)
    {
		context.getModEventBus().addListener(this::setup);
    }

	public void setup(final FMLCommonSetupEvent event)
	{
		Register.initPackets();
	}

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents
    {
	    @SubscribeEvent
	    public static void onRegister(final RegisterEvent event)
	    {
	    	@NotNull
			ResourceKey<? extends Registry<?>> key = event.getRegistryKey();
	    	if (key.equals(ForgeRegistries.Keys.ITEMS))
	    		ModItems.register(event.getForgeRegistry());
	    	else if (key.equals(ForgeRegistries.Keys.RECIPE_SERIALIZERS))
	    		event.getForgeRegistry().register(EARecipe.NAME, EARecipe.SERIALIZER);
	    }

	    @SubscribeEvent
	    public static void onCreativeTab(BuildCreativeModeTabContentsEvent event)
	    {
	    	ModItems.setupTabs(event);
	    }

        @SubscribeEvent
        public static void onColorsRegistry(final RegisterColorHandlersEvent.Item event)
        {
        	ModItems.register(event);
        }
    }
}
