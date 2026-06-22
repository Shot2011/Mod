package com.onepiececraft.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OPCSwordItem extends SwordItem {
    private final String swordName;

    public OPCSwordItem(Tier tier, int attackDamageModifier, float attackSpeedModifier,
                        String swordName, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
        this.swordName = swordName;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level,
                                List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§6⚔ " + swordName));
        tooltip.add(Component.literal("§7A legendary blade from the world of One Piece."));
        if (swordName.contains("Kitetsu")) {
            tooltip.add(Component.literal("§4Cursed blade! Dangerous but incredibly sharp."));
        }
        if (swordName.equals("Yoru - Black Blade")) {
            tooltip.add(Component.literal("§8The world's strongest sword, wielded by Hawk-Eyes Mihawk."));
        }
        if (swordName.equals("Shusui")) {
            tooltip.add(Component.literal("§8A black blade, national treasure of Wano."));
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return swordName.equals("Yoru - Black Blade") || swordName.equals("Shusui");
    }
}
