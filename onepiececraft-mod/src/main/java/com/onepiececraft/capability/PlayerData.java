package com.onepiececraft.capability;

import com.onepiececraft.data.EnumDevilFruit;
import com.onepiececraft.data.EnumFaction;
import com.onepiececraft.data.EnumRace;
import net.minecraft.nbt.CompoundTag;

public class PlayerData implements IPlayerData {
    private EnumFaction faction = EnumFaction.NONE;
    private EnumRace race = EnumRace.HUMAN;
    private EnumDevilFruit devilFruit = EnumDevilFruit.NONE;
    private int level = 1;
    private int xp = 0;
    private int stamina = 100;
    private int maxStamina = 100;
    private long bounty = 0;
    private long honor = 0;
    private boolean raceInitialized = false;
    private boolean factionChosen = false;
    private int ability1Cooldown = 0;
    private int ability2Cooldown = 0;
    private int[] fruitAttackCooldowns = new int[6];

    @Override public EnumFaction getFaction() { return faction; }
    @Override public void setFaction(EnumFaction f) { this.faction = f; }
    @Override public EnumRace getRace() { return race; }
    @Override public void setRace(EnumRace r) { this.race = r; }
    @Override public EnumDevilFruit getDevilFruit() { return devilFruit; }
    @Override public void setDevilFruit(EnumDevilFruit d) { this.devilFruit = d; }
    @Override public int getLevel() { return level; }
    @Override public void setLevel(int l) { this.level = Math.max(1, Math.min(100, l)); }
    @Override public int getXp() { return xp; }
    @Override public void setXp(int x) { this.xp = Math.max(0, x); }
    @Override public int getStamina() { return stamina; }
    @Override public void setStamina(int s) { this.stamina = Math.max(0, Math.min(maxStamina, s)); }
    @Override public int getMaxStamina() { return maxStamina; }
    @Override public void setMaxStamina(int m) { this.maxStamina = Math.max(10, m); }
    @Override public long getBounty() { return bounty; }
    @Override public void setBounty(long b) { this.bounty = Math.max(0, b); }
    @Override public long getHonor() { return honor; }
    @Override public void setHonor(long h) { this.honor = Math.max(0, h); }
    @Override public boolean isRaceInitialized() { return raceInitialized; }
    @Override public void setRaceInitialized(boolean v) { this.raceInitialized = v; }
    @Override public boolean isFactionChosen() { return factionChosen; }
    @Override public void setFactionChosen(boolean v) { this.factionChosen = v; }
    @Override public boolean isAbility1Unlocked() { return level >= 5; }
    @Override public boolean isAbility2Unlocked() { return level >= 35; }
    @Override public int getAbility1Cooldown() { return ability1Cooldown; }
    @Override public void setAbility1Cooldown(int t) { this.ability1Cooldown = Math.max(0, t); }
    @Override public int getAbility2Cooldown() { return ability2Cooldown; }
    @Override public void setAbility2Cooldown(int t) { this.ability2Cooldown = Math.max(0, t); }
    @Override public int[] getFruitAttackCooldowns() { return fruitAttackCooldowns; }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("faction", faction.name());
        tag.putString("race", race.name());
        tag.putString("devilFruit", devilFruit.name());
        tag.putInt("level", level);
        tag.putInt("xp", xp);
        tag.putInt("stamina", stamina);
        tag.putInt("maxStamina", maxStamina);
        tag.putLong("bounty", bounty);
        tag.putLong("honor", honor);
        tag.putBoolean("raceInit", raceInitialized);
        tag.putBoolean("factionChosen", factionChosen);
        tag.putInt("ab1cd", ability1Cooldown);
        tag.putInt("ab2cd", ability2Cooldown);
        int[] cds = fruitAttackCooldowns;
        for (int i = 0; i < cds.length; i++) tag.putInt("fatk" + i, cds[i]);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        try { faction = EnumFaction.valueOf(tag.getString("faction")); } catch (Exception e) { faction = EnumFaction.NONE; }
        try { race = EnumRace.valueOf(tag.getString("race")); } catch (Exception e) { race = EnumRace.HUMAN; }
        try { devilFruit = EnumDevilFruit.valueOf(tag.getString("devilFruit")); } catch (Exception e) { devilFruit = EnumDevilFruit.NONE; }
        level = tag.getInt("level");
        xp = tag.getInt("xp");
        stamina = tag.getInt("stamina");
        maxStamina = tag.getInt("maxStamina");
        bounty = tag.getLong("bounty");
        honor = tag.getLong("honor");
        raceInitialized = tag.getBoolean("raceInit");
        factionChosen = tag.getBoolean("factionChosen");
        ability1Cooldown = tag.getInt("ab1cd");
        ability2Cooldown = tag.getInt("ab2cd");
        for (int i = 0; i < fruitAttackCooldowns.length; i++) fruitAttackCooldowns[i] = tag.getInt("fatk" + i);
        if (level < 1) level = 1;
        if (maxStamina < 10) maxStamina = 100;
    }
}
