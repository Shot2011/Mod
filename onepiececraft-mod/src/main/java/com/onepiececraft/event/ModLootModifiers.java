package com.onepiececraft.event;

import com.mojang.serialization.Codec;
import com.onepiececraft.OnePieceCraft;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, OnePieceCraft.MOD_ID);

    public static final RegistryObject<Codec<ModLootModifier>> FRUIT_INJECTOR =
            LOOT_MODIFIER_SERIALIZERS.register("fruit_injector",
                    () -> ModLootModifier.DIRECT_CODEC.xmap(
                            conditions -> new ModLootModifier(conditions),
                            modifier -> modifier.conditions
                    ));
}
