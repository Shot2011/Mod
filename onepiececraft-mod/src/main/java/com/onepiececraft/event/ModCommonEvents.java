package com.onepiececraft.event;

import com.onepiececraft.capability.IPlayerData;
import com.onepiececraft.capability.OPCCapabilities;
import com.onepiececraft.data.EnumDevilFruit;
import com.onepiececraft.data.EnumFaction;
import com.onepiececraft.data.EnumRace;
import com.onepiececraft.init.ModEntityTypes;
import com.onepiececraft.network.ModPackets;
import com.onepiececraft.network.packets.SyncPlayerDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModCommonEvents {

    // Register entity attributes
    @SubscribeEvent
    public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.MARINE.get(),
                com.onepiececraft.entity.MarineEntity.createAttributes().build());
        event.put(ModEntityTypes.PIRATE.get(),
                com.onepiececraft.entity.PirateEntity.createAttributes().build());
    }

    // Initialize race when player first joins
    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        player.getCapability(OPCCapabilities.PLAYER_DATA).ifPresent(data -> {
            if (!data.isRaceInitialized()) {
                EnumRace randomRace = EnumRace.random();
                data.setRace(randomRace);
                data.setRaceInitialized(true);
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                        "§aWelcome to the world of One Piece!"));
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                        "§eYour race is: §b" + randomRace.getDisplayName()));
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                        "§7Press §eG §7to choose your faction (Pirate or Marine)!"));
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                        "§7Racial abilities unlock at Level §a5 §7(§eR§7) and Level §a35 §7(§eF§7)"));
                applyRacePassives(player, data);
                ModPackets.sendToPlayer(new SyncPlayerDataPacket(data.serializeNBT()), player);
            }
        });
    }

    // Sync on respawn/dimension change
    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        player.getCapability(OPCCapabilities.PLAYER_DATA).ifPresent(data -> {
            applyRacePassives(player, data);
            ModPackets.sendToPlayer(new SyncPlayerDataPacket(data.serializeNBT()), player);
        });
    }

    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        player.getCapability(OPCCapabilities.PLAYER_DATA).ifPresent(data -> {
            applyRacePassives(player, data);
            ModPackets.sendToPlayer(new SyncPlayerDataPacket(data.serializeNBT()), player);
        });
    }

    // Devil Fruit water weakness + stamina regen + cooldown tick
    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        player.getCapability(OPCCapabilities.PLAYER_DATA).ifPresent(data -> {
            // Water damage amplification for devil fruit users
            if (data.getDevilFruit() != EnumDevilFruit.NONE) {
                if (event.getSource().is(DamageTypes.DROWN) || event.getSource().is(DamageTypes.IN_WALL)) {
                    event.setAmount(event.getAmount() * 2.5f);
                }
            }
        });
    }

    // XP -> Level system & kills
    @SubscribeEvent
    public void onMobDeath(LivingDeathEvent event) {
        LivingEntity killed = event.getEntity();
        if (!(killed.getLastHurtByMob() instanceof ServerPlayer player)) return;
        player.getCapability(OPCCapabilities.PLAYER_DATA).ifPresent(data -> {
            // XP gain
            int xpGain = calculateXpGain(killed);
            int newXp = data.getXp() + xpGain;
            int xpForNextLevel = xpForLevel(data.getLevel());
            if (newXp >= xpForNextLevel && data.getLevel() < 100) {
                data.setLevel(data.getLevel() + 1);
                data.setXp(newXp - xpForNextLevel);
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                        "§a§lLevel Up! You are now Level " + data.getLevel() + "!"));
                if (data.getLevel() == 5) {
                    player.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                            "§eRace Ability 1 unlocked! Press §bR §eto use it!"));
                }
                if (data.getLevel() == 35) {
                    player.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                            "§eRace Ability 2 unlocked! Press §bF §eto use it!"));
                }
            } else {
                data.setXp(newXp);
            }

            // Bounty / Honor system
            if (data.getFaction() == EnumFaction.PIRATE) {
                long bountyGain = (long)(xpGain * 500);
                data.setBounty(data.getBounty() + bountyGain);
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                        "§6Bounty: §e" + formatBerry(data.getBounty())));
            } else if (data.getFaction() == EnumFaction.MARINE) {
                if (killed instanceof Monster) {
                    long honorGain = (long)(xpGain * 300);
                    data.setHonor(data.getHonor() + honorGain);
                    player.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                            "§bHonor: §3" + formatBerry(data.getHonor())));
                }
            }
            ModPackets.sendToPlayer(new SyncPlayerDataPacket(data.serializeNBT()), player);
        });
    }

    // Bounty decrease on death
    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        player.getCapability(OPCCapabilities.PLAYER_DATA).ifPresent(data -> {
            if (data.getFaction() == EnumFaction.PIRATE) {
                long loss = data.getBounty() / 10;
                data.setBounty(data.getBounty() - loss);
                if (loss > 0) {
                    player.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                            "§cYou lost §e" + formatBerry(loss) + "§c in bounty!"));
                }
            }
            // Note: Devil Fruit is NOT lost on normal death — only when eating a second fruit
            ModPackets.sendToPlayer(new SyncPlayerDataPacket(data.serializeNBT()), player);
        });
    }

    // Stamina and cooldown tick (server-side, every 20 ticks = 1 second)
    @SubscribeEvent
    public void onPlayerTick(net.minecraftforge.event.TickEvent.PlayerTickEvent event) {
        if (event.phase != net.minecraftforge.event.TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;
        if (player.tickCount % 20 != 0) return;

        player.getCapability(OPCCapabilities.PLAYER_DATA).ifPresent(data -> {
            boolean changed = false;

            // Stamina regen
            if (data.getStamina() < data.getMaxStamina()) {
                data.setStamina(data.getStamina() + 5);
                changed = true;
            }

            // Devil fruit water weakness: passive damage in water
            if (data.getDevilFruit() != EnumDevilFruit.NONE && player.isInWater()) {
                player.hurt(player.level().damageSources().drown(), 2.0f);
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 1, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2, false, false));
                if (data.getStamina() > 0) {
                    data.setStamina(data.getStamina() - 3);
                    changed = true;
                }
            }

            // Tick down cooldowns
            int[] fcd = data.getFruitAttackCooldowns();
            for (int i = 0; i < fcd.length; i++) {
                if (fcd[i] > 0) { fcd[i] = Math.max(0, fcd[i] - 20); changed = true; }
            }
            if (data.getAbility1Cooldown() > 0) { data.setAbility1Cooldown(data.getAbility1Cooldown() - 20); changed = true; }
            if (data.getAbility2Cooldown() > 0) { data.setAbility2Cooldown(data.getAbility2Cooldown() - 20); changed = true; }

            if (changed) ModPackets.sendToPlayer(new SyncPlayerDataPacket(data.serializeNBT()), player);
        });
    }

    // ---- Helpers ----
    private void applyRacePassives(ServerPlayer player, IPlayerData data) {
        switch (data.getRace()) {
            case FISHMAN -> {
                player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, Integer.MAX_VALUE, 0, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, Integer.MAX_VALUE, 0, false, false));
            }
            case MINK -> player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, Integer.MAX_VALUE, 0, false, false));
            case LUNARIAN -> player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));
            case GIANT -> {
                player.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, Integer.MAX_VALUE, 1, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, Integer.MAX_VALUE, 0, false, false));
            }
            case ANCIENT_ZOAN -> player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
            case HUMAN -> player.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, Integer.MAX_VALUE, 0, false, false));
        }
        if (data.getFaction() == EnumFaction.PIRATE) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, Integer.MAX_VALUE, 0, false, false));
        } else if (data.getFaction() == EnumFaction.MARINE) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));
        }
    }

    private int calculateXpGain(LivingEntity entity) {
        if (entity instanceof Monster) return 15 + entity.level().random.nextInt(10);
        return 5 + entity.level().random.nextInt(5);
    }

    private int xpForLevel(int level) {
        return 100 + (level * 50);
    }

    private String formatBerry(long amount) {
        if (amount >= 1_000_000_000L) return String.format("%.1fB Berry", amount / 1_000_000_000.0);
        if (amount >= 1_000_000L) return String.format("%.1fM Berry", amount / 1_000_000.0);
        if (amount >= 1_000L) return String.format("%.1fK Berry", amount / 1_000.0);
        return amount + " Berry";
    }
}
