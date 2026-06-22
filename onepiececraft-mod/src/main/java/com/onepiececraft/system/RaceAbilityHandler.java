package com.onepiececraft.system;

import com.onepiececraft.data.EnumRace;
import com.onepiececraft.init.ModEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class RaceAbilityHandler {

    public static void execute(ServerPlayer p, EnumRace race, int slot) {
        switch (race) {
            case HUMAN -> human(p, slot);
            case FISHMAN -> fishman(p, slot);
            case MINK -> mink(p, slot);
            case LUNARIAN -> lunarian(p, slot);
            case GIANT -> giant(p, slot);
            case ANCIENT_ZOAN -> ancientZoan(p, slot);
        }
    }

    public static int getCooldown(EnumRace race, int slot) {
        return switch (race) {
            case GIANT, ANCIENT_ZOAN -> slot == 2 ? 400 : 200;
            case LUNARIAN -> slot == 2 ? 600 : 300;
            case MINK -> slot == 2 ? 400 : 200;
            default -> slot == 2 ? 300 : 150;
        };
    }

    // HUMAN
    private static void human(ServerPlayer p, int slot) {
        if (slot == 1) {
            // Resolve: temporary resistance
            p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 2, false, true));
            p.sendSystemMessage(net.minecraft.network.chat.Component.literal("§fResolve activated! (10s resistance)"));
        } else {
            // Infinite Potential: all stats boosted
            p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 1, false, true));
            p.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 1, false, true));
            p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 1, false, true));
            p.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1, false, true));
            p.sendSystemMessage(net.minecraft.network.chat.Component.literal("§f§lInfinite Potential! All stats boosted for 20s!"));
        }
    }

    // FISHMAN
    private static void fishman(ServerPlayer p, int slot) {
        if (slot == 1) {
            // Aquatic Strike
            damageAOE(p, 5.0, 8.0f);
            p.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 400, 0, false, false));
            p.sendSystemMessage(net.minecraft.network.chat.Component.literal("§3Aquatic Strike!"));
        } else {
            // Fishman Karate - massive water AOE
            damageAOE(p, 10.0, 18.0f);
            if (p.level() instanceof ServerLevel level) {
                level.sendParticles(net.minecraft.core.particles.ParticleTypes.SPLASH,
                        p.getX(), p.getY() + 1, p.getZ(), 60, 3, 1, 3, 0.3);
            }
            p.sendSystemMessage(net.minecraft.network.chat.Component.literal("§3§lFishman Karate - Thousand Brick Fist!"));
        }
    }

    // MINK
    private static void mink(ServerPlayer p, int slot) {
        if (slot == 1) {
            // Electro
            electricAOE(p, 5.0, 6.0f);
            p.sendSystemMessage(net.minecraft.network.chat.Component.literal("§eElectro!"));
        } else {
            // Sulong - if night, massive power
            p.addEffect(new MobEffectInstance(ModEffects.SULONG_FORM.get(), 400, 0, false, true));
            p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 4, false, true));
            p.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 3, false, true));
            p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 2, false, true));
            electricAOE(p, 8.0, 15.0f);
            p.sendSystemMessage(net.minecraft.network.chat.Component.literal("§f§lSULONG! Full moon power awakened!"));
        }
    }

    // LUNARIAN
    private static void lunarian(ServerPlayer p, int slot) {
        if (slot == 1) {
            // Fire Wings - launch upward with fire trail
            p.setDeltaMovement(p.getDeltaMovement().x, 1.5, p.getDeltaMovement().z);
            p.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 100, 0, false, false));
            fireAOE(p, 4.0, 5.0f);
            p.sendSystemMessage(net.minecraft.network.chat.Component.literal("§6Fire Wings!"));
        } else {
            // Brief Immortality
            p.addEffect(new MobEffectInstance(ModEffects.IMMORTALITY_BRIEF.get(), 100, 0, false, true));
            p.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 4, false, true));
            p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 4, false, true));
            p.sendSystemMessage(net.minecraft.network.chat.Component.literal("§c§lBrief Immortality! Invincible for 5s!"));
        }
    }

    // GIANT
    private static void giant(ServerPlayer p, int slot) {
        if (slot == 1) {
            // Colossal Strike - massive stomp AOE
            damageAOE(p, 6.0, 12.0f);
            if (p.level() instanceof ServerLevel level) {
                level.sendParticles(net.minecraft.core.particles.ParticleTypes.EXPLOSION_EMITTER,
                        p.getX(), p.getY(), p.getZ(), 3, 0, 0, 0, 0);
            }
            p.sendSystemMessage(net.minecraft.network.chat.Component.literal("§7Colossal Strike!"));
        } else {
            // Giant Form - massive buffs representing giant size
            p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 3, false, true));
            p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 2, false, true));
            p.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 4, false, true));
            p.heal(20.0f);
            damageAOE(p, 8.0, 20.0f);
            p.sendSystemMessage(net.minecraft.network.chat.Component.literal("§7§lGiant Form! Colossal power awakened!"));
        }
    }

    // ANCIENT ZOAN
    private static void ancientZoan(ServerPlayer p, int slot) {
        if (slot == 1) {
            // Partial Beast Form
            p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 2, false, true));
            p.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 1, false, true));
            damageAOE(p, 4.0, 8.0f);
            p.sendSystemMessage(net.minecraft.network.chat.Component.literal("§2Partial Beast Form!"));
        } else {
            // Full Beast Form
            p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 4, false, true));
            p.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 2, false, true));
            p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 3, false, true));
            p.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 1, false, true));
            damageAOE(p, 10.0, 25.0f);
            p.sendSystemMessage(net.minecraft.network.chat.Component.literal("§2§lFull Beast Form! Raw ancient power!"));
        }
    }

    // ---- Utility ----
    private static void damageAOE(ServerPlayer p, double radius, float damage) {
        if (!(p.level() instanceof ServerLevel level)) return;
        Vec3 pos = p.position();
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
                new AABB(pos.x - radius, pos.y - 2, pos.z - radius, pos.x + radius, pos.y + 4, pos.z + radius));
        for (LivingEntity e : entities) {
            if (e == p) continue;
            e.hurt(level.damageSources().playerAttack(p), damage);
        }
    }

    private static void electricAOE(ServerPlayer p, double radius, float damage) {
        if (!(p.level() instanceof ServerLevel level)) return;
        Vec3 pos = p.position();
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
                new AABB(pos.x - radius, pos.y - 2, pos.z - radius, pos.x + radius, pos.y + 4, pos.z + radius));
        for (LivingEntity e : entities) {
            if (e == p) continue;
            e.hurt(level.damageSources().lightningBolt(), damage);
        }
        level.sendParticles(net.minecraft.core.particles.ParticleTypes.END_ROD,
                p.getX(), p.getY() + 1, p.getZ(), 30, 2, 1, 2, 0.2);
    }

    private static void fireAOE(ServerPlayer p, double radius, float damage) {
        if (!(p.level() instanceof ServerLevel level)) return;
        Vec3 pos = p.position();
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
                new AABB(pos.x - radius, pos.y - 2, pos.z - radius, pos.x + radius, pos.y + 4, pos.z + radius));
        for (LivingEntity e : entities) {
            if (e == p) continue;
            e.hurt(level.damageSources().onFire(), damage);
            e.setRemainingFireTicks(40);
        }
        level.sendParticles(net.minecraft.core.particles.ParticleTypes.FLAME,
                p.getX(), p.getY() + 1, p.getZ(), 20, 1, 1, 1, 0.15);
    }
}
