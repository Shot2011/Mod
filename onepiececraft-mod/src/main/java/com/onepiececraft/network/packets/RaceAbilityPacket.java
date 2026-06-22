package com.onepiececraft.network.packets;

import com.onepiececraft.capability.OPCCapabilities;
import com.onepiececraft.network.ModPackets;
import com.onepiececraft.system.RaceAbilityHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RaceAbilityPacket {
    private final int abilitySlot; // 1 or 2

    public RaceAbilityPacket(int abilitySlot) {
        this.abilitySlot = abilitySlot;
    }

    public static RaceAbilityPacket decode(FriendlyByteBuf buf) {
        return new RaceAbilityPacket(buf.readInt());
    }

    public static void encode(RaceAbilityPacket packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.abilitySlot);
    }

    public static void handle(RaceAbilityPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;
            player.getCapability(OPCCapabilities.PLAYER_DATA).ifPresent(data -> {
                if (packet.abilitySlot == 1) {
                    if (!data.isAbility1Unlocked()) {
                        player.sendSystemMessage(Component.literal("§cReach Level 5 to unlock Race Ability 1!"));
                        return;
                    }
                    if (data.getAbility1Cooldown() > 0) {
                        player.sendSystemMessage(Component.literal("§cAbility 1 on cooldown: " + (data.getAbility1Cooldown() / 20) + "s"));
                        return;
                    }
                    if (data.getStamina() < 15) {
                        player.sendSystemMessage(Component.literal("§cNot enough stamina!"));
                        return;
                    }
                    data.setStamina(data.getStamina() - 15);
                    RaceAbilityHandler.execute(player, data.getRace(), 1);
                    data.setAbility1Cooldown(RaceAbilityHandler.getCooldown(data.getRace(), 1));
                } else if (packet.abilitySlot == 2) {
                    if (!data.isAbility2Unlocked()) {
                        player.sendSystemMessage(Component.literal("§cReach Level 35 to unlock Race Ability 2!"));
                        return;
                    }
                    if (data.getAbility2Cooldown() > 0) {
                        player.sendSystemMessage(Component.literal("§cAbility 2 on cooldown: " + (data.getAbility2Cooldown() / 20) + "s"));
                        return;
                    }
                    if (data.getStamina() < 30) {
                        player.sendSystemMessage(Component.literal("§cNot enough stamina!"));
                        return;
                    }
                    data.setStamina(data.getStamina() - 30);
                    RaceAbilityHandler.execute(player, data.getRace(), 2);
                    data.setAbility2Cooldown(RaceAbilityHandler.getCooldown(data.getRace(), 2));
                }
                ModPackets.sendToPlayer(new SyncPlayerDataPacket(data.serializeNBT()), player);
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
