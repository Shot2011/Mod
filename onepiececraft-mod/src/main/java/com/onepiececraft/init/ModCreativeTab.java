package com.onepiececraft.init;

import com.onepiececraft.OnePieceCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OnePieceCraft.MOD_ID);

    public static final RegistryObject<CreativeModeTab> OPC_TAB = CREATIVE_MODE_TABS.register("opc_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.onepiececraft"))
                    .icon(() -> new ItemStack(ModItems.GOMU_GOMU_FRUIT.get()))
                    .displayItems((params, output) -> {
                        // Devil Fruits
                        output.accept(ModItems.GOMU_GOMU_FRUIT.get());
                        output.accept(ModItems.MERA_MERA_FRUIT.get());
                        output.accept(ModItems.HIE_HIE_FRUIT.get());
                        output.accept(ModItems.GURA_GURA_FRUIT.get());
                        output.accept(ModItems.OPE_OPE_FRUIT.get());
                        output.accept(ModItems.YAMI_YAMI_FRUIT.get());
                        output.accept(ModItems.PIKA_PIKA_FRUIT.get());
                        output.accept(ModItems.SUNA_SUNA_FRUIT.get());
                        output.accept(ModItems.GORO_GORO_FRUIT.get());
                        output.accept(ModItems.TORI_TORI_FRUIT.get());
                        // Swords
                        output.accept(ModItems.KATANA.get());
                        output.accept(ModItems.WADO_ICHIMONJI.get());
                        output.accept(ModItems.SANDAI_KITETSU.get());
                        output.accept(ModItems.SHUSUI.get());
                        output.accept(ModItems.NIDAI_KITETSU.get());
                        output.accept(ModItems.BISENTO.get());
                        output.accept(ModItems.YORU.get());
                        // Materials
                        output.accept(ModItems.FOOLS_GOLD_NUGGET.get());
                        output.accept(ModItems.FOOLS_GOLD_INGOT.get());
                        output.accept(ModBlocks.FOOLS_GOLD_ORE.get());
                        output.accept(ModBlocks.DEEPSLATE_FOOLS_GOLD_ORE.get());
                    })
                    .build());
}
