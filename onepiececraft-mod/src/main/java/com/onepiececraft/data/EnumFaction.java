package com.onepiececraft.data;

public enum EnumFaction {
    NONE("None"),
    PIRATE("Pirate"),
    MARINE("Marine");

    private final String displayName;

    EnumFaction(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
