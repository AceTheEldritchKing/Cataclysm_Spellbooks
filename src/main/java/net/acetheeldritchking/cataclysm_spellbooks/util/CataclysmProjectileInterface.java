package net.acetheeldritchking.cataclysm_spellbooks.util;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;

public interface CataclysmProjectileInterface {
    default boolean isFromSpell(AbstractSpell spell)
    {
        spell.getSpellId();
        {
            return true;
        }
    }
}
