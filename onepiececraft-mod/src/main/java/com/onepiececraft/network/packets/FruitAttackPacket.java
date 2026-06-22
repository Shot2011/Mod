package com.onepiececraft.network.packets;

import com.onepiececraft.capability.IPlayerData;
import com.onepiececraft.capability.OPCCapabilities;
import com.onepiececraft.data.EnumDevilFruit;
import com.onepiececraft.network.ModPackets;
import com.onepiececraft.system.FruitAttackHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FruitAttackPacket {
    private final int attackIndex; // 0-5

    public FruitAttackPacket(int attackIndex) {
        this.attackIndex = attackIndex;
    }

    public static FruitAttackPacket decode(FriendlyByteBuf buf) {
        return new FruitAttackPacket(buf.readInt());
    }

    public static void encode(FruitAttackPacket packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.attackIndex);
    }

    public static void handle(FruitAttackPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;
            player.getCapability(OPCCapabilities.PLAYER_DATA).ifPresent(data -> {
                EnumDevilFruit fruit = data.getDevilFruit();
                if (fruit == EnumDevilFruit.NONE) return;
                if (packet.attackIndex >= fruit.getAttackCount()) return;
                int[] cooldowns = data.getFruitAttackCooldowns();
                if (cooldowns[packet.attackIndex] > 0) return;
                if (data.getStamina() < 10) {
                    player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§cNot enough stamina!"));
                    return;
                }
                data.setStamina(data.getStamina() - 10);
                FruitAttackHandler.execute(player, fruit, packet.attackIndex);
                cooldowns[packet.attackIndex] = FruitAttackHandler.getCooldown(fruit, packet.attackIndex);
                ModPackets.sendToPlayer(new SyncPlayerDataPacket(data.serializeNBT()), player);
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
