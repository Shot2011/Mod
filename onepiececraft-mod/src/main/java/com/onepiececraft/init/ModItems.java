package com.onepiececraft.init;

import com.onepiececraft.OnePieceCraft;
import com.onepiececraft.data.EnumDevilFruit;
import com.onepiececraft.item.DevilFruitItem;
import com.onepiececraft.item.OPCSwordItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, OnePieceCraft.MOD_ID);

    // Devil Fruits
    public static final RegistryObject<Item> GOMU_GOMU_FRUIT = ITEMS.register("gomu_gomu_fruit",
            () -> new DevilFruitItem(EnumDevilFruit.GOMU_GOMU, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> MERA_MERA_FRUIT = ITEMS.register("mera_mera_fruit",
            () -> new DevilFruitItem(EnumDevilFruit.MERA_MERA, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> HIE_HIE_FRUIT = ITEMS.register("hie_hie_fruit",
            () -> new DevilFruitItem(EnumDevilFruit.HIE_HIE, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GURA_GURA_FRUIT = ITEMS.register("gura_gura_fruit",
            () -> new DevilFruitItem(EnumDevilFruit.GURA_GURA, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> OPE_OPE_FRUIT = ITEMS.register("ope_ope_fruit",
            () -> new DevilFruitItem(EnumDevilFruit.OPE_OPE, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> YAMI_YAMI_FRUIT = ITEMS.register("yami_yami_fruit",
            () -> new DevilFruitItem(EnumDevilFruit.YAMI_YAMI, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PIKA_PIKA_FRUIT = ITEMS.register("pika_pika_fruit",
            () -> new DevilFruitItem(EnumDevilFruit.PIKA_PIKA, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SUNA_SUNA_FRUIT = ITEMS.register("suna_suna_fruit",
            () -> new DevilFruitItem(EnumDevilFruit.SUNA_SUNA, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GORO_GORO_FRUIT = ITEMS.register("goro_goro_fruit",
            () -> new DevilFruitItem(EnumDevilFruit.GORO_GORO, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> TORI_TORI_FRUIT = ITEMS.register("tori_tori_fruit",
            () -> new DevilFruitItem(EnumDevilFruit.TORI_TORI, new Item.Properties().stacksTo(1)));

    // Weapons - Swords
    public static final RegistryObject<Item> KATANA = ITEMS.register("katana",
            () -> new OPCSwordItem(Tiers.IRON, 4, -2.2f, "Katana", new Item.Properties()));
    public static final RegistryObject<Item> WADO_ICHIMONJI = ITEMS.register("wado_ichimonji",
            () -> new OPCSwordItem(Tiers.DIAMOND, 6, -2.1f, "Wado Ichimonji", new Item.Properties()));
    public static final RegistryObject<Item> SANDAI_KITETSU = ITEMS.register("sandai_kitetsu",
            () -> new OPCSwordItem(Tiers.DIAMOND, 7, -2.0f, "Sandai Kitetsu", new Item.Properties()));
    public static final RegistryObject<Item> SHUSUI = ITEMS.register("shusui",
            () -> new OPCSwordItem(Tiers.NETHERITE, 8, -2.0f, "Shusui", new Item.Properties()));
    public static final RegistryObject<Item> NIDAI_KITETSU = ITEMS.register("nidai_kitetsu",
            () -> new OPCSwordItem(Tiers.NETHERITE, 9, -2.0f, "Nidai Kitetsu", new Item.Properties()));
    public static final RegistryObject<Item> BISENTO = ITEMS.register("bisento",
            () -> new OPCSwordItem(Tiers.NETHERITE, 10, -2.4f, "Bisento", new Item.Properties()));
    public static final RegistryObject<Item> YORU = ITEMS.register("yoru",
            () -> new OPCSwordItem(Tiers.NETHERITE, 14, -2.0f, "Yoru - Black Blade", new Item.Properties()));

    // Fool's Gold Ore item
    public static final RegistryObject<Item> FOOLS_GOLD_NUGGET = ITEMS.register("fools_gold_nugget",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FOOLS_GOLD_INGOT = ITEMS.register("fools_gold_ingot",
            () -> new Item(new Item.Properties()));
}
