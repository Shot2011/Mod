package com.onepiececraft.screen;

import com.onepiececraft.capability.IPlayerData;
import com.onepiececraft.data.EnumDevilFruit;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class StatusScreen extends Screen {
    private final IPlayerData data;

    public StatusScreen(IPlayerData data) {
        super(Component.literal("Player Status"));
        this.data = data;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        int cx = this.width / 2;
        int startY = 30;

        // Title
        graphics.drawCenteredString(font, "§6§l⚓ PLAYER STATUS ⚓", cx, startY, 0xFFFFFF);
        startY += 20;

        // Panel background
        graphics.fill(cx - 120, startY - 5, cx + 120, startY + 170, 0xB0000000);

        int lx = cx - 110;

        // Name
        String playerName = minecraft != null && minecraft.player != null ? minecraft.player.getName().getString() : "Unknown";
        graphics.drawString(font, "§eName: §f" + playerName, lx, startY, 0xFFFFFF, false);
        startY += 14;

        // Faction
        String factionColor = switch (data.getFaction()) {
            case PIRATE -> "§6";
            case MARINE -> "§3";
            default -> "§7";
        };
        graphics.drawString(font, "§eFaction: " + factionColor + data.getFaction().getDisplayName(), lx, startY, 0xFFFFFF, false);
        startY += 14;

        // Race
        graphics.drawString(font, "§eRace: §b" + data.getRace().getDisplayName(), lx, startY, 0xFFFFFF, false);
        startY += 14;

        // Level & XP
        graphics.drawString(font, "§eLevel: §a" + data.getLevel() + " §7| XP: §a" + data.getXp()
                + " §7/ §a" + (100 + data.getLevel() * 50), lx, startY, 0xFFFFFF, false);
        startY += 14;

        // Stamina
        graphics.drawString(font, "§eStamina: §a" + data.getStamina() + " §7/ §a" + data.getMaxStamina(), lx, startY, 0xFFFFFF, false);
        startY += 14;

        // Devil Fruit
        String fruitStr = data.getDevilFruit() == EnumDevilFruit.NONE ? "§7None" : "§d" + data.getDevilFruit().getDisplayName() +
                " §7[§f" + data.getDevilFruit().getFruitType() + "§7]";
        graphics.drawString(font, "§eFruit: " + fruitStr, lx, startY, 0xFFFFFF, false);
        startY += 14;

        // Bounty / Honor
        if (data.getFaction() == com.onepiececraft.data.EnumFaction.PIRATE) {
            graphics.drawString(font, "§e☠ Bounty: §6" + formatBerry(data.getBounty()), lx, startY, 0xFFFFFF, false);
        } else if (data.getFaction() == com.onepiececraft.data.EnumFaction.MARINE) {
            graphics.drawString(font, "§e★ Honor: §b" + formatBerry(data.getHonor()), lx, startY, 0xFFFFFF, false);
        }
        startY += 20;

        // Race abilities
        graphics.drawString(font, "§e--- Race Abilities ---", lx, startY, 0xFFFFFF, false);
        startY += 12;
        String ab1Status = data.isAbility1Unlocked() ? "§a[R] §f" + data.getRace().getAbility1Desc() : "§8[R] Locked (Need Lv.5)";
        graphics.drawString(font, ab1Status, lx, startY, 0xFFFFFF, false);
        startY += 12;
        String ab2Status = data.isAbility2Unlocked() ? "§a[F] §f" + data.getRace().getAbility2Desc() : "§8[F] Locked (Need Lv.35)";
        graphics.drawString(font, ab2Status, lx, startY, 0xFFFFFF, false);
        startY += 20;

        // Controls hint
        graphics.drawCenteredString(font, "§7[Press ESC to close]", cx, startY, 0xAAAAAA);

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private String formatBerry(long amount) {
        if (amount >= 1_000_000_000L) return String.format("%.1fB Berry", amount / 1_000_000_000.0);
        if (amount >= 1_000_000L) return String.format("%.1fM Berry", amount / 1_000_000.0);
        if (amount >= 1_000L) return String.format("%.1fK Berry", amount / 1_000.0);
        return amount + " Berry";
    }

    @Override public boolean isPauseScreen() { return false; }
}
