package com.onepiececraft.init;

import com.onepiececraft.OnePieceCraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, OnePieceCraft.MOD_ID);

    public static final RegistryObject<MobEffect> DEVIL_FRUIT_WEAKNESS = EFFECTS.register("devil_fruit_weakness",
            () -> new MobEffect(MobEffectCategory.HARMFUL, 0xA020F0) {});

    public static final RegistryObject<MobEffect> HAKI_POWER = EFFECTS.register("haki_power",
            () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x1C1C1C) {});

    public static final RegistryObject<MobEffect> SULONG_FORM = EFFECTS.register("sulong_form",
            () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0xFFFFFF) {});

    public static final RegistryObject<MobEffect> IMMORTALITY_BRIEF = EFFECTS.register("immortality_brief",
            () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0xFF4500) {});
}
