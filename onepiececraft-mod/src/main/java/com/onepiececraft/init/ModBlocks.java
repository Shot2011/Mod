package com.onepiececraft.init;

import com.onepiececraft.OnePieceCraft;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, OnePieceCraft.MOD_ID);

    public static final RegistryObject<Block> FOOLS_GOLD_ORE = BLOCKS.register("fools_gold_ore",
            () -> new DropExperienceBlock(
                    net.minecraft.util.valueproviders.UniformInt.of(2, 5),
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.STONE)
                            .requiresCorrectToolForDrops()
                            .strength(3.0f, 3.0f)
                            .sound(SoundType.STONE)
            ));

    public static final RegistryObject<Block> DEEPSLATE_FOOLS_GOLD_ORE = BLOCKS.register("deepslate_fools_gold_ore",
            () -> new DropExperienceBlock(
                    net.minecraft.util.valueproviders.UniformInt.of(3, 7),
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.DEEPSLATE)
                            .requiresCorrectToolForDrops()
                            .strength(4.5f, 3.0f)
                            .sound(SoundType.DEEPSLATE)
            ));

    static {
        // Register BlockItems automatically
        ModItems.ITEMS.register("fools_gold_ore",
                () -> new BlockItem(FOOLS_GOLD_ORE.get(), new Item.Properties()));
        ModItems.ITEMS.register("deepslate_fools_gold_ore",
                () -> new BlockItem(DEEPSLATE_FOOLS_GOLD_ORE.get(), new Item.Properties()));
    }
}
