package com.onepiececraft.data;

public enum EnumDevilFruit {
    NONE("None", FruitType.NONE, 0),
    GOMU_GOMU("Gomu Gomu no Mi", FruitType.PARAMECIA, 4),
    MERA_MERA("Mera Mera no Mi", FruitType.LOGIA, 5),
    HIE_HIE("Hie Hie no Mi", FruitType.LOGIA, 5),
    GURA_GURA("Gura Gura no Mi", FruitType.PARAMECIA, 6),
    OPE_OPE("Ope Ope no Mi", FruitType.PARAMECIA, 5),
    YAMI_YAMI("Yami Yami no Mi", FruitType.LOGIA, 6),
    PIKA_PIKA("Pika Pika no Mi", FruitType.LOGIA, 5),
    SUNA_SUNA("Suna Suna no Mi", FruitType.LOGIA, 4),
    GORO_GORO("Goro Goro no Mi", FruitType.LOGIA, 5),
    TORI_TORI("Tori Tori no Mi (Phoenix)", FruitType.ZOAN, 4);

    public enum FruitType { NONE, PARAMECIA, LOGIA, ZOAN }

    private final String displayName;
    private final FruitType fruitType;
    private final int attackCount;

    EnumDevilFruit(String displayName, FruitType fruitType, int attackCount) {
        this.displayName = displayName;
        this.fruitType = fruitType;
        this.attackCount = attackCount;
    }

    public String getDisplayName() { return displayName; }
    public FruitType getFruitType() { return fruitType; }
    public int getAttackCount() { return attackCount; }
}
