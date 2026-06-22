package com.onepiececraft;

import com.onepiececraft.capability.OPCCapabilities;
import com.onepiececraft.event.ModClientEvents;
import com.onepiececraft.event.ModCommonEvents;
import com.onepiececraft.init.*;
import com.onepiececraft.keybinding.ModKeybindings;
import com.onepiececraft.network.ModPackets;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(OnePieceCraft.MOD_ID)
public class OnePieceCraft {
    public static final String MOD_ID = "onepiececraft";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public OnePieceCraft() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);
        ModEffects.EFFECTS.register(modEventBus);
        ModCreativeTab.CREATIVE_MODE_TABS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(OPCCapabilities::register);

        MinecraftForge.EVENT_BUS.register(new ModCommonEvents());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModPackets::register);
        LOGGER.info("[OnePieceCraft] Common setup done.");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ModClientEvents());
        ModKeybindings.register();
        LOGGER.info("[OnePieceCraft] Client setup done.");
    }
}
