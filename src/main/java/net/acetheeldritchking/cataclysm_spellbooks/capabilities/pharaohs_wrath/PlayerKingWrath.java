package net.acetheeldritchking.cataclysm_spellbooks.capabilities.pharaohs_wrath;

import net.minecraft.nbt.CompoundTag;

public class PlayerKingWrath {
    private int kingsWrath;
    private final int MAX_WRATH = 4;
    private final int MIN_WRATH = 0;

    public int getWrath()
    {
        return kingsWrath;
    }

    public int setWrath(int set)
    {
        this.kingsWrath = set;
        return kingsWrath;
    }

    public void resetWrath()
    {
        this.kingsWrath = 0;
    }

    public void addWrath(int add)
    {
        this.kingsWrath = Math.min(kingsWrath + add, MAX_WRATH);
    }

    public void subWrath(int sub)
    {
        this.kingsWrath = Math.max(kingsWrath - sub, MIN_WRATH);
    }

    public void copyFrom(PlayerKingWrath source)
    {
        this.kingsWrath = source.kingsWrath;
    }

    public void saveNBTdata(CompoundTag nbt)
    {
        nbt.putInt("kings_wrath", kingsWrath);
    }

    public void loadNBTdata(CompoundTag nbt)
    {
        kingsWrath = nbt.getInt("kings_wrath");
    }
}
