package com.onepiececraft.capability;

import com.onepiececraft.OnePieceCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OPCCapabilities {
    public static final Capability<IPlayerData> PLAYER_DATA =
            CapabilityManager.get(new CapabilityToken<>() {});

    public static final ResourceLocation PLAYER_DATA_ID =
            new ResourceLocation(OnePieceCraft.MOD_ID, "player_data");

    public static void register(RegisterCapabilitiesEvent event) {
        event.register(IPlayerData.class);
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<net.minecraft.world.entity.Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(PLAYER_DATA_ID, new PlayerDataProvider());
        }
    }
}
