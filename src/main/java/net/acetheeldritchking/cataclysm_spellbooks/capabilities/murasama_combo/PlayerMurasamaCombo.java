package net.acetheeldritchking.cataclysm_spellbooks.capabilities.murasama_combo;

import net.minecraft.nbt.CompoundTag;

public class PlayerMurasamaCombo {
    private int murasamaCombo;
    private final int MAX_MURA_COMBO = 5;
    private final int MIN_MURA_COMBO = 0;

    public int getMuraCombo()
    {
        return murasamaCombo;
    }

    public int setMuraCombo(int set)
    {
        this.murasamaCombo = set;
        return murasamaCombo;
    }

    public void resetMuraCombo()
    {
        this.murasamaCombo = 0;
    }

    public void addMuraCombo(int add)
    {
        this.murasamaCombo = Math.min(murasamaCombo + add, MAX_MURA_COMBO);
    }

    public void subMuraCombo(int sub)
    {
        this.murasamaCombo = Math.max(murasamaCombo - sub, MIN_MURA_COMBO);
    }

    public void copyFrom(PlayerMurasamaCombo source)
    {
        this.murasamaCombo = source.murasamaCombo;
    }

    public void saveNBTdata(CompoundTag nbt)
    {
        nbt.putInt("murasama_combo", murasamaCombo);
    }

    public void loadNBTdata(CompoundTag nbt)
    {
        murasamaCombo = nbt.getInt("murasama_combo");
    }
}
