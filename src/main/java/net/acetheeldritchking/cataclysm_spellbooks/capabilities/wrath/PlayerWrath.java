package net.acetheeldritchking.cataclysm_spellbooks.capabilities.wrath;

import net.minecraft.nbt.CompoundTag;

public class PlayerWrath {
    private int wrath;
    private final int MAX_WRATH = 4;
    private final int MIN_WRATH = 0;

    public int getWrath()
    {
        return wrath;
    }

    public int setWrath(int set)
    {
        this.wrath = set;
        return wrath;
    }

    public void resetWrath()
    {
        this.wrath = 0;
    }

    public void addWrath(int add)
    {
        this.wrath = Math.min(wrath + add, MAX_WRATH);
    }

    public void subWrath(int sub)
    {
        this.wrath = Math.max(wrath - sub, MIN_WRATH);
    }

    public void copyFrom(PlayerWrath source)
    {
        this.wrath = source.wrath;
    }

    public void saveNBTdata(CompoundTag nbt)
    {
        nbt.putInt("wrath", wrath);
    }

    public void loadNBTdata(CompoundTag nbt)
    {
        wrath = nbt.getInt("wrath");
    }
}
