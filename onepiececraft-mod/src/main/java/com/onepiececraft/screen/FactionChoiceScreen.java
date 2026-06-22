package com.onepiececraft.screen;

import com.onepiececraft.data.EnumFaction;
import com.onepiececraft.network.ModPackets;
import com.onepiececraft.network.packets.FactionChoicePacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class FactionChoiceScreen extends Screen {
    public FactionChoiceScreen() {
        super(Component.literal("Choose Your Faction"));
    }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int cy = this.height / 2;

        // Pirate button
        this.addRenderableWidget(Button.builder(Component.literal("⚓ Join the Pirates"), btn -> {
            ModPackets.sendToServer(new FactionChoicePacket(EnumFaction.PIRATE.ordinal()));
            this.onClose();
        }).bounds(cx - 120, cy, 100, 24).build());

        // Marine button
        this.addRenderableWidget(Button.builder(Component.literal("⚖ Join the Marines"), btn -> {
            ModPackets.sendToServer(new FactionChoicePacket(EnumFaction.MARINE.ordinal()));
            this.onClose();
        }).bounds(cx + 20, cy, 100, 24).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        int cx = this.width / 2;
        int cy = this.height / 2;

        // Background panel
        graphics.fill(cx - 140, cy - 90, cx + 140, cy + 60, 0xC0000000);

        graphics.drawCenteredString(font, "§6§l⚓ CHOOSE YOUR FACTION ⚓", cx, cy - 80, 0xFFFFFF);

        graphics.drawCenteredString(font, "§eThis choice is permanent!", cx, cy - 60, 0xFFFFFF);

        // Pirate description
        graphics.drawString(font, "§6☠ PIRATE", cx - 130, cy - 40, 0xFFFFFF, false);
        graphics.drawString(font, "§7+ Speed +5%", cx - 130, cy - 28, 0xFFFFFF, false);
        graphics.drawString(font, "§7+ Damage +5%", cx - 130, cy - 16, 0xFFFFFF, false);
        graphics.drawString(font, "§7+ Earn Bounty", cx - 130, cy - 4, 0xFFFFFF, false);

        // Marine description
        graphics.drawString(font, "§3⚖ MARINE", cx + 20, cy - 40, 0xFFFFFF, false);
        graphics.drawString(font, "§7+ Resistance +5%", cx + 20, cy - 28, 0xFFFFFF, false);
        graphics.drawString(font, "§7+ Healing +10%", cx + 20, cy - 16, 0xFFFFFF, false);
        graphics.drawString(font, "§7+ Earn Honor", cx + 20, cy - 4, 0xFFFFFF, false);

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override public boolean shouldCloseOnEsc() { return false; }
    @Override public boolean isPauseScreen() { return false; }
}
