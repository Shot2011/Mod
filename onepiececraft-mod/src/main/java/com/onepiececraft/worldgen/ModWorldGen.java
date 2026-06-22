package com.onepiececraft.worldgen;

import com.onepiececraft.OnePieceCraft;
import com.onepiececraft.init.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModWorldGen {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registries.CONFIGURED_FEATURE, OnePieceCraft.MOD_ID);

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registries.PLACED_FEATURE, OnePieceCraft.MOD_ID);

    public static final RegistryObject<ConfiguredFeature<?, ?>> FOOLS_GOLD_ORE_CF =
            CONFIGURED_FEATURES.register("fools_gold_ore", () -> new ConfiguredFeature<>(
                    Feature.ORE, new OreConfiguration(
                    List.of(
                            OreConfiguration.target(
                                    new TagMatchTest(net.minecraft.tags.BlockTags.STONE_ORE_REPLACEABLES),
                                    ModBlocks.FOOLS_GOLD_ORE.get().defaultBlockState()),
                            OreConfiguration.target(
                                    new TagMatchTest(net.minecraft.tags.BlockTags.DEEPSLATE_ORE_REPLACEABLES),
                                    ModBlocks.DEEPSLATE_FOOLS_GOLD_ORE.get().defaultBlockState())
                    ), 8)));

    public static final RegistryObject<PlacedFeature> FOOLS_GOLD_ORE_PF =
            PLACED_FEATURES.register("fools_gold_ore", () -> new PlacedFeature(
                    FOOLS_GOLD_ORE_CF.getHolder().get(),
                    List.of(
                            CountPlacement.of(8),
                            InSquarePlacement.spread(),
                            HeightRangePlacement.triangle(
                                    VerticalAnchor.aboveBottom(16),
                                    VerticalAnchor.belowTop(32)),
                            BiomeFilter.biome()
                    )));
}
