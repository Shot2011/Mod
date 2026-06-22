package com.onepiececraft.item;

import com.onepiececraft.capability.IPlayerData;
import com.onepiececraft.capability.OPCCapabilities;
import com.onepiececraft.data.EnumDevilFruit;
import com.onepiececraft.network.ModPackets;
import com.onepiececraft.network.packets.SyncPlayerDataPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DevilFruitItem extends Item {
    private final EnumDevilFruit fruitType;

    public DevilFruitItem(EnumDevilFruit fruitType, Properties properties) {
        super(properties);
        this.fruitType = fruitType;
    }

    public EnumDevilFruit getFruitType() {
        return fruitType;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(OPCCapabilities.PLAYER_DATA).ifPresent(data -> {
                EnumDevilFruit current = data.getDevilFruit();
                if (current != EnumDevilFruit.NONE) {
                    // Player already has a fruit — they die and lose both fruits
                    data.setDevilFruit(EnumDevilFruit.NONE);
                    serverPlayer.hurt(serverPlayer.level().damageSources().magic(), Float.MAX_VALUE);
                    serverPlayer.sendSystemMessage(Component.literal(
                            "§4You ate a second Devil Fruit! No one can survive two Devil Fruits!"));
                    stack.shrink(1);
                    ModPackets.sendToPlayer(new SyncPlayerDataPacket(data.serializeNBT()), serverPlayer);
                } else {
                    data.setDevilFruit(fruitType);
                    stack.shrink(1);
                    serverPlayer.sendSystemMessage(Component.literal(
                            "§dYou ate the §b" + fruitType.getDisplayName() + "§d! You now have Devil Fruit powers!"));
                    serverPlayer.sendSystemMessage(Component.literal(
                            "§eWarning: §7You will be weak in water! (Press 1-6 to use attacks)"));
                    ModPackets.sendToPlayer(new SyncPlayerDataPacket(data.serializeNBT()), serverPlayer);
                }
            });
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§bType: §f" + fruitType.getFruitType().name()));
        tooltip.add(Component.literal("§eAttacks: §f" + fruitType.getAttackCount() + " (Press 1-"
                + fruitType.getAttackCount() + ")"));
        tooltip.add(Component.literal("§cWarning: §7Eats 2 fruits = death!"));
        tooltip.add(Component.literal("§7Weakness: Water & Sea Stone"));
    }
}
