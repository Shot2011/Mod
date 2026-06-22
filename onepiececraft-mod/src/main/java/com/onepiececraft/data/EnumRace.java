package com.onepiececraft.data;

public enum EnumRace {
    HUMAN("Human", "R: Resolve (temp. resistance)", "F: Infinite Potential (+10% all stats)"),
    FISHMAN("Fishman", "R: Aquatic Strike (water dmg)", "F: Fishman Karate (AOE water)"),
    MINK("Mink", "R: Electro Touch (lightning dmg)", "F: Sulong (lunar form, huge stats)"),
    LUNARIAN("Lunarian", "R: Fire Wings (short flight)", "F: Brief Immortality (invincible 5s)"),
    GIANT("Giant", "R: Colossal Strike (AOE stomp)", "F: Giant Form (size up, +50% HP)"),
    ANCIENT_ZOAN("Ancient Zoan", "R: Partial Beast Form (+dmg)", "F: Full Beast Form (transform)");

    private final String displayName;
    private final String ability1Desc;
    private final String ability2Desc;

    EnumRace(String displayName, String ability1Desc, String ability2Desc) {
        this.displayName = displayName;
        this.ability1Desc = ability1Desc;
        this.ability2Desc = ability2Desc;
    }

    public String getDisplayName() { return displayName; }
    public String getAbility1Desc() { return ability1Desc; }
    public String getAbility2Desc() { return ability2Desc; }

    public static EnumRace random() {
        EnumRace[] values = values();
        return values[(int)(Math.random() * values.length)];
    }
}
