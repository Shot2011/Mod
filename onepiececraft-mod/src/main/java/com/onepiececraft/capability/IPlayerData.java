package com.onepiececraft.capability;

import com.onepiececraft.data.EnumDevilFruit;
import com.onepiececraft.data.EnumFaction;
import com.onepiececraft.data.EnumRace;
import net.minecraft.nbt.CompoundTag;

public interface IPlayerData {
    EnumFaction getFaction();
    void setFaction(EnumFaction faction);

    EnumRace getRace();
    void setRace(EnumRace race);

    EnumDevilFruit getDevilFruit();
    void setDevilFruit(EnumDevilFruit fruit);

    int getLevel();
    void setLevel(int level);

    int getXp();
    void setXp(int xp);

    int getStamina();
    void setStamina(int stamina);

    int getMaxStamina();
    void setMaxStamina(int maxStamina);

    long getBounty();
    void setBounty(long bounty);

    long getHonor();
    void setHonor(long honor);

    boolean isRaceInitialized();
    void setRaceInitialized(boolean value);

    boolean isFactionChosen();
    void setFactionChosen(boolean value);

    boolean isAbility1Unlocked();
    boolean isAbility2Unlocked();

    int getAbility1Cooldown();
    void setAbility1Cooldown(int ticks);

    int getAbility2Cooldown();
    void setAbility2Cooldown(int ticks);

    int[] getFruitAttackCooldowns();

    CompoundTag serializeNBT();
    void deserializeNBT(CompoundTag tag);
}
