package com.onepiececraft.event;

import com.onepiececraft.init.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

/**
 * Injects Devil Fruits into vanilla loot tables (5% chance per chest loot).
 */
public class ModLootModifier extends LootModifier {
    public ModLootModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextFloat() < 0.05f) {
            // Pick random fruit
            var fruits = new net.minecraft.world.item.Item[]{
                    ModItems.GOMU_GOMU_FRUIT.get(), ModItems.MERA_MERA_FRUIT.get(),
                    ModItems.HIE_HIE_FRUIT.get(), ModItems.GURA_GURA_FRUIT.get(),
                    ModItems.OPE_OPE_FRUIT.get(), ModItems.YAMI_YAMI_FRUIT.get(),
                    ModItems.PIKA_PIKA_FRUIT.get(), ModItems.SUNA_SUNA_FRUIT.get(),
                    ModItems.GORO_GORO_FRUIT.get(), ModItems.TORI_TORI_FRUIT.get()
            };
            int idx = context.getRandom().nextInt(fruits.length);
            generatedLoot.add(new ItemStack(fruits[idx]));
        }
        return generatedLoot;
    }

    @Override
    public com.mojang.serialization.Codec<? extends IGlobalLootModifier> codec() {
        return ModLootModifiers.FRUIT_INJECTOR.get();
    }
}
