package com.onepiececraft.network.packets;

import com.onepiececraft.capability.OPCCapabilities;
import com.onepiececraft.data.EnumFaction;
import com.onepiececraft.network.ModPackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FactionChoicePacket {
    private final int factionOrdinal;

    public FactionChoicePacket(int factionOrdinal) {
        this.factionOrdinal = factionOrdinal;
    }

    public static FactionChoicePacket decode(FriendlyByteBuf buf) {
        return new FactionChoicePacket(buf.readInt());
    }

    public static void encode(FactionChoicePacket packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.factionOrdinal);
    }

    public static void handle(FactionChoicePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;
            player.getCapability(OPCCapabilities.PLAYER_DATA).ifPresent(data -> {
                if (data.isFactionChosen()) {
                    player.sendSystemMessage(Component.literal("§cYou have already chosen your faction!"));
                    return;
                }
                EnumFaction[] factions = EnumFaction.values();
                if (packet.factionOrdinal < 0 || packet.factionOrdinal >= factions.length) return;
                EnumFaction chosen = factions[packet.factionOrdinal];
                if (chosen == EnumFaction.NONE) return;
                data.setFaction(chosen);
                data.setFactionChosen(true);
                if (chosen == EnumFaction.PIRATE) {
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, Integer.MAX_VALUE, 0, false, false));
                    player.sendSystemMessage(Component.literal("§6⚓ You have joined the Pirates! Seek freedom and treasure!"));
                    player.sendSystemMessage(Component.literal("§7Bonus: +5% Speed, +5% Damage. Kill mobs to gain §eBounty§7!"));
                } else if (chosen == EnumFaction.MARINE) {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));
                    player.sendSystemMessage(Component.literal("§3⚓ You have joined the Marines! Uphold justice!"));
                    player.sendSystemMessage(Component.literal("§7Bonus: +5% Resistance, +10% Healing. Kill evil mobs to gain §bHonor§7!"));
                }
                ModPackets.sendToPlayer(new SyncPlayerDataPacket(data.serializeNBT()), player);
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
