package com.onepiececraft.network;

import com.onepiececraft.OnePieceCraft;
import com.onepiececraft.network.packets.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPackets {
    private static final String PROTOCOL_VERSION = "1";
    private static int packetId = 0;

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(OnePieceCraft.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        CHANNEL.messageBuilder(FruitAttackPacket.class, packetId++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(FruitAttackPacket::decode)
                .encoder(FruitAttackPacket::encode)
                .consumerMainThread(FruitAttackPacket::handle)
                .add();

        CHANNEL.messageBuilder(FactionChoicePacket.class, packetId++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(FactionChoicePacket::decode)
                .encoder(FactionChoicePacket::encode)
                .consumerMainThread(FactionChoicePacket::handle)
                .add();

        CHANNEL.messageBuilder(RaceAbilityPacket.class, packetId++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(RaceAbilityPacket::decode)
                .encoder(RaceAbilityPacket::encode)
                .consumerMainThread(RaceAbilityPacket::handle)
                .add();

        CHANNEL.messageBuilder(SyncPlayerDataPacket.class, packetId++, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncPlayerDataPacket::decode)
                .encoder(SyncPlayerDataPacket::encode)
                .consumerMainThread(SyncPlayerDataPacket::handle)
                .add();
    }

    public static void sendToPlayer(Object packet, ServerPlayer player) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void sendToServer(Object packet) {
        CHANNEL.sendToServer(packet);
    }
}
