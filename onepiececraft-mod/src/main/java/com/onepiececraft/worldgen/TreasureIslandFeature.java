package com.onepiececraft.worldgen;

import com.onepiececraft.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class TreasureIslandFeature extends Feature<NoneFeatureConfiguration> {

    public TreasureIslandFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos origin = ctx.origin();
        Random rand = new Random(ctx.random().nextLong());

        // Find ocean surface
        BlockPos surface = origin.above(5);

        // Build small island base (sand)
        int islandRadius = 8;
        for (int x = -islandRadius; x <= islandRadius; x++) {
            for (int z = -islandRadius; z <= islandRadius; z++) {
                if (x * x + z * z <= islandRadius * islandRadius) {
                    double dist = Math.sqrt(x * x + z * z);
                    int height = (int) Math.max(0, 3 - dist * 0.4);
                    for (int y = -2; y <= height; y++) {
                        BlockPos p = surface.offset(x, y, z);
                        if (y < height) {
                            level.setBlock(p, Blocks.SANDSTONE.defaultBlockState(), 2);
                        } else {
                            level.setBlock(p, Blocks.SAND.defaultBlockState(), 2);
                        }
                    }
                }
            }
        }

        // Place some palm trees (logs + leaves)
        placePalmTree(level, surface.offset(-2, 1, 0));
        placePalmTree(level, surface.offset(3, 1, 2));

        // Place deco rocks
        level.setBlock(surface.offset(-4, 1, 3), Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 2);
        level.setBlock(surface.offset(4, 1, -3), Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 2);

        // Place the single treasure chest at island center
        BlockPos chestPos = surface.offset(0, 1, 0);
        level.setBlock(chestPos, Blocks.CHEST.defaultBlockState(), 2);
        if (level.getBlockEntity(chestPos) instanceof ChestBlockEntity chest) {
            fillTreasureChest(chest, rand);
        }

        return true;
    }

    private void placePalmTree(WorldGenLevel level, BlockPos base) {
        // Trunk
        for (int i = 0; i < 5; i++) {
            level.setBlock(base.above(i), Blocks.JUNGLE_LOG.defaultBlockState(), 2);
        }
        // Leaves top
        BlockPos top = base.above(5);
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                if (Math.abs(x) + Math.abs(z) <= 3) {
                    level.setBlock(top.offset(x, 0, z), Blocks.JUNGLE_LEAVES.defaultBlockState(), 2);
                    if (Math.abs(x) <= 1 && Math.abs(z) <= 1) {
                        level.setBlock(top.offset(x, 1, z), Blocks.JUNGLE_LEAVES.defaultBlockState(), 2);
                    }
                }
            }
        }
    }

    private void fillTreasureChest(ChestBlockEntity chest, Random rand) {
        // 75% chance of a devil fruit
        if (rand.nextFloat() < 0.75f) {
            var fruits = com.onepiececraft.data.EnumDevilFruit.values();
            // Pick random fruit (skip NONE at index 0)
            int fruitIdx = 1 + rand.nextInt(fruits.length - 1);
            var fruitItem = getFruitItem(fruits[fruitIdx]);
            if (fruitItem != null) {
                chest.setItem(0, new net.minecraft.world.item.ItemStack(fruitItem));
            }
        }

        // Always: gold, emeralds, diamonds, fools gold
        chest.setItem(1, new net.minecraft.world.item.ItemStack(Items.GOLD_INGOT, 8 + rand.nextInt(8)));
        chest.setItem(2, new net.minecraft.world.item.ItemStack(Items.EMERALD, 4 + rand.nextInt(6)));
        chest.setItem(3, new net.minecraft.world.item.ItemStack(Items.DIAMOND, 2 + rand.nextInt(4)));
        chest.setItem(4, new net.minecraft.world.item.ItemStack(ModItems.FOOLS_GOLD_INGOT.get(), 3 + rand.nextInt(5)));
        chest.setItem(5, new net.minecraft.world.item.ItemStack(Items.GOLD_NUGGET, 16 + rand.nextInt(16)));
        chest.setItem(6, new net.minecraft.world.item.ItemStack(Items.EXPERIENCE_BOTTLE, 3 + rand.nextInt(5)));

        // Chance for a sword
        if (rand.nextFloat() < 0.4f) {
            var swords = new net.minecraft.world.item.Item[]{
                    ModItems.KATANA.get(), ModItems.WADO_ICHIMONJI.get(), ModItems.SANDAI_KITETSU.get()
            };
            chest.setItem(7, new net.minecraft.world.item.ItemStack(swords[rand.nextInt(swords.length)]));
        }

        chest.setItem(8, new net.minecraft.world.item.ItemStack(Items.ENCHANTED_GOLDEN_APPLE, rand.nextInt(2) + 1));
    }

    private net.minecraft.world.item.Item getFruitItem(com.onepiececraft.data.EnumDevilFruit fruit) {
        return switch (fruit) {
            case GOMU_GOMU -> ModItems.GOMU_GOMU_FRUIT.get();
            case MERA_MERA -> ModItems.MERA_MERA_FRUIT.get();
            case HIE_HIE -> ModItems.HIE_HIE_FRUIT.get();
            case GURA_GURA -> ModItems.GURA_GURA_FRUIT.get();
            case OPE_OPE -> ModItems.OPE_OPE_FRUIT.get();
            case YAMI_YAMI -> ModItems.YAMI_YAMI_FRUIT.get();
            case PIKA_PIKA -> ModItems.PIKA_PIKA_FRUIT.get();
            case SUNA_SUNA -> ModItems.SUNA_SUNA_FRUIT.get();
            case GORO_GORO -> ModItems.GORO_GORO_FRUIT.get();
            case TORI_TORI -> ModItems.TORI_TORI_FRUIT.get();
            default -> null;
        };
    }
}
