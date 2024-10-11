package net.acetheeldritchking.cataclysm_spellbooks.util;

// This isn't really used since Cataclysm already added stuff for addons adding damage parameters
// But I don't want to trash this yet so ig it stays here
public interface IExtendedCataclysmProjectileInterface {
    boolean isFromSpell();
    void setFromSpell(boolean bool);
}
