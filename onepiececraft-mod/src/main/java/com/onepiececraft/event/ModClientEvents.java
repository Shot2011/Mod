package com.onepiececraft.event;

import com.onepiececraft.capability.IPlayerData;
import com.onepiececraft.capability.OPCCapabilities;
import com.onepiececraft.data.EnumDevilFruit;
import com.onepiececraft.keybinding.ModKeybindings;
import com.onepiececraft.network.ModPackets;
import com.onepiececraft.network.packets.FactionChoicePacket;
import com.onepiececraft.network.packets.FruitAttackPacket;
import com.onepiececraft.network.packets.RaceAbilityPacket;
import com.onepiececraft.screen.FactionChoiceScreen;
import com.onepiececraft.screen.StatusScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModClientEvents {

    private boolean gPressedLastTick = false;

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || mc.screen != null) return;

        // G key — faction chooser or status screen
        if (ModKeybindings.OPEN_MENU.consumeClick()) {
            player.getCapability(OPCCapabilities.PLAYER_DATA).ifPresent(data -> {
                if (!data.isFactionChosen()) {
                    mc.setScreen(new FactionChoiceScreen());
                } else {
                    mc.setScreen(new StatusScreen(data));
                }
            });
        }

        // Fruit attacks 1-6
        if (ModKeybindings.FRUIT_ATTACK_1.consumeClick()) sendFruitAttack(0);
        if (ModKeybindings.FRUIT_ATTACK_2.consumeClick()) sendFruitAttack(1);
        if (ModKeybindings.FRUIT_ATTACK_3.consumeClick()) sendFruitAttack(2);
        if (ModKeybindings.FRUIT_ATTACK_4.consumeClick()) sendFruitAttack(3);
        if (ModKeybindings.FRUIT_ATTACK_5.consumeClick()) sendFruitAttack(4);
        if (ModKeybindings.FRUIT_ATTACK_6.consumeClick()) sendFruitAttack(5);

        // Race abilities R and F
        if (ModKeybindings.RACE_ABILITY_1.consumeClick()) ModPackets.sendToServer(new RaceAbilityPacket(1));
        if (ModKeybindings.RACE_ABILITY_2.consumeClick()) ModPackets.sendToServer(new RaceAbilityPacket(2));
    }

    private void sendFruitAttack(int index) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        mc.player.getCapability(OPCCapabilities.PLAYER_DATA).ifPresent(data -> {
            EnumDevilFruit fruit = data.getDevilFruit();
            if (fruit == EnumDevilFruit.NONE) return;
            if (index >= fruit.getAttackCount()) return;
            ModPackets.sendToServer(new FruitAttackPacket(index));
        });
    }

    // HUD Overlay — shows stamina bar, fruit name, cooldowns
    @SubscribeEvent
    public void onRenderHUD(RenderGuiOverlayEvent.Post event) {
        if (!event.getOverlay().id().equals(VanillaGuiOverlay.HOTBAR.id())) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        mc.player.getCapability(OPCCapabilities.PLAYER_DATA).ifPresent(data -> {
            var pose = event.getGuiGraphics();
            int screenW = mc.getWindow().getGuiScaledWidth();
            int screenH = mc.getWindow().getGuiScaledHeight();
            int hudX = 10;
            int hudY = screenH - 80;

            // Stamina bar background
            pose.fill(hudX - 1, hudY - 1, hudX + 101, hudY + 7, 0x80000000);
            // Stamina bar fill
            int staminaWidth = (int)((data.getStamina() / (float) data.getMaxStamina()) * 100);
            pose.fill(hudX, hudY, hudX + staminaWidth, hudY + 6, 0xFF00CC44);
            // Stamina label
            pose.drawString(mc.font, "§aSTAMINA §f" + data.getStamina() + "/" + data.getMaxStamina(),
                    hudX, hudY - 10, 0xFFFFFF, false);

            // Level & XP
            pose.drawString(mc.font, "§eLv." + data.getLevel() + " §7XP:" + data.getXp(),
                    hudX, hudY + 12, 0xFFFFFF, false);

            // Devil Fruit name + attacks available
            if (data.getDevilFruit() != EnumDevilFruit.NONE) {
                pose.drawString(mc.font, "§d" + data.getDevilFruit().getDisplayName(),
                        hudX, hudY + 24, 0xFFFFFF, false);
                // Show attack slots and cooldowns
                int[] cds = data.getFruitAttackCooldowns();
                int fruitAtks = data.getDevilFruit().getAttackCount();
                StringBuilder atkLine = new StringBuilder("§7Attacks: ");
                for (int i = 0; i < fruitAtks; i++) {
                    if (cds[i] > 0) {
                        atkLine.append("§c[").append(i + 1).append(":").append(cds[i] / 20).append("s]§7 ");
                    } else {
                        atkLine.append("§a[").append(i + 1).append("]§7 ");
                    }
                }
                pose.drawString(mc.font, atkLine.toString(), hudX, hudY + 35, 0xFFFFFF, false);
            }

            // Race abilities cooldowns
            String ab1 = data.isAbility1Unlocked()
                    ? (data.getAbility1Cooldown() > 0 ? "§c[R:" + data.getAbility1Cooldown() / 20 + "s]" : "§a[R:RDY]")
                    : "§8[R:Lv5]";
            String ab2 = data.isAbility2Unlocked()
                    ? (data.getAbility2Cooldown() > 0 ? "§c[F:" + data.getAbility2Cooldown() / 20 + "s]" : "§a[F:RDY]")
                    : "§8[F:Lv35]";
            pose.drawString(mc.font, ab1 + " " + ab2, hudX, hudY + 46, 0xFFFFFF, false);
        });
    }
}
