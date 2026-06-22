package com.onepiececraft.init;

import com.onepiececraft.OnePieceCraft;
import com.onepiececraft.entity.MarineEntity;
import com.onepiececraft.entity.PirateEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, OnePieceCraft.MOD_ID);

    public static final RegistryObject<EntityType<MarineEntity>> MARINE =
            ENTITY_TYPES.register("marine",
                    () -> EntityType.Builder.<MarineEntity>of(MarineEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.95f)
                            .clientTrackingRange(8)
                            .build("marine"));

    public static final RegistryObject<EntityType<PirateEntity>> PIRATE =
            ENTITY_TYPES.register("pirate",
                    () -> EntityType.Builder.<PirateEntity>of(PirateEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.95f)
                            .clientTrackingRange(8)
                            .build("pirate"));
}
