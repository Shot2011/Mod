package com.onepiececraft.system;

import com.onepiececraft.data.EnumDevilFruit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class FruitAttackHandler {

    public static void execute(ServerPlayer player, EnumDevilFruit fruit, int index) {
        switch (fruit) {
            case GOMU_GOMU -> gomuGomu(player, index);
            case MERA_MERA -> meraMera(player, index);
            case HIE_HIE -> hieHie(player, index);
            case GURA_GURA -> guraGura(player, index);
            case OPE_OPE -> opeOpe(player, index);
            case YAMI_YAMI -> yamiYami(player, index);
            case PIKA_PIKA -> pikaPika(player, index);
            case SUNA_SUNA -> sunaSuna(player, index);
            case GORO_GORO -> goroGoro(player, index);
            case TORI_TORI -> toriTori(player, index);
            default -> {}
        }
    }

    public static int getCooldown(EnumDevilFruit fruit, int index) {
        return switch (fruit) {
            case GURA_GURA, YAMI_YAMI -> index >= 4 ? 200 : 60;
            case MERA_MERA, HIE_HIE, OPE_OPE, PIKA_PIKA, GORO_GORO -> index >= 3 ? 180 : 60;
            case TORI_TORI -> index >= 3 ? 200 : 80;
            default -> 60;
        };
    }

    // ---- GOMU GOMU NO MI (4 attacks) ----
    private static void gomuGomu(ServerPlayer p, int idx) {
        switch (idx) {
            case 0 -> { // Gomu Gomu no Pistol - punch forward
                knockbackAOE(p, 4.0, 5.0f, 1.5f);
                particles(p, "FLAME", 10);
                msg(p, "§eGomu Gomu no Pistol!");
            }
            case 1 -> { // Gomu Gomu no Gatling - rapid hits
                knockbackAOE(p, 3.0, 3.0f, 0.8f);
                knockbackAOE(p, 3.0, 3.0f, 0.8f);
                msg(p, "§eGomu Gomu no Gatling!");
            }
            case 2 -> { // Gomu Gomu no Bazooka - big knockback
                knockbackAOE(p, 5.0, 10.0f, 2.5f);
                particles(p, "EXPLOSION", 5);
                msg(p, "§eGomu Gomu no Bazooka!");
            }
            case 3 -> { // Gomu Gomu no Axe - ground slam
                groundSlam(p, 6.0, 12.0f);
                msg(p, "§eGomu Gomu no Axe!");
            }
        }
    }

    // ---- MERA MERA NO MI (5 attacks) ----
    private static void meraMera(ServerPlayer p, int idx) {
        switch (idx) {
            case 0 -> { fireAOE(p, 4.0, 6.0f); msg(p, "§cHi Hi no Mi: Flame Fist!"); }
            case 1 -> { fireAOE(p, 6.0, 4.0f); particles(p, "FLAME", 30); msg(p, "§cFire Pillar!"); }
            case 2 -> { fireAOE(p, 3.0, 8.0f); msg(p, "§cSaber of Xebec!"); }
            case 3 -> { fireAOE(p, 8.0, 6.0f); particles(p, "EXPLOSION_EMITTER", 3); msg(p, "§cFire Storm!"); }
            case 4 -> { fireAOE(p, 10.0, 15.0f); particles(p, "EXPLOSION_EMITTER", 8); msg(p, "§4§lGreat Fire Festival!"); }
        }
    }

    // ---- HIE HIE NO MI (5 attacks) ----
    private static void hieHie(ServerPlayer p, int idx) {
        switch (idx) {
            case 0 -> { freezeAOE(p, 4.0, 4.0f); msg(p, "§bIce Saber!"); }
            case 1 -> { freezeAOE(p, 5.0, 6.0f); msg(p, "§bPheasant Beak!"); }
            case 2 -> { freezeAOE(p, 7.0, 5.0f); msg(p, "§bIce Block Partisan!"); }
            case 3 -> { freezeAOE(p, 8.0, 8.0f); msg(p, "§bIce Age!"); }
            case 4 -> { freezeAOE(p, 15.0, 12.0f); particles(p, "SNOWFLAKE", 40); msg(p, "§b§lAbsolute Zero!"); }
        }
    }

    // ---- GURA GURA NO MI (6 attacks) ----
    private static void guraGura(ServerPlayer p, int idx) {
        switch (idx) {
            case 0 -> { knockbackAOE(p, 5.0, 8.0f, 2.0f); msg(p, "§7Quake Bubble!"); }
            case 1 -> { knockbackAOE(p, 6.0, 10.0f, 2.5f); msg(p, "§7Seaquake!"); }
            case 2 -> { groundSlam(p, 7.0, 10.0f); msg(p, "§7Shima Yurashi!"); }
            case 3 -> { knockbackAOE(p, 8.0, 12.0f, 3.0f); msg(p, "§7Quake Punch!"); }
            case 4 -> { groundSlam(p, 10.0, 15.0f); particles(p, "EXPLOSION_EMITTER", 5); msg(p, "§7Gura Gura Punch!"); }
            case 5 -> { knockbackAOE(p, 15.0, 20.0f, 4.0f); particles(p, "EXPLOSION_EMITTER", 12); msg(p, "§8§lWorld Shaking Quake!"); }
        }
    }

    // ---- OPE OPE NO MI (5 attacks) ----
    private static void opeOpe(ServerPlayer p, int idx) {
        switch (idx) {
            case 0 -> { teleportBehind(p); msg(p, "§dRoom: Teleport!"); }
            case 1 -> { knockbackAOE(p, 5.0, 7.0f, 1.5f); msg(p, "§dShambles!"); }
            case 2 -> { knockbackAOE(p, 4.0, 9.0f, 2.0f); msg(p, "§dTakt!"); }
            case 3 -> { p.heal(10.0f); msg(p, "§dCounter Shock - heal!"); }
            case 4 -> { knockbackAOE(p, 10.0, 15.0f, 3.0f); particles(p, "END_ROD", 20); msg(p, "§5§lGamma Knife!"); }
        }
    }

    // ---- YAMI YAMI NO MI (6 attacks) ----
    private static void yamiYami(ServerPlayer p, int idx) {
        switch (idx) {
            case 0 -> { pullAOE(p, 6.0, 5.0f); msg(p, "§8Black Hole!"); }
            case 1 -> { knockbackAOE(p, 5.0, 7.0f, 1.5f); msg(p, "§8Dark Matter!"); }
            case 2 -> { pullAOE(p, 8.0, 8.0f); msg(p, "§8Liberation!"); }
            case 3 -> { knockbackAOE(p, 7.0, 10.0f, 2.0f); msg(p, "§8Black Vortex!"); }
            case 4 -> { pullAOE(p, 12.0, 12.0f); msg(p, "§8§lBlack Hole Absorb!"); }
            case 5 -> { knockbackAOE(p, 20.0, 25.0f, 5.0f); particles(p, "EXPLOSION_EMITTER", 15); msg(p, "§0§lBlackbeard's Darkness!"); }
        }
    }

    // ---- PIKA PIKA NO MI (5 attacks) ----
    private static void pikaPika(ServerPlayer p, int idx) {
        switch (idx) {
            case 0 -> { teleportForward(p, 10.0); msg(p, "§ePika Dash!"); }
            case 1 -> { knockbackAOE(p, 5.0, 8.0f, 1.5f); particles(p, "END_ROD", 15); msg(p, "§eLight Beam!"); }
            case 2 -> { knockbackAOE(p, 6.0, 10.0f, 2.0f); particles(p, "END_ROD", 20); msg(p, "§eAmaru: Ray of Light!"); }
            case 3 -> { teleportForward(p, 20.0); knockbackAOE(p, 4.0, 12.0f, 1.0f); msg(p, "§eEleph Gun!"); }
            case 4 -> { knockbackAOE(p, 15.0, 18.0f, 3.0f); particles(p, "END_ROD", 50); msg(p, "§e§lSpeed of Light!"); }
        }
    }

    // ---- SUNA SUNA NO MI (4 attacks) ----
    private static void sunaSuna(ServerPlayer p, int idx) {
        switch (idx) {
            case 0 -> { sandDrain(p, 4.0, 3.0f); msg(p, "§6Desert Spada!"); }
            case 1 -> { sandDrain(p, 6.0, 5.0f); msg(p, "§6Desert Girasole!"); }
            case 2 -> { sandDrain(p, 8.0, 7.0f); msg(p, "§6Sables!"); }
            case 3 -> { sandDrain(p, 12.0, 12.0f); particles(p, "CLOUD", 30); msg(p, "§6§lBastardo!"); }
        }
    }

    // ---- GORO GORO NO MI (5 attacks) ----
    private static void goroGoro(ServerPlayer p, int idx) {
        switch (idx) {
            case 0 -> { lightningStrike(p, 1); msg(p, "§eLightning Strike!"); }
            case 1 -> { lightningStrike(p, 3); msg(p, "§eThunder Bolt!"); }
            case 2 -> { knockbackAOE(p, 5.0, 8.0f, 1.5f); lightningStrike(p, 1); msg(p, "§eBolt of Wrath!"); }
            case 3 -> { lightningStrike(p, 5); msg(p, "§eElectric Ball!"); }
            case 4 -> { lightningStrike(p, 10); particles(p, "END_ROD", 40); msg(p, "§e§lEl Thor!"); }
        }
    }

    // ---- TORI TORI NO MI PHOENIX (4 attacks) ----
    private static void toriTori(ServerPlayer p, int idx) {
        switch (idx) {
            case 0 -> { p.heal(6.0f); fireAOE(p, 3.0, 5.0f); msg(p, "§5Phoenix Flame!"); }
            case 1 -> { p.heal(4.0f); knockbackAOE(p, 5.0, 7.0f, 1.5f); msg(p, "§5Blue Flame Dive!"); }
            case 2 -> { p.heal(8.0f); fireAOE(p, 6.0, 8.0f); msg(p, "§5Eternal Regeneration!"); }
            case 3 -> { p.heal(20.0f); fireAOE(p, 10.0, 15.0f); particles(p, "FLAME", 60); msg(p, "§5§lPhoenix Rebirth!"); }
        }
    }

    // ---- Utility Methods ----
    private static void knockbackAOE(ServerPlayer p, double radius, float damage, float knockback) {
        if (!(p.level() instanceof ServerLevel level)) return;
        Vec3 pos = p.position();
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
                new AABB(pos.x - radius, pos.y - 2, pos.z - radius, pos.x + radius, pos.y + 4, pos.z + radius));
        for (LivingEntity e : entities) {
            if (e == p) continue;
            e.hurt(level.damageSources().playerAttack(p), damage);
            Vec3 dir = e.position().subtract(pos).normalize().scale(knockback);
            e.setDeltaMovement(dir.x, 0.5, dir.z);
        }
    }

    private static void fireAOE(ServerPlayer p, double radius, float damage) {
        if (!(p.level() instanceof ServerLevel level)) return;
        Vec3 pos = p.position();
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
                new AABB(pos.x - radius, pos.y - 2, pos.z - radius, pos.x + radius, pos.y + 4, pos.z + radius));
        for (LivingEntity e : entities) {
            if (e == p) continue;
            e.hurt(level.damageSources().onFire(), damage);
            e.setRemainingFireTicks(60);
        }
        particles(p, "FLAME", 30);
    }

    private static void freezeAOE(ServerPlayer p, double radius, float damage) {
        if (!(p.level() instanceof ServerLevel level)) return;
        Vec3 pos = p.position();
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
                new AABB(pos.x - radius, pos.y - 2, pos.z - radius, pos.x + radius, pos.y + 4, pos.z + radius));
        for (LivingEntity e : entities) {
            if (e == p) continue;
            e.hurt(level.damageSources().freeze(), damage);
            e.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 60, 3));
        }
        particles(p, "SNOWFLAKE", 25);
    }

    private static void sandDrain(ServerPlayer p, double radius, float damage) {
        if (!(p.level() instanceof ServerLevel level)) return;
        Vec3 pos = p.position();
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
                new AABB(pos.x - radius, pos.y - 2, pos.z - radius, pos.x + radius, pos.y + 4, pos.z + radius));
        for (LivingEntity e : entities) {
            if (e == p) continue;
            e.hurt(level.damageSources().magic(), damage);
            e.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.WEAKNESS, 80, 1));
        }
    }

    private static void pullAOE(ServerPlayer p, double radius, float damage) {
        if (!(p.level() instanceof ServerLevel level)) return;
        Vec3 pos = p.position();
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
                new AABB(pos.x - radius, pos.y - 2, pos.z - radius, pos.x + radius, pos.y + 4, pos.z + radius));
        for (LivingEntity e : entities) {
            if (e == p) continue;
            e.hurt(level.damageSources().playerAttack(p), damage);
            Vec3 dir = pos.subtract(e.position()).normalize().scale(1.5);
            e.setDeltaMovement(dir.x, 0.3, dir.z);
        }
        particles(p, "ASH", 30);
    }

    private static void groundSlam(ServerPlayer p, double radius, float damage) {
        if (!(p.level() instanceof ServerLevel level)) return;
        Vec3 pos = p.position();
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
                new AABB(pos.x - radius, pos.y - 1, pos.z - radius, pos.x + radius, pos.y + 2, pos.z + radius));
        for (LivingEntity e : entities) {
            if (e == p) continue;
            e.hurt(level.damageSources().playerAttack(p), damage);
            e.setDeltaMovement(e.getDeltaMovement().x, 1.5, e.getDeltaMovement().z);
        }
        particles(p, "EXPLOSION_EMITTER", 3);
    }

    private static void teleportBehind(ServerPlayer p) {
        var target = p.getLastHurtMob();
        if (target != null) {
            Vec3 behind = target.position().subtract(target.getLookAngle().scale(2));
            p.teleportTo(behind.x, behind.y, behind.z);
        }
    }

    private static void teleportForward(ServerPlayer p, double distance) {
        Vec3 look = p.getLookAngle().scale(distance);
        p.teleportTo(p.getX() + look.x, p.getY() + look.y, p.getZ() + look.z);
    }

    private static void lightningStrike(ServerPlayer p, int count) {
        if (!(p.level() instanceof ServerLevel level)) return;
        Vec3 pos = p.position();
        for (int i = 0; i < count; i++) {
            double ox = (Math.random() - 0.5) * 6;
            double oz = (Math.random() - 0.5) * 6;
            net.minecraft.world.entity.LightningBolt bolt = net.minecraft.world.entity.EntityType.LIGHTNING_BOLT.create(level);
            if (bolt != null) {
                bolt.moveTo(pos.x + ox, pos.y, pos.z + oz);
                bolt.setCause(p);
                level.addFreshEntity(bolt);
            }
        }
    }

    private static void particles(ServerPlayer p, String type, int count) {
        if (!(p.level() instanceof ServerLevel level)) return;
        var particle = switch (type) {
            case "FLAME" -> ParticleTypes.FLAME;
            case "EXPLOSION" -> ParticleTypes.EXPLOSION;
            case "EXPLOSION_EMITTER" -> ParticleTypes.EXPLOSION_EMITTER;
            case "END_ROD" -> ParticleTypes.END_ROD;
            case "SNOWFLAKE" -> ParticleTypes.SNOWFLAKE;
            case "CLOUD" -> ParticleTypes.CLOUD;
            case "ASH" -> ParticleTypes.ASH;
            default -> ParticleTypes.HAPPY_VILLAGER;
        };
        for (int i = 0; i < count; i++) {
            double ox = (Math.random() - 0.5) * 2;
            double oy = Math.random() * 2;
            double oz = (Math.random() - 0.5) * 2;
            level.sendParticles(particle, p.getX() + ox, p.getY() + oy, p.getZ() + oz, 1, 0, 0, 0, 0.1);
        }
    }

    private static void msg(ServerPlayer p, String text) {
        p.sendSystemMessage(net.minecraft.network.chat.Component.literal(text));
    }
}
